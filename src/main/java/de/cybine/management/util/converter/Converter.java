package de.cybine.management.util.converter;

import java.util.*;
import java.util.stream.*;

/**
 * <p>Interface to define object mappings</p>
 *
 * @param <I> type of the object to be mapped
 * @param <O> type of the object that is generated in the mapping process
 */
public interface Converter<I, O>
{
    /**
     * @return type of the object to be mapped
     */
    Class<I> getInputType( );

    /**
     * @return type of the object that is generated in the mapping process
     */
    Class<O> getOutputType( );

    /**
     * @return compound type of the converter
     */
    default ConverterType<I, O> getType()
    {
        return new ConverterType<>(this.getInputType(), this.getOutputType());
    }

    /**
     * <p>Method to perform object mapping</p>
     * <p>Implementation should check against {@link ConverterTreeNode} for restrictions to determine if the input
     * should get mapped</p>
     *
     * @param input      object to be mappepd
     * @param parentNode meta-info for mapping of the previously mapped object
     *
     * @return mapped object or default value if restrictions aren't met (maybe null)
     */
    O convert(I input, ConverterTreeNode parentNode);

    /**
     * <p>Method to perform object mapping on collections of objects</p>
     * <p>Should call {@link #convert(I, ConverterTreeNode)} method to perform mapping</p>
     * <p>Should enforce mapping constraints for the collection as a whole before checking constraints on element level
     * during mapping</p>
     *
     * @param input      collection of objects to be mapped
     * @param parentNode meta-info for mapping of the previously mapped object
     *
     * @return {@link List<O>} of mapped objects (maybe null)
     */
    default List<O> convertList(Collection<I> input, ConverterTreeNode parentNode)
    {
        ConverterConstraint generalConstraint = parentNode.getConstraint();
        ConverterConstraint typeSpecificConstraint = parentNode.getConstraint(parentNode.getItemType());
        boolean allowEmptyCollection = typeSpecificConstraint.getAllowEmptyCollection()
                .or(generalConstraint::getAllowEmptyCollection)
                .orElse(false);

        boolean shouldFilterNullValues = typeSpecificConstraint.getFilterNullValues()
                .or(generalConstraint::getFilterNullValues)
                .orElse(true);

        if (!parentNode.shouldBeProcessed(input))
        {
            return this.processEmptyCollection(Collections.emptyList(), allowEmptyCollection);
        }

        return this.processEmptyCollection(input.stream()
                .map(item -> this.convert(item, parentNode))
                .filter(item -> !shouldFilterNullValues || item != null)
                .toList(), allowEmptyCollection);
    }

    /**
     * <p>Method to perform object mapping on collections of objects</p>
     * <p>Should call {@link #convert(I, ConverterTreeNode)} method to perform mapping</p>
     * <p>Should enforce mapping constraints for the collection as a whole before checking constraints on element level
     * during mapping</p>
     *
     * @param input      collection of objects to be mapped
     * @param parentNode meta-info for mapping of the previously mapped object
     *
     * @return {@link Set<O>} of mapped objects (maybe null)
     */
    default Set<O> convertSet(Collection<I> input, ConverterTreeNode parentNode)
    {
        ConverterConstraint generalConstraint = parentNode.getConstraint();
        ConverterConstraint typeSpecificConstraint = parentNode.getConstraint(parentNode.getItemType());
        boolean allowEmptyCollection = typeSpecificConstraint.getAllowEmptyCollection()
                .or(generalConstraint::getAllowEmptyCollection)
                .orElse(false);

        boolean shouldFilterNullValues = typeSpecificConstraint.getFilterNullValues()
                .or(generalConstraint::getFilterNullValues)
                .orElse(true);

        if (!parentNode.shouldBeProcessed(input))
        {
            return this.processEmptyCollection(Collections.emptySet(), allowEmptyCollection);
        }

        return this.processEmptyCollection(input.stream()
                .map(item -> this.convert(item, parentNode))
                .filter(item -> !shouldFilterNullValues || item != null)
                .collect(Collectors.toSet()), allowEmptyCollection);
    }

    private <T extends Collection<O>> T processEmptyCollection(T collection, boolean allowEmptyCollection)
    {
        return allowEmptyCollection || !collection.isEmpty() ? collection : null;
    }
}
