package de.cybine.management.util.converter;

import lombok.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class ConversionHelper
{
    private final ConverterTreeNode parentNode;

    private final ConverterResolver converterResolver;

    public <I, O> Function<I, O> toItem(Class<I> inputType, Class<O> outputType)
    {
        Converter<I, O> converter = this.getConverter(inputType, outputType);
        return this.toItem(converter);
    }

    public <I, O> Function<I, O> toItem(Converter<I, O> converter)
    {
        return input ->
        {
            ConverterTreeNode node = this.parentNode.process(input).orElse(null);
            if (node == null)
                return null;

            return converter.convert(input, new ConversionHelper(node, this.converterResolver));
        };
    }

    public <I, O> Function<Collection<I>, List<O>> toList(Class<I> inputType, Class<O> outputType)
    {
        Converter<I, O> converter = this.getConverter(inputType, outputType);
        return this.toList(converter);
    }

    public <I, O> Function<Collection<I>, List<O>> toList(Converter<I, O> converter)
    {
        return this.toCollection(converter, Collections.emptyList(), Collectors.toList());
    }

    public <I, O> Function<Collection<I>, Set<O>> toSet(Class<I> inputType, Class<O> outputType)
    {
        Converter<I, O> converter = this.getConverter(inputType, outputType);
        return this.toSet(converter);
    }

    public <I, O> Function<Collection<I>, Set<O>> toSet(Converter<I, O> converter)
    {
        return this.toCollection(converter, Collections.emptySet(), Collectors.toSet());
    }

    public <I, O, C extends Collection<O>> Function<Collection<I>, C> toCollection(Class<I> inputType,
            Class<O> outputType, C defaultValue, Collector<O, ?, C> collector)
    {
        Converter<I, O> converter = this.getConverter(inputType, outputType);
        return this.toCollection(converter, defaultValue, collector);
    }

    public <I, O, C extends Collection<O>> Function<Collection<I>, C> toCollection(Converter<I, O> converter,
            C defaultValue, Collector<O, ?, C> collector)
    {
        Function<I, O> conversionFunction = this.toItem(converter);
        return input ->
        {
            ConverterConstraint generalConstraint = this.parentNode.getConstraint();
            ConverterConstraint typeSpecificConstraint = this.parentNode.getConstraint(this.parentNode.getItemType());
            boolean allowEmptyCollection = typeSpecificConstraint.getAllowEmptyCollection()
                                                                 .or(generalConstraint::getAllowEmptyCollection)
                                                                 .orElse(false);

            boolean shouldFilterNullValues = typeSpecificConstraint.getFilterNullValues()
                                                                   .or(generalConstraint::getFilterNullValues)
                                                                   .orElse(true);

            if (!this.parentNode.shouldBeProcessed(input))
            {
                return this.processEmptyCollection(defaultValue, allowEmptyCollection);
            }

            return this.processEmptyCollection(input.stream()
                                                    .map(conversionFunction)
                                                    .filter(item -> !shouldFilterNullValues || item != null)
                                                    .collect(collector), allowEmptyCollection);
        };
    }

    private <I, O> Converter<I, O> getConverter(Class<I> inputType, Class<O> outputType)
    {
        return this.converterResolver.getConverter(inputType, outputType);
    }

    private <T extends Collection<?>> T processEmptyCollection(T collection, boolean allowEmptyCollection)
    {
        return allowEmptyCollection || !collection.isEmpty() ? collection : null;
    }
}
