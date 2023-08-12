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
                                .domainId(data.getDomainId().getValue())
                                .domain(helper.toItem(MailDomain.class, MailDomainEntity.class)
                                              .apply(data.getDomain().orElse(null)))
                                .name(data.getName())
                                .action(data.getAction())
                                .forwardsTo(helper.toSet(MailForwarding.class, MailForwardingEntity.class)
                                                  .apply(data.getForwardsTo().orElse(null)))
                                .receivesFrom(helper.toSet(MailForwarding.class, MailForwardingEntity.class)
                                                    .apply(data.getReceivesFrom().orElse(null)))
                                .mailboxes(helper.toSet(Mailbox.class, MailboxEntity.class)
                                                 .apply(data.getMailboxes().orElse(null)))
                                .senders(helper.toSet(MailUser.class, MailUserEntity.class)
                                               .apply(data.getSenders().orElse(null)))
                                .build();
    }

    @Override
    public MailAddress toData(MailAddressEntity entity, ConversionHelper helper)
    {
        return MailAddress.builder()
                          .id(MailAddressId.of(entity.getId()))
                          .domainId(MailDomainId.of(entity.getDomainId()))
                          .domain(EntityMapper.mapInitialized(entity::getDomain,
                                  helper.toItem(MailDomainEntity.class, MailDomain.class)))
                          .name(entity.getName())
                          .action(entity.getAction())
                          .forwardsTo(EntityMapper.mapInitialized(entity::getForwardsTo,
                                  helper.toSet(MailForwardingEntity.class, MailForwarding.class)))
                          .receivesFrom(EntityMapper.mapInitialized(entity::getReceivesFrom,
                                  helper.toSet(MailForwardingEntity.class, MailForwarding.class)))
                          .mailboxes(EntityMapper.mapInitialized(entity::getMailboxes,
                                  helper.toSet(MailboxEntity.class, Mailbox.class)))
                          .senders(EntityMapper.mapInitialized(entity::getSenders,
                                  helper.toSet(MailUserEntity.class, MailUser.class)))
                          .build();
    }
}
