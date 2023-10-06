package de.cybine.management.util.api.converter;

import de.cybine.management.exception.*;
import de.cybine.management.util.api.datasource.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.converter.*;

public class ApiCountRelationConverter implements Converter<ApiCountRelationInfo, DatasourceRelationInfo>
{
    @Override
    public Class<ApiCountRelationInfo> getInputType( )
    {
        return ApiCountRelationInfo.class;
    }

    @Override
    public Class<DatasourceRelationInfo> getOutputType( )
    {
        return DatasourceRelationInfo.class;
    }

    @Override
    public DatasourceRelationInfo convert(ApiCountRelationInfo input, ConversionHelper helper)
    {
        DatasourceFieldPath path = ApiQueryConverter.getFieldPathOrThrow(helper, input.getProperty());
        if (path.getLength() > 1)
            throw new UnknownRelationException(
                    String.format("Cannot traverse multiple elements while resolving relations (%s)",
                            input.getProperty()));

        helper.withContext(ApiQueryConverter.DATA_TYPE_PROPERTY, path.getLast().getFieldType());

        return DatasourceRelationInfo.builder()
                                     .property(path.asString())
                                     .groupingProperties(input.getGroupingProperties()
                                                              .stream()
                                                              .map(item -> ApiQueryConverter.getFieldPathOrThrow(helper,
                                                                      item))
                                                              .map(DatasourceFieldPath::asString)
                                                              .toList())
                                     .condition(helper.toItem(ApiConditionInfo.class, DatasourceConditionInfo.class)
                                                      .map(input::getCondition))
                                     .build();
    }
}
