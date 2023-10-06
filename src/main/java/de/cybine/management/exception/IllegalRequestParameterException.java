package de.cybine.management.exception;

import org.jboss.resteasy.reactive.*;

@SuppressWarnings("unused")
public class IllegalRequestParameterException extends ServiceException
{
    public IllegalRequestParameterException(String message)
    {
        this(message, null);
    }

    public IllegalRequestParameterException(String message, Throwable cause)
    {
        super("illegal-request-parameter", RestResponse.Status.BAD_REQUEST, message, cause);
    }
}
