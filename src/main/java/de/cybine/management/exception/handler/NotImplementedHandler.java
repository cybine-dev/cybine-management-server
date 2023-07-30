package de.cybine.management.exception.handler;

import de.cybine.management.exception.*;
import de.cybine.management.util.api.*;
import io.vertx.core.http.*;
import jakarta.ws.rs.core.*;
import org.jboss.resteasy.reactive.*;
import org.jboss.resteasy.reactive.server.*;

@SuppressWarnings("unused")
public class NotImplementedHandler
{
    @Context
    HttpServerRequest request;

    @ServerExceptionMapper
    public RestResponse<ApiResponse<ErrorResponse>> toResponse(NotImplementedException exception)
    {
        return ApiResponse.<ErrorResponse>builder()
                .status(RestResponse.Status.NOT_IMPLEMENTED)
                .self(ApiResourceInfo.builder().href(this.request.absoluteURI()).build())
                .value(ErrorResponse.builder().message(exception.getMessage()).build())
                .build()
                .toResponse();
    }
}
