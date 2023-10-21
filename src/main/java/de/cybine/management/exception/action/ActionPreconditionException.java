package de.cybine.management.exception.action;

import de.cybine.management.exception.*;
import org.jboss.resteasy.reactive.*;

@SuppressWarnings("unused")
public class ActionPreconditionException extends ServiceException
{
    public ActionPreconditionException(String message)
    {
        this(message, null);
    }

    public ActionPreconditionException(String message, Throwable cause)
    {
        super("action-precondition-unfulfilled", RestResponse.Status.BAD_REQUEST, message, cause);
    }
}
