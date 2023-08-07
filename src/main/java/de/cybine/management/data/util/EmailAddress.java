package de.cybine.management.data.util;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.util.primitive.*;
import de.cybine.management.exception.*;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.enums.*;
import org.eclipse.microprofile.openapi.annotations.media.*;

@Data
@AllArgsConstructor(access = AccessLevel.NONE)
@Schema(type = SchemaType.STRING, implementation = String.class)
public class EmailAddress
{
    @JsonValue
    @Schema(hidden = true)
    private final String value;

    @Schema(hidden = true)
    private final String    namePart;

    @Schema(hidden = true)
    private final Hyperlink domainPart;

    private EmailAddress(String value)
    {
        this.value = value;
        if (value.length() > 320)
            throw new MailValidationException("Address is longer than 320 characters");

        this.domainPart = this.getDomain(value);
        this.namePart = this.getName(value, this.domainPart);
    }

    private Hyperlink getDomain(String email)
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
        String domain = parts[ parts.length - 1 ];
        if (domain.length() > 255)
            throw new MailValidationException("Domain-part longer than 255 characters");

        return Domain.of(domain);
    }

    private String getName(String email, Hyperlink domain)
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

    public static EmailAddress of(String email)
    {
        return new EmailAddress(email);
    }
}
