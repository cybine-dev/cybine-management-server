package de.cybine.management.data.mail.forwarding;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.util.datasource.*;
import jakarta.persistence.metamodel.*;
import lombok.experimental.*;

import java.time.*;

@UtilityClass
@SuppressWarnings("unused")
@StaticMetamodel(MailForwardingEntity.class)
public class MailForwardingEntity_
{
    public static final String TABLE  = "forwarding";
    public static final String ENTITY = "MailForwarding";

    public static final String RECEIVER_ADDRESS_ID_COLUMN   = "receiver_address_id";
    public static final String FORWARDING_ADDRESS_ID_COLUMN = "forwarding_address_id";
    public static final String STARTS_AT_COLUMN             = "starts_at";
    public static final String ENDS_AT_COLUMN               = "ends_at";

    // @formatter:off
    public static final DatasourceField RECEIVER_ADDRESS_ID   =
            DatasourceField.property(MailForwardingEntity.class, "receiverAddressId", Long.class);
    public static final DatasourceField FORWARDING_ADDRESS    =
            DatasourceField.property(MailForwardingEntity.class, "forwardingAddress", MailAddressEntity.class);
    public static final DatasourceField FORWARDING_ADDRESS_ID =
            DatasourceField.property(MailForwardingEntity.class, "forwardingAddressId", Long.class);
    public static final DatasourceField RECEIVER_ADDRESS      =
            DatasourceField.property(MailForwardingEntity.class, "receiverAddress", MailAddressEntity.class);
    public static final DatasourceField STARTS_AT             =
            DatasourceField.property(MailForwardingEntity.class, "startsAt", ZonedDateTime.class);
    public static final DatasourceField ENDS_AT               =
            DatasourceField.property(MailForwardingEntity.class, "endsAt", ZonedDateTime.class);
    // @formatter:on

    public static final String FORWARDING_ADDRESS_RELATION = "forwardingAddress";
    public static final String RECEIVER_ADDRESS_RELATION   = "receiverAddress";

    public static volatile SingularAttribute<MailForwardingEntity, Long>              receiverAddressId;
    public static volatile SingularAttribute<MailForwardingEntity, MailAddressEntity> receiverAddress;
    public static volatile SingularAttribute<MailForwardingEntity, Long>              forwardingAddressId;
    public static volatile SingularAttribute<MailForwardingEntity, MailAddressEntity> forwardingAddress;
    public static volatile SingularAttribute<MailForwardingEntity, ZonedDateTime>     startsAt;
    public static volatile SingularAttribute<MailForwardingEntity, ZonedDateTime>     endsAt;
}
