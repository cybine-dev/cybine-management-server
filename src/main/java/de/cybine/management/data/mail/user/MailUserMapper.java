package de.cybine.management.data.mail.user;

import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.util.*;
import de.cybine.management.data.util.password.*;
import de.cybine.management.data.util.primitive.*;
import de.cybine.management.util.converter.*;
import jakarta.enterprise.context.*;
import lombok.*;

@ApplicationScoped
@RequiredArgsConstructor
public class MailUserMapper implements EntityMapper<MailUserEntity, MailUser>
{
    private final ConverterRegistry registry;

    @Override
    public Class<MailUserEntity> getEntityType( )
    {
        return MailUserEntity.class;
    }

    @Override
    public Class<MailUser> getDataType( )
    {
        return MailUser.class;
    }

    @Override
    public MailUserEntity toEntity(MailUser data, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(data).orElse(null);
        if (node == null)
            return null;

        return MailUserEntity.builder()
                             .id(data.findId().map(Id::getValue).orElse(null))
                             .domainId(data.getDomainId().getValue())
                             .domain(this.getDomainMapper().toEntity(data.getDomain().orElse(null), node))
                             .username(data.getUsername().asString())
                             .passwordHash(data.getPasswordHash().asString())
                             .isEnabled(data.isEnabled())
                             .build();
    }

    @Override
    public MailUser toData(MailUserEntity entity, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(entity).orElse(null);
        if (node == null)
            return null;

        return MailUser.builder()
                       .id(MailUserId.of(entity.getId()))
                       .domainId(MailDomainId.of(entity.getDomainId()))
                       .domain(EntityMapper.mapInitialized(entity::getDomain, null, this.getDomainMapper()::toData,
                               node))
                       .username(Username.of(entity.getUsername()))
                       .passwordHash(BCryptPasswordHash.of(entity.getPasswordHash()))
                       .isEnabled(entity.getIsEnabled())
                       .build();
    }

    private EntityMapper<MailDomainEntity, MailDomain> getDomainMapper( )
    {
        return this.registry.findEntityMapper(MailDomainEntity.class, MailDomain.class).orElseThrow();
    }
}
