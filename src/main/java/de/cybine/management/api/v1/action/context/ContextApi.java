package de.cybine.management.api.v1.action.context;

import de.cybine.management.data.action.context.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.api.response.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.tags.*;
import org.jboss.resteasy.reactive.*;

import java.util.*;

@Path("/api/v1/action/context")
@Tag(name = "ActionContext Resource")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ContextApi
{
    @GET
    @Path("/find/id/{id}")
    RestResponse<ApiResponse<ActionContext>> fetchById(@PathParam("id") @Min(1) long id);

    @GET
    @Path("/find/correlation-id/{correlation-id}")
    RestResponse<ApiResponse<ActionContext>> fetchByCorrelationId(@PathParam("correlation-id") UUID correlationId);

    @POST
    RestResponse<ApiResponse<List<ActionContext>>> fetch(@Valid @NotNull ApiQuery query);

    @POST
    @Path("find")
    RestResponse<ApiResponse<ActionContext>> fetchSingle(@Valid @NotNull ApiQuery query);

    @POST
    @Path("count")
    RestResponse<ApiResponse<List<ApiCountInfo>>> fetchCount(@Valid @NotNull ApiCountQuery query);

    @POST
    @Path("options")
    RestResponse<ApiResponse<List<Object>>> fetchOptions(@Valid @NotNull ApiOptionQuery query);
}