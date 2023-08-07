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
public class Mailbox implements WithId<MailboxId>
{
    @JsonProperty("id")
    private final MailboxId id;

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
    private final Set<MailAddress> sourceAddresses;

    @JsonProperty("users")
    @JsonView(Views.Extended.class)
    private final Set<MailUser> users;

    public Optional<Set<MailAddress>> getSourceAddresses( )
    {
        return Optional.ofNullable(this.sourceAddresses);
    }

    @JsonView(Views.Simple.class)
    @JsonProperty("source_address_ids")
    public Optional<Set<MailAddressId>> getSourceAddressIds( )
    {
        return this.getSourceAddresses().map(items -> items.stream().map(WithId::getId).collect(Collectors.toSet()));
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
}
