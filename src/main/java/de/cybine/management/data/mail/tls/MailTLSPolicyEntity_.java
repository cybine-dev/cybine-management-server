package de.cybine.management.data.mail.tls;

import de.cybine.management.data.mail.domain.*;
import de.cybine.management.util.api.datasource.*;
import jakarta.persistence.metamodel.*;
import lombok.experimental.*;

@UtilityClass
@SuppressWarnings("unused")
@StaticMetamodel(MailTLSPolicyEntity.class)
public class MailTLSPolicyEntity_
{
    public static final String TABLE  = "tls_policies";
    public static final String ENTITY = "MailTLSPolicy";

    public static final String ID_COLUMN        = "id";
    public static final String DOMAIN_ID_COLUMN = "domain_id";
    public static final String POLICY_COLUMN    = "policy";
    public static final String PARAMS_COLUMN    = "params";

    // @formatter:off
    public static final DatasourceField ID        =
            DatasourceField.property(MailTLSPolicyEntity.class, "id", Long.class);
    public static final DatasourceField DOMAIN_ID =
            DatasourceField.property(MailTLSPolicyEntity.class, "domainId", Long.class);
    public static final DatasourceField DOMAIN    =
            DatasourceField.property(MailTLSPolicyEntity.class, "domain", MailDomainEntity.class);
    public static final DatasourceField POLICY    =
            DatasourceField.property(MailTLSPolicyEntity.class, "type", MailTLSPolicyType.class);
    public static final DatasourceField PARAMS    =
            DatasourceField.property(MailTLSPolicyEntity.class, "params", String.class);
    // @formatter:on

    public static final String DOMAIN_RELATION = "domain";

    public static volatile SingularAttribute<MailTLSPolicyEntity, Long>              id;
    public static volatile SingularAttribute<MailTLSPolicyEntity, Long>              domainId;
    public static volatile SingularAttribute<MailTLSPolicyEntity, MailDomainEntity>  domain;
    public static volatile SingularAttribute<MailTLSPolicyEntity, MailTLSPolicyType> type;
    public static volatile SingularAttribute<MailTLSPolicyEntity, String>            params;
}
