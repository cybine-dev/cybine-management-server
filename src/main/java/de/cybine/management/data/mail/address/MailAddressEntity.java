package de.cybine.management.data.mail.address;

import de.cybine.management.converter.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.mail.forwarding.*;
import de.cybine.management.data.mail.mailbox.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.util.*;
import io.quarkus.hibernate.orm.panache.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.*;
import java.util.*;

@Data
@NoArgsConstructor
@Table(name = MailAddressEntity_.TABLE)
@Entity(name = MailAddressEntity_.ENTITY)
@Builder(builderClassName = "Generator")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MailAddressEntity extends PanacheEntityBase implements Serializable, WithId<Long>
{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = MailAddressEntity_.ID_COLUMN, nullable = false, unique = true)
    private Long id;

    @Column(name = MailAddressEntity_.DOMAIN_ID_COLUMN, nullable = false, insertable = false, updatable = false)
    private long domainId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = MailAddressEntity_.DOMAIN_ID_COLUMN, nullable = false)
    private MailDomainEntity domain;

    @Size(min = 5, max = 255)
    @Column(name = MailAddressEntity_.NAME_COLUMN, nullable = false)
    private String name;

    @Convert(converter = AddressActionConverter.class)
    @Column(name = MailAddressEntity_.ACTION_COLUMN, nullable = false)
    private MailAddressAction action;

    @OneToMany(mappedBy = MailForwardingEntity_.RECEIVER_ADDRESS_RELATION)
    private Set<MailForwardingEntity> forwardsTo;

    @OneToMany(mappedBy = MailForwardingEntity_.FORWARDING_ADDRESS_RELATION)
    private Set<MailForwardingEntity> receivesFrom;

    @ManyToMany(mappedBy = MailboxEntity_.SOURCE_ADDRESSES_RELATION)
    private Set<MailboxEntity> mailboxes;

    @ManyToMany
    @JoinTable(name = MailAddressPermission_.TABLE,
               joinColumns = @JoinColumn(name = MailAddressPermission_.ADDRESS_ID_COLUMN),
               inverseJoinColumns = @JoinColumn(name = MailAddressPermission_.USER_ID_COLUMN))
    private Set<MailUserEntity> senders;

    public Optional<MailDomainEntity> getDomain( )
    {
        return Optional.ofNullable(this.domain);
    }

    public Optional<Set<MailForwardingEntity>> getForwardsTo( )
    {
        return Optional.ofNullable(this.forwardsTo);
    }

    public Optional<Set<MailForwardingEntity>> getReceivesFrom( )
    {
        return Optional.ofNullable(this.receivesFrom);
    }

    public Optional<Set<MailboxEntity>> getMailboxes( )
    {
        return Optional.ofNullable(this.mailboxes);
    }

    public Optional<Set<MailUserEntity>> getSenders( )
    {
        return Optional.ofNullable(this.senders);
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
