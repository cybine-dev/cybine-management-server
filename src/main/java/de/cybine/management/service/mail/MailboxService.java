package de.cybine.management.service.mail;

import de.cybine.management.data.mail.mailbox.*;
import de.cybine.management.util.api.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.datasource.*;
import io.quarkus.runtime.*;
import jakarta.annotation.*;
import jakarta.enterprise.context.*;
import lombok.*;

import java.util.*;

import static de.cybine.management.data.mail.mailbox.MailboxEntity_.*;

@Startup
@ApplicationScoped
@RequiredArgsConstructor
public class MailboxService
{
    private final GenericDatasourceService<MailboxEntity, Mailbox> service = GenericDatasourceService.forType(
            MailboxEntity.class, Mailbox.class);

    private final ApiFieldResolver resolver;

    @PostConstruct
    void setup( )
    {
        this.resolver.registerTypeRepresentation(Mailbox.class, MailboxEntity.class)
                     .registerField("id", ID)
                     .registerField("name", NAME)
                     .registerField("description", DESCRIPTION)
                     .registerField("is_enabled", ENABLED)
                     .registerField("quota", QUOTA)
                     .registerField("source_addresses", SOURCE_ADDRESSES)
                     .registerField("users", USERS);
    }

    public List<Mailbox> fetch(ApiQuery query)
    {
        return this.service.fetch(query);
    }

    public Optional<Mailbox> fetchSingle(ApiQuery query)
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
