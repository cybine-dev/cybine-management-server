package de.cybine.management.data.mail.address;

import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.mail.mailbox.*;
import de.cybine.management.data.mail.user.*;
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

    public static final String ID        = "id";
    public static final String DOMAIN_ID = "domain_id";
    public static final String NAME      = "name";
    public static final String ACTION    = "action";

    public static final String DOMAIN        = "domain";
    public static final String FORWARDS_TO   = "forwardsTo";
    public static final String RECEIVES_FROM = "receivesFrom";
    public static final String MAILBOXES     = "mailboxes";
    public static final String SENDERS       = "senders";

    public static final String[] RELATIONS = new String[] { DOMAIN, FORWARDS_TO, RECEIVES_FROM, MAILBOXES, SENDERS };

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