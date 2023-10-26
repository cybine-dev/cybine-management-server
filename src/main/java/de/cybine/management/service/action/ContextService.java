package de.cybine.management.service.action;

import de.cybine.management.data.action.context.*;
import de.cybine.management.util.api.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.datasource.*;
import io.quarkus.runtime.*;
import jakarta.annotation.*;
import jakarta.enterprise.context.*;
import lombok.*;

import java.util.*;

import static de.cybine.management.data.action.context.ActionContextEntity_.*;

@Startup
@ApplicationScoped
@RequiredArgsConstructor
public class ContextService
{
    private final GenericDatasourceService<ActionContextEntity, ActionContext> service =
            GenericDatasourceService.forType(
            ActionContextEntity.class, ActionContext.class);

    private final ApiFieldResolver resolver;

    @PostConstruct
    void setup( )
    {
        this.resolver.registerTypeRepresentation(ActionContext.class, ActionContextEntity.class)
                     .registerField("id", ID)
                     .registerField("metadata_id", METADATA_ID)
                     .registerField("metadata", METADATA)
                     .registerField("correlation_id", CORRELATION_ID)
                     .registerField("item_id", ITEM_ID)
                     .registerField("processes", PROCESSES);
    }

    public Optional<ActionContext> fetchById(ActionContextId id)
    {
        DatasourceConditionDetail<Long> idEquals = DatasourceHelper.isEqual(ID, id.getValue());
        DatasourceConditionInfo condition = DatasourceHelper.and(idEquals);

        return this.service.fetchSingle(DatasourceQuery.builder().condition(condition).build());
    }

    public Optional<ActionContext> fetchByCorrelationId(UUID correlationId)
    {
        DatasourceConditionDetail<String> correlationIdEquals = DatasourceHelper.isEqual(CORRELATION_ID,
                correlationId.toString());
        DatasourceConditionInfo condition = DatasourceHelper.and(correlationIdEquals);

        return this.service.fetchSingle(DatasourceQuery.builder().condition(condition).build());
    }

    public List<ActionContext> fetch(ApiQuery query)
    {
        return this.service.fetch(query);
    }

    public Optional<ActionContext> fetchSingle(ApiQuery query)
    {
        return this.service.fetchSingle(query);
    }

    public <O> List<O> fetchOptions(ApiOptionQuery query)
    {
        return this.service.fetchOptions(query);
    }

    public List<ApiCountInfo> fetchTotal(ApiCountQuery query)
    {
        return this.service.fetchTotal(query);
    }
}
