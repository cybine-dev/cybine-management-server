package de.cybine.management.data.mail.mailbox;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.data.util.primitive.*;
import de.cybine.management.util.converter.*;

public class MailboxMapper implements EntityMapper<MailboxEntity, Mailbox>
{
    @Override
    public Class<MailboxEntity> getEntityType( )
    {
        return MailboxEntity.class;
    }

    @Override
    public Class<Mailbox> getDataType( )
    {
        return Mailbox.class;
    }

    @Override
    public MailboxEntity toEntity(Mailbox data, ConversionHelper helper)
    {
        return MailboxEntity.builder()
                            .id(data.findId().map(Id::getValue).orElse(null))
                            .name(data.getName())
                            .description(data.getDescription())
                            .isEnabled(data.isEnabled())
                            .quota(data.getQuota())
                            .sourceAddresses(helper.toSet(MailAddress.class, MailAddressEntity.class)
                                                   .apply(data.getSourceAddresses().orElse(null)))
                            .users(helper.toSet(MailUser.class, MailUserEntity.class)
                                         .apply(data.getUsers().orElse(null)))
                            .build();
    }

    @Override
    public Mailbox toData(MailboxEntity entity, ConversionHelper helper)
    {
        return Mailbox.builder()
                      .id(MailboxId.of(entity.getId()))
                      .name(entity.getName())
                      .description(entity.getDescription())
                      .isEnabled(entity.getIsEnabled())
                      .quota(entity.getQuota())
                      .sourceAddresses(EntityMapper.mapInitialized(entity::getSourceAddresses,
                              helper.toSet(MailAddressEntity.class, MailAddress.class)))
                      .users(EntityMapper.mapInitialized(entity::getUsers,
                              helper.toSet(MailUserEntity.class, MailUser.class)))
                      .build();
    }
}
