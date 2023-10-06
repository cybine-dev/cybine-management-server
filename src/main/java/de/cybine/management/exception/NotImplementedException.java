package de.cybine.management.exception;

import org.jboss.resteasy.reactive.*;

@SuppressWarnings("unused")
public class NotImplementedException extends ServiceException
{
    public NotImplementedException( )
    {
        super("not-implemented", RestResponse.Status.NOT_IMPLEMENTED, "This method has not been implemented yet.");
    }
}
