package de.cybine.management.util.cloudevent;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import jakarta.validation.*;
import lombok.*;
import lombok.extern.jackson.*;

import java.time.*;
import java.util.*;

@Data
@Jacksonized
@Builder(builderClassName = "Generator")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CloudEvent
{
    @Builder.Default
    @JsonProperty("specversion")
    private final String specVersion = "1.0";

    @JsonProperty("id")
    private final String id;

    @JsonProperty("type")
    private final String type;

    @JsonProperty("subject")
    private final String subject;

    @JsonProperty("source")
    private final String source;

    @JsonProperty("time")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private final ZonedDateTime time;

    @JsonProperty("correlation-id")
    private final String correlationId;

    @JsonProperty("priority")
    private final Integer priority;

    @JsonProperty("datacontenttype")
    private final String contentType;

    @Valid
    @JsonProperty(value = "data")
    @JsonSerialize(converter = CloudEventDataSerializer.class)
    @JsonDeserialize(converter = CloudEventDataDeserializer.class)
    private final CloudEventData<?> data;

    public Optional<String> getSubject( )
    {
        return Optional.ofNullable(this.subject);
    }

    public Optional<String> getCorrelationId( )
    {
        return Optional.ofNullable(this.correlationId);
    }

    public Optional<Integer> getPriority( )
    {
        return Optional.ofNullable(this.priority);
    }

    public Optional<String> getContentType( )
    {
        return Optional.ofNullable(this.contentType);
    }

    @JsonIgnore
    @SuppressWarnings("unchecked")
    public <T> Optional<CloudEventData<T>> getData( )
    {
        return Optional.ofNullable((CloudEventData<T>) this.data);
    }
}
