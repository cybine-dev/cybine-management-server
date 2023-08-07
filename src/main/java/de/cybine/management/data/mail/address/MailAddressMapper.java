package de.cybine.management.data.mail.address;

import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.mail.forwarding.*;
import de.cybine.management.data.mail.mailbox.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.data.util.primitive.*;
import de.cybine.management.util.converter.*;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class MailAddressMapper implements EntityMapper<MailAddressEntity, MailAddress>
{
    private final ConverterRegistry registry;

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
    public MailAddressEntity toEntity(MailAddress data, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(data).orElse(null);
        if (node == null)
            return null;

        return MailAddressEntity.builder()
                                .id(data.findId().map(Id::getValue).orElse(null))
                                .domainId(data.getDomainId().getValue())
                                .domain(this.getDomainMapper().toEntity(data.getDomain().orElse(null), node))
                                .name(data.getName())
                                .action(data.getAction())
                                .forwardsTo(
                                        this.getForwardingMapper().toEntitySet(data.getForwardsTo().orElse(null), node))
                                .receivesFrom(this.getForwardingMapper()
                                                  .toEntitySet(data.getReceivesFrom().orElse(null), node))
                                .mailboxes(this.getMailboxMapper().toEntitySet(data.getMailboxes().orElse(null), node))
                                .senders(this.getUserMapper().toEntitySet(data.getSenders().orElse(null), node))
                                .build();
    }

    @Override
    public MailAddress toData(MailAddressEntity entity, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(entity).orElse(null);
        if (node == null)
            return null;

        return MailAddress.builder()
                          .id(MailAddressId.of(entity.getId()))
                          .domainId(MailDomainId.of(entity.getDomainId()))
                          .domain(EntityMapper.mapInitialized(entity::getDomain, null, this.getDomainMapper()::toData,
                                  node))
                          .name(entity.getName())
                          .action(entity.getAction())
                          .forwardsTo(EntityMapper.mapInitialized(entity::getForwardsTo, null,
                                  this.getForwardingMapper()::toDataSet, node))
                          .receivesFrom(EntityMapper.mapInitialized(entity::getReceivesFrom, null,
                                  this.getForwardingMapper()::toDataSet, node))
                          .mailboxes(EntityMapper.mapInitialized(entity::getMailboxes, null,
                                  this.getMailboxMapper()::toDataSet, node))
                          .senders(
                                  EntityMapper.mapInitialized(entity::getSenders, null, this.getUserMapper()::toDataSet,
                                          node))
                          .build();
    }

    private EntityMapper<MailDomainEntity, MailDomain> getDomainMapper( )
    {
        return this.registry.findEntityMapper(MailDomainEntity.class, MailDomain.class).orElseThrow();
    }

    private EntityMapper<MailForwardingEntity, MailForwarding> getForwardingMapper( )
    {
        return this.registry.findEntityMapper(MailForwardingEntity.class, MailForwarding.class).orElseThrow();
    }

    private EntityMapper<MailboxEntity, Mailbox> getMailboxMapper( )
    {
        return this.registry.findEntityMapper(MailboxEntity.class, Mailbox.class).orElseThrow();
    }

    private EntityMapper<MailUserEntity, MailUser> getUserMapper( )
    {
        return this.registry.findEntityMapper(MailUserEntity.class, MailUser.class).orElseThrow();
    }
}
