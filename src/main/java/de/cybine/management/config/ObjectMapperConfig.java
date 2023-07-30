package de.cybine.management.config;

import com.fasterxml.jackson.annotation.JsonInclude.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jdk8.*;
import io.quarkus.jackson.*;
import jakarta.enterprise.context.*;

@ApplicationScoped
public class ObjectMapperConfig implements ObjectMapperCustomizer
{
    @Override
    public void customize(ObjectMapper mapper)
    {
        mapper.registerModule(new Jdk8Module());
        mapper.setSerializationInclusion(Include.NON_ABSENT);
    }
}
