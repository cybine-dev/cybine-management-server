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
public class MailForwarding implements WithId<MailForwarding.Id>
{
    @JsonProperty("forwarding_address_id")
    private final MailAddressId forwardingAddressId;

    @JsonView(Views.Extended.class)
    @JsonProperty("forwarding_address")
    private final MailAddress forwardingAddress;

    @JsonProperty("receiver_address_id")
    private final MailAddressId receiverAddressId;

    @JsonView(Views.Extended.class)
    @JsonProperty("receiver_address")
    private final MailAddress receiverAddress;

    @JsonProperty("starts_at")
    private final ZonedDateTime startsAt;

    @JsonProperty("ends_at")
    private final ZonedDateTime endsAt;

    @Override
    @JsonIgnore
    public MailForwarding.Id getId( )
    {
        return Id.of(this.forwardingAddressId, this.receiverAddressId);
    }

    public Optional<MailAddress> getForwardingAddress( )
    {
        return Optional.ofNullable(this.forwardingAddress);
    }

    public Optional<MailAddress> getReceiverAddress( )
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
        private final MailAddressId forwardingAddressId;

        private final MailAddressId receiverAddressId;
    }
}
