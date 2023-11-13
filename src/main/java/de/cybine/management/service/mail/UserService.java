package de.cybine.management.service.mail;

import de.cybine.management.data.mail.user.*;
import de.cybine.management.util.api.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.datasource.*;
import io.quarkus.runtime.*;
import jakarta.annotation.*;
import jakarta.enterprise.context.*;
import lombok.*;

import java.util.*;

import static de.cybine.management.data.mail.user.MailUserEntity_.*;

@Startup
@ApplicationScoped
@RequiredArgsConstructor
public class UserService
{
    private final GenericDatasourceService<MailUserEntity, MailUser> service = GenericDatasourceService.forType(
            MailUserEntity.class, MailUser.class);

    private final ApiFieldResolver resolver;

    @PostConstruct
    void setup( )
    {
        this.resolver.registerTypeRepresentation(MailUser.class, MailUserEntity.class)
                     .registerField("id", ID)
                     .registerField("domain_id", DOMAIN_ID)
                     .registerField("domain", DOMAIN)
                     .registerField("username", USERNAME)
                     .registerField("password_hash", PASSWORD_HASH) // TODO: restrict query
                     .registerField("is_enabled", IS_ENABLED)
                     .registerField("mailboxes", MAILBOXES)
                     .registerField("permitted_addresses", PERMITTED_ADDRESSES);
    }

    public List<MailUser> fetch(ApiQuery query)
    {
        return this.service.fetch(query);
    }

    public Optional<MailUser> fetchSingle(ApiQuery query)
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
