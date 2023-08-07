package de.cybine.management.data.mail.mailbox;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.user.*;
import jakarta.persistence.metamodel.*;
import lombok.experimental.*;

@UtilityClass
@SuppressWarnings("unused")
@StaticMetamodel(MailboxEntity.class)
public class MailboxEntity_
{
    public static final String TABLE  = "mailboxes";
    public static final String ENTITY = "Mailbox";

    public static final String ID_COLUMN          = "id";
    public static final String NAME_COLUMN        = "name";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String ENABLED_COLUMN     = "enabled";
    public static final String QUOTA_COLUMN       = "quota";

    public static final String ID          = "id";
    public static final String NAME        = "name";
    public static final String DESCRIPTION = "description";
    public static final String ENABLED     = "enabled";
    public static final String QUOTA       = "quota";

    public static final String SOURCE_ADDRESSES = "sourceAddresses";
    public static final String USERS            = "users";

    public static final String[] RELATIONS = new String[] { SOURCE_ADDRESSES, USERS };

    public static volatile SingularAttribute<MailboxEntity, Long>         id;
    public static volatile SingularAttribute<MailboxEntity, String>       name;
    public static volatile SingularAttribute<MailboxEntity, String>       description;
    public static volatile SingularAttribute<MailboxEntity, Boolean>      enabled;
    public static volatile SingularAttribute<MailboxEntity, Long>         quota;
    public static volatile SetAttribute<MailboxEntity, MailAddressEntity> sourceAddresses;
    public static volatile SetAttribute<MailboxEntity, MailUserEntity>    users;
}
