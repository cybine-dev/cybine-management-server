package de.cybine.management.data.mail.address;

import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.mail.mailbox.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.util.api.datasource.*;
import jakarta.persistence.metamodel.*;
import lombok.experimental.*;

@UtilityClass
@SuppressWarnings("unused")
@StaticMetamodel(MailAddressEntity.class)
public class MailAddressEntity_
{
    public static final String TABLE  = "addresses";
    public static final String ENTITY = "MailAddress";

    public static final String ID_COLUMN        = "id";
    public static final String DOMAIN_ID_COLUMN = "domain_id";
    public static final String NAME_COLUMN      = "name";
    public static final String ACTION_COLUMN    = "action";

    // @formatter:off
    public static final DatasourceField ID            =
            DatasourceField.property(MailAddressEntity.class, "id", Long.class);
    public static final DatasourceField DOMAIN_ID     =
            DatasourceField.property(MailAddressEntity.class, "domainId", Long.class);
    public static final DatasourceField DOMAIN        =
            DatasourceField.property(MailAddressEntity.class, "domain", MailDomainEntity.class);
    public static final DatasourceField NAME          =
            DatasourceField.property(MailAddressEntity.class, "name", String.class);
    public static final DatasourceField ACTION        =
            DatasourceField.property(MailAddressEntity.class, "action", MailDomainAction.class);
    public static final DatasourceField FORWARDS_TO   =
            DatasourceField.property(MailAddressEntity.class, "forwardsTo", MailAddressEntity.class);
    public static final DatasourceField RECEIVES_FROM =
            DatasourceField.property(MailAddressEntity.class, "receivesFrom", MailAddressEntity.class);
    public static final DatasourceField MAILBOXES     =
            DatasourceField.property(MailAddressEntity.class, "mailboxes", MailboxEntity.class);
    public static final DatasourceField SENDERS       =
            DatasourceField.property(MailAddressEntity.class, "senders", MailUserEntity.class);
    // @formatter:on

    public static final String DOMAIN_RELATION        = "domain";
    public static final String FORWARDS_TO_RELATION   = "forwardsTo";
    public static final String RECEIVES_FROM_RELATION = "receivesFrom";
    public static final String MAILBOXES_RELATION     = "mailboxes";
    public static final String SENDERS_RELATION       = "senders";

    public static volatile SingularAttribute<MailAddressEntity, Long>             id;
    public static volatile SingularAttribute<MailAddressEntity, Long>             domainId;
    public static volatile SingularAttribute<MailAddressEntity, MailDomainEntity> domain;
    public static volatile SingularAttribute<MailAddressEntity, String>           name;
    public static volatile SingularAttribute<MailAddressEntity, MailDomainAction> action;
    public static volatile SetAttribute<MailAddressEntity, MailAddressEntity>     forwardsTo;
    public static volatile SetAttribute<MailAddressEntity, MailAddressEntity>     receivesFrom;
    public static volatile SetAttribute<MailAddressEntity, MailboxEntity>         mailboxes;
    public static volatile SetAttribute<MailAddressEntity, MailUserEntity>        senders;
}