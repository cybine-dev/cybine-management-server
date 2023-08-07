package de.cybine.management.exception.handler;

import de.cybine.management.util.api.*;
import io.vertx.core.http.*;
import jakarta.ws.rs.core.*;
import org.hibernate.exception.*;
import org.jboss.resteasy.reactive.*;
import org.jboss.resteasy.reactive.server.*;

@SuppressWarnings("unused")
public class ConstraintViolationExceptionHandler
{
    @Context
    HttpServerRequest request;

    @ServerExceptionMapper
    public RestResponse<ApiResponse<ErrorResponse>> toResponse(ConstraintViolationException exception)
    {
        return ApiResponse.<ErrorResponse>builder()
                          .status(RestResponse.Status.CONFLICT)
                          .self(ApiResourceInfo.builder().href(this.request.absoluteURI()).build())
                          .value(ErrorResponse.builder().message(exception.getCause().getMessage()).build())
                          .build()
                          .toResponse();
    }
}
