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

    public <E, D> void addEntityMapper(EntityMapper<E, D> mapper)
    {
        this.addConverter(mapper.toDataConverter());
        this.addConverter(mapper.toEntityConverter());
    }

    @SuppressWarnings("unchecked")
    <I, O> Optional<Converter<I, O>> findConverter(Class<I> inputType, Class<O> outputType)
    {
        ConverterType<I, O> type = new ConverterType<>(inputType, outputType);
        return Optional.ofNullable((Converter<I, O>) this.converters.get(type));
    }

    public <I, O> ConversionProcessor<I, O> getProcessor(Class<I> inputType, Class<O> outputType)
    {
        return this.getProcessor(inputType, outputType, ConverterTree.create());
    }

    public <I, O> ConversionProcessor<I, O> getProcessor(Class<I> inputType, Class<O> outputType,
            ConverterTree metadata)
    {
        return new ConversionProcessor<>(inputType, outputType, metadata, this);
    }
}
