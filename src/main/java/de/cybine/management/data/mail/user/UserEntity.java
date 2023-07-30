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
@Table(name = UserEntity_.TABLE)
@Entity(name = UserEntity_.ENTITY)
@Builder(builderClassName = "Builder")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserEntity extends PanacheEntityBase implements Serializable, WithId<Long>
{
    @Id
    @NotNull
    @GeneratedValue
    @EqualsAndHashCode.Include
    @Column(name = UserEntity_.ID_COLUMN, nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = UserEntity_.DOMAIN_ID_COLUMN, nullable = false, insertable = false, updatable = false)
    private long domainId;

    @NotNull
    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = UserEntity_.DOMAIN_ID_COLUMN, nullable = false)
    private DomainEntity domain;

    @NotNull
    @Size(min = 5, max = 255)
    @Column(name = UserEntity_.USERNAME_COLUMN, nullable = false)
    private String username;

    @NotNull
    @Size(max = 255)
    @Column(name = UserEntity_.PASSWORD_COLUMN, nullable = false)
    private String passwordHash;

    @NotNull
    @Column(name = UserEntity_.ENABLED_COLUMN, nullable = false)
    private Boolean isEnabled;

    @NotNull
    @ManyToMany(mappedBy = MailboxEntity_.USERS)
    private Set<MailboxEntity> mailboxes;

    @NotNull
    @ManyToMany(mappedBy = AddressEntity_.SENDERS)
    private Set<AddressEntity> permittedAddresses;
}