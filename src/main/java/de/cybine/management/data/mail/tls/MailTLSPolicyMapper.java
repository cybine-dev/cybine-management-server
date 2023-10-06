package de.cybine.management.data.mail.tls;

import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.util.primitive.*;
import de.cybine.management.util.converter.*;

public class MailTLSPolicyMapper implements EntityMapper<MailTLSPolicyEntity, MailTLSPolicy>
{
    @Override
    public Class<MailTLSPolicyEntity> getEntityType( )
    {
        return MailTLSPolicyEntity.class;
    }

    @Override
    public Class<MailTLSPolicy> getDataType( )
    {
        return MailTLSPolicy.class;
    }

    @Override
    public MailTLSPolicyEntity toEntity(MailTLSPolicy data, ConversionHelper helper)
    {
        return MailTLSPolicyEntity.builder()
                                  .id(data.findId().map(Id::getValue).orElse(null))
                                  .domainId(data.getDomainId().getValue())
                                  .domain(helper.toItem(MailDomain.class, MailDomainEntity.class).map(data::getDomain))
                                  .type(data.getType())
                                  .params(data.getParams().orElse(null))
                                  .build();
    }

    @Override
    public MailTLSPolicy toData(MailTLSPolicyEntity entity, ConversionHelper helper)
    {
        return MailTLSPolicy.builder()
                            .id(MailTLSPolicyId.of(entity.getId()))
                            .domainId(MailDomainId.of(entity.getDomainId()))
                            .domain(helper.toItem(MailDomainEntity.class, MailDomain.class).map(entity::getDomain))
                            .type(entity.getType())
                            .params(entity.getParams().orElse(null))
                            .build();
    }
}
