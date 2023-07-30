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
@Table(name = ForwardingEntity_.TABLE)
@Builder(builderClassName = "Builder")
@Entity(name = ForwardingEntity_.ENTITY)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ForwardingEntity extends PanacheEntityBase implements Serializable, WithId<Forwarding.Id>
{
    @Id
    @NotNull
    @Column(name = ForwardingEntity_.FORWARDING_ADDRESS_ID_COLUMN, insertable = false, updatable = false)
    private long forwardingAddressId;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = ForwardingEntity_.FORWARDING_ADDRESS_ID_COLUMN, nullable = false)
    private AddressEntity forwardingAddress;

    @Id
    @NotNull
    @Column(name = ForwardingEntity_.RECEIVER_ADDRESS_ID_COLUMN, insertable = false, updatable = false)
    private long receiverAddressId;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = ForwardingEntity_.RECEIVER_ADDRESS_ID_COLUMN, nullable = false)
    private AddressEntity receiverAddress;

    @Nullable
    @Column(name = ForwardingEntity_.STARTS_AT_COLUMN)
    private ZonedDateTime startsAt;

    @Nullable
    @Column(name = ForwardingEntity_.ENDS_AT_COLUMN)
    private ZonedDateTime endsAt;

    @Override
    public Forwarding.Id getId( )
    {
        return Forwarding.Id.of(this.forwardingAddressId, this.receiverAddressId);
    }
}
