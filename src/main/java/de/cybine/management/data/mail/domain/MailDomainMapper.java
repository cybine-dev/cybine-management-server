package de.cybine.management.data.mail.domain;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.tls.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.data.util.*;
import de.cybine.management.data.util.primitive.*;
import de.cybine.management.util.converter.*;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class MailDomainMapper implements EntityMapper<MailDomainEntity, MailDomain>
{
    private final ConverterRegistry registry;

    @Override
    public Class<MailDomainEntity> getEntityType( )
    {
        return MailDomainEntity.class;
    }

    @Override
    public Class<MailDomain> getDataType( )
    {
        return MailDomain.class;
    }

    @Override
    public MailDomainEntity toEntity(MailDomain data, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(data).orElse(null);
        if (node == null)
            return null;

        return MailDomainEntity.builder()
                               .id(data.findId().map(Id::getValue).orElse(null))
                               .domain(data.getDomain().asString())
                               .action(data.getAction())
                               .tlsPolicy(this.getTlsPolicyMapper().toEntity(data.getTlsPolicy().orElse(null), node))
                               .users(this.getUserMapper().toEntitySet(data.getUsers().orElse(null), node))
                               .addresses(this.getAddressMapper().toEntitySet(data.getAddresses().orElse(null), node))
                               .build();
    }

    @Override
    public MailDomain toData(MailDomainEntity entity, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(entity).orElse(null);
        if (node == null)
            return null;

        return MailDomain.builder()
                         .id(MailDomainId.of(entity.getId()))
                         .domain(Domain.of(entity.getDomain()))
                         .action(entity.getAction())
                         .tlsPolicy(EntityMapper.mapInitialized(entity::getTlsPolicy, null,
                                 this.getTlsPolicyMapper()::toData, node))
                         .users(EntityMapper.mapInitialized(entity::getUsers, null, this.getUserMapper()::toDataSet,
                                 node))
                         .addresses(EntityMapper.mapInitialized(entity::getAddresses, null,
                                 this.getAddressMapper()::toDataSet, node))
                         .build();
    }

    private EntityMapper<MailTLSPolicyEntity, MailTLSPolicy> getTlsPolicyMapper( )
    {
        return this.registry.findEntityMapper(MailTLSPolicyEntity.class, MailTLSPolicy.class).orElseThrow();
    }

    private EntityMapper<MailUserEntity, MailUser> getUserMapper( )
    {
        return this.registry.findEntityMapper(MailUserEntity.class, MailUser.class).orElseThrow();
    }

    private EntityMapper<MailAddressEntity, MailAddress> getAddressMapper( )
    {
        return this.registry.findEntityMapper(MailAddressEntity.class, MailAddress.class).orElseThrow();
    }
}
