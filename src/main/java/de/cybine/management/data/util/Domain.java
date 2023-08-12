package de.cybine.management.data.util;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.util.primitive.*;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.enums.*;
import org.eclipse.microprofile.openapi.annotations.media.*;

import java.net.*;
import java.util.*;

@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(type = SchemaType.STRING, implementation = String.class)
public class Domain implements Hyperlink
{
    @JsonValue
    @Schema(hidden = true)
    private final String value;

    public String asString( )
    {
        return this.value;
    }

    public static Domain of(String value)
    {
        if (value.length() > 255)
            throw new IllegalArgumentException("Domain names must have less than 256 characters");

        if (Arrays.stream(value.split("\\.")).anyMatch(label -> label.length() > 63))
            throw new IllegalArgumentException("Domain name labels must have less than 64 characters");

        // In theory a domain name must start with a letter and end with a letter or digit according to RFC 3492
        // since there are domains that don't obey this (such as 4chan.org), the check is omitted
        return new Domain(IDN.toASCII(value.toLowerCase(), IDN.ALLOW_UNASSIGNED));
    }

    public static Optional<Domain> safeOf(String value)
    {
        try
        {
            return Optional.of(Domain.of(value));
        }
        catch (IllegalArgumentException ignored)
        {
            return Optional.empty();
        }
    }
}
