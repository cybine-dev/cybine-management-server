package de.cybine.management.data.mail.tls;

import jakarta.persistence.metamodel.*;

@SuppressWarnings("unused")
@StaticMetamodel(TLSPolicyEntity.class)
public interface TLSPolicyEntity_
{
    String TABLE  = "tls_policies";
    String ENTITY = "MailTLSPolicy";

    String ID_COLUMN        = "id";
    String DOMAIN_ID_COLUMN = "domain_id";
    String POLICY_COLUMN    = "policy";
    String PARAMS_COLUMN    = "params";

    String ID        = "id";
    String DOMAIN_ID = "domainId";
    String POLICY    = "policy";
    String PARAMS    = "params";

    String DOMAIN = "domain";

    String[] RELATIONS = new String[] { DOMAIN };
}
