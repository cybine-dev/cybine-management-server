package de.cybine.management.util.converter;

import lombok.*;

import java.util.function.*;

@Getter
@RequiredArgsConstructor
public class TypeConverter<I, O> implements Converter<I, O>
{
    private final Class<I> inputType;
    private final Class<O> outputType;

    @Getter(AccessLevel.NONE)
    private final BiFunction<I, ConverterTreeNode, O> conversionFunction;

    @Override
    public O convert(I input, ConverterTreeNode parentNode)
    {
        return this.conversionFunction.apply(input, parentNode);
    }
}
