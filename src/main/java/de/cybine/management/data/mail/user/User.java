package de.cybine.management.data.mail.user;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.util.*;
import lombok.*;
import lombok.extern.jackson.*;

import java.util.*;

@Data
@Jacksonized
@Builder(builderClassName = "Builder")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User implements WithId<Long>
{
    @JsonProperty("id")
    private final Long id;

    @JsonProperty("domain_id")
    private final long domainId;

    @JsonProperty("domain")
    @JsonView(Views.Extended.class)
    private final Domain domain;

    @JsonProperty("username")
    private final String username;

    @JsonIgnore
    @JsonProperty("password_hash")
    private final String passwordHash;

    @JsonProperty("is_enabled")
    private final boolean isEnabled;

    public Optional<Domain> getDomain( )
    {
        return Optional.ofNullable(this.domain);
    }
}
