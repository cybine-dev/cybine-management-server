package de.cybine.management.data.mail.domain;

import io.quarkus.hibernate.orm.panache.*;
import jakarta.enterprise.context.*;
import lombok.*;

import java.util.*;

@ApplicationScoped
@RequiredArgsConstructor
public class MailDomainRepository implements PanacheRepository<MailDomainEntity>
{
    public PanacheQuery<MailDomainEntity> fetch( )
    {
        return find(String.format("SELECT item FROM %s item %s", MailDomainEntity_.ENTITY, getRelationFetchQuery()));
    }

    public PanacheQuery<MailDomainEntity> fetchById(Collection<Long> ids)
    {
        return find(String.format("SELECT item FROM %s item %s WHERE item.%s IN :ids", MailDomainEntity_.ENTITY,
                getRelationFetchQuery(), MailDomainEntity_.ID_COLUMN), ids);
    }

    public PanacheQuery<MailDomainEntity> fetchByDomain(Collection<String> domains)
    {
        return find(String.format("SELECT item FROM %s item %s WHERE item.%s IN :domains", MailDomainEntity_.ENTITY,
                getRelationFetchQuery(), MailDomainEntity_.DOMAIN_COLUMN), domains);
    }

    private String getRelationFetchQuery( )
    {
        List<String> relations = new ArrayList<>();
        for (String relation : MailDomainEntity_.RELATIONS)
        {
            relations.add(String.format("LEFT JOIN FETCH item.%s", relation));
        }

        return String.join(" ", relations);
    }
}
