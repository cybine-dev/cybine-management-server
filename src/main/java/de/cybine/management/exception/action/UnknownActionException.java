package de.cybine.management.exception.action;

import de.cybine.management.exception.*;
import org.jboss.resteasy.reactive.*;

@SuppressWarnings("unused")
public class UnknownActionException extends ServiceException
{
    public UnknownActionException(String message)
    {
        this(message, null);
    }

    public UnknownActionException(String message, Throwable cause)
    {
        super("unknown-action", RestResponse.Status.BAD_REQUEST, message, cause);
    }
}
