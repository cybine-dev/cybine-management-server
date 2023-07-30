package de.cybine.management.util.api;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Data
@Builder(builderClassName = "Builder")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse
{
    @JsonProperty("message")
    private final String message;
}
