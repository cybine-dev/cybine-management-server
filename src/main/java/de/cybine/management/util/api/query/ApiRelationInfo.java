package de.cybine.management.util.api.query;

import com.fasterxml.jackson.annotation.*;
import io.smallrye.common.constraint.*;
import lombok.*;
import lombok.extern.jackson.*;

import java.util.*;

@Data
@Jacksonized
@Builder(builderClassName = "Generator")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiRelationInfo
{
    @NotNull
    @JsonProperty("property")
    private final String property;

    @Builder.Default
    @JsonProperty("fetch")
    private final boolean fetch = false;

    @JsonProperty("condition")
    private final ApiConditionInfo condition;

    @Singular("order")
    @JsonProperty("order")
    private final List<ApiOrderInfo> order;

    public Optional<ApiConditionInfo> getCondition( )
    {
        return Optional.ofNullable(this.condition);
    }
}
