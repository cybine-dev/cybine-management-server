package de.cybine.management.config;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.mail.forwarding.*;
import de.cybine.management.data.mail.mailbox.*;
import de.cybine.management.data.mail.tls.*;
import de.cybine.management.data.mail.user.*;
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

    @PostConstruct
    public void setup( )
    {
        this.registry.addEntityMapper(new MailAddressMapper());
        this.registry.addEntityMapper(new MailDomainMapper());
        this.registry.addEntityMapper(new MailForwardingMapper());
        this.registry.addEntityMapper(new MailboxMapper());
        this.registry.addEntityMapper(new MailTLSPolicyMapper());
        this.registry.addEntityMapper(new MailUserMapper());
    }
}
