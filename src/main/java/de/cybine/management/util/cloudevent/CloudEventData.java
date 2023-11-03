package de.cybine.management.util.cloudevent;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.databind.type.*;
import de.cybine.management.service.action.*;
import io.quarkus.arc.*;
import lombok.*;
import lombok.extern.jackson.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

@Data
@Jacksonized
@AllArgsConstructor
@Builder(builderClassName = "Generator")
public class CloudEventData<T>
{
    @JsonProperty("@type")
    @JsonSerialize(converter = ActionDataTypeSerializer.class)
    @JsonDeserialize(converter = ActionDataTypeDeserializer.class)
    private final JavaType type;

    @JsonProperty("value")
    private final T value;

    public String toBase64( )
    {
        try
        {
            ObjectMapper mapper = Arc.container().select(ObjectMapper.class).get();
            return new String(Base64.getEncoder().encode(mapper.writeValueAsBytes(this)), StandardCharsets.UTF_8);
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static <T> CloudEventData<T> of(T value)
    {
        TypeFactory typeFactory = Arc.container().select(ObjectMapper.class).get().getTypeFactory();
        return new CloudEventData<>(typeFactory.constructType(value.getClass()), value);
    }

    public static <T> CloudEventData<T> of(T value, Class<?> containerType, Class<?>... parameterTypes)
    {
        TypeFactory typeFactory = Arc.container().select(ObjectMapper.class).get().getTypeFactory();
        return new CloudEventData<>(typeFactory.constructParametricType(containerType, parameterTypes), value);
    }

    @SuppressWarnings("unchecked")
    public static <T> CloudEventData<T> fromBase64(String data)
    {
        try
        {
            ObjectMapper mapper = Arc.container().select(ObjectMapper.class).get();
            return mapper.readValue(Base64.getDecoder().decode(data), CloudEventData.class);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
