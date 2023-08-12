package de.cybine.management.util.converter;

import lombok.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@RequiredArgsConstructor
public class ConversionHelper
{
    private final ConverterRegistry registry;
    private final ConverterTreeNode parentNode;

    public <I, O> Function<I, O> toItem(Class<I> inputType, Class<O> outputType)
    {
        Converter<I, O> converter = this.registry.findConverter(inputType, outputType).orElseThrow();
        return input ->
        {
            ConverterTreeNode node = this.parentNode.process(input).orElse(null);
            if (node == null)
                return null;

            return converter.convert(input, new ConversionHelper(this.registry, node));
        };
    }

    public <I, O> Function<Collection<I>, List<O>> toList(Class<I> inputType, Class<O> outputType)
    {
        Function<I, O> converter = this.toItem(inputType, outputType);
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
                return this.processEmptyCollection(Collections.emptyList(), allowEmptyCollection);
            }

            return this.processEmptyCollection(
                    input.stream().map(converter).filter(item -> !shouldFilterNullValues || item != null).toList(),
                    allowEmptyCollection);
        };
    }

    public <I, O> Function<Collection<I>, Set<O>> toSet(Class<I> inputType, Class<O> outputType)
    {
        Function<I, O> converter = this.toItem(inputType, outputType);
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
                return this.processEmptyCollection(Collections.emptySet(), allowEmptyCollection);
            }

            return this.processEmptyCollection(input.stream()
                                                    .map(converter)
                                                    .filter(item -> !shouldFilterNullValues || item != null)
                                                    .collect(Collectors.toSet()), allowEmptyCollection);
        };
    }

    private <T extends Collection<?>> T processEmptyCollection(T collection, boolean allowEmptyCollection)
    {
        return allowEmptyCollection || !collection.isEmpty() ? collection : null;
    }
}
