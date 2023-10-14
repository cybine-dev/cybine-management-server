package de.cybine.management.config;

import com.fasterxml.jackson.databind.*;
import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.mail.forwarding.*;
import de.cybine.management.data.mail.mailbox.*;
import de.cybine.management.data.mail.tls.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.util.api.converter.*;
import de.cybine.management.util.converter.*;
import io.quarkus.runtime.*;
import jakarta.annotation.*;
import jakarta.enterprise.context.*;
import lombok.*;

@Startup
@Dependent
@RequiredArgsConstructor
public class ConverterConfig
{
    private final ConverterRegistry registry;

    private final ObjectMapper objectMapper;

    @PostConstruct
    public void setup( )
    {
        this.registry.addConverter(new ApiConditionDetailConverter(this.objectMapper));
        this.registry.addConverter(new ApiConditionInfoConverter());
        this.registry.addConverter(new ApiCountQueryConverter());
        this.registry.addConverter(new ApiCountRelationConverter());
        this.registry.addConverter(new ApiQueryConverter());
        this.registry.addConverter(new ApiOptionQueryConverter());
        this.registry.addConverter(new ApiOrderInfoConverter());
        this.registry.addConverter(new ApiOptionQueryConverter());
        this.registry.addConverter(new ApiPaginationInfoConverter());
        this.registry.addConverter(new ApiRelationInfoConverter());
        this.registry.addEntityMapper(new CountInfoMapper());

        this.registry.addEntityMapper(new MailAddressMapper());
        this.registry.addEntityMapper(new MailDomainMapper());
        this.registry.addEntityMapper(new MailForwardingMapper());
        this.registry.addEntityMapper(new MailboxMapper());
        this.registry.addEntityMapper(new MailTLSPolicyMapper());
        this.registry.addEntityMapper(new MailUserMapper());
    }
}
