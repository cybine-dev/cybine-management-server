package de.cybine.management.util.converter.sample;

import de.cybine.management.util.converter.*;

public class StringToIntConverter implements Converter<String, Integer>
{
    @Override
    public Class<String> getInputType( )
    {
        return String.class;
    }

    @Override
    public Class<Integer> getOutputType( )
    {
        return Integer.class;
    }

    @Override
    public Integer convert(String input, ConversionHelper helper)
    {
        return Integer.parseInt(input);
    }
}
