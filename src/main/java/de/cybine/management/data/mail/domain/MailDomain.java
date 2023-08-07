package de.cybine.management.data.mail.domain;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.tls.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.data.util.*;
import de.cybine.management.util.*;
import lombok.*;
import lombok.extern.jackson.*;

import java.util.*;
import java.util.stream.*;

@Data
@Jacksonized
@Builder(builderClassName = "Builder")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MailDomain implements WithId<MailDomainId>
{
    @JsonProperty("id")
    private final MailDomainId id;

    @JsonProperty("domain")
    private final Domain domain;

    @JsonProperty("action")
    private final MailDomainAction action;

    @JsonProperty("tls_policy")
    @JsonView(Views.Extended.class)
    private final MailTLSPolicy tlsPolicy;

    @JsonProperty("users")
    @JsonView(Views.Extended.class)
    private final Set<MailUser> users;

    @JsonProperty("addresses")
    @JsonView(Views.Extended.class)
    private final Set<MailAddress> addresses;

    public Optional<MailTLSPolicy> getTlsPolicy( )
    {
        return Optional.ofNullable(this.tlsPolicy);
    }

    @JsonView(Views.Simple.class)
    @JsonProperty("tls_policy_id")
    public Optional<MailTLSPolicyId> getTlsPolicyId( )
    {
        return this.getTlsPolicy().map(WithId::getId);
    }

    public Optional<Set<MailUser>> getUsers( )
    {
        return Optional.ofNullable(this.users);
    }

    @JsonProperty("user_ids")
    @JsonView(Views.Simple.class)
    public Optional<Set<MailUserId>> getUserIds( )
    {
        return this.getUsers().map(items -> items.stream().map(WithId::getId).collect(Collectors.toSet()));
    }

    public Optional<Set<MailAddress>> getAddresses( )
    {
        return Optional.ofNullable(this.addresses);
    }

    @JsonProperty("address_ids")
    @JsonView(Views.Simple.class)
    public Optional<Set<MailAddressId>> getAddressIds( )
    {
        return this.getAddresses().map(items -> items.stream().map(WithId::getId).collect(Collectors.toSet()));
    }
}
