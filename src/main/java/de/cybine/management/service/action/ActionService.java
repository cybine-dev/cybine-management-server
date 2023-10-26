package de.cybine.management.service.action;

import de.cybine.management.data.action.context.*;
import de.cybine.management.data.action.metadata.*;
import de.cybine.management.data.action.process.*;
import de.cybine.management.exception.action.*;
import de.cybine.management.util.converter.*;
import de.cybine.management.util.datasource.*;
import io.quarkus.runtime.*;
import jakarta.enterprise.context.*;
import jakarta.ws.rs.core.*;
import lombok.*;
import org.hibernate.*;

import java.time.*;
import java.util.*;

@Startup
@ApplicationScoped
@RequiredArgsConstructor
public class ActionService
{
    private final ConverterRegistry       converterRegistry;
    private final ActionProcessorRegistry processorRegistry;

    private final ContextService  contextService;
    private final ProcessService  processService;
    private final MetadataService metadataService;

    private final SessionFactory sessionFactory;

    private final SecurityContext securityContext;

    public ActionContext createContext(ActionContextMetadata contextMetadata)
    {
        ActionMetadata metadata = this.fetchMetadata(contextMetadata).orElse(null);
        if (metadata == null)
            throw new NoSuchElementException("unknown metadata");

        if (contextMetadata.getItemId().isPresent() && !this.fetchActiveContexts(contextMetadata).isEmpty())
            throw new AmbiguousActionException(
                    String.format("Action ns(%s) cat(%s) name(%s) already active for item-id %s",
                            contextMetadata.getNamespace(), contextMetadata.getCategory(), contextMetadata.getName(),
                            contextMetadata.getItemId().get()));

        ConverterTree converterTree = ConverterTree.create();
        ActionContext context = ActionContext.builder()
                                             .metadata(metadata)
                                             .itemId(contextMetadata.getItemId().orElse(null))
                                             .build();

        ActionContextEntity entity = this.converterRegistry.getProcessor(ActionContext.class, ActionContextEntity.class,
                converterTree).toItem(context).result();

        try (Session session = this.sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            transaction.begin();

            session.persist(entity);

            ActionContextId contextId = ActionContextId.of(entity.getId());
            ActionProcess process = ActionProcess.builder()
                                                 .contextId(contextId)
                                                 .context(ActionContext.builder().id(contextId).build())
                                                 .status(BaseActionProcessStatus.INITIALIZED.getName())
                                                 .creatorId(this.securityContext.getUserPrincipal() != null ?
                                                         this.securityContext.getUserPrincipal().getName() : null)
                                                 .createdAt(ZonedDateTime.now())
                                                 .build();

            session.persist(this.converterRegistry.getProcessor(ActionProcess.class, ActionProcessEntity.class)
                                                  .toItem(process)
                                                  .result());

            transaction.commit();
        }

        return this.converterRegistry.getProcessor(ActionContextEntity.class, ActionContext.class)
                                     .toItem(entity)
                                     .result();
    }

    public void updateItemId(ActionContextId contextId, String itemId)
    {
        try (Session session = this.sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();

            ActionContextEntity entity = session.byId(ActionContextEntity.class).load(contextId.getValue());
            if (entity.getItemId().isPresent())
                throw new ActionProcessingException("Cannot modify item_id of context when already set");

            entity.setItemId(itemId);

            transaction.commit();
        }
    }

    public <T> ActionProcessorResult<T> terminateContext(ActionContextId contextId)
    {
        return this.process(ActionProcessMetadata.builder()
                                                 .contextId(contextId)
                                                 .status(BaseActionProcessStatus.TERMINATED.getName())
                                                 .createdAt(ZonedDateTime.now())
                                                 .build());
    }

    @SuppressWarnings("unchecked")
    public <T> T process(ActionProcessMetadata nextState)
    {
        try (Session session = this.sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();

            ActionProcess state = this.fetchCurrentState(nextState.getContextId()).orElse(null);
            if (state == null)
                throw new UnknownActionStateException(
                        "Could not find action-state for context-id " + nextState.getContextId().getValue());

            ActionContext context = this.contextService.fetchById(nextState.getContextId()).orElseThrow();
            ActionMetadata metadata = this.metadataService.fetchById(context.getMetadataId()).orElseThrow();
            ActionProcessorMetadata processorMetadata = ActionProcessorMetadata.builder()
                                                                               .namespace(metadata.getNamespace())
                                                                               .category(metadata.getCategory())
                                                                               .name(metadata.getName())
                                                                               .fromStatus(state.getStatus())
                                                                               .toStatus(nextState.getStatus())
                                                                               .build();

            ActionProcessor<?> processor = this.processorRegistry.findProcessor(processorMetadata).orElse(null);
            if (processor == null)
                throw new UnknownActionException("Unknown action " + processorMetadata.asString());

            if (!processor.shouldExecute(this, nextState))
                throw new ActionPreconditionException(null);

            ActionContextId contextId = ActionContextId.of(nextState.getContextId().getValue());
            String creator = this.securityContext.getUserPrincipal() != null ?
                    this.securityContext.getUserPrincipal().getName() : null;

            ActionProcess process = ActionProcess.builder()
                                                 .contextId(contextId)
                                                 .context(ActionContext.builder().id(contextId).build())
                                                 .status(nextState.getStatus())
                                                 .priority(nextState.getPriority().orElse(100))
                                                 .description(nextState.getDescription().orElse(null))
                                                 .creatorId(creator)
                                                 .createdAt(nextState.getCreatedAt())
                                                 .dueAt(nextState.getDueAt().orElse(null))
                                                 .data(nextState.getData().map(ActionData::data).orElse(null))
                                                 .build();

            session.persist(this.converterRegistry.getProcessor(ActionProcess.class, ActionProcessEntity.class)
                                                  .toItem(process)
                                                  .result());

            T result = (T) processor.process(this, state);

            transaction.commit();

            return result;
        }
    }

    public Optional<ActionMetadata> fetchMetadata(ActionContextMetadata context)
    {
        DatasourceConditionDetail<String> namespaceEquals = DatasourceHelper.isEqual(ActionMetadataEntity_.NAMESPACE,
                context.getNamespace());

        DatasourceConditionDetail<String> categoryEquals = DatasourceHelper.isEqual(ActionMetadataEntity_.CATEGORY,
                context.getCategory());

        DatasourceConditionDetail<String> nameEquals = DatasourceHelper.isEqual(ActionMetadataEntity_.NAME,
                context.getName());

        DatasourceConditionInfo condition = DatasourceHelper.and(namespaceEquals, categoryEquals, nameEquals);

        DatasourceQueryInterpreter<ActionMetadataEntity> interpreter = DatasourceQueryInterpreter.of(
                ActionMetadataEntity.class, DatasourceQuery.builder().condition(condition).build());

        ConversionProcessor<ActionMetadataEntity, ActionMetadata> processor = this.converterRegistry.getProcessor(
                ActionMetadataEntity.class, ActionMetadata.class);

        return interpreter.prepareDataQuery()
                          .getResultStream()
                          .findAny()
                          .map(processor::toItem)
                          .map(ConversionResult::result);
    }

    public Set<ActionContext> fetchActiveContexts(ActionContextMetadata context)
    {
        DatasourceConditionDetail<String> namespaceEquals = DatasourceHelper.isEqual(ActionMetadataEntity_.NAMESPACE,
                context.getNamespace());

        DatasourceConditionDetail<String> categoryEquals = DatasourceHelper.isEqual(ActionMetadataEntity_.CATEGORY,
                context.getCategory());

        DatasourceConditionDetail<String> nameEquals = DatasourceHelper.isEqual(ActionMetadataEntity_.NAME,
                context.getName());

        DatasourceConditionInfo metadataCondition = DatasourceHelper.and(namespaceEquals, categoryEquals, nameEquals);

        DatasourceConditionDetail<Void> noItemId = DatasourceHelper.isNull(ActionContextEntity_.ITEM_ID);
        DatasourceConditionDetail<String> itemIdEquals = DatasourceHelper.isEqual(ActionContextEntity_.ITEM_ID,
                context.getItemId().orElse(null));

        DatasourceConditionInfo condition = DatasourceHelper.and(
                context.getItemId().isPresent() ? itemIdEquals : noItemId);

        DatasourceConditionDetail<String> notTerminated = DatasourceHelper.isNotPresent(ActionProcessEntity_.STATUS,
                BaseActionProcessStatus.TERMINATED.getName());

        DatasourceRelationInfo metadataRelation = DatasourceRelationInfo.builder()
                                                                        .property(
                                                                                ActionContextEntity_.METADATA.getName())
                                                                        .condition(metadataCondition)
                                                                        .build();

        DatasourceRelationInfo processRelation = DatasourceRelationInfo.builder()
                                                                       .property(
                                                                               ActionContextEntity_.PROCESSES.getName())
                                                                       .condition(DatasourceHelper.and(notTerminated))
                                                                       .build();

        DatasourceQueryInterpreter<ActionContextEntity> interpreter = DatasourceQueryInterpreter.of(
                ActionContextEntity.class, DatasourceQuery.builder()
                                                          .condition(condition)
                                                          .relation(metadataRelation)
                                                          .relation(processRelation)
                                                          .build());

        return this.converterRegistry.getProcessor(ActionContextEntity.class, ActionContext.class)
                                     .toSet(interpreter.prepareDataQuery().getResultList())
                                     .result();
    }

    public Set<ActionContext> fetchContexts(ActionContextMetadata context)
    {
        DatasourceConditionDetail<String> namespaceEquals = DatasourceHelper.isEqual(ActionMetadataEntity_.NAMESPACE,
                context.getNamespace());

        DatasourceConditionDetail<String> categoryEquals = DatasourceHelper.isEqual(ActionMetadataEntity_.CATEGORY,
                context.getCategory());

        DatasourceConditionDetail<String> nameEquals = DatasourceHelper.isEqual(ActionMetadataEntity_.NAME,
                context.getName());

        DatasourceConditionInfo metadataCondition = DatasourceHelper.and(namespaceEquals, categoryEquals, nameEquals);

        DatasourceConditionDetail<Void> noItemId = DatasourceHelper.isNull(ActionContextEntity_.ITEM_ID);
        DatasourceConditionDetail<String> itemIdEquals = DatasourceHelper.isEqual(ActionContextEntity_.ITEM_ID,
                context.getItemId().orElse(null));

        DatasourceConditionInfo condition = DatasourceHelper.and(
                context.getItemId().isPresent() ? itemIdEquals : noItemId);

        DatasourceRelationInfo metadataRelation = DatasourceRelationInfo.builder()
                                                                        .property(
                                                                                ActionContextEntity_.METADATA.getName())
                                                                        .fetch(true)
                                                                        .condition(metadataCondition)
                                                                        .build();

        DatasourceQueryInterpreter<ActionContextEntity> interpreter = DatasourceQueryInterpreter.of(
                ActionContextEntity.class, DatasourceQuery.builder()
                                                          .condition(condition)
                                                          .relation(metadataRelation)
                                                          .relation(DatasourceHelper.fetch(
                                                                  ActionContextEntity_.PROCESSES))
                                                          .build());

        return this.converterRegistry.getProcessor(ActionContextEntity.class, ActionContext.class)
                                     .toSet(interpreter.prepareDataQuery().getResultList())
                                     .result();
    }

    public Optional<ActionProcess> fetchCurrentState(ActionContextId contextId)
    {
        DatasourceConditionDetail<Long> contextIdEquals = DatasourceHelper.isEqual(ActionProcessEntity_.CONTEXT_ID,
                contextId.getValue());

        DatasourceConditionInfo condition = DatasourceHelper.and(contextIdEquals);

        DatasourceQueryInterpreter<ActionProcessEntity> interpreter = DatasourceQueryInterpreter.of(
                ActionProcessEntity.class, DatasourceQuery.builder()
                                                          .condition(condition)
                                                          .order(DatasourceHelper.desc(ActionProcessEntity_.ID))
                                                          .build());

        ConversionProcessor<ActionProcessEntity, ActionProcess> processor = this.converterRegistry.getProcessor(
                ActionProcessEntity.class, ActionProcess.class);

        return interpreter.prepareDataQuery()
                          .getResultStream()
                          .findAny()
                          .map(processor::toItem)
                          .map(ConversionResult::result);
    }
}
