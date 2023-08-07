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
public class MailAddress implements WithId<MailAddressId>
{
    @JsonProperty("id")
    private final MailAddressId id;

    @JsonProperty("domain_id")
    private final MailDomainId domainId;

    @JsonProperty("domain")
    @JsonView(Views.Extended.class)
    private final MailDomain domain;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("action")
    private final MailAddressAction action;

    @JsonProperty("forwards_to")
    @JsonView(Views.Extended.class)
    private final Set<MailForwarding> forwardsTo;

    @JsonProperty("receives_from")
    @JsonView(Views.Extended.class)
    private final Set<MailForwarding> receivesFrom;

    @JsonProperty("mailboxes")
    @JsonView(Views.Extended.class)
    private final Set<Mailbox> mailboxes;

    @JsonProperty("senders")
    @JsonView(Views.Extended.class)
    private final Set<MailUser> senders;

    public Optional<MailDomain> getDomain( )
    {
        return Optional.ofNullable(this.domain);
    }

    public Optional<Set<MailForwarding>> getForwardsTo( )
    {
        return Optional.ofNullable(this.forwardsTo);
    }

    @JsonView(Views.Simple.class)
    @JsonProperty("forwards_to_ids")
    public Optional<Set<MailAddressId>> getForwardsToIds( )
    {
        return this.getForwardsTo()
                   .map(items -> items.stream()
                                      .map(MailForwarding::getForwardingAddressId)
                                      .collect(Collectors.toSet()));
    }

    public Optional<Set<MailForwarding>> getReceivesFrom( )
    {
        return Optional.ofNullable(this.receivesFrom);
    }

    @JsonView(Views.Simple.class)
    @JsonProperty("receives_from_ids")
    public Optional<Set<MailAddressId>> getReceivesFromIds( )
    {
        return this.getReceivesFrom()
                   .map(items -> items.stream().map(MailForwarding::getReceiverAddressId).collect(Collectors.toSet()));
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

    public Optional<Set<MailUser>> getSenders( )
    {
        return Optional.ofNullable(this.senders);
    }

    @JsonProperty("sender_ids")
    @JsonView(Views.Simple.class)
    public Optional<Set<MailUserId>> getSenderIds( )
    {
        return this.getSenders().map(items -> items.stream().map(WithId::getId).collect(Collectors.toSet()));
    }
}
