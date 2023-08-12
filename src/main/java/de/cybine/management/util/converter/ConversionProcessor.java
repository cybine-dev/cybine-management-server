package de.cybine.management.util.converter;

import lombok.*;

import java.util.*;

@RequiredArgsConstructor
public class ConversionProcessor<I, O>
{
    private final Class<I> inputType;
    private final Class<O> outputType;

    private final ConverterTree metadata;

    private final ConverterRegistry registry;

    public ConversionResult<O> toItem(I input)
    {
        ConversionHelper helper = this.createConversionHelper();
        return new ConversionResult<>(this.metadata, helper.toItem(this.inputType, this.outputType).apply(input));
    }

    public ConversionResult<List<O>> toList(Collection<I> input)
    {
        ConversionHelper helper = this.createConversionHelper();
        return new ConversionResult<>(this.metadata, helper.toList(this.inputType, this.outputType).apply(input));
    }

    public ConversionResult<Set<O>> toSet(Collection<I> input)
    {
        ConversionHelper helper = this.createConversionHelper();
        return new ConversionResult<>(this.metadata, helper.toSet(this.inputType, this.outputType).apply(input));
    }

    private ConversionHelper createConversionHelper( )
    {
        return new ConversionHelper(this.registry, this.metadata.getRootNode());
    }
}
