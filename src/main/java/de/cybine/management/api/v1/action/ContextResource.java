package de.cybine.management.api.v1.action;

import de.cybine.management.data.action.context.*;
import de.cybine.management.service.action.*;
import jakarta.enterprise.context.*;
import jakarta.ws.rs.*;
import lombok.*;
import org.jboss.resteasy.reactive.*;

import java.util.*;

@ApplicationScoped
@RequiredArgsConstructor
@Path("/api/v1/action/context")
public class ContextResource
{
    private final ActionService actionService;

    @GET
    @Path("/{namespace}/{category}/{name}")
    public RestResponse<Set<ActionContext>> fetch(@PathParam("namespace") String namespace,
            @PathParam("category") String category, @PathParam("name") String name, @QueryParam("id") String itemId)
    {
        ActionContextMetadata metadata = ActionContextMetadata.builder()
                                                              .namespace(namespace)
                                                              .category(category)
                                                              .name(name)
                                                              .itemId(itemId)
                                                              .build();

        return RestResponse.ok(this.actionService.fetchContexts(metadata));
    }
}
