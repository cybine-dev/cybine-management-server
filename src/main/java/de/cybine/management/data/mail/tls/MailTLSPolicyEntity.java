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
@Table(name = MailTLSPolicyEntity_.TABLE)
@Builder(builderClassName = "Generator")
@Entity(name = MailTLSPolicyEntity_.ENTITY)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class MailTLSPolicyEntity extends PanacheEntityBase implements Serializable, WithId<Long>
{
    @Id
    @NotNull
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = MailTLSPolicyEntity_.ID_COLUMN, nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = MailTLSPolicyEntity_.DOMAIN_ID_COLUMN, nullable = false, insertable = false, updatable = false)
    private long domainId;

    @NotNull
    @ToString.Exclude
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = MailTLSPolicyEntity_.DOMAIN_ID_COLUMN, nullable = false)
    private MailDomainEntity domain;

    @NotNull
    @Convert(converter = TLSPolicyTypeConverter.class)
    @Column(name = MailTLSPolicyEntity_.POLICY_COLUMN, nullable = false)
    private MailTLSPolicyType type;

    @Nullable
    @Size(max = 255)
    @Column(name = MailTLSPolicyEntity_.PARAMS_COLUMN)
    private String params;
}
