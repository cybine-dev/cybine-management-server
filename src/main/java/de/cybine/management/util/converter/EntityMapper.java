package de.cybine.management.util.converter;

import org.hibernate.*;

import java.util.function.*;

/**
 * <p>Two way {@link Converter}</p>
 *
 * @param <E>
 *         type of the entity
 * @param <D>
 *         type of the data-object
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
     *
     * @param data
     *         object to be mappepd
     * @param helper
     *         helper for mapping of related objects
     *
     * @return mapped object
     */
    E toEntity(D data, ConversionHelper helper);

    /**
     * <p>Method to perform object mapping</p>
     *
     * @param entity
     *         object to be mappepd
     * @param helper
     *         helper for mapping of related objects
     *
     * @return mapped object
     */
    D toData(E entity, ConversionHelper helper);

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
     * @param value
     *         reference to the getter of the field to get mapped
     * @param mapper
     *         method to perform mapping
     * @param <I>
     *         type of the object to be mapped
     * @param <O>type
     *         of the object that is generated in the mapping process
     *
     * @return mapped object or provided default value if field is not initialized
     */
    static <I, O> O mapInitialized(Supplier<I> value, Function<I, O> mapper)
    {
        I item = value.get();
        if (!Hibernate.isInitialized(item))
        {
            return null;
        }

        return mapper.apply(item);
    }
}
