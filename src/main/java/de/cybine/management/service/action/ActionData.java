package de.cybine.management.service.action;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.databind.type.*;
import io.quarkus.arc.*;

public record ActionData<T>(
        @JsonProperty("@type") @JsonSerialize(converter = ActionDataTypeSerializer.class) @JsonDeserialize(converter
                = ActionDataTypeDeserializer.class) JavaType type,
        @JsonProperty("value") T value)
{
    public static <T> ActionData<T> of(T value)
    {
        TypeFactory typeFactory = Arc.container().select(ObjectMapper.class).get().getTypeFactory();
        return new ActionData<>(typeFactory.constructType(value.getClass()), value);
    }

    public static <T> ActionData<T> of(T value, Class<?> containerType, Class<?>... parameterTypes)
    {
        TypeFactory typeFactory = Arc.container().select(ObjectMapper.class).get().getTypeFactory();
        return new ActionData<>(typeFactory.constructParametricType(containerType, parameterTypes), value);
    }
}
