package de.cybine.management.data.mail.mailbox;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.util.*;
import lombok.*;
import lombok.extern.jackson.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

@Data
@Jacksonized
@Builder(builderClassName = "Generator")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Mailbox implements Serializable, WithId<MailboxId>
{
    @Serial
    private static final long serialVersionUID = 1L;

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

    public Optional<String> getDescription( )
    {
        return Optional.ofNullable(this.description);
    }

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

    @Override
    public boolean equals(Object other)
    {
        if(other == null)
            return false;

        if(this.getClass() != other.getClass())
            return false;

        WithId<?> that = ((WithId<?>) other);
        if (this.findId().isEmpty() || that.findId().isEmpty())
            return false;

        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode( )
    {
        return this.findId().map(Object::hashCode).orElse(0);
    }
}
