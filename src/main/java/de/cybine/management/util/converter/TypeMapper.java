package de.cybine.management.util.converter;

import lombok.*;

@AllArgsConstructor
public class TypeMapper<E, D> implements EntityMapper<E, D>
{
    @Getter
    private final Class<E> entityType;

    @Getter
    private final Class<D> dataType;

    private final Converter<E, D> entityConverter;
    private final Converter<D, E> dataConverter;

    @Override
    public E toEntity(D data, ConverterTreeNode parentNode)
    {
        return this.dataConverter.convert(data, parentNode);
    }

    @Override
    public D toData(E entity, ConverterTreeNode parentNode)
    {
        return this.entityConverter.convert(entity, parentNode);
    }
}
