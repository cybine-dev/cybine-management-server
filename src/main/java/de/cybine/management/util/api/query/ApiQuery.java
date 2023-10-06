package de.cybine.management.util.api.query;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.extern.jackson.*;

import java.util.*;

@Data
@Jacksonized
@Builder(builderClassName = "Generator")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiQuery
{
    @JsonProperty("pagination")
    private final ApiPaginationInfo pagination;

    @JsonProperty("condition")
    private final ApiConditionInfo condition;

    @Singular("order")
    @JsonProperty("order")
    private final List<ApiOrderInfo> order;

    @Singular
    @JsonProperty("relations")
    private final List<ApiRelationInfo> relations;

    public Optional<ApiPaginationInfo> getPagination( )
    {
        return Optional.ofNullable(this.pagination);
    }

    public Optional<ApiConditionInfo> getCondition( )
    {
        return Optional.ofNullable(this.condition);
    }
}
