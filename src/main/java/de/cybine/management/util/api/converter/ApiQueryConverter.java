package de.cybine.management.util.api.converter;

import de.cybine.management.exception.*;
import de.cybine.management.util.api.*;
import de.cybine.management.util.api.datasource.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.converter.*;
import io.quarkus.arc.*;

import java.lang.reflect.*;

public class ApiQueryConverter implements Converter<ApiQuery, DatasourceQuery>
{
    public static final String CONTEXT_PROPERTY   = "context";
    public static final String DATA_TYPE_PROPERTY = "data-type";

    @Override
    public Class<ApiQuery> getInputType( )
    {
        return ApiQuery.class;
    }

    @Override
    public Class<DatasourceQuery> getOutputType( )
    {
        return DatasourceQuery.class;
    }

    @Override
    public DatasourceQuery convert(ApiQuery input, ConversionHelper helper)
    {
        return DatasourceQuery.builder()
                              .pagination(helper.toItem(ApiPaginationInfo.class, DatasourcePaginationInfo.class)
                                                .map(input::getPagination))
                              .condition(helper.toItem(ApiConditionInfo.class, DatasourceConditionInfo.class)
                                               .map(input::getCondition))
                              .order(helper.toList(ApiOrderInfo.class, DatasourceOrderInfo.class)
                                           .apply(input::getOrder))
                              .relations(helper.toList(ApiRelationInfo.class, DatasourceRelationInfo.class)
                                               .apply(input::getRelations))
                              .build();
    }

    static DatasourceFieldPath getFieldPathOrThrow(ConversionHelper helper, String name)
    {
        if (name == null)
            throw new UnknownRelationException("No field name specified");

        ApiFieldResolver resolver = Arc.container().select(ApiFieldResolver.class).get();

        String contextName = helper.getContextOrThrow(ApiQueryConverter.CONTEXT_PROPERTY);
        ServiceException unknownContextError = new UnknownApiContextException("Unable to find context").addData(
                ApiQueryConverter.CONTEXT_PROPERTY, contextName);

        ApiFieldResolverContext context = resolver.findContext(contextName).orElseThrow(( ) -> unknownContextError);

        Class<?> type = helper.getContextOrThrow(ApiQueryConverter.DATA_TYPE_PROPERTY);
        Type dataType = context.findRepresentationType(type).orElse(type);

        ServiceException unknownFieldError = new UnknownApiContextException("Unable to find field").addData("name",
                name).addData("type", dataType.getTypeName()).addData(ApiQueryConverter.CONTEXT_PROPERTY, contextName);

        return context.findField(dataType, name).orElseThrow(( ) -> unknownFieldError);
    }
}
