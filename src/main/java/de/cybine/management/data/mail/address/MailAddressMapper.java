package de.cybine.management.data.mail.address;

import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.mail.forwarding.*;
import de.cybine.management.data.mail.mailbox.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.util.converter.*;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class MailAddressMapper implements EntityMapper<AddressEntity, Address>
{
    private final ConverterRegistry registry;

    @Override
    public Class<AddressEntity> getEntityType( )
    {
        return AddressEntity.class;
    }

    @Override
    public Class<Address> getDataType( )
    {
        return Address.class;
    }

    @Override
    public AddressEntity toEntity(Address data, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(data).orElse(null);
        if (node == null)
            return null;

        return AddressEntity.builder()
                            .id(data.getId())
                            .domainId(data.getDomainId())
                            .domain(this.getDomainMapper().toEntity(data.getDomain().orElse(null), node))
                            .name(data.getName())
                            .action(data.getAction())
                            .forwardsTo(this.getForwardingMapper().toEntitySet(data.getForwardsTo().orElse(null), node))
                            .receivesFrom(
                                    this.getForwardingMapper().toEntitySet(data.getReceivesFrom().orElse(null), node))
                            .mailboxes(this.getMailboxMapper().toEntitySet(data.getMailboxes().orElse(null), node))
                            .senders(this.getUserMapper().toEntitySet(data.getSenders().orElse(null), node))
                            .build();
    }

    @Override
    public Address toData(AddressEntity entity, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(entity).orElse(null);
        if (node == null)
            return null;

        return Address.builder()
                      .id(entity.getId())
                      .domainId(entity.getDomainId())
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
                      .senders(EntityMapper.mapInitialized(entity::getSenders, null, this.getUserMapper()::toDataSet,
                              node))
                      .build();
    }

    private EntityMapper<DomainEntity, Domain> getDomainMapper( )
    {
        return this.registry.findEntityMapper(DomainEntity.class, Domain.class).orElseThrow();
    }

    private EntityMapper<ForwardingEntity, Forwarding> getForwardingMapper( )
    {
        return this.registry.findEntityMapper(ForwardingEntity.class, Forwarding.class).orElseThrow();
    }

    private EntityMapper<MailboxEntity, Mailbox> getMailboxMapper( )
    {
        return this.registry.findEntityMapper(MailboxEntity.class, Mailbox.class).orElseThrow();
    }

    private EntityMapper<UserEntity, User> getUserMapper( )
    {
        return this.registry.findEntityMapper(UserEntity.class, User.class).orElseThrow();
    }
}
