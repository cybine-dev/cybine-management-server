package de.cybine.management.util.converter;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;
import java.util.stream.*;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class ConversionHelper
{
    private final ConverterTreeNode parentNode;

    private final ConverterResolver converterResolver;

    public <I, O> ConverterFunction<I, O> toItem(Class<I> inputType, Class<O> outputType)
    {
        Converter<I, O> converter = this.getConverter(inputType, outputType);
        return this.toItem(converter);
    }

    public <I, O> ConverterFunction<I, O> toItem(Converter<I, O> converter)
    {
        return input ->
        {
            if (!this.isInitialized(input))
                return null;

            ConverterTreeNode node = this.parentNode.process(input).orElse(null);
            if (node == null)
                return null;

            return converter.convert(input, new ConversionHelper(node, this.converterResolver));
        };
    }

    public <I, O> ConverterFunction<Collection<I>, List<O>> toList(Class<I> inputType, Class<O> outputType)
    {
        Converter<I, O> converter = this.getConverter(inputType, outputType);
        return this.toList(converter);
    }

    public <I, O> ConverterFunction<Collection<I>, List<O>> toList(Converter<I, O> converter)
    {
        return this.toCollection(converter, Collections.emptyList(), Collectors.toList());
    }

    public <I, O> ConverterFunction<Collection<I>, Set<O>> toSet(Class<I> inputType, Class<O> outputType)
    {
        Converter<I, O> converter = this.getConverter(inputType, outputType);
        return this.toSet(converter);
    }

    public <I, O> ConverterFunction<Collection<I>, Set<O>> toSet(Converter<I, O> converter)
    {
        return this.toCollection(converter, Collections.emptySet(), Collectors.toSet());
    }

    public <I, O, C extends Collection<O>> ConverterFunction<Collection<I>, C> toCollection(Class<I> inputType,
            Class<O> outputType, C defaultValue, Collector<O, ?, C> collector)
    {
        Converter<I, O> converter = this.getConverter(inputType, outputType);
        return this.toCollection(converter, defaultValue, collector);
    }

    public <I, O, C extends Collection<O>> ConverterFunction<Collection<I>, C> toCollection(Converter<I, O> converter,
            C defaultValue, Collector<O, ?, C> collector)
    {
        ConverterFunction<I, O> converterFunction = this.toItem(converter);
        return input ->
        {
            if (!this.isInitialized(input))
                return null;

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
                                                    .map(converterFunction)
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

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isInitialized(Object input)
    {
        return Persistence.getPersistenceUtil().isLoaded(input);
    }
}
