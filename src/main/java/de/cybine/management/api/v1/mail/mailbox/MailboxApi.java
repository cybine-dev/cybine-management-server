package de.cybine.management.api.v1.mail.mailbox;

import de.cybine.management.data.mail.mailbox.*;
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

@Authenticated
@Path("/api/v1/mail/mailbox")
@Tag(name = "Mailbox Resource")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface MailboxApi
{
    @POST
    RestResponse<ApiResponse<List<Mailbox>>> fetch(@Valid @NotNull ApiQuery query);

    @POST
    @Path("find")
    RestResponse<ApiResponse<Mailbox>> fetchSingle(@Valid @NotNull ApiQuery query);

    @POST
    @Path("count")
    RestResponse<ApiResponse<List<ApiCountInfo>>> fetchCount(@Valid @NotNull ApiCountQuery query);

    @POST
    @Path("options")
    RestResponse<ApiResponse<List<Object>>> fetchOptions(@Valid @NotNull ApiOptionQuery query);
}
