package de.cybine.management.data.mail.domain;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.tls.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.util.datasource.*;
import jakarta.persistence.metamodel.*;
import lombok.experimental.*;

@UtilityClass
@SuppressWarnings("unused")
@StaticMetamodel(MailDomainEntity.class)
public class MailDomainEntity_
{
    public static final String TABLE  = "domains";
    public static final String ENTITY = "MailDomain";

    public static final String ID_COLUMN     = "id";
    public static final String DOMAIN_COLUMN = "domain";
    public static final String ACTION_COLUMN = "action";

    // @formatter:off
    public static final DatasourceField ID         =
            DatasourceField.property(MailDomainEntity.class, "id", Long.class);
    public static final DatasourceField DOMAIN     =
            DatasourceField.property(MailDomainEntity.class, "domain", String.class);
    public static final DatasourceField ACTION     =
            DatasourceField.property(MailDomainEntity.class, "action", MailDomainAction.class);
    public static final DatasourceField TLS_POLICY =
            DatasourceField.property(MailDomainEntity.class, "tlsPolicy", MailUserEntity.class);
    public static final DatasourceField USERS      =
            DatasourceField.property(MailDomainEntity.class, "users", MailUserEntity.class);
    public static final DatasourceField ADDRESSES  =
            DatasourceField.property(MailDomainEntity.class, "addresses", MailAddressEntity.class);
    // @formatter:on

    public static final String TLS_POLICY_RELATION = "tlsPolicy";
    public static final String USERS_RELATION      = "users";
    public static final String ADDRESSES_RELATION  = "addresses";

    public static volatile SingularAttribute<MailDomainEntity, Long>                id;
    public static volatile SingularAttribute<MailDomainEntity, String>              domain;
    public static volatile SingularAttribute<MailDomainEntity, MailDomainAction>    action;
    public static volatile SingularAttribute<MailDomainEntity, MailTLSPolicyEntity> tlsPolicy;
    public static volatile SetAttribute<MailDomainEntity, MailUserEntity>           users;
    public static volatile SetAttribute<MailDomainEntity, MailAddressEntity>        addresses;
}
