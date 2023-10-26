package de.cybine.management.data.mail.user;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.mail.mailbox.*;
import de.cybine.management.data.util.*;
import de.cybine.management.data.util.password.*;
import de.cybine.management.data.util.primitive.*;
import de.cybine.management.util.converter.*;

public class MailUserMapper implements EntityMapper<MailUserEntity, MailUser>
{
    @Override
    public Class<MailUserEntity> getEntityType( )
    {
        return MailUserEntity.class;
    }

    @Override
    public Class<MailUser> getDataType( )
    {
        return MailUser.class;
    }

    @Override
    public MailUserEntity toEntity(MailUser data, ConversionHelper helper)
    {
        return MailUserEntity.builder()
                             .id(data.findId().map(Id::getValue).orElse(null))
                             .domainId(helper.optional(data::getDomainId).map(Id::getValue).orElse(null))
                             .domain(helper.toItem(MailDomain.class, MailDomainEntity.class).map(data::getDomain))
                             .username(helper.optional(data::getUsername).map(Username::asString).orElse(null))
                             .passwordHash(helper.optional(data::getPasswordHash)
                                                 .map(BCryptPasswordHash::asString)
                                                 .orElse(null))
                             .isEnabled(data.isEnabled())
                             .mailboxes(helper.toSet(Mailbox.class, MailboxEntity.class).map(data::getMailboxes))
                             .permittedAddresses(helper.toSet(MailAddress.class, MailAddressEntity.class)
                                                       .map(data::getPermittedAddresses))
                             .build();
    }

    @Override
    public MailUser toData(MailUserEntity entity, ConversionHelper helper)
    {
        return MailUser.builder()
                       .id(helper.optional(entity::getId).map(MailUserId::of).orElse(null))
                       .domainId(helper.optional(entity::getDomainId).map(MailDomainId::of).orElse(null))
                       .domain(helper.toItem(MailDomainEntity.class, MailDomain.class).map(entity::getDomain))
                       .username(helper.optional(entity::getUsername).map(Username::of).orElse(null))
                       .passwordHash(helper.optional(entity::getPasswordHash).map(BCryptPasswordHash::of).orElse(null))
                       .isEnabled(entity.getIsEnabled())
                       .mailboxes(helper.toSet(MailboxEntity.class, Mailbox.class).map(entity::getMailboxes))
                       .permittedAddresses(helper.toSet(MailAddressEntity.class, MailAddress.class)
                                                 .map(entity::getPermittedAddresses))
                       .build();
    }
}
