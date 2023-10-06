package de.cybine.management.exception;

import org.jboss.resteasy.reactive.*;

@SuppressWarnings("unused")
public class UnknownApiContextException extends ServiceException
{
    public UnknownApiContextException(String message)
    {
        this(message, null);
    }

    public UnknownApiContextException(String message, Throwable cause)
    {
        super("unknown-api-context", RestResponse.Status.BAD_REQUEST, message, cause);
    }
}
