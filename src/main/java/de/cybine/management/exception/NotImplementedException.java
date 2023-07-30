package de.cybine.management.exception;

@SuppressWarnings("unused")
public class NotImplementedException extends TechnicalException
{
    public NotImplementedException( )
    {
        super("This method has not been implemented yet.");
    }
}
