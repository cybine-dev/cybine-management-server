package de.cybine.management.data.mail.domain;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.tls.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.data.util.*;
import de.cybine.management.data.util.primitive.*;
import de.cybine.management.util.converter.*;

public class MailDomainMapper implements EntityMapper<MailDomainEntity, MailDomain>
{
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
    public MailDomainEntity toEntity(MailDomain data, ConversionHelper helper)
    {
        return MailDomainEntity.builder()
                               .id(data.findId().map(Id::getValue).orElse(null))
                               .domain(data.getDomain().asString())
                               .action(data.getAction())
                               .tlsPolicy(helper.toItem(MailTLSPolicy.class, MailTLSPolicyEntity.class)
                                                .apply(data.getTlsPolicy().orElse(null)))
                               .users(helper.toSet(MailUser.class, MailUserEntity.class)
                                            .apply(data.getUsers().orElse(null)))
                               .addresses(helper.toSet(MailAddress.class, MailAddressEntity.class)
                                                .apply(data.getAddresses().orElse(null)))
                               .build();
    }

    @Override
    public MailDomain toData(MailDomainEntity entity, ConversionHelper helper)
    {
        return MailDomain.builder()
                         .id(MailDomainId.of(entity.getId()))
                         .domain(Domain.of(entity.getDomain()))
                         .action(entity.getAction())
                         .tlsPolicy(EntityMapper.mapInitialized(entity::getTlsPolicy,
                                 helper.toItem(MailTLSPolicyEntity.class, MailTLSPolicy.class)))
                         .users(EntityMapper.mapInitialized(entity::getUsers,
                                 helper.toSet(MailUserEntity.class, MailUser.class)))
                         .addresses(EntityMapper.mapInitialized(entity::getAddresses,
                                 helper.toSet(MailAddressEntity.class, MailAddress.class)))
                         .build();
    }
}
