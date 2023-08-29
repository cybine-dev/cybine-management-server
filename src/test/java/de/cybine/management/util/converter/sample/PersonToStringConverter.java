package de.cybine.management.util.converter.sample;

import de.cybine.management.util.converter.*;
import de.cybine.management.util.converter.data.*;

public class PersonToStringConverter implements Converter<Person, String>
{
    @Override
    public Class<Person> getInputType( )
    {
        return Person.class;
    }

    @Override
    public Class<String> getOutputType( )
    {
        return String.class;
    }

    @Override
    public String convert(Person input, ConversionHelper helper)
    {
        return String.format("Person[firstname=%s, lastname=%s, address=%s, contact=%s]", input.getFirstname(),
                input.getLastname(), helper.toItem(Address.class, String.class).apply(input::getAddress),
                helper.toItem(Contact.class, String.class).apply(input::getContact));
    }
}
