package de.cybine.management.service.mail;

import de.cybine.management.data.mail.tls.*;
import de.cybine.management.util.api.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.datasource.*;
import io.quarkus.runtime.*;
import jakarta.annotation.*;
import jakarta.enterprise.context.*;
import lombok.*;

import java.util.*;

import static de.cybine.management.data.mail.tls.MailTLSPolicyEntity_.*;

@Startup
@ApplicationScoped
@RequiredArgsConstructor
public class TLSPolicyService
{
    private final GenericDatasourceService<MailTLSPolicyEntity, MailTLSPolicy> service =
            GenericDatasourceService.forType(
            MailTLSPolicyEntity.class, MailTLSPolicy.class);

    private final ApiFieldResolver resolver;

    @PostConstruct
    void setup( )
    {
        this.resolver.registerTypeRepresentation(MailTLSPolicy.class, MailTLSPolicyEntity.class)
                     .registerField("id", ID)
                     .registerField("domain_id", DOMAIN_ID)
                     .registerField("domain", DOMAIN)
                     .registerField("type", POLICY)
                     .registerField("params", PARAMS);
    }

    public List<MailTLSPolicy> fetch(ApiQuery query)
    {
        return this.service.fetch(query);
    }

    public Optional<MailTLSPolicy> fetchSingle(ApiQuery query)
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
