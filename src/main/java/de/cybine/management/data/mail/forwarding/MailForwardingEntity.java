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

@Data
@NoArgsConstructor
@Table(name = MailForwardingEntity_.TABLE)
@Builder(builderClassName = "Builder")
@Entity(name = MailForwardingEntity_.ENTITY)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class MailForwardingEntity extends PanacheEntityBase implements Serializable, WithId<MailForwarding.Id>
{
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
}
