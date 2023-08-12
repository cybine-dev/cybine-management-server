package de.cybine.management.data.util;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.util.primitive.*;
import de.cybine.management.exception.*;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.enums.*;
import org.eclipse.microprofile.openapi.annotations.media.*;

import java.util.*;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(type = SchemaType.STRING, implementation = String.class)
public class EmailAddress
{
    @JsonValue
    @Schema(hidden = true)
    private final String value;

    @Schema(hidden = true)
    private final String namePart;

    @Schema(hidden = true)
    private final Hyperlink domainPart;

    public static EmailAddress of(String value)
    {
        if (value.length() > 320)
            throw new MailValidationException("Address is longer than 320 characters");

        Hyperlink address = EmailAddress.getDomain(value);
        String name = EmailAddress.getName(value, address);

        return new EmailAddress(value, name, address);
    }

    public static Optional<EmailAddress> safeOf(String value)
    {
        try
        {
            return Optional.of(EmailAddress.of(value));
        }
        catch (IllegalArgumentException ignored)
        {
            return Optional.empty();
        }
    }

    private static Hyperlink getDomain(String email)
    {
        if (!email.contains("@"))
            throw new MailValidationException("No at-sign found");

        boolean isQuoted = false;
        boolean isEscaped = false;
        boolean unescapedAtSignFound = false;
        for (int i = 0; i < email.length(); i++)
        {
            char character = email.charAt(i);
            if (isQuoted)
            {
                if (character == '"')
                    isQuoted = false;

                continue;
            }

            if (isEscaped)
            {
                isEscaped = false;
                continue;
            }

            switch (character)
            {
                case '"' -> isQuoted = true;
                case '\\' -> isEscaped = true;
                case '@' -> unescapedAtSignFound = true;
            }

            if (unescapedAtSignFound)
                break;
        }

        if (!unescapedAtSignFound)
            throw new MailValidationException("No unescaped at-sign found");

        String[] parts = email.split("@");
        String address = parts[ parts.length - 1 ];
        if (address.startsWith("[") && address.endsWith("]"))
        {
            String ipAddress = address.substring(1, address.length() - 1);

            IPv4Address iPv4Address = IPv4Address.safeOf(ipAddress).orElse(null);
            if (iPv4Address != null)
                return iPv4Address;

            IPv6Address iPv6Address = IPv6Address.safeOf(ipAddress).orElse(null);
            if (iPv6Address != null)
                return iPv6Address;

            throw new MailValidationException("Unknown IP-Address format");
        }

        return Domain.of(address);
    }

    private static String getName(String email, Hyperlink domain)
    {
        String name = email.substring(0, email.length() - domain.asString().length() - 1);
        if (name.length() > 64)
            throw new MailValidationException("Local-part longer than 64 characters");

        boolean isQuoted = false;
        boolean isEscaped = false;
        for (int i = 0; i < name.length(); i++)
        {
            char character = name.charAt(i);
            if (isQuoted)
            {
                if (character == '"')
                    isQuoted = false;

                continue;
            }

            if (isEscaped)
            {
                isEscaped = false;
                continue;
            }

            switch (character)
            {
                case '@' -> throw new MailValidationException("Local-part may not contain unescaped at-sign");
                case ',' -> throw new MailValidationException("Local-part may not contain unescaped comma");
                case '[', ']' ->
                        throw new MailValidationException("Local-part may not contain unescaped square-bracket");
                case ' ' -> throw new MailValidationException("Local-part may not contain unescaped space");

                case '.' ->
                {
                    if (i == 0)
                        throw new MailValidationException("Local-part may not start with a period");

                    if (i == name.length() - 1)
                        throw new MailValidationException("Local-part may not end with a period");

                    if (name.charAt(i - 1) == '.')
                        throw new MailValidationException("Local-part may not contain two or more consecutive periods");
                }

                case '"' -> isQuoted = true;
                case '\\' -> isEscaped = true;
            }
        }

        if (isEscaped || isQuoted)
            throw new MailValidationException("Escape sequence not ended");

        return name;
    }
}
