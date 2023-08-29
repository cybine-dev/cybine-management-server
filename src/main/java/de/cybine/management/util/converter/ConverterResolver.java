package de.cybine.management.util.converter;

public interface ConverterResolver
{
    <I, O> Converter<I, O> getConverter(ConverterType<I, O> type);

    default <I, O> Converter<I, O> getConverter(Class<I> inputType, Class<O> outputType)
    {
        return this.getConverter(new ConverterType<>(inputType, outputType));
    }
}
