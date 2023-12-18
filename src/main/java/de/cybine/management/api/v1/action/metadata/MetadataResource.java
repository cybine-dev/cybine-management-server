package de.cybine.management.api.v1.action.metadata;

import de.cybine.management.data.action.metadata.*;
import de.cybine.management.service.action.*;
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
public class MetadataResource implements MetadataApi
{
    private final MetadataService service;

    @Override
    public RestResponse<ApiResponse<ActionMetadata>> fetchById(UUID id)
    {
        return ApiResponse.<ActionMetadata>builder()
                          .value(this.service.fetchById(ActionMetadataId.of(id)).orElseThrow())
                          .build()
                          .toResponse();
    }

    @Override
    public RestResponse<ApiResponse<List<ActionMetadata>>> fetch(ApiQuery query)
    {
        return ApiResponse.<List<ActionMetadata>>builder().value(this.service.fetch(query)).build().toResponse();
    }

    @Override
    public RestResponse<ApiResponse<ActionMetadata>> fetchSingle(ApiQuery query)
    {
        return ApiResponse.<ActionMetadata>builder()
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
