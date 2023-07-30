package de.cybine.management.data.mail.tls;

import de.cybine.management.data.mail.domain.*;
import de.cybine.management.util.converter.*;
import jakarta.enterprise.context.*;
import lombok.*;

@ApplicationScoped
@RequiredArgsConstructor
public class MailTLSPolicyMapper implements EntityMapper<TLSPolicyEntity, TLSPolicy>
{
    private final ConverterRegistry registry;

    @Override
    public Class<TLSPolicyEntity> getEntityType( )
    {
        return TLSPolicyEntity.class;
    }

    @Override
    public Class<TLSPolicy> getDataType( )
    {
        return TLSPolicy.class;
    }

    @Override
    public TLSPolicyEntity toEntity(TLSPolicy data, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(data).orElse(null);
        if (node == null)
            return null;

        return TLSPolicyEntity.builder()
                              .id(data.getId())
                              .domainId(data.getDomainId())
                              .domain(this.getDomainMapper().toEntity(data.getDomain().orElse(null), node))
                              .type(data.getType())
                              .params(data.getParams())
                              .build();
    }

    @Override
    public TLSPolicy toData(TLSPolicyEntity entity, ConverterTreeNode parentNode)
    {
        ConverterTreeNode node = parentNode.process(entity).orElse(null);
        if (node == null)
            return null;

        return TLSPolicy.builder()
                        .id(entity.getId())
                        .domainId(entity.getDomainId())
                        .domain(EntityMapper.mapInitialized(entity::getDomain, null, this.getDomainMapper()::toData,
                                node))
                        .type(entity.getType())
                        .params(entity.getParams())
                        .build();
    }

    private EntityMapper<DomainEntity, Domain> getDomainMapper( )
    {
        return this.registry.findEntityMapper(DomainEntity.class, Domain.class).orElseThrow();
    }
}
