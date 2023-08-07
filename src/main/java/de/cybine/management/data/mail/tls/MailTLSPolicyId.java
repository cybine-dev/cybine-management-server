package de.cybine.management.data.mail.tls;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.util.primitive.*;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.enums.*;
import org.eclipse.microprofile.openapi.annotations.media.*;

@Data
@RequiredArgsConstructor(staticName = "of")
@Schema(type = SchemaType.NUMBER, implementation = Long.class)
public class MailTLSPolicyId implements Id<Long>
{
    @JsonValue
    @Schema(hidden = true)
    private final Long value;
}
