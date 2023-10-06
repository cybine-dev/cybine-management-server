package de.cybine.management.util.api.datasource;

import lombok.*;

import java.util.*;

@Data
@Builder(builderClassName = "Generator")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DatasourceCountInfo
{
    @Singular("key")
    private final List<?> groupKey;

    private final long count;
}
