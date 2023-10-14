package de.cybine.management.util.api;

import de.cybine.management.util.datasource.*;
import lombok.*;

import java.lang.reflect.*;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ApiFieldResolverContextHelper
{
    private final ApiFieldResolverContext context;

    private final Type dataType;

    public ApiFieldResolverContextHelper registerField(String alias, DatasourceField field)
    {
        this.context.registerField(this.dataType, alias, field);
        return this;
    }
}
