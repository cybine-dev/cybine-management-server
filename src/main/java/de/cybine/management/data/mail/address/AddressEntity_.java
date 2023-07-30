package de.cybine.management.data.mail.address;

import jakarta.persistence.metamodel.*;

@SuppressWarnings("unused")
@StaticMetamodel(AddressEntity.class)
public interface AddressEntity_
{
    String TABLE  = "addresses";
    String ENTITY = "MailAddress";

    String ID_COLUMN        = "id";
    String DOMAIN_ID_COLUMN = "domain_id";
    String NAME_COLUMN      = "name";
    String ACTION_COLUMN    = "action";

    String ID        = "id";
    String DOMAIN_ID = "domain_id";
    String NAME      = "name";
    String ACTION    = "action";

    String DOMAIN        = "domain";
    String FORWARDS_TO   = "forwardsTo";
    String RECEIVES_FROM = "receivesFrom";
    String MAILBOXES     = "mailboxes";
    String SENDERS       = "senders";

    String[] RELATIONS = new String[] { DOMAIN, FORWARDS_TO, RECEIVES_FROM, MAILBOXES, SENDERS };
}