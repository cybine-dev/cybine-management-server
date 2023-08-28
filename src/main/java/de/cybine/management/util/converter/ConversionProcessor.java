package de.cybine.management.util.converter;

import lombok.*;

import java.util.*;
import java.util.stream.*;

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

    public <C extends Collection<O>> ConversionResult<C> toCollection(Collection<I> input, C defaultValue,
            Collector<O, ?, C> collector)
    {
        ConversionHelper helper = this.createConversionHelper();
        return new ConversionResult<>(this.metadata,
                helper.toCollection(this.inputType, this.outputType, defaultValue, collector).apply(input));
    }

    private ConversionHelper createConversionHelper( )
    {
        return new ConversionHelper(this.registry, this.metadata.getRootNode());
    }
}
