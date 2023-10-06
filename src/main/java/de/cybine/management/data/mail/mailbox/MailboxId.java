package de.cybine.management.data.mail.mailbox;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.util.primitive.*;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.enums.*;
import org.eclipse.microprofile.openapi.annotations.media.*;

import java.io.*;

@Data
@RequiredArgsConstructor(staticName = "of")
@Schema(type = SchemaType.NUMBER, implementation = Long.class)
public class MailboxId implements Serializable, Id<Long>
{
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonValue
    @Schema(hidden = true)
    private final Long value;
}
