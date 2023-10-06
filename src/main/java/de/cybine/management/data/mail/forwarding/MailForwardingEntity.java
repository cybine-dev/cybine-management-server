package de.cybine.management.data.mail.forwarding;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.util.*;
import io.quarkus.hibernate.orm.panache.*;
import jakarta.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.*;
import java.time.*;
import java.util.*;

@Data
@NoArgsConstructor
@Table(name = MailForwardingEntity_.TABLE)
@Builder(builderClassName = "Generator")
@Entity(name = MailForwardingEntity_.ENTITY)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MailForwardingEntity extends PanacheEntityBase implements Serializable, WithId<MailForwarding.Id>
{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = MailForwardingEntity_.FORWARDING_ADDRESS_ID_COLUMN, insertable = false, updatable = false)
    private long forwardingAddressId;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = MailForwardingEntity_.FORWARDING_ADDRESS_ID_COLUMN, nullable = false)
    private MailAddressEntity forwardingAddress;

    @Id
    @NotNull
    @Column(name = MailForwardingEntity_.RECEIVER_ADDRESS_ID_COLUMN, insertable = false, updatable = false)
    private long receiverAddressId;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = MailForwardingEntity_.RECEIVER_ADDRESS_ID_COLUMN, nullable = false)
    private MailAddressEntity receiverAddress;

    @Nullable
    @Column(name = MailForwardingEntity_.STARTS_AT_COLUMN)
    private ZonedDateTime startsAt;

    @Nullable
    @Column(name = MailForwardingEntity_.ENDS_AT_COLUMN)
    private ZonedDateTime endsAt;

    @Override
    public MailForwarding.Id getId( )
    {
        return MailForwarding.Id.of(MailAddressId.of(this.forwardingAddressId),
                MailAddressId.of(this.receiverAddressId));
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
