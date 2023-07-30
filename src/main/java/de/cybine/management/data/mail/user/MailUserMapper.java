package de.cybine.management.data.mail.user;

import de.cybine.management.data.mail.domain.*;
import de.cybine.management.util.converter.*;
import jakarta.enterprise.context.*;
import lombok.*;

@ApplicationScoped
@RequiredArgsConstructor
public class MailUserMapper implements EntityMapper<UserEntity, User>
{
    private final ConverterRegistry registry;

    @Override
    public Class<UserEntity> getEntityType( )
    {
        return UserEntity.class;
    }

    @Override
    public Class<User> getDataType( )
    {
        return User.class;
    }

    @Override
    public UserEntity toEntity(User data, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(data).orElse(null);
        if (node == null)
            return null;

        return UserEntity.builder()
                         .id(data.getId())
                         .domainId(data.getDomainId())
                         .domain(this.getDomainMapper().toEntity(data.getDomain().orElse(null), node))
                         .username(data.getUsername())
                         .passwordHash(data.getPasswordHash())
                         .isEnabled(data.isEnabled())
                         .build();
    }

    @Override
    public User toData(UserEntity entity, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(entity).orElse(null);
        if (node == null)
            return null;

        return User.builder()
                   .id(entity.getId())
                   .domainId(entity.getDomainId())
                   .domain(EntityMapper.mapInitialized(entity::getDomain, null, this.getDomainMapper()::toData, node))
                   .username(entity.getUsername())
                   .passwordHash(entity.getPasswordHash())
                   .isEnabled(entity.getIsEnabled())
                   .build();
    }

    private EntityMapper<DomainEntity, Domain> getDomainMapper( )
    {
        return this.registry.findEntityMapper(DomainEntity.class, Domain.class).orElseThrow();
    }
}
