package de.cybine.management.exception.handler;

import de.cybine.management.util.api.*;
import io.vertx.core.http.*;
import jakarta.ws.rs.core.*;
import org.jboss.resteasy.reactive.*;
import org.jboss.resteasy.reactive.server.*;

import java.util.*;

@SuppressWarnings("unused")
public class NoSuchElementHandler
{
    @Context
    HttpServerRequest request;

    @ServerExceptionMapper
    public RestResponse<ApiResponse<ErrorResponse>> toResponse(NoSuchElementException exception)
    {
        return ApiResponse.<ErrorResponse>builder()
                .status(RestResponse.Status.NOT_FOUND)
                .self(ApiResourceInfo.builder().href(this.request.absoluteURI()).build())
                .value(ErrorResponse.builder().message(exception.getMessage()).build())
                .build()
                .toResponse();
    }
}
