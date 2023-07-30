package de.cybine.management.util.converter;

import org.hibernate.*;

import java.util.*;
import java.util.function.*;

/**
 * <p>Two way {@link Converter}</p>
 *
 * @param <E> type of the entity
 * @param <D> type of the data-object
 *
 * @see Converter
 */
public interface EntityMapper<E, D>
{
    /**
     * @return type of the entity
     */
    Class<E> getEntityType( );

    /**
     * @return type of the data-object
     */
    Class<D> getDataType( );

    /**
     * <p>Method to perform object mapping</p>
     * <p>Implementation should check against {@link ConverterTreeNode} for restrictions to determine if the input
     * should get mapped</p>
     *
     * @param data       object to be mappepd
     * @param parentNode meta-info for mapping of the previously mapped object
     *
     * @return mapped object or default value if restrictions aren't met (maybe null)
     */
    E toEntity(D data, ConverterTreeNode parentNode);

    /**
     * <p>Method to perform object mapping</p>
     * <p>Implementation should check against {@link ConverterTreeNode} for restrictions to determine if the input
     * should get mapped</p>
     *
     * @param entity     object to be mappepd
     * @param parentNode meta-info for mapping of the previously mapped object
     *
     * @return mapped object or default value if restrictions aren't met (maybe null)
     */
    D toData(E entity, ConverterTreeNode parentNode);

    /**
     * <p>Method to perform object mapping on collections of objects</p>
     * <p>Should call {@link #toEntity(D, ConverterTreeNode)} method to perform mapping</p>
     * <p>Should enforce mapping constraints for the collection as a whole before checking constraints on element level
     * during mapping</p>
     *
     * @param data       collection of objects to be mapped
     * @param parentNode meta-info for mapping of the previously mapped object
     *
     * @return {@link List<E>} of mapped objects (maybe null)
     */
    default List<E> toEntityList(Collection<D> data, ConverterTreeNode parentNode)
    {
        return this.toEntityConverter().convertList(data, parentNode);
    }

    /**
     * <p>Method to perform object mapping on collections of objects</p>
     * <p>Should call {@link #toData(E, ConverterTreeNode)} method to perform mapping</p>
     * <p>Should enforce mapping constraints for the collection as a whole before checking constraints on element level
     * during mapping</p>
     *
     * @param entities   collection of objects to be mapped
     * @param parentNode meta-info for mapping of the previously mapped object
     *
     * @return {@link List<D>} of mapped objects (maybe null)
     */
    default List<D> toDataList(Collection<E> entities, ConverterTreeNode parentNode)
    {
        return this.toDataConverter().convertList(entities, parentNode);
    }

    /**
     * <p>Method to perform object mapping on collections of objects</p>
     * <p>Should call {@link #toEntity(D, ConverterTreeNode)} method to perform mapping</p>
     * <p>Should enforce mapping constraints for the collection as a whole before checking constraints on element level
     * during mapping</p>
     *
     * @param data       collection of objects to be mapped
     * @param parentNode meta-info for mapping of the previously mapped object
     *
     * @return {@link Set<E>} of mapped objects (maybe null)
     */
    default Set<E> toEntitySet(Collection<D> data, ConverterTreeNode parentNode)
    {
        return this.toEntityConverter().convertSet(data, parentNode);
    }

    /**
     * <p>Method to perform object mapping on collections of objects</p>
     * <p>Should call {@link #toData(E, ConverterTreeNode)} method to perform mapping</p>
     * <p>Should enforce mapping constraints for the collection as a whole before checking constraints on element level
     * during mapping</p>
     *
     * @param entities   collection of objects to be mapped
     * @param parentNode meta-info for mapping of the previously mapped object
     *
     * @return {@link Set<D>} of mapped objects (maybe null)
     */
    default Set<D> toDataSet(Collection<E> entities, ConverterTreeNode parentNode)
    {
        return this.toDataConverter().convertSet(entities, parentNode);
    }

    /**
     * @return converter for one-way conversion from entity {@link E} to data {@link D}
     */
    default Converter<E, D> toDataConverter( )
    {
        return new TypeConverter<>(this.getEntityType(), this.getDataType(), this::toData);
    }

    /**
     * @return converter for one-way conversion from data {@link D} to entity {@link E}
     */
    default Converter<D, E> toEntityConverter( )
    {
        return new TypeConverter<>(this.getDataType(), this.getEntityType(), this::toEntity);
    }

    /**
     * <p>Conditionally executes provided mapping method if the data returned by the value supplier is initialized in
     * the current hibernate session context</p>
     *
     * @param value        reference to the getter of the field to get mapped
     * @param defaultValue value to be returned if the field in not initialized in current context
     * @param mapper       method to perform mapping
     * @param parentNode   meta-info for mapping of the previously mapped object
     * @param <I>          type of the object to be mapped
     * @param <O>type      of the object that is generated in the mapping process
     *
     * @return mapped object or provided default value if field is not initialized or default value of mapper if
     *         restrictions aren't met (maybe null)
     */
    static <I, O> O mapInitialized(Supplier<I> value, O defaultValue, BiFunction<I, ConverterTreeNode, O> mapper,
            ConverterTreeNode parentNode)
    {
        I item = value.get();
        if (!Hibernate.isInitialized(item))
        {
            return defaultValue;
        }

        return mapper.apply(item, parentNode);
    }
}
