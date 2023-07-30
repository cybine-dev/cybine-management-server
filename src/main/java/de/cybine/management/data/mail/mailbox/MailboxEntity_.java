package de.cybine.management.data.mail.mailbox;

import jakarta.persistence.metamodel.*;

@SuppressWarnings("unused")
@StaticMetamodel(MailboxEntity.class)
public interface MailboxEntity_
{
    String TABLE  = "mailboxes";
    String ENTITY = "Mailbox";

    String ID_COLUMN          = "id";
    String NAME_COLUMN        = "name";
    String DESCRIPTION_COLUMN = "description";
    String ENABLED_COLUMN     = "enabled";
    String QUOTA_COLUMN       = "quota";

    String ID          = "id";
    String NAME        = "name";
    String DESCRIPTION = "description";
    String ENABLED     = "enabled";
    String QUOTA       = "quota";

    String SOURCE_ADDRESSES = "sourceAddresses";
    String USERS            = "users";

    String[] RELATIONS = new String[] { SOURCE_ADDRESSES, USERS };
}
