package de.cybine.management.data.mail.address;

import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.mail.forwarding.*;
import de.cybine.management.data.mail.mailbox.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.data.util.primitive.*;
import de.cybine.management.util.converter.*;

public class MailAddressMapper implements EntityMapper<MailAddressEntity, MailAddress>
{
    @Override
    public Class<MailAddressEntity> getEntityType( )
    {
        return MailAddressEntity.class;
    }

    @Override
    public Class<MailAddress> getDataType( )
    {
        return MailAddress.class;
    }

    @Override
    public MailAddressEntity toEntity(MailAddress data, ConversionHelper helper)
    {
        return MailAddressEntity.builder()
                                .id(data.findId().map(Id::getValue).orElse(null))
                                .domainId(helper.optional(data::getDomainId).map(Id::getValue).orElse(null))
                                .domain(helper.toItem(MailDomain.class, MailDomainEntity.class).map(data::getDomain))
                                .name(data.getName())
                                .action(data.getAction())
                                .forwardsTo(helper.toSet(MailForwarding.class, MailForwardingEntity.class)
                                                  .map(data::getForwardsTo))
                                .receivesFrom(helper.toSet(MailForwarding.class, MailForwardingEntity.class)
                                                    .map(data.getReceivesFrom()))
                                .mailboxes(helper.toSet(Mailbox.class, MailboxEntity.class).map(data::getMailboxes))
                                .senders(helper.toSet(MailUser.class, MailUserEntity.class).map(data::getSenders))
                                .build();
    }

    @Override
    public MailAddress toData(MailAddressEntity entity, ConversionHelper helper)
    {
        return MailAddress.builder()
                          .id(MailAddressId.of(entity.getId()))
                          .domainId(helper.optional(entity::getDomainId).map(MailDomainId::of).orElse(null))
                          .domain(helper.toItem(MailDomainEntity.class, MailDomain.class).map(entity::getDomain))
                          .name(entity.getName())
                          .action(entity.getAction())
                          .forwardsTo(helper.toSet(MailForwardingEntity.class, MailForwarding.class)
                                            .map(entity::getForwardsTo))
                          .receivesFrom(helper.toSet(MailForwardingEntity.class, MailForwarding.class)
                                              .map(entity::getReceivesFrom))
                          .mailboxes(helper.toSet(MailboxEntity.class, Mailbox.class).map(entity::getMailboxes))
                          .senders(helper.toSet(MailUserEntity.class, MailUser.class).map(entity::getSenders))
                          .build();
    }
}
