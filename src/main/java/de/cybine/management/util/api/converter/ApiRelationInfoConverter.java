package de.cybine.management.util.api.converter;

import de.cybine.management.exception.*;
import de.cybine.management.util.api.datasource.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.converter.*;

import java.util.*;

public class ApiRelationInfoConverter implements Converter<ApiRelationInfo, DatasourceRelationInfo>
{
    @Override
    public Class<ApiRelationInfo> getInputType( )
    {
        return ApiRelationInfo.class;
    }

    @Override
    public Class<DatasourceRelationInfo> getOutputType( )
    {
        return DatasourceRelationInfo.class;
    }

    @Override
    public DatasourceRelationInfo convert(ApiRelationInfo input, ConversionHelper helper)
    {
        DatasourceFieldPath path = ApiQueryConverter.getFieldPathOrThrow(helper, input.getProperty());
        if (path.getLength() > 1)
            throw new UnknownRelationException(
                    String.format("Cannot traverse multiple elements while resolving relations (%s)",
                            input.getProperty()));

        return DatasourceRelationInfo.builder()
                                     .property(path.asString())
                                     .fetch(input.isFetch())
                                     .condition(helper.toItem(ApiConditionInfo.class, DatasourceConditionInfo.class)
                                                      .map(input::getCondition))
                                     .order(helper.toList(ApiOrderInfo.class, DatasourceOrderInfo.class)
                                                  .apply(input::getOrder))
                                     // Only allow one relation layer via the API
                                     .relations(Collections.emptyList())
                                     .build();
    }
}
