package de.cybine.management.data.mail.forwarding;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.mail.address.*;
import de.cybine.management.util.*;
import lombok.*;
import lombok.extern.jackson.*;

import java.io.*;
import java.time.*;
import java.util.*;

@Data
@Jacksonized
@Builder(builderClassName = "Builder")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Forwarding implements WithId<Forwarding.Id>
{
    @JsonProperty("forwarding_address_id")
    private final long forwardingAddressId;

    @JsonView(Views.Extended.class)
    @JsonProperty("forwarding_address")
    private final Address forwardingAddress;

    @JsonProperty("receiver_address_id")
    private final long receiverAddressId;

    @JsonView(Views.Extended.class)
    @JsonProperty("receiver_address")
    private final Address receiverAddress;

    @JsonProperty("starts_at")
    private final ZonedDateTime startsAt;

    @JsonProperty("ends_at")
    private final ZonedDateTime endsAt;

    @Override
    @JsonIgnore
    public Forwarding.Id getId( )
    {
        return Id.of(this.forwardingAddressId, this.receiverAddressId);
    }

    public Optional<Address> getForwardingAddress( )
    {
        return Optional.ofNullable(this.forwardingAddress);
    }

    public Optional<Address> getReceiverAddress( )
    {
        return Optional.ofNullable(this.receiverAddress);
    }

    public Optional<ZonedDateTime> getStartsAt( )
    {
        return Optional.ofNullable(this.startsAt);
    }

    public Optional<ZonedDateTime> getEndsAt( )
    {
        return Optional.ofNullable(this.endsAt);
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    public static class Id implements Serializable
    {
        private final long forwardingAddressId;

        private final long receiverAddressId;
    }
}
