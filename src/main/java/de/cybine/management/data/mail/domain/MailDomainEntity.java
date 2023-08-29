package de.cybine.management.data.mail.domain;

import de.cybine.management.converter.*;
import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.tls.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.util.*;
import jakarta.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.*;
import java.util.*;

@Data
@NoArgsConstructor
@Table(name = MailDomainEntity_.TABLE)
@Entity(name = MailDomainEntity_.ENTITY)
@Builder(builderClassName = "Generator")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class MailDomainEntity implements Serializable, WithId<Long>
{
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = MailDomainEntity_.ID_COLUMN, nullable = false, unique = true)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = MailDomainEntity_.DOMAIN_COLUMN, nullable = false, unique = true)
    private String domain;

    @NotNull
    @Convert(converter = DomainActionConverter.class)
    @Column(name = MailDomainEntity_.ACTION_COLUMN, nullable = false)
    private MailDomainAction action;

    @Nullable
    @ToString.Exclude
    @OneToOne(mappedBy = MailTLSPolicyEntity_.DOMAIN, optional = false, fetch = FetchType.LAZY)
    private MailTLSPolicyEntity tlsPolicy;

    @Nullable
    @ToString.Exclude
    @OneToMany(mappedBy = MailUserEntity_.DOMAIN)
    private Set<MailUserEntity> users;

    @Nullable
    @ToString.Exclude
    @OneToMany(mappedBy = MailAddressEntity_.DOMAIN)
    private Set<MailAddressEntity> addresses;
}