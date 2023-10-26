package de.cybine.management.service.mail;

import de.cybine.management.api.v1.mail.domain.params.*;
import de.cybine.management.data.action.context.*;
import de.cybine.management.data.action.process.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.service.action.*;
import de.cybine.management.service.mail.domain.*;
import de.cybine.management.util.api.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.datasource.*;
import io.quarkus.runtime.*;
import jakarta.annotation.*;
import jakarta.enterprise.context.*;
import jakarta.transaction.*;
import lombok.*;

import java.time.*;
import java.util.*;

import static de.cybine.management.data.mail.domain.MailDomainEntity_.*;

@Startup
@ApplicationScoped
@RequiredArgsConstructor
public class DomainService
{
    public static final ActionContextMetadata CREATION = ActionContextMetadata.builder()
                                                                              .namespace("cybine-management-mail")
                                                                              .category("domain")
                                                                              .name("creation")
                                                                              .build();

    private final GenericDatasourceService<MailDomainEntity, MailDomain> service = GenericDatasourceService.forType(
            MailDomainEntity.class, MailDomain.class);

    private final ApiFieldResolver resolver;

    private final ActionService           actionService;
    private final ActionProcessorRegistry processorRegistry;

    @PostConstruct
    public void setup( )
    {
        this.resolver.registerTypeRepresentation(MailDomain.class, MailDomainEntity.class)
                     .registerField("id", ID)
                     .registerField("domain", DOMAIN)
                     .registerField("action", ACTION)
                     .registerField("tls_policy", TLS_POLICY)
                     .registerField("users", USERS)
                     .registerField("addresses", ADDRESSES);

        ActionProcessorMetadata prepareCreation = DomainService.CREATION.toProcessorMetadata(
                BaseActionProcessStatus.INITIALIZED.getName(), "prepare");

        this.processorRegistry.registerProcessors(
                List.of(GenericActionProcessor.of(prepareCreation), new DomainCreationProcessor()));
    }

    public List<MailDomain> fetch(ApiQuery query)
    {
        return this.service.fetch(query);
    }

    public Optional<MailDomain> fetchSingle(ApiQuery query)
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

    @Transactional
    public MailDomain addDomain(DomainCreationParameter parameter)
    {
        MailDomain domain = MailDomain.builder().domain(parameter.getDomain()).action(parameter.getAction()).build();

        ActionContext context = this.actionService.createContext(DomainService.CREATION);
        this.actionService.process(ActionProcessMetadata.builder()
                                                        .status("prepare")
                                                        .contextId(context.getId())
                                                        .createdAt(ZonedDateTime.now())
                                                        .data(ActionData.of(domain))
                                                        .build());

        return this.actionService.<MailDomain>terminateContext(context.getId()).getData().orElseThrow();
    }
}
