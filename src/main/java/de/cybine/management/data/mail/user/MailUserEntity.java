package de.cybine.management.data.mail.user;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.mail.mailbox.*;
import de.cybine.management.util.*;
import io.quarkus.hibernate.orm.panache.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.*;
import java.util.*;

@Data
@NoArgsConstructor
@Table(name = MailUserEntity_.TABLE)
@Entity(name = MailUserEntity_.ENTITY)
@Builder(builderClassName = "Generator")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MailUserEntity extends PanacheEntityBase implements Serializable, WithId<Long>
{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = MailUserEntity_.ID_COLUMN, nullable = false, unique = true)
    private Long id;

    @Column(name = MailUserEntity_.DOMAIN_ID_COLUMN, nullable = false, insertable = false, updatable = false)
    private long domainId;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = MailUserEntity_.DOMAIN_ID_COLUMN, nullable = false)
    private MailDomainEntity domain;

    @Size(min = 5, max = 255)
    @Column(name = MailUserEntity_.USERNAME_COLUMN, nullable = false)
    private String username;

    @Size(max = 255)
    @Column(name = MailUserEntity_.PASSWORD_COLUMN, nullable = false)
    private String passwordHash;

    @Column(name = MailUserEntity_.ENABLED_COLUMN, nullable = false)
    private Boolean isEnabled;

    @ManyToMany(mappedBy = MailboxEntity_.USERS_RELATION)
    private Set<MailboxEntity> mailboxes;

    @ManyToMany(mappedBy = MailAddressEntity_.SENDERS_RELATION)
    private Set<MailAddressEntity> permittedAddresses;

    public Optional<MailDomainEntity> getDomain( )
    {
        return Optional.ofNullable(this.domain);
    }

    public Optional<Set<MailboxEntity>> getMailboxes( )
    {
        return Optional.ofNullable(this.mailboxes);
    }

    public Optional<Set<MailAddressEntity>> getPermittedAddresses( )
    {
        return Optional.ofNullable(this.permittedAddresses);
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