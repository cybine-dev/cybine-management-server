package de.cybine.management.data.mail.domain;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.tls.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.util.converter.*;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class MailDomainMapper implements EntityMapper<DomainEntity, Domain>
{
    private final ConverterRegistry registry;

    @Override
    public Class<DomainEntity> getEntityType( )
    {
        return DomainEntity.class;
    }

    @Override
    public Class<Domain> getDataType( )
    {
        return Domain.class;
    }

    @Override
    public DomainEntity toEntity(Domain data, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(data).orElse(null);
        if (node == null)
            return null;

        return DomainEntity.builder()
                           .id(data.getId())
                           .domain(data.getDomain())
                           .action(data.getAction())
                           .tlsPolicy(this.getTlsPolicyMapper().toEntity(data.getTlsPolicy().orElse(null), node))
                           .users(this.getUserMapper().toEntitySet(data.getUsers().orElse(null), node))
                           .addresses(this.getAddressMapper().toEntitySet(data.getAddresses().orElse(null), node))
                           .build();
    }

    @Override
    public Domain toData(DomainEntity entity, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(entity).orElse(null);
        if (node == null)
            return null;

        return Domain.builder()
                     .id(entity.getId())
                     .domain(entity.getDomain())
                     .action(entity.getAction())
                     .tlsPolicy(
                             EntityMapper.mapInitialized(entity::getTlsPolicy, null, this.getTlsPolicyMapper()::toData,
                                     node))
                     .users(EntityMapper.mapInitialized(entity::getUsers, null, this.getUserMapper()::toDataSet, node))
                     .addresses(
                             EntityMapper.mapInitialized(entity::getAddresses, null, this.getAddressMapper()::toDataSet,
                                     node))
                     .build();
    }

    private EntityMapper<TLSPolicyEntity, TLSPolicy> getTlsPolicyMapper( )
    {
        return this.registry.findEntityMapper(TLSPolicyEntity.class, TLSPolicy.class).orElseThrow();
    }

    private EntityMapper<UserEntity, User> getUserMapper( )
    {
        return this.registry.findEntityMapper(UserEntity.class, User.class).orElseThrow();
    }

    private EntityMapper<AddressEntity, Address> getAddressMapper( )
    {
        return this.registry.findEntityMapper(AddressEntity.class, Address.class).orElseThrow();
    }
}
