package de.cybine.management.data.mail.domain;

import jakarta.persistence.metamodel.*;

@SuppressWarnings("unused")
@StaticMetamodel(DomainEntity.class)
public interface DomainEntity_
{
    String TABLE  = "domains";
    String ENTITY = "MailDomain";

    String ID_COLUMN     = "id";
    String DOMAIN_COLUMN = "domain";
    String ACTION_COLUMN = "action";

    String ID     = "id";
    String DOMAIN = "domain";
    String ACTION = "action";

    String TLS_POLICY = "tlsPolicy";
    String USERS      = "users";
    String ADDRESSES  = "addresses";

    String[] RELATIONS = new String[] { TLS_POLICY, USERS, ADDRESSES };
}
