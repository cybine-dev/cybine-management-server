package de.cybine.management.data.mail.domain;

import de.cybine.management.converter.*;
import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.tls.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.util.*;
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
public class MailDomainEntity implements Serializable, WithId<Long>
{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = MailDomainEntity_.ID_COLUMN, nullable = false, unique = true)
    private Long id;

    @Size(max = 255)
    @Column(name = MailDomainEntity_.DOMAIN_COLUMN, nullable = false, unique = true)
    private String domain;

    @Convert(converter = DomainActionConverter.class)
    @Column(name = MailDomainEntity_.ACTION_COLUMN, nullable = false)
    private MailDomainAction action;

    @ToString.Exclude
    @OneToOne(mappedBy = MailTLSPolicyEntity_.DOMAIN_RELATION, optional = false, fetch = FetchType.LAZY)
    private MailTLSPolicyEntity tlsPolicy;

    @ToString.Exclude
    @OneToMany(mappedBy = MailUserEntity_.DOMAIN_RELATION)
    private Set<MailUserEntity> users;

    @ToString.Exclude
    @OneToMany(mappedBy = MailAddressEntity_.DOMAIN_RELATION)
    private Set<MailAddressEntity> addresses;

    public Optional<MailTLSPolicyEntity> getTlsPolicy( )
    {
        return Optional.ofNullable(this.tlsPolicy);
    }

    public Optional<Set<MailUserEntity>> getUsers( )
    {
        return Optional.ofNullable(this.users);
    }

    public Optional<Set<MailAddressEntity>> getAddresses( )
    {
        return Optional.ofNullable(this.addresses);
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