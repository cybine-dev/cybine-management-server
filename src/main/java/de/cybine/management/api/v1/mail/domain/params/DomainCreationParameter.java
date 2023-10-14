package de.cybine.management.api.v1.mail.domain.params;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.data.util.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.jackson.*;

@Data
@Jacksonized
@Builder(builderClassName = "Generator")
public class DomainCreationParameter
{
    @NotNull
    @JsonProperty("domain")
    private final Domain domain;

    @NotNull
    @JsonProperty("action")
    private final MailDomainAction action;
}
