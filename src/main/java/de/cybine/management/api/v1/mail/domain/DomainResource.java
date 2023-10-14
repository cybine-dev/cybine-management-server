package de.cybine.management.api.v1.mail.domain;

import de.cybine.management.api.v1.mail.domain.params.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.service.mail.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.api.response.*;
import jakarta.enterprise.context.*;
import lombok.*;
import org.jboss.resteasy.reactive.*;

import java.util.*;

@ApplicationScoped
@RequiredArgsConstructor
public class DomainResource implements DomainApi
{
    private final DomainService service;

    @Override
    public RestResponse<ApiResponse<List<MailDomain>>> fetch(ApiQuery query)
    {
        return ApiResponse.<List<MailDomain>>builder().value(this.service.fetch(query)).build().toResponse();
    }

    @Override
    public RestResponse<ApiResponse<MailDomain>> fetchSingle(ApiQuery query)
    {
        return ApiResponse.<MailDomain>builder()
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

    @Override
    public RestResponse<ApiResponse<MailDomain>> create(DomainCreationParameter domain)
    {
        return ApiResponse.<MailDomain>builder()
                          .status(RestResponse.Status.CREATED)
                          .value(this.service.addDomain(domain))
                          .build()
                          .toResponse();
    }
}
