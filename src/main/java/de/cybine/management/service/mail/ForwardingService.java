package de.cybine.management.service.mail;

import de.cybine.management.data.mail.forwarding.*;
import de.cybine.management.util.api.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.datasource.*;
import io.quarkus.runtime.*;
import jakarta.annotation.*;
import jakarta.enterprise.context.*;
import lombok.*;

import java.util.*;

import static de.cybine.management.data.mail.forwarding.MailForwardingEntity_.*;

@Startup
@ApplicationScoped
@RequiredArgsConstructor
public class ForwardingService
{
    private final GenericDatasourceService<MailForwardingEntity, MailForwarding> service = GenericDatasourceService.forType(
            MailForwardingEntity.class, MailForwarding.class);

    private final ApiFieldResolver resolver;

    @PostConstruct
    void setup( )
    {
        this.resolver.registerTypeRepresentation(MailForwarding.class, MailForwardingEntity.class)
                     .registerField("forwarding_address_id", FORWARDING_ADDRESS_ID)
                     .registerField("forwarding_address", FORWARDING_ADDRESS)
                     .registerField("receiver_address_id", RECEIVER_ADDRESS_ID)
                     .registerField("receiver_address", RECEIVER_ADDRESS)
                     .registerField("starts_at", STARTS_AT)
                     .registerField("ends_at", ENDS_AT);
    }

    public List<MailForwarding> fetch(ApiQuery query)
    {
        return this.service.fetch(query);
    }

    public Optional<MailForwarding> fetchSingle(ApiQuery query)
    {
        return this.service.fetchSingle(query);
    }

    public <O> List<O> fetchOptions(ApiOptionQuery query)
    {
        return this.service.fetchOptions(query);
    }

    public List<ApiCountInfo> fetchTotal(ApiCountQuery query)
    {
        return this.service.fetchTotal(query);
    }
}
