package de.cybine.management.util.validator;

import de.cybine.management.exception.*;
import jakarta.enterprise.context.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.slf4j.*;

import java.util.function.*;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class MailAddressValidator implements Predicate<String>
{
    /**
     * Validate Email addresses against RFC3696
     */
    @Override
    public boolean test(@NotNull String email)
    {
        if (email.length() > 320)
        {
            log.debug("Mail address '{}' is invalid: Mail address is longer than 320 characters", email);
            return false;
        }

        try
        {
            String domain = this.getDomain(email);
            this.getName(email, domain);

            return true;
        }
        catch (MailValidationException exception)
        {
            log.debug("Mail address '{}' is invalid: {}", email, exception.getMessage());
            return false;
        }
    }

    public String getDomain(@NotNull String email)
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

        return domain;
    }

    public String getName(@NotNull String email)
    {
        return this.getName(email, this.getDomain(email));
    }

    private String getName(@NotNull String email, @NotNull String domain)
    {
        String name = email.substring(0, email.length() - domain.length() - 1);
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
