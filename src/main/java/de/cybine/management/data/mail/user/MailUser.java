package de.cybine.management.data.mail.user;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.util.*;
import de.cybine.management.data.util.password.*;
import de.cybine.management.util.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.jackson.*;

import java.util.*;

@Data
@Jacksonized
@Builder(builderClassName = "Builder")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MailUser implements WithId<MailUserId>
{
    @JsonProperty("id")
    private final MailUserId id;

    @JsonProperty("domain_id")
    private final MailDomainId domainId;

    @JsonProperty("domain")
    @JsonView(Views.Extended.class)
    private final MailDomain domain;

    @JsonProperty("username")
    private final Username username;

    @JsonIgnore
    @JsonProperty("password_hash")
    private final BCryptPasswordHash passwordHash;

    @JsonProperty("is_enabled")
    private final boolean isEnabled;

    public Optional<MailDomain> getDomain( )
    {
        return Optional.ofNullable(this.domain);
    }
}
