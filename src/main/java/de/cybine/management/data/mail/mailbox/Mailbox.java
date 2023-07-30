package de.cybine.management.data.mail.mailbox;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.mail.address.*;
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
public class Mailbox implements WithId<Long>
{
    @JsonProperty("id")
    private final Long id;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("is_enabled")
    private final boolean isEnabled;

    @JsonProperty("quota")
    private final long quota;

    @JsonView(Views.Extended.class)
    @JsonProperty("source_addresses")
    private final Set<Address> sourceAddresses;

    @JsonProperty("users")
    @JsonView(Views.Extended.class)
    private final Set<User> users;

    public Optional<Set<Address>> getSourceAddresses( )
    {
        return Optional.ofNullable(this.sourceAddresses);
    }

    @JsonView(Views.Simple.class)
    @JsonProperty("source_address_ids")
    public Optional<Set<Long>> getSourceAddressIds( )
    {
        return this.getSourceAddresses().map(items -> items.stream().map(WithId::getId).collect(Collectors.toSet()));
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
}
