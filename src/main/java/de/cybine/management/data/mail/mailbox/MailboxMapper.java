package de.cybine.management.data.mail.mailbox;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.data.util.primitive.*;
import de.cybine.management.util.converter.*;
import jakarta.enterprise.context.*;
import lombok.*;

@ApplicationScoped
@RequiredArgsConstructor
public class MailboxMapper implements EntityMapper<MailboxEntity, Mailbox>
{
    private final ConverterRegistry registry;

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
    public MailboxEntity toEntity(Mailbox data, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(data).orElse(null);
        if (node == null)
            return null;

        return MailboxEntity.builder()
                            .id(data.findId().map(Id::getValue).orElse(null))
                            .name(data.getName())
                            .description(data.getDescription())
                            .isEnabled(data.isEnabled())
                            .quota(data.getQuota())
                            .sourceAddresses(
                                    this.getAddressMapper().toEntitySet(data.getSourceAddresses().orElse(null), node))
                            .users(this.getUserMapper().toEntitySet(data.getUsers().orElse(null), node))
                            .build();
    }

    @Override
    public Mailbox toData(MailboxEntity entity, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(entity).orElse(null);
        if (node == null)
            return null;

        return Mailbox.builder()
                      .id(MailboxId.of(entity.getId()))
                      .name(entity.getName())
                      .description(entity.getDescription())
                      .isEnabled(entity.getIsEnabled())
                      .quota(entity.getQuota())
                      .sourceAddresses(EntityMapper.mapInitialized(entity::getSourceAddresses, null,
                              this.getAddressMapper()::toDataSet, node))
                      .users(EntityMapper.mapInitialized(entity::getUsers, null, this.getUserMapper()::toDataSet, node))
                      .build();
    }

    private EntityMapper<MailAddressEntity, MailAddress> getAddressMapper( )
    {
        return this.registry.findEntityMapper(MailAddressEntity.class, MailAddress.class).orElseThrow();
    }

    private EntityMapper<MailUserEntity, MailUser> getUserMapper( )
    {
        return this.registry.findEntityMapper(MailUserEntity.class, MailUser.class).orElseThrow();
    }
}
