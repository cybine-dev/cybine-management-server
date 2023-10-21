package de.cybine.management.service.action;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.*;
import io.quarkus.arc.*;

public record ActionData<T>(@JsonProperty("@type") JavaType type, @JsonProperty("data") T data)
{
    public static <T> ActionData<T> of(T data)
    {
        TypeFactory typeFactory = Arc.container().select(ObjectMapper.class).get().getTypeFactory();
        return new ActionData<>(typeFactory.constructType(data.getClass()), data);
    }

    public static <T> ActionData<T> of(T data, Class<?> containerType, Class<?>... parameterTypes)
    {
        TypeFactory typeFactory = Arc.container().select(ObjectMapper.class).get().getTypeFactory();
        return new ActionData<>(typeFactory.constructParametricType(containerType, parameterTypes), data);
    }
}
