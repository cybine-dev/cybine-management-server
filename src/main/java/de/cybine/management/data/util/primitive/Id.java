package de.cybine.management.data.util.primitive;

import java.util.*;

public interface Id<T>
{
    T getValue( );

    default Optional<T> findValue( )
    {
        return Optional.ofNullable(this.getValue());
    }
}
