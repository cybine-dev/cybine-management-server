package de.cybine.management.service.action;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.*;
import com.fasterxml.jackson.databind.util.*;
import io.quarkus.arc.*;
import lombok.extern.slf4j.*;

@Slf4j
public class ActionDataTypeSerializer implements Converter<JavaType, String>
{
    @Override
    public JavaType getInputType(TypeFactory typeFactory)
    {
        return typeFactory.constructType(JavaType.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory)
    {
        return typeFactory.constructType(String.class);
    }

    @Override
    public String convert(JavaType value)
    {
        ActionDataTypeRegistry registry = Arc.container().select(ActionDataTypeRegistry.class).get();
        String typeName = registry.findTypeName(value).orElse(null);
        if (typeName == null)
        {
            log.warn("Unknown action data-type '{}' found: Using type-name '{}'", value.getTypeName(),
                    registry.getDefaultTypeName());

            typeName = registry.getDefaultTypeName();
        }

        return typeName;
    }
}
