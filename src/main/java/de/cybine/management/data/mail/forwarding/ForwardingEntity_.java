package de.cybine.management.data.mail.forwarding;

@SuppressWarnings("unused")
public interface ForwardingEntity_
{
    String TABLE  = "forwarding";
    String ENTITY = "MailForwarding";

    String RECEIVER_ADDRESS_ID_COLUMN   = "receiver_address_id";
    String FORWARDING_ADDRESS_ID_COLUMN = "forwarding_address_id";
    String STARTS_AT_COLUMN             = "starts_at";
    String ENDS_AT_COLUMN               = "ends_at";

    String RECEIVER_ADDRESS_ID   = "receiverAddressId";
    String FORWARDING_ADDRESS_ID = "forwardingAddressId";
    String STARTS_AT             = "startsAt";
    String ENDS_AT               = "endsAt";

    String FORWARDING_ADDRESS = "forwardingAddress";
    String RECEIVER_ADDRESS   = "receiverAddress";

    String[] RELATIONS = new String[] { FORWARDING_ADDRESS, RECEIVER_ADDRESS };
}
