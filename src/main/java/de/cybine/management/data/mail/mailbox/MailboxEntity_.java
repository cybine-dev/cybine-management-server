package de.cybine.management.data.mail.mailbox;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.util.datasource.*;
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

    // @formatter:off
    public static final DatasourceField ID               =
            DatasourceField.property(MailboxEntity.class, "id", Long.class);
    public static final DatasourceField NAME             =
            DatasourceField.property(MailboxEntity.class, "name", String.class);
    public static final DatasourceField DESCRIPTION      =
            DatasourceField.property(MailboxEntity.class, "description", String.class);
    public static final DatasourceField ENABLED          =
            DatasourceField.property(MailboxEntity.class, "isEnabled", Boolean.class);
    public static final DatasourceField QUOTA            =
            DatasourceField.property(MailboxEntity.class, "quota", Long.class);
    public static final DatasourceField SOURCE_ADDRESSES =
            DatasourceField.property(MailboxEntity.class , "sourceAddresses", MailboxEntity.class);
    public static final DatasourceField USERS            =
            DatasourceField.property(MailboxEntity.class, "users", MailUserEntity.class);
    // @formatter:on

    public static final String SOURCE_ADDRESSES_RELATION = "sourceAddresses";
    public static final String USERS_RELATION            = "users";

    public static volatile SingularAttribute<MailboxEntity, Long>         id;
    public static volatile SingularAttribute<MailboxEntity, String>       name;
    public static volatile SingularAttribute<MailboxEntity, String>       description;
    public static volatile SingularAttribute<MailboxEntity, Boolean>      isEnabled;
    public static volatile SingularAttribute<MailboxEntity, Long>         quota;
    public static volatile SetAttribute<MailboxEntity, MailAddressEntity> sourceAddresses;
    public static volatile SetAttribute<MailboxEntity, MailUserEntity>    users;
}
