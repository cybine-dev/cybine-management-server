package de.cybine.management.data.action.process;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.action.context.*;
import de.cybine.management.data.util.*;
import de.cybine.management.util.*;
import lombok.*;
import lombok.extern.jackson.*;

import java.io.*;
import java.time.*;
import java.util.*;

@Data
@Jacksonized
@Builder(builderClassName = "Generator")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ActionProcess implements Serializable, WithId<ActionProcessId>
{
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private final ActionProcessId id;

    @Builder.Default
    @JsonProperty("event_id")
    private final String eventId = UUIDv7.generate().toString();

    @JsonProperty("context_id")
    @JsonView(Views.Simple.class)
    private final ActionContextId contextId;

    @JsonProperty("context")
    @JsonView(Views.Extended.class)
    private final ActionContext context;

    @JsonProperty("status")
    private final String status;

    @JsonProperty("priority")
    private final Integer priority;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("creator_id")
    private final String creatorId;

    @JsonProperty("created_at")
    private final ZonedDateTime createdAt;

    @JsonProperty("due_at")
    private final ZonedDateTime dueAt;

    @JsonProperty("data")
    private final Object data;

    public Optional<Integer> getPriority( )
    {
        return Optional.ofNullable(this.priority);
    }

    public Optional<ActionContext> getContext( )
    {
        return Optional.ofNullable(this.context);
    }

    public Optional<String> getDescription( )
    {
        return Optional.ofNullable(this.description);
    }

    public Optional<String> getCreatorId( )
    {
        return Optional.ofNullable(this.creatorId);
    }

    public Optional<ZonedDateTime> getDueAt( )
    {
        return Optional.ofNullable(this.dueAt);
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> getData( )
    {
        return Optional.ofNullable((T) this.data);
    }

    @Override
    public boolean equals(Object other)
    {
        if(other == null)
            return false;

        if(this.getClass() != other.getClass())
            return false;

        WithId<?> that = ((WithId<?>) other);
        if (this.findId().isEmpty() || that.findId().isEmpty())
            return false;

        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode( )
    {
        return this.findId().map(Object::hashCode).orElse(0);
    }
}
