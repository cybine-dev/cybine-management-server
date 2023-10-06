package de.cybine.management.exception;

import org.jboss.resteasy.reactive.*;

@SuppressWarnings("unused")
public class UnknownRelationException extends ServiceException
{
    public UnknownRelationException(String message)
    {
        this(message, null);
    }

    public UnknownRelationException(String message, Throwable cause)
    {
        super("unknown-relation", RestResponse.Status.BAD_REQUEST, message, cause);
    }
}
