package de.cybine.management.util.converter.sample;

import de.cybine.management.util.converter.*;
import de.cybine.management.util.converter.data.*;

public class AddressToStringConverter implements Converter<Address, String>
{
    @Override
    public Class<Address> getInputType( )
    {
        return Address.class;
    }

    @Override
    public Class<String> getOutputType( )
    {
        return String.class;
    }

    @Override
    public String convert(Address input, ConversionHelper helper)
    {
        return String.format("Address[street=%s, city=%s, country=%s]", input.getStreet(), input.getCity(),
                input.getCountry());
    }
}
