package de.cybine.management.service.action;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import de.cybine.management.exception.action.*;
import io.quarkus.arc.*;
import jakarta.persistence.*;
import lombok.extern.slf4j.*;

import java.util.*;

@Slf4j
@Converter
public class ActionDataAttributeConverter implements AttributeConverter<ActionData<?>, String>
{
    @Override
    public String convertToDatabaseColumn(ActionData<?> attribute)
    {
        if (attribute == null)
            return null;

        try
        {
            ActionDataTypeRegistry registry = this.getRegistry();
            String type = registry.findTypeName(attribute.type()).orElse(null);
            if (type == null)
            {
                log.warn("Unknown action data-type '{}' found: Using type-name '{}'", attribute.type().getTypeName(),
                        registry.getDefaultTypeName());

                type = registry.getDefaultTypeName();
            }

            Map<String, Object> data = new HashMap<>();
            data.put("@type", type);
            data.put("value", attribute.value());

            return this.getObjectMapper().writeValueAsString(data);
        }
        catch (JsonProcessingException exception)
        {
            throw new ActionProcessingException(exception);
        }
    }

    @Override
    public ActionData<?> convertToEntityAttribute(String dbData)
    {
        if (dbData == null)
            return null;

        try
        {
            JsonNode jsonNode = this.getObjectMapper().readTree(dbData);
            String typeName = jsonNode.findValue("@type").asText();
            JavaType type = this.getRegistry().findType(typeName).orElse(null);
            if (type == null)
            {
                log.warn("Unknown action data-type '{}' found: Using default map-type", typeName);
                type = this.getRegistry().getDefaultType();
            }

            Object data = this.getObjectMapper().treeToValue(jsonNode.findValue("value"), type);

            return new ActionData<>(type, data);
        }
        catch (JsonProcessingException exception)
        {
            throw new ActionProcessingException(exception);
        }
    }

    private ObjectMapper getObjectMapper( )
    {
        return Arc.container().select(ObjectMapper.class).get();
    }

    private ActionDataTypeRegistry getRegistry( )
    {
        return Arc.container().select(ActionDataTypeRegistry.class).get();
    }
}