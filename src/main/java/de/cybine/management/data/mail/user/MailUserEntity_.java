package de.cybine.management.data.mail.user;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.mail.mailbox.*;
import de.cybine.management.util.api.datasource.*;
import jakarta.persistence.metamodel.*;
import lombok.experimental.*;

@UtilityClass
@SuppressWarnings("unused")
@StaticMetamodel(MailUserEntity.class)
public class MailUserEntity_
{
    public static final String TABLE  = "users";
    public static final String ENTITY = "MailUser";

    public static final String ID_COLUMN        = "id";
    public static final String DOMAIN_ID_COLUMN = "domain_id";
    public static final String USERNAME_COLUMN  = "username";
    public static final String PASSWORD_COLUMN  = "password";
    public static final String ENABLED_COLUMN   = "enabled";

    // @formatter:off
    public static final DatasourceField ID                  =
            DatasourceField.property(MailUserEntity.class, "id", Long.class);
    public static final DatasourceField DOMAIN_ID           =
            DatasourceField.property(MailUserEntity.class, "domainId", Long.class);
    public static final DatasourceField DOMAIN              =
            DatasourceField.property(MailUserEntity.class, "domain", MailDomainEntity.class);
    public static final DatasourceField USERNAME            =
            DatasourceField.property(MailUserEntity.class, "username", String.class);
    public static final DatasourceField PASSWORD_HASH       =
            DatasourceField.property(MailUserEntity.class, "passwordHash", String.class);
    public static final DatasourceField IS_ENABLED          =
            DatasourceField.property(MailUserEntity.class, "isEnabled", Boolean.class);
    public static final DatasourceField MAILBOXES           =
            DatasourceField.property(MailUserEntity.class, "mailboxes", MailboxEntity.class);
    public static final DatasourceField PERMITTED_ADDRESSES =
            DatasourceField.property(MailUserEntity.class, "permittedAddresses", MailAddressEntity.class);
    // @formatter:on

    public static final String DOMAIN_RELATION              = "domain";
    public static final String MAILBOXES_RELATION           = "mailboxes";
    public static final String PERMITTED_ADDRESSES_RELATION = "permittedAddresses";

    public static volatile SingularAttribute<MailUserEntity, Long>             id;
    public static volatile SingularAttribute<MailUserEntity, Long>             domainId;
    public static volatile SingularAttribute<MailUserEntity, String>           username;
    public static volatile SingularAttribute<MailUserEntity, String>           passwordHash;
    public static volatile SingularAttribute<MailUserEntity, Boolean>          isEnabled;
    public static volatile SingularAttribute<MailUserEntity, MailDomainEntity> domain;
    public static volatile SetAttribute<MailUserEntity, MailboxEntity>         mailboxes;
    public static volatile SetAttribute<MailUserEntity, MailAddressEntity>     permittedAddresses;
}
