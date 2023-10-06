package de.cybine.management.util.api.converter;

import de.cybine.management.util.api.datasource.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.converter.*;

import java.util.*;

public class ApiConditionInfoConverter implements Converter<ApiConditionInfo, DatasourceConditionInfo>
{
    @Override
    public Class<ApiConditionInfo> getInputType( )
    {
        return ApiConditionInfo.class;
    }

    @Override
    public Class<DatasourceConditionInfo> getOutputType( )
    {
        return DatasourceConditionInfo.class;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public DatasourceConditionInfo convert(ApiConditionInfo input, ConversionHelper helper)
    {
        return DatasourceConditionInfo.builder()
                                      .type(input.getType())
                                      .isInverted(input.isInverted())
                                      .details((Collection) helper.toList(ApiConditionDetail.class,
                                              DatasourceConditionDetail.class).apply(input::getDetails))
                                      .subConditions(
                                              helper.toList(ApiConditionInfo.class, DatasourceConditionInfo.class)
                                                    .apply(input::getSubConditions))
                                      .build();
    }
}
