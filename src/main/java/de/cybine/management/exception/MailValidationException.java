package de.cybine.management.exception;

@SuppressWarnings("unused")
public class MailValidationException extends IllegalArgumentException
{
    public MailValidationException( )
    {
    }

    public MailValidationException(String message)
    {
        super(message);
    }
}
