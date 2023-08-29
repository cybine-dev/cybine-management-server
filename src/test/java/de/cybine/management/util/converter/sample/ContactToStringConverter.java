package de.cybine.management.util.converter.sample;

import de.cybine.management.util.converter.*;
import de.cybine.management.util.converter.data.*;

public class ContactToStringConverter implements Converter<Contact, String>
{
    @Override
    public Class<Contact> getInputType( )
    {
        return Contact.class;
    }

    @Override
    public Class<String> getOutputType( )
    {
        return String.class;
    }

    @Override
    public String convert(Contact input, ConversionHelper helper)
    {
        return String.format("Contact[email=%s]", input.getEmail());
    }
}
