package de.cybine.management.data.mail.user;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.mail.mailbox.*;
import jakarta.persistence.metamodel.*;

@SuppressWarnings("unused")
@StaticMetamodel(UserEntity.class)
public abstract class UserEntity_
{
    public static final String TABLE  = "users";
    public static final String ENTITY = "MailUser";

    public static final String ID_COLUMN        = "id";
    public static final String DOMAIN_ID_COLUMN = "domain_id";
    public static final String USERNAME_COLUMN  = "username";
    public static final String PASSWORD_COLUMN  = "password";
    public static final String ENABLED_COLUMN   = "enabled";

    public static volatile SingularAttribute<UserEntity, Long>         id;
    public static volatile SingularAttribute<UserEntity, Long>         domainId;
    public static volatile SingularAttribute<UserEntity, String>       username;
    public static volatile SingularAttribute<UserEntity, String>       passwordHash;
    public static volatile SingularAttribute<UserEntity, Boolean>      isEnabled;
    public static volatile SingularAttribute<UserEntity, DomainEntity> domain;
    public static volatile SetAttribute<UserEntity, MailboxEntity>     mailboxes;
    public static volatile SetAttribute<UserEntity, AddressEntity>     permittedAddresses;

    public static final String ID            = "id";
    public static final String DOMAIN_ID     = "domainId";
    public static final String USERNAME      = "username";
    public static final String PASSWORD_HASH = "passwordHash";
    public static final String IS_ENABLED    = "isEnabled";

    public static final String DOMAIN              = "domain";
    public static final String MAILBOXES           = "mailboxes";
    public static final String PERMITTED_ADDRESSES = "permittedAddresses";

    public static final String[] RELATIONS = new String[] { DOMAIN, MAILBOXES, PERMITTED_ADDRESSES };
}
