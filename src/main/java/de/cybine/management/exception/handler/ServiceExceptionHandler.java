package de.cybine.management.exception.handler;

import de.cybine.management.exception.*;
import de.cybine.management.util.api.*;
import org.jboss.resteasy.reactive.*;
import org.jboss.resteasy.reactive.server.*;

@SuppressWarnings("unused")
public class ServiceExceptionHandler
{
    @ServerExceptionMapper
    public RestResponse<ApiResponse<ErrorResponse>> toResponse(ServiceException exception)
    {
        return ApiResponse.<ErrorResponse>builder()
                          .status(exception.getStatus())
                          .value(exception.toResponse())
                          .build()
                          .toResponse();
    }
}
