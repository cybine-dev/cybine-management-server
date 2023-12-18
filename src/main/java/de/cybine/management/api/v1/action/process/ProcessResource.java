package de.cybine.management.api.v1.action.process;

import de.cybine.management.data.action.process.*;
import de.cybine.management.service.action.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.api.response.*;
import de.cybine.management.util.cloudevent.*;
import io.quarkus.security.*;
import jakarta.enterprise.context.*;
import lombok.*;
import org.jboss.resteasy.reactive.*;

import java.util.*;

@Authenticated
@ApplicationScoped
@RequiredArgsConstructor
public class ProcessResource implements ProcessApi
{
    private final ProcessService service;

    @Override
    public RestResponse<ApiResponse<ActionProcess>> fetchById(UUID id)
    {
        return ApiResponse.<ActionProcess>builder()
                          .value(this.service.fetchById(ActionProcessId.of(id)).orElseThrow())
                          .build()
                          .toResponse();
    }

    @Override
    public RestResponse<ApiResponse<ActionProcess>> fetchByEventId(String eventId)
    {
        return ApiResponse.<ActionProcess>builder()
                          .value(this.service.fetchByEventId(eventId).orElseThrow())
                          .build()
                          .toResponse();
    }

    @Override
    public RestResponse<ApiResponse<List<ActionProcess>>> fetchByCorrelationId(String correlationId)
    {
        return ApiResponse.<List<ActionProcess>>builder()
                          .value(this.service.fetchByCorrelationId(correlationId))
                          .build()
                          .toResponse();
    }

    @Override
    public RestResponse<ApiResponse<CloudEvent>> fetchCloudEventByEventId(String eventId)
    {
        return ApiResponse.<CloudEvent>builder()
                          .value(this.service.fetchAsCloudEventByEventId(eventId).orElseThrow())
                          .build()
                          .toResponse();
    }

    @Override
    public RestResponse<ApiResponse<List<CloudEvent>>> fetchCloudEventsByCorrelationId(String correlationId)
    {
        return ApiResponse.<List<CloudEvent>>builder()
                          .value(this.service.fetchAsCloudEventsByCorrelationId(correlationId))
                          .build()
                          .toResponse();
    }

    @Override
    public RestResponse<ApiResponse<List<ActionProcess>>> fetch(ApiQuery query)
    {
        return ApiResponse.<List<ActionProcess>>builder().value(this.service.fetch(query)).build().toResponse();
    }

    @Override
    public RestResponse<ApiResponse<ActionProcess>> fetchSingle(ApiQuery query)
    {
        return ApiResponse.<ActionProcess>builder()
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
