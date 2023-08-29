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
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class MailAddressEntity extends PanacheEntityBase implements Serializable, WithId<Long>
{
    @Id
    @NotNull
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = MailAddressEntity_.ID, nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = MailAddressEntity_.DOMAIN_ID, nullable = false, insertable = false, updatable = false)
    private long domainId;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = MailAddressEntity_.DOMAIN_ID, nullable = false)
    private MailDomainEntity domain;

    @NotNull
    @Size(min = 5, max = 255)
    @Column(name = MailAddressEntity_.NAME, nullable = false)
    private String name;

    @NotNull
    @Convert(converter = AddressActionConverter.class)
    @Column(name = MailAddressEntity_.ACTION, nullable = false)
    private MailAddressAction action;

    @NotNull
    @OneToMany(mappedBy = MailForwardingEntity_.RECEIVER_ADDRESS)
    private Set<MailForwardingEntity> forwardsTo;

    @NotNull
    @OneToMany(mappedBy = MailForwardingEntity_.FORWARDING_ADDRESS)
    private Set<MailForwardingEntity> receivesFrom;

    @NotNull
    @ManyToMany(mappedBy = MailboxEntity_.SOURCE_ADDRESSES)
    private Set<MailboxEntity> mailboxes;

    @NotNull
    @ManyToMany
    @JoinTable(name = MailAddressPermission_.TABLE,
               joinColumns = @JoinColumn(name = MailAddressPermission_.ADDRESS_ID_COLUMN),
               inverseJoinColumns = @JoinColumn(name = MailAddressPermission_.USER_ID_COLUMN))
    private Set<MailUserEntity> senders;

    public static PanacheQuery<MailAddressEntity> fetch( )
    {
        return find(
                String.format("SELECT item FROM %s item %s", MailAddressEntity_.ENTITY, getRelationFetchQuery()));
    }

    private static String getRelationFetchQuery( )
    {
        List<String> relations = new ArrayList<>();
        for (String relation : MailAddressEntity_.RELATIONS)
        {
            relations.add(String.format("LEFT JOIN FETCH item.%s", relation));
        }

        return String.join(" ", relations);
    }
}
