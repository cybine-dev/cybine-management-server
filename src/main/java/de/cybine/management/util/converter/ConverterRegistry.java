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

    public <I, O> Optional<Converter<I, O>> findConverter(Class<I> inputType, Class<O> outputType)
    {
        return this.findConverter(new ConverterType<>(inputType, outputType));
    }

    @SuppressWarnings("unchecked")
    public <I, O> Optional<Converter<I, O>> findConverter(ConverterType<I, O> type)
    {
        return Optional.ofNullable((Converter<I, O>) this.converters.get(type));
    }

    public <E, D> Optional<EntityMapper<E, D>> findEntityMapper(Class<E> entityType, Class<D> dataType)
    {
        Converter<E, D> entityConverter = this.findConverter(entityType, dataType).orElse(null);
        Converter<D, E> dataConverter = this.findConverter(dataType, entityType).orElse(null);

        if (entityConverter == null || dataConverter == null)
            return Optional.empty();

        return Optional.of(new TypeMapper<>(entityType, dataType, entityConverter, dataConverter));
    }
}
