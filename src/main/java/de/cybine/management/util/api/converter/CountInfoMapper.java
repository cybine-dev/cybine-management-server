package de.cybine.management.util.api.converter;

import de.cybine.management.util.api.datasource.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.converter.*;

public class CountInfoMapper implements EntityMapper<DatasourceCountInfo, ApiCountInfo>
{
    @Override
    public Class<DatasourceCountInfo> getEntityType( )
    {
        return DatasourceCountInfo.class;
    }

    @Override
    public Class<ApiCountInfo> getDataType( )
    {
        return ApiCountInfo.class;
    }

    @Override
    public DatasourceCountInfo toEntity(ApiCountInfo data, ConversionHelper helper)
    {
        return DatasourceCountInfo.builder().groupKey(data.getGroupKey()).count(data.getCount()).build();
    }

    @Override
    public ApiCountInfo toData(DatasourceCountInfo entity, ConversionHelper helper)
    {
        return ApiCountInfo.builder().groupKey(entity.getGroupKey()).count(entity.getCount()).build();
    }
}
