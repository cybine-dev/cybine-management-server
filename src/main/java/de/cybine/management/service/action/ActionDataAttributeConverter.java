package de.cybine.management.service.action;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import de.cybine.management.exception.action.*;
import io.quarkus.arc.*;
import jakarta.persistence.*;

import java.util.*;

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
            String type = this.getRegistry()
                              .findTypeName(attribute.type())
                              .orElseThrow(( ) -> new ActionProcessingException("Unknown action data type"));

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
            JavaType type = this.getRegistry()
                                .findType(jsonNode.findValue("@type").asText())
                                .orElseThrow(( ) -> new ActionProcessingException("Unknown action data type"));

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