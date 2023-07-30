package de.cybine.management.data.mail.address;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.mail.forwarding.*;
import de.cybine.management.data.mail.mailbox.*;
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
public class Address implements WithId<Long>
{
    @JsonProperty("id")
    private final Long id;

    @JsonProperty("domain_id")
    private final long domainId;

    @JsonProperty("domain")
    @JsonView(Views.Extended.class)
    private final Domain domain;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("action")
    private final AddressAction action;

    @JsonProperty("forwards_to")
    @JsonView(Views.Extended.class)
    private final Set<Forwarding> forwardsTo;

    @JsonProperty("receives_from")
    @JsonView(Views.Extended.class)
    private final Set<Forwarding> receivesFrom;

    @JsonProperty("mailboxes")
    @JsonView(Views.Extended.class)
    private final Set<Mailbox> mailboxes;

    @JsonProperty("senders")
    @JsonView(Views.Extended.class)
    private final Set<User> senders;

    public Optional<Domain> getDomain( )
    {
        return Optional.ofNullable(this.domain);
    }

    public Optional<Set<Forwarding>> getForwardsTo( )
    {
        return Optional.ofNullable(this.forwardsTo);
    }

    @JsonView(Views.Simple.class)
    @JsonProperty("forwards_to_ids")
    public Optional<Set<Long>> getForwardsToIds( )
    {
        return this.getForwardsTo()
                   .map(items -> items.stream().map(Forwarding::getForwardingAddressId).collect(Collectors.toSet()));
    }

    public Optional<Set<Forwarding>> getReceivesFrom( )
    {
        return Optional.ofNullable(this.receivesFrom);
    }

    @JsonView(Views.Simple.class)
    @JsonProperty("receives_from_ids")
    public Optional<Set<Long>> getReceivesFromIds( )
    {
        return this.getReceivesFrom()
                   .map(items -> items.stream().map(Forwarding::getReceiverAddressId).collect(Collectors.toSet()));
    }

    public Optional<Set<Mailbox>> getMailboxes( )
    {
        return Optional.ofNullable(this.mailboxes);
    }

    @JsonProperty("mailbox_ids")
    @JsonView(Views.Simple.class)
    public Optional<Set<Long>> getMailboxIds( )
    {
        return this.getMailboxes().map(items -> items.stream().map(WithId::getId).collect(Collectors.toSet()));
    }

    public Optional<Set<User>> getSenders( )
    {
        return Optional.ofNullable(this.senders);
    }

    @JsonProperty("sender_ids")
    @JsonView(Views.Simple.class)
    public Optional<Set<Long>> getSenderIds( )
    {
        return this.getSenders().map(items -> items.stream().map(WithId::getId).collect(Collectors.toSet()));
    }
}
