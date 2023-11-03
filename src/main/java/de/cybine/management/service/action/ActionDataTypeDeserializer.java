package de.cybine.management.service.action;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.*;
import com.fasterxml.jackson.databind.util.*;
import io.quarkus.arc.*;

public class ActionDataTypeDeserializer implements Converter<String, JavaType>
{
    @Override
    public JavaType getInputType(TypeFactory typeFactory)
    {
        return typeFactory.constructType(String.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory)
    {
        return typeFactory.constructType(JavaType.class);
    }

    @Override
    public JavaType convert(String value)
    {
        ActionDataTypeRegistry registry = Arc.container().select(ActionDataTypeRegistry.class).get();
        return registry.findType(value).orElseThrow();
    }
}

