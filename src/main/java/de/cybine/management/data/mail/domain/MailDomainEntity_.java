package de.cybine.management.data.mail.domain;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.tls.*;
import de.cybine.management.data.mail.user.*;
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

    public static final String ID     = "id";
    public static final String DOMAIN = "domain";
    public static final String ACTION = "action";

    public static final String TLS_POLICY = "tlsPolicy";
    public static final String USERS      = "users";
    public static final String ADDRESSES  = "addresses";

    public static final String[] RELATIONS = new String[] { TLS_POLICY, USERS, ADDRESSES };

    public static volatile SingularAttribute<MailDomainEntity, Long>                id;
    public static volatile SingularAttribute<MailDomainEntity, String>              domain;
    public static volatile SingularAttribute<MailDomainEntity, MailDomainAction>    action;
    public static volatile SingularAttribute<MailDomainEntity, MailTLSPolicyEntity> tlsPolicy;
    public static volatile SetAttribute<MailDomainEntity, MailUserEntity>           users;
    public static volatile SetAttribute<MailDomainEntity, MailAddressEntity>        addresses;
}
