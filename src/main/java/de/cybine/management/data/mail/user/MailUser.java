package de.cybine.management.data.mail.user;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.util.*;
import de.cybine.management.data.util.password.*;
import de.cybine.management.util.*;
import lombok.*;
import lombok.extern.jackson.*;

import java.io.*;
import java.util.*;

@Data
@Jacksonized
@Builder(builderClassName = "Generator")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MailUser implements Serializable, WithId<MailUserId>
{
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    @EqualsAndHashCode.Include
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
