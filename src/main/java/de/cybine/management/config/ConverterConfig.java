package de.cybine.management.config;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.mail.forwarding.*;
import de.cybine.management.data.mail.mailbox.*;
import de.cybine.management.data.mail.tls.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.util.converter.*;
import io.quarkus.runtime.*;
import jakarta.enterprise.context.*;
import jakarta.enterprise.event.*;
import lombok.*;

@ApplicationScoped
@RequiredArgsConstructor
public class ConverterConfig
{
    private final ConverterRegistry registry;

    public void setup(@Observes StartupEvent event)
    {
        this.registry.addEntityMapper(new MailAddressMapper(this.registry));
        this.registry.addEntityMapper(new MailDomainMapper(this.registry));
        this.registry.addEntityMapper(new MailForwardingMapper(this.registry));
        this.registry.addEntityMapper(new MailboxMapper(this.registry));
        this.registry.addEntityMapper(new MailTLSPolicyMapper(this.registry));
        this.registry.addEntityMapper(new MailUserMapper(this.registry));
    }
}
