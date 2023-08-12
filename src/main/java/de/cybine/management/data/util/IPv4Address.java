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
public class IPv4Address implements Hyperlink
{
    @JsonValue
    @Schema(hidden = true)
    private final String value;

    private IPv4Address(String value)
    {
        this.value = value;
    }

    @Override
    public String asString( )
    {
        return this.value;
    }

    public static IPv4Address of(String value)
    {
        try
        {
            String[] parts = value.split("\\.");
            if (parts.length != 4)
                throw new IllegalArgumentException("IPv4-addresses must have 4 octets");

            for (String part : parts)
            {
                int octet = Integer.parseInt(part);
                if (octet < 0)
                    throw new IllegalArgumentException("IPv4-address octets cannot be less than 0");

                if (octet > 255)
                    throw new IllegalArgumentException("IPv4-address octets cannot be more than 255");
            }
        }
        catch (NumberFormatException exception)
        {
            throw new IllegalArgumentException("Invalid IPv4-address format", exception);
        }

        return new IPv4Address(value);
    }

    public static Optional<IPv4Address> safeOf(String value)
    {
        try
        {
            return Optional.of(IPv4Address.of(value));
        }
        catch (IllegalArgumentException ignored)
        {
            return Optional.empty();
        }
    }
}
