package de.cybine.management.exception;

@SuppressWarnings("unused")
public class DatabaseMigrationException extends TechnicalException
{
    public DatabaseMigrationException(String message)
    {
        super(message);
    }

    public DatabaseMigrationException(Throwable cause)
    {
        super(cause);
    }

    public DatabaseMigrationException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
