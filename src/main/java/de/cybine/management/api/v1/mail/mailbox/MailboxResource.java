package de.cybine.management.api.v1.mail.mailbox;

import de.cybine.management.data.mail.mailbox.*;
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
public class MailboxResource implements MailboxApi
{
    private final MailboxService service;

    @Override
    public RestResponse<ApiResponse<List<Mailbox>>> fetch(ApiQuery query)
    {
        return ApiResponse.<List<Mailbox>>builder().value(this.service.fetch(query)).build().toResponse();
    }

    @Override
    public RestResponse<ApiResponse<Mailbox>> fetchSingle(ApiQuery query)
    {
        return ApiResponse.<Mailbox>builder().value(this.service.fetchSingle(query).orElseThrow()).build().toResponse();
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
