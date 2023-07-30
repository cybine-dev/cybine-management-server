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
@Table(name = AddressEntity_.TABLE)
@Entity(name = AddressEntity_.ENTITY)
@Builder(builderClassName = "Builder")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class AddressEntity extends PanacheEntityBase implements Serializable, WithId<Long>
{
    @Id
    @NotNull
    @GeneratedValue
    @EqualsAndHashCode.Include
    @Column(name = AddressEntity_.ID, nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = AddressEntity_.DOMAIN_ID, nullable = false, insertable = false, updatable = false)
    private long domainId;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = AddressEntity_.DOMAIN_ID, nullable = false)
    private DomainEntity domain;

    @NotNull
    @Size(min = 5, max = 255)
    @Column(name = AddressEntity_.NAME, nullable = false)
    private String name;

    @NotNull
    @Convert(converter = AddressActionConverter.class)
    @Column(name = AddressEntity_.ACTION, nullable = false)
    private AddressAction action;

    @NotNull
    @OneToMany(mappedBy = ForwardingEntity_.RECEIVER_ADDRESS)
    private Set<ForwardingEntity> forwardsTo;

    @NotNull
    @OneToMany(mappedBy = ForwardingEntity_.FORWARDING_ADDRESS)
    private Set<ForwardingEntity> receivesFrom;

    @NotNull
    @ManyToMany(mappedBy = MailboxEntity_.SOURCE_ADDRESSES)
    private Set<MailboxEntity> mailboxes;

    @NotNull
    @ManyToMany
    @JoinTable(name = AddressPermission_.TABLE,
               joinColumns = @JoinColumn(name = AddressPermission_.ADDRESS_ID_COLUMN),
               inverseJoinColumns = @JoinColumn(name = AddressPermission_.USER_ID_COLUMN))
    private Set<UserEntity> senders;

    public static PanacheQuery<AddressEntity> fetch( )
    {
        return find(
                String.format("SELECT item FROM %s item %s", AddressEntity_.ENTITY, getRelationFetchQuery()));
    }

    private static String getRelationFetchQuery( )
    {
        List<String> relations = new ArrayList<>();
        for (String relation : AddressEntity_.RELATIONS)
        {
            relations.add(String.format("LEFT JOIN FETCH item.%s", relation));
        }

        return String.join(" ", relations);
    }
}
