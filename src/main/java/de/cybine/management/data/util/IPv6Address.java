package de.cybine.management.data.util;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.util.primitive.*;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.enums.*;
import org.eclipse.microprofile.openapi.annotations.media.*;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "of")
@Schema(type = SchemaType.STRING, implementation = String.class)
public class IPv6Address implements Hyperlink
{
    @JsonValue
    @Schema(hidden = true)
    private final String value;

    @Override
    public String asString( )
    {
        return this.value;
    }
}
