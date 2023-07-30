package de.cybine.management.data.mail.domain;

import de.cybine.management.converter.*;
import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.tls.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.util.*;
import io.quarkus.hibernate.orm.panache.*;
import jakarta.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.*;
import java.util.*;

@Data
@NoArgsConstructor
@Table(name = DomainEntity_.TABLE)
@Entity(name = DomainEntity_.ENTITY)
@Builder(builderClassName = "Builder")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class DomainEntity extends PanacheEntityBase implements Serializable, WithId<Long>
{
    @Id
    @NotNull
    @GeneratedValue
    @EqualsAndHashCode.Include
    @Column(name = DomainEntity_.ID_COLUMN, nullable = false, unique = true)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = DomainEntity_.DOMAIN_COLUMN, nullable = false, unique = true)
    private String domain;

    @NotNull
    @Convert(converter = DomainActionConverter.class)
    @Column(name = DomainEntity_.ACTION_COLUMN, nullable = false)
    private DomainAction action;

    @Nullable
    @ToString.Exclude
    @OneToOne(mappedBy = TLSPolicyEntity_.DOMAIN, optional = false, fetch = FetchType.LAZY)
    private TLSPolicyEntity tlsPolicy;

    @NotNull
    @ToString.Exclude
    @OneToMany(mappedBy = UserEntity_.DOMAIN)
    private Set<UserEntity> users;

    @NotNull
    @ToString.Exclude
    @OneToMany(mappedBy = AddressEntity_.DOMAIN)
    private Set<AddressEntity> addresses;

    public static PanacheQuery<DomainEntity> fetch( )
    {
        return find(
                String.format("SELECT item FROM %s item %s", DomainEntity_.ENTITY, getRelationFetchQuery()));
    }

    public static PanacheQuery<DomainEntity> fetchById(Collection<Long> ids)
    {
        return find(String.format("SELECT item FROM %s item %s WHERE item.%s IN :ids", DomainEntity_.ENTITY,
                getRelationFetchQuery(), DomainEntity_.ID_COLUMN), ids);
    }

    public static PanacheQuery<DomainEntity> fetchByDomain(Collection<String> domains)
    {
        return find(
                String.format("SELECT item FROM %s item %s WHERE item.%s IN :domains", DomainEntity_.ENTITY,
                        getRelationFetchQuery(), DomainEntity_.DOMAIN_COLUMN), domains);
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