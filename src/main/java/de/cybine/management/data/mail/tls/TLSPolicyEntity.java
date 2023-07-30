package de.cybine.management.data.mail.tls;

import de.cybine.management.converter.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.util.*;
import io.quarkus.hibernate.orm.panache.*;
import jakarta.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.*;

@Data
@NoArgsConstructor
@Table(name = TLSPolicyEntity_.TABLE)
@Builder(builderClassName = "Builder")
@Entity(name = TLSPolicyEntity_.ENTITY)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class TLSPolicyEntity extends PanacheEntityBase implements Serializable, WithId<Long>
{
    @Id
    @NotNull
    @GeneratedValue
    @EqualsAndHashCode.Include
    @Column(name = TLSPolicyEntity_.ID_COLUMN, nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = TLSPolicyEntity_.DOMAIN_ID_COLUMN, nullable = false, insertable = false, updatable = false)
    private long domainId;

    @NotNull
    @ToString.Exclude
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = TLSPolicyEntity_.DOMAIN_ID_COLUMN, nullable = false)
    private DomainEntity domain;

    @NotNull
    @Convert(converter = TLSPolicyTypeConverter.class)
    @Column(name = TLSPolicyEntity_.POLICY_COLUMN, nullable = false)
    private TLSPolicyType type;

    @Nullable
    @Size(max = 255)
    @Column(name = TLSPolicyEntity_.PARAMS_COLUMN)
    private String params;
}
