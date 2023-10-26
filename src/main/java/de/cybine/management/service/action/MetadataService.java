package de.cybine.management.service.action;

import de.cybine.management.data.action.metadata.*;
import de.cybine.management.util.api.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.datasource.*;
import io.quarkus.runtime.*;
import jakarta.annotation.*;
import jakarta.enterprise.context.*;
import lombok.*;

import java.util.*;

import static de.cybine.management.data.action.context.ActionContextEntity_.ID;
import static de.cybine.management.data.action.metadata.ActionMetadataEntity_.*;

@Startup
@ApplicationScoped
@RequiredArgsConstructor
public class MetadataService
{
    private final GenericDatasourceService<ActionMetadataEntity, ActionMetadata> service =
            GenericDatasourceService.forType(
            ActionMetadataEntity.class, ActionMetadata.class);

    private final ApiFieldResolver resolver;

    @PostConstruct
    void setup( )
    {
        this.resolver.registerTypeRepresentation(ActionMetadata.class, ActionMetadataEntity.class)
                     .registerField("id", ID)
                     .registerField("namespace", NAMESPACE)
                     .registerField("category", CATEGORY)
                     .registerField("name", NAME)
                     .registerField("type", TYPE)
                     .registerField("contexts", CONTEXTS);
    }

    public Optional<ActionMetadata> fetchById(ActionMetadataId id)
    {
        DatasourceConditionDetail<Long> idEquals = DatasourceHelper.isEqual(ID, id.getValue());
        DatasourceConditionInfo condition = DatasourceHelper.and(idEquals);

        return this.service.fetchSingle(DatasourceQuery.builder().condition(condition).build());
    }

    public List<ActionMetadata> fetch(ApiQuery query)
    {
        return this.service.fetch(query);
    }

    public Optional<ActionMetadata> fetchSingle(ApiQuery query)
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
