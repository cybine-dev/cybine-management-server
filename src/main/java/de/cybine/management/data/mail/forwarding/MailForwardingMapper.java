package de.cybine.management.data.mail.forwarding;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.util.converter.*;
import jakarta.enterprise.context.*;
import lombok.*;

@ApplicationScoped
@RequiredArgsConstructor
public class MailForwardingMapper implements EntityMapper<ForwardingEntity, Forwarding>
{
    private final ConverterRegistry registry;

    @Override
    public Class<ForwardingEntity> getEntityType( )
    {
        return ForwardingEntity.class;
    }

    @Override
    public Class<Forwarding> getDataType( )
    {
        return Forwarding.class;
    }

    @Override
    public ForwardingEntity toEntity(Forwarding data, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(data).orElse(null);
        if (node == null)
            return null;

        return ForwardingEntity.builder()
                               .forwardingAddressId(data.getForwardingAddressId())
                               .forwardingAddress(
                                       this.getAddressMapper().toEntity(data.getForwardingAddress().orElse(null), node))
                               .receiverAddressId(data.getReceiverAddressId())
                               .receiverAddress(
                                       this.getAddressMapper().toEntity(data.getReceiverAddress().orElse(null), node))
                               .startsAt(data.getStartsAt().orElse(null))
                               .endsAt(data.getEndsAt().orElse(null))
                               .build();
    }

    @Override
    public Forwarding toData(ForwardingEntity entity, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(entity).orElse(null);
        if (node == null)
            return null;

        return Forwarding.builder()
                         .forwardingAddressId(entity.getForwardingAddressId())
                         .forwardingAddress(EntityMapper.mapInitialized(entity::getForwardingAddress, null,
                                 this.getAddressMapper()::toData, node))
                         .receiverAddressId(entity.getReceiverAddressId())
                         .receiverAddress(EntityMapper.mapInitialized(entity::getReceiverAddress, null,
                                 this.getAddressMapper()::toData, node))
                         .startsAt(entity.getStartsAt())
                         .endsAt(entity.getEndsAt())
                         .build();
    }

    private EntityMapper<AddressEntity, Address> getAddressMapper( )
    {
        return this.registry.findEntityMapper(AddressEntity.class, Address.class).orElseThrow();
    }
}
