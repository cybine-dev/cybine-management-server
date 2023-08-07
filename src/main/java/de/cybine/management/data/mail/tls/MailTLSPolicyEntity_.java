package de.cybine.management.data.mail.tls;

import de.cybine.management.data.mail.domain.*;
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

    public static final String ID        = "id";
    public static final String DOMAIN_ID = "domainId";
    public static final String POLICY    = "policy";
    public static final String PARAMS    = "params";

    public static final String DOMAIN = "domain";

    public static final String[] RELATIONS = new String[] { DOMAIN };

    public static volatile SingularAttribute<MailTLSPolicyEntity, Long>              id;
    public static volatile SingularAttribute<MailTLSPolicyEntity, Long>              domainId;
    public static volatile SingularAttribute<MailTLSPolicyEntity, MailDomainEntity>  domain;
    public static volatile SingularAttribute<MailTLSPolicyEntity, MailTLSPolicyType> policy;
    public static volatile SingularAttribute<MailTLSPolicyEntity, String>            params;
}
