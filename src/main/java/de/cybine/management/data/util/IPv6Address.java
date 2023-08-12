package de.cybine.management.data.util;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.util.primitive.*;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.enums.*;
import org.eclipse.microprofile.openapi.annotations.media.*;

import java.util.*;

@ToString
@EqualsAndHashCode
@Schema(type = SchemaType.STRING, implementation = String.class)
public class IPv6Address implements Hyperlink
{
    @JsonValue
    @Schema(hidden = true)
    private final String value;

    private IPv6Address(String value)
    {
        this.value = value;
    }

    @Override
    public String asString( )
    {
        return this.value;
    }

    public static IPv6Address of(String value)
    {
        try
        {
            String[] parts = value.split(":");
            if (parts.length != 16)
                throw new IllegalArgumentException("IPv6-addresses must have 16 octets");

            for (String part : parts)
            {
                int octet = Integer.parseInt(part, 16);
                if (octet < 0)
                    throw new IllegalArgumentException("IPv6-address octets cannot be less than 0");

                if (octet > 255)
                    throw new IllegalArgumentException("IPv6-address octets cannot be more than 255");
            }
        }
        catch (NumberFormatException exception)
        {
            throw new IllegalArgumentException("Invalid IPv6-address format", exception);
        }

        return new IPv6Address(value);
    }

    public static Optional<IPv6Address> safeOf(String value)
    {
        try
        {
            return Optional.of(IPv6Address.of(value));
        }
        catch (IllegalArgumentException ignored)
        {
            return Optional.empty();
        }
    }
}
