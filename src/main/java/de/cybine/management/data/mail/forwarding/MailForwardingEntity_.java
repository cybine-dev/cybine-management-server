package de.cybine.management.data.mail.forwarding;

import de.cybine.management.data.action.process.*;
import de.cybine.management.data.mail.address.*;
import jakarta.persistence.metamodel.*;
import lombok.experimental.*;

import java.time.*;

@UtilityClass
@SuppressWarnings("unused")
@StaticMetamodel(ActionProcessEntity.class)
public class MailForwardingEntity_
{
    public static final String TABLE  = "forwarding";
    public static final String ENTITY = "MailForwarding";

    public static final String RECEIVER_ADDRESS_ID_COLUMN   = "receiver_address_id";
    public static final String FORWARDING_ADDRESS_ID_COLUMN = "forwarding_address_id";
    public static final String STARTS_AT_COLUMN             = "starts_at";
    public static final String ENDS_AT_COLUMN               = "ends_at";

    public static final String RECEIVER_ADDRESS_ID   = "receiverAddressId";
    public static final String FORWARDING_ADDRESS_ID = "forwardingAddressId";
    public static final String STARTS_AT             = "startsAt";
    public static final String ENDS_AT               = "endsAt";

    public static final String FORWARDING_ADDRESS = "forwardingAddress";
    public static final String RECEIVER_ADDRESS   = "receiverAddress";

    public static final String[] RELATIONS = new String[] { FORWARDING_ADDRESS, RECEIVER_ADDRESS };

    public static volatile SingularAttribute<MailForwardingEntity, Long>              receiverAddressId;
    public static volatile SingularAttribute<MailForwardingEntity, MailAddressEntity> receiverAddress;
    public static volatile SingularAttribute<MailForwardingEntity, Long>              forwardingAddressId;
    public static volatile SingularAttribute<MailForwardingEntity, MailAddressEntity> forwardingAddress;
    public static volatile SingularAttribute<MailForwardingEntity, ZonedDateTime>     startedAt;
    public static volatile SingularAttribute<MailForwardingEntity, ZonedDateTime>     endsAt;
}
