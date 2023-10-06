package de.cybine.management.data.mail.tls;

import de.cybine.management.converter.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.util.*;
import io.quarkus.hibernate.orm.panache.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.*;
import java.util.*;

@Data
@NoArgsConstructor
@Table(name = MailTLSPolicyEntity_.TABLE)
@Builder(builderClassName = "Generator")
@Entity(name = MailTLSPolicyEntity_.ENTITY)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MailTLSPolicyEntity extends PanacheEntityBase implements Serializable, WithId<Long>
{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = MailTLSPolicyEntity_.ID_COLUMN, nullable = false, unique = true)
    private Long id;

    @Column(name = MailTLSPolicyEntity_.DOMAIN_ID_COLUMN, nullable = false, insertable = false, updatable = false)
    private long domainId;

    @ToString.Exclude
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = MailTLSPolicyEntity_.DOMAIN_ID_COLUMN, nullable = false)
    private MailDomainEntity domain;

    @Convert(converter = TLSPolicyTypeConverter.class)
    @Column(name = MailTLSPolicyEntity_.POLICY_COLUMN, nullable = false)
    private MailTLSPolicyType type;

    @Size(max = 255)
    @Column(name = MailTLSPolicyEntity_.PARAMS_COLUMN)
    private String params;

    public Optional<MailDomainEntity> getDomain( )
    {
        return Optional.ofNullable(this.domain);
    }

    public Optional<String> getParams( )
    {
        return Optional.ofNullable(this.params);
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
