package de.cybine.management.util.api.converter;

import de.cybine.management.util.api.datasource.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.converter.*;

public class ApiPaginationInfoConverter implements Converter<ApiPaginationInfo, DatasourcePaginationInfo>
{
    @Override
    public Class<ApiPaginationInfo> getInputType( )
    {
        return ApiPaginationInfo.class;
    }

    @Override
    public Class<DatasourcePaginationInfo> getOutputType( )
    {
        return DatasourcePaginationInfo.class;
    }

    @Override
    public DatasourcePaginationInfo convert(ApiPaginationInfo input, ConversionHelper helper)
    {
        return DatasourcePaginationInfo.builder()
                                       .size(input.getSize().orElse(null))
                                       .offset(input.getOffset().orElse(null))
                                       .includeTotal(input.includeTotal())
                                       .build();
    }
}
