package de.cybine.management.service.mail;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.util.api.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.datasource.*;
import io.quarkus.runtime.*;
import jakarta.annotation.*;
import jakarta.enterprise.context.*;
import lombok.*;

import java.util.*;

import static de.cybine.management.data.mail.address.MailAddressEntity_.*;

@Startup
@ApplicationScoped
@RequiredArgsConstructor
public class AddressService
{
    private final GenericDatasourceService<MailAddressEntity, MailAddress> service = GenericDatasourceService.forType(
            MailAddressEntity.class, MailAddress.class);

    private final ApiFieldResolver resolver;

    @PostConstruct
    void setup( )
    {
        this.resolver.registerTypeRepresentation(MailAddress.class, MailAddressEntity.class)
                     .registerField("id", ID)
                     .registerField("domain_id", DOMAIN_ID)
                     .registerField("domain", DOMAIN)
                     .registerField("name", NAME)
                     .registerField("action", ACTION)
                     .registerField("forwards_to", FORWARDS_TO)
                     .registerField("receives_from", RECEIVES_FROM)
                     .registerField("mailboxes", MAILBOXES)
                     .registerField("senders", SENDERS);
    }

    public List<MailAddress> fetch(ApiQuery query)
    {
        return this.service.fetch(query);
    }

    public Optional<MailAddress> fetchSingle(ApiQuery query)
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
