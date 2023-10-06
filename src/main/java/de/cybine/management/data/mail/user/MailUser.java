package de.cybine.management.data.mail.user;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.mail.mailbox.*;
import de.cybine.management.data.util.*;
import de.cybine.management.data.util.password.*;
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
public class MailUser implements Serializable, WithId<MailUserId>
{
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    @EqualsAndHashCode.Include
    private final MailUserId id;

    @JsonProperty("domain_id")
    private final MailDomainId domainId;

    @JsonProperty("domain")
    @JsonView(Views.Extended.class)
    private final MailDomain domain;

    @JsonProperty("username")
    private final Username username;

    @JsonIgnore
    @JsonProperty("password_hash")
    private final BCryptPasswordHash passwordHash;

    @JsonProperty("is_enabled")
    private final boolean isEnabled;

    @JsonProperty("mailboxes")
    @JsonView(Views.Extended.class)
    private Set<Mailbox> mailboxes;

    @JsonView(Views.Extended.class)
    @JsonProperty("permitted_addresses")
    private Set<MailAddress> permittedAddresses;

    public Optional<MailDomain> getDomain( )
    {
        return Optional.ofNullable(this.domain);
    }

    public Optional<Set<Mailbox>> getMailboxes( )
    {
        return Optional.ofNullable(this.mailboxes);
    }

    @JsonProperty("mailbox_ids")
    @JsonView(Views.Simple.class)
    public Optional<Set<MailboxId>> getMailboxIds( )
    {
        return this.getMailboxes().map(items -> items.stream().map(WithId::getId).collect(Collectors.toSet()));
    }

    public Optional<Set<MailAddress>> getPermittedAddresses( )
    {
        return Optional.ofNullable(this.permittedAddresses);
    }

    @JsonView(Views.Simple.class)
    @JsonProperty("permitted_address_ids")
    public Optional<Set<MailAddressId>> getPermittedAddressIds( )
    {
        return this.getPermittedAddresses().map(items -> items.stream().map(WithId::getId).collect(Collectors.toSet()));
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null)
            return false;

        if (this.getClass() != other.getClass())
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
