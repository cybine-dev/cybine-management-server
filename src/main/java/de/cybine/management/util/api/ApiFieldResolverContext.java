package de.cybine.management.util.api;

import de.cybine.management.util.*;
import de.cybine.management.util.datasource.*;
import lombok.*;
import lombok.extern.slf4j.*;

import java.lang.reflect.*;
import java.util.*;

@Data
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class ApiFieldResolverContext
{
    private final String contextName;

    @Getter(AccessLevel.NONE)
    private final Map<BiTuple<Type, String>, DatasourceField> fields = new HashMap<>();

    @Getter(AccessLevel.NONE)
    private final Map<Type, Type> representationTypes = new HashMap<>();

    public ApiFieldResolverContext registerField(Type dataType, String alias, DatasourceField field)
    {
        log.debug("Registering api-field: [{}] {}({})", this.contextName, dataType.getTypeName(), alias);

        this.fields.put(new BiTuple<>(this.findRepresentationType(dataType).orElse(dataType), alias), field);
        return this;
    }

    public ApiFieldResolverContextHelper registerTypeRepresentation(Type representationType, Type datasourceType)
    {
        this.representationTypes.put(representationType, datasourceType);
        return this.getTypeRepresentationHelper(representationType);
    }

    public ApiFieldResolverContextHelper getTypeRepresentationHelper(Type representationType)
    {
        return new ApiFieldResolverContextHelper(this, representationType);
    }

    public Optional<Type> findRepresentationType(Type type)
    {
        return Optional.ofNullable(this.representationTypes.get(type));
    }

    public Optional<DatasourceFieldPath> findField(Type responseType, String fieldName)
    {
        if (fieldName == null || fieldName.isBlank())
            return Optional.empty();

        DatasourceField field = null;
        String[] names = fieldName.split("\\.");
        DatasourceFieldPath.Generator path = DatasourceFieldPath.builder();
        for (String name : names)
        {
            BiTuple<Type, String> fieldDefinition = new BiTuple<>(responseType, name);
            if (field != null)
                fieldDefinition = new BiTuple<>(field.getFieldType(), name);

            field = this.fields.get(fieldDefinition);
            if (field == null)
                return Optional.empty();

            path.field(field);
        }

        return Optional.of(path.build());
    }
}
