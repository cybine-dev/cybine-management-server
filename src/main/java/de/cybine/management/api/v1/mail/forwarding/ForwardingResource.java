package de.cybine.management.api.v1.mail.forwarding;

import de.cybine.management.data.mail.forwarding.*;
import de.cybine.management.service.mail.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.api.response.*;
import jakarta.enterprise.context.*;
import lombok.*;
import org.jboss.resteasy.reactive.*;

import java.util.*;

@ApplicationScoped
@RequiredArgsConstructor
public class ForwardingResource implements ForwardingApi
{
    private final ForwardingService service;

    @Override
    public RestResponse<ApiResponse<List<MailForwarding>>> fetch(ApiQuery query)
    {
        return ApiResponse.<List<MailForwarding>>builder().value(this.service.fetch(query)).build().toResponse();
    }

    @Override
    public RestResponse<ApiResponse<MailForwarding>> fetchSingle(ApiQuery query)
    {
        return ApiResponse.<MailForwarding>builder()
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
