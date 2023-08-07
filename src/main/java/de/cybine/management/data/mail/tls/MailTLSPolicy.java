package de.cybine.management.data.mail.tls;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.util.*;
import lombok.*;
import lombok.extern.jackson.*;

import java.util.*;

@Data
@Jacksonized
@Builder(builderClassName = "Builder")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MailTLSPolicy implements WithId<MailTLSPolicyId>
{
    @JsonProperty("id")
    private final MailTLSPolicyId id;

    @JsonProperty("domain_id")
    private final MailDomainId domainId;

    @JsonProperty("domain")
    @JsonView(Views.Extended.class)
    private final MailDomain domain;

    @JsonProperty("type")
    private final MailTLSPolicyType type;

    @JsonProperty("params")
    private final String params;

    public Optional<MailDomain> getDomain( )
    {
        return Optional.ofNullable(this.domain);
    }
}
