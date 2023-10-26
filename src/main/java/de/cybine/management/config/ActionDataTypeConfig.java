package de.cybine.management.config;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.service.action.*;
import io.quarkus.runtime.*;
import jakarta.annotation.*;
import jakarta.enterprise.context.*;
import lombok.*;

@Startup
@Dependent
@RequiredArgsConstructor
public class ActionDataTypeConfig
{
    private final ActionDataTypeRegistry registry;

    private final ObjectMapper objectMapper;

    @PostConstruct
    public void setup( )
    {
        TypeFactory typeFactory = this.objectMapper.getTypeFactory();

        this.registry.registerType("de.cybine.management.v1.mail.domain", typeFactory.constructType(MailDomain.class));
    }
}
