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
public class ApiCountRelationInfo
{
    @NotNull
    @JsonProperty("property")
    private final String property;

    @JsonProperty("condition")
    private final ApiConditionInfo condition;

    @Singular("groupBy")
    @JsonProperty("group_by")
    private final List<String> groupingProperties;

    public Optional<ApiConditionInfo> getCondition( )
    {
        return Optional.ofNullable(this.condition);
    }
}
