package de.cybine.management.data.mail.domain;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.tls.*;
import de.cybine.management.data.mail.user.*;
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
public class Domain implements WithId<Long>
{
    @JsonProperty("id")
    private final Long id;

    @JsonProperty("domain")
    private final String domain;

    @JsonProperty("action")
    private final DomainAction action;

    @JsonProperty("tls_policy")
    @JsonView(Views.Extended.class)
    private final TLSPolicy tlsPolicy;

    @JsonProperty("users")
    @JsonView(Views.Extended.class)
    private final Set<User> users;

    @JsonProperty("addresses")
    @JsonView(Views.Extended.class)
    private final Set<Address> addresses;

    public Optional<TLSPolicy> getTlsPolicy( )
    {
        return Optional.ofNullable(this.tlsPolicy);
    }

    @JsonView(Views.Simple.class)
    @JsonProperty("tls_policy_id")
    public Optional<Long> getTlsPolicyId()
    {
        return this.getTlsPolicy().map(WithId::getId);
    }

    public Optional<Set<User>> getUsers( )
    {
        return Optional.ofNullable(this.users);
    }

    @JsonProperty("user_ids")
    @JsonView(Views.Simple.class)
    public Optional<Set<Long>> getUserIds( )
    {
        return this.getUsers().map(items -> items.stream().map(WithId::getId).collect(Collectors.toSet()));
    }

    public Optional<Set<Address>> getAddresses( )
    {
        return Optional.ofNullable(this.addresses);
    }

    @JsonProperty("address_ids")
    @JsonView(Views.Simple.class)
    public Optional<Set<Long>> getAddressIds( )
    {
        return this.getAddresses().map(items -> items.stream().map(WithId::getId).collect(Collectors.toSet()));
    }
}
