package de.cybine.management.util.cloudevent;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.*;
import com.fasterxml.jackson.databind.util.*;

public class CloudEventDataDeserializer implements Converter<String, CloudEventData<?>>
{
    @Override
    public JavaType getInputType(TypeFactory typeFactory)
    {
        return typeFactory.constructType(String.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory)
    {
        return typeFactory.constructParametricType(CloudEventData.class, Object.class);
    }

    @Override
    public CloudEventData<?> convert(String value)
    {
        return CloudEventData.fromBase64(value);
    }
}
