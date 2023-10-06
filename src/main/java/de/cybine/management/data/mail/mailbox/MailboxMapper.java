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
                            .description(data.getDescription().orElse(null))
                            .isEnabled(data.isEnabled())
                            .quota(data.getQuota())
                            .sourceAddresses(helper.toSet(MailAddress.class, MailAddressEntity.class)
                                                   .map(data::getSourceAddresses))
                            .users(helper.toSet(MailUser.class, MailUserEntity.class).map(data::getUsers))
                            .build();
    }

    @Override
    public Mailbox toData(MailboxEntity entity, ConversionHelper helper)
    {
        return Mailbox.builder()
                      .id(MailboxId.of(entity.getId()))
                      .name(entity.getName())
                      .description(entity.getDescription().orElse(null))
                      .isEnabled(entity.getIsEnabled())
                      .quota(entity.getQuota())
                      .sourceAddresses(helper.toSet(MailAddressEntity.class, MailAddress.class)
                                             .map(entity::getSourceAddresses))
                      .users(helper.toSet(MailUserEntity.class, MailUser.class).map(entity::getUsers))
                      .build();
    }
}
