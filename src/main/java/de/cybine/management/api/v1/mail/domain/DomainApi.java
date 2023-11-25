package de.cybine.management.api.v1.mail.domain;

import de.cybine.management.api.v1.mail.domain.params.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.util.api.query.*;
import de.cybine.management.util.api.response.*;
import io.quarkus.security.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.tags.*;
import org.jboss.resteasy.reactive.*;

import java.util.*;

@Path("/api/v1/mail/domain")
@Tag(name = "MailDomain Resource")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public interface DomainApi
{
    @POST
    RestResponse<ApiResponse<List<MailDomain>>> fetch(@Valid @NotNull ApiQuery query);

    @POST
    @Path("find")
    RestResponse<ApiResponse<MailDomain>> fetchSingle(@Valid @NotNull ApiQuery query);

    @POST
    @Path("count")
    RestResponse<ApiResponse<List<ApiCountInfo>>> fetchCount(@Valid @NotNull ApiCountQuery query);

    @POST
    @Path("options")
    RestResponse<ApiResponse<List<Object>>> fetchOptions(@Valid @NotNull ApiOptionQuery query);

    @POST
    @Path("create")
    RestResponse<ApiResponse<MailDomain>> create(@Valid @NotNull DomainCreationParameter domain);
}
