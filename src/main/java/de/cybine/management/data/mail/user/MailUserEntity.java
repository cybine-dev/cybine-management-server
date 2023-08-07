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
@Builder(builderClassName = "Builder")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class MailUserEntity extends PanacheEntityBase implements Serializable, WithId<Long>
{
    @Id
    @NotNull
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = MailUserEntity_.ID_COLUMN, nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = MailUserEntity_.DOMAIN_ID_COLUMN, nullable = false, insertable = false, updatable = false)
    private long domainId;

    @NotNull
    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = MailUserEntity_.DOMAIN_ID_COLUMN, nullable = false)
    private MailDomainEntity domain;

    @NotNull
    @Size(min = 5, max = 255)
    @Column(name = MailUserEntity_.USERNAME_COLUMN, nullable = false)
    private String username;

    @NotNull
    @Size(max = 255)
    @Column(name = MailUserEntity_.PASSWORD_COLUMN, nullable = false)
    private String passwordHash;

    @NotNull
    @Column(name = MailUserEntity_.ENABLED_COLUMN, nullable = false)
    private Boolean isEnabled;

    @NotNull
    @ManyToMany(mappedBy = MailboxEntity_.USERS)
    private Set<MailboxEntity> mailboxes;

    @NotNull
    @ManyToMany(mappedBy = MailAddressEntity_.SENDERS)
    private Set<MailAddressEntity> permittedAddresses;
}