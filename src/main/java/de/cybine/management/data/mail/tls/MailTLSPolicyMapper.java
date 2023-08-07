package de.cybine.management.data.mail.tls;

import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.util.primitive.*;
import de.cybine.management.util.converter.*;
import jakarta.enterprise.context.*;
import lombok.*;

@ApplicationScoped
@RequiredArgsConstructor
public class MailTLSPolicyMapper implements EntityMapper<MailTLSPolicyEntity, MailTLSPolicy>
{
    private final ConverterRegistry registry;

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
    public MailTLSPolicyEntity toEntity(MailTLSPolicy data, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(data).orElse(null);
        if (node == null)
            return null;

        return MailTLSPolicyEntity.builder()
                                  .id(data.findId().map(Id::getValue).orElse(null))
                                  .domainId(data.getDomainId().getValue())
                                  .domain(this.getDomainMapper().toEntity(data.getDomain().orElse(null), node))
                                  .type(data.getType())
                                  .params(data.getParams())
                                  .build();
    }

    @Override
    public MailTLSPolicy toData(MailTLSPolicyEntity entity, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(entity).orElse(null);
        if (node == null)
            return null;

        return MailTLSPolicy.builder()
                            .id(MailTLSPolicyId.of(entity.getId()))
                            .domainId(MailDomainId.of(entity.getDomainId()))
                            .domain(EntityMapper.mapInitialized(entity::getDomain, null, this.getDomainMapper()::toData,
                                    node))
                            .type(entity.getType())
                            .params(entity.getParams())
                            .build();
    }

    private EntityMapper<MailDomainEntity, MailDomain> getDomainMapper( )
    {
        return this.registry.findEntityMapper(MailDomainEntity.class, MailDomain.class).orElseThrow();
    }
}
