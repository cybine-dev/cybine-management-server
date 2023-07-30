package de.cybine.management.exception;

@SuppressWarnings("unused")
public class MailValidationException extends ServiceException
{
    public MailValidationException( )
    {
    }

    public MailValidationException(String message)
    {
        super(message);
    }
}
