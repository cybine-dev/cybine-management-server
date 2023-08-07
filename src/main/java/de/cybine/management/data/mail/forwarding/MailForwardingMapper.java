package de.cybine.management.data.mail.forwarding;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.util.converter.*;
import jakarta.enterprise.context.*;
import lombok.*;

@ApplicationScoped
@RequiredArgsConstructor
public class MailForwardingMapper implements EntityMapper<MailForwardingEntity, MailForwarding>
{
    private final ConverterRegistry registry;

    @Override
    public Class<MailForwardingEntity> getEntityType( )
    {
        return MailForwardingEntity.class;
    }

    @Override
    public Class<MailForwarding> getDataType( )
    {
        return MailForwarding.class;
    }

    @Override
    public MailForwardingEntity toEntity(MailForwarding data, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(data).orElse(null);
        if (node == null)
            return null;

        return MailForwardingEntity.builder()
                                   .forwardingAddressId(data.getForwardingAddressId().getValue())
                                   .forwardingAddress(this.getAddressMapper()
                                                          .toEntity(data.getForwardingAddress().orElse(null), node))
                                   .receiverAddressId(data.getReceiverAddressId().getValue())
                                   .receiverAddress(this.getAddressMapper()
                                                        .toEntity(data.getReceiverAddress().orElse(null), node))
                                   .startsAt(data.getStartsAt().orElse(null))
                                   .endsAt(data.getEndsAt().orElse(null))
                                   .build();
    }

    @Override
    public MailForwarding toData(MailForwardingEntity entity, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(entity).orElse(null);
        if (node == null)
            return null;

        return MailForwarding.builder()
                             .forwardingAddressId(MailAddressId.of(entity.getForwardingAddressId()))
                             .forwardingAddress(EntityMapper.mapInitialized(entity::getForwardingAddress, null,
                                     this.getAddressMapper()::toData, node))
                             .receiverAddressId(MailAddressId.of(entity.getReceiverAddressId()))
                             .receiverAddress(EntityMapper.mapInitialized(entity::getReceiverAddress, null,
                                     this.getAddressMapper()::toData, node))
                             .startsAt(entity.getStartsAt())
                             .endsAt(entity.getEndsAt())
                             .build();
    }

    private EntityMapper<MailAddressEntity, MailAddress> getAddressMapper( )
    {
        return this.registry.findEntityMapper(MailAddressEntity.class, MailAddress.class).orElseThrow();
    }
}
