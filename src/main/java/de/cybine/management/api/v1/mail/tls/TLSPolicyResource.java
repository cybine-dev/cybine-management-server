package de.cybine.management.api.v1.mail.tls;

import de.cybine.management.data.mail.tls.*;
import de.cybine.management.service.mail.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.api.response.*;
import io.quarkus.security.*;
import jakarta.enterprise.context.*;
import lombok.*;
import org.jboss.resteasy.reactive.*;

import java.util.*;

@Authenticated
@ApplicationScoped
@RequiredArgsConstructor
public class TLSPolicyResource implements TLSPolicyApi
{
    private final TLSPolicyService service;

    @Override
    public RestResponse<ApiResponse<List<MailTLSPolicy>>> fetch(ApiQuery query)
    {
        return ApiResponse.<List<MailTLSPolicy>>builder().value(this.service.fetch(query)).build().toResponse();
    }

    @Override
    public RestResponse<ApiResponse<MailTLSPolicy>> fetchSingle(ApiQuery query)
    {
        return ApiResponse.<MailTLSPolicy>builder()
                          .value(this.service.fetchSingle(query).orElseThrow())
                          .build()
                          .toResponse();
    }

    @Override
    public RestResponse<ApiResponse<List<ApiCountInfo>>> fetchCount(ApiCountQuery query)
    {
        return ApiResponse.<List<ApiCountInfo>>builder().value(this.service.fetchTotal(query)).build().toResponse();
    }

    @Override
    public RestResponse<ApiResponse<List<Object>>> fetchOptions(ApiOptionQuery query)
    {
        return ApiResponse.<List<Object>>builder().value(this.service.fetchOptions(query)).build().toResponse();
    }
}
