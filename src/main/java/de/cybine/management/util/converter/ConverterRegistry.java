package de.cybine.management.util.converter;

import jakarta.enterprise.context.*;

import java.util.*;

@ApplicationScoped
@SuppressWarnings("unused")
public class ConverterRegistry
{
    private final Map<ConverterType<?, ?>, Converter<?, ?>> converters = new HashMap<>();

    public void addConverter(Converter<?, ?> converter)
    {
        this.converters.put(converter.getType(), converter);
    }

    public void addEntityMapper(EntityMapper<?, ?> mapper)
    {
        this.addConverter(mapper.toDataConverter());
        this.addConverter(mapper.toEntityConverter());
    }

    public <I, O> ConversionProcessor<I, O> getProcessor(Class<I> inputType, Class<O> outputType)
    {
        return this.getProcessor(inputType, outputType, ConverterTree.create());
    }

    public <I, O> ConversionProcessor<I, O> getProcessor(Class<I> inputType, Class<O> outputType,
            ConverterTree metadata)
    {
        return new ConversionProcessor<>(inputType, outputType, metadata, this::getConverter);
    }

    @SuppressWarnings("unchecked")
    private <I, O> Converter<I, O> getConverter(ConverterType<I, O> type)
    {
        return (Converter<I, O>) this.converters.get(type);
    }
}
