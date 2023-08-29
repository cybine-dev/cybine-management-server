package de.cybine.management.data.mail.forwarding;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.util.converter.*;

public class MailForwardingMapper implements EntityMapper<MailForwardingEntity, MailForwarding>
{
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
    public MailForwardingEntity toEntity(MailForwarding data, ConversionHelper helper)
    {
        return MailForwardingEntity.builder()
                                   .forwardingAddressId(data.getForwardingAddressId().getValue())
                                   .forwardingAddress(helper.toItem(MailAddress.class, MailAddressEntity.class)
                                                            .map(data::getForwardingAddress))
                                   .receiverAddressId(data.getReceiverAddressId().getValue())
                                   .receiverAddress(helper.toItem(MailAddress.class, MailAddressEntity.class)
                                                          .map(data::getReceiverAddress))
                                   .startsAt(data.getStartsAt().orElse(null))
                                   .endsAt(data.getEndsAt().orElse(null))
                                   .build();
    }

    @Override
    public MailForwarding toData(MailForwardingEntity entity, ConversionHelper helper)
    {
        return MailForwarding.builder()
                             .forwardingAddressId(MailAddressId.of(entity.getForwardingAddressId()))
                             .forwardingAddress(helper.toItem(MailAddressEntity.class, MailAddress.class)
                                                      .apply(entity::getForwardingAddress))
                             .receiverAddressId(MailAddressId.of(entity.getReceiverAddressId()))
                             .receiverAddress(helper.toItem(MailAddressEntity.class, MailAddress.class)
                                                    .apply(entity::getReceiverAddress))
                             .startsAt(entity.getStartsAt())
                             .endsAt(entity.getEndsAt())
                             .build();
    }
}
