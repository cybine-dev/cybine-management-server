package de.cybine.management.converter;

import de.cybine.management.data.mail.domain.*;
import jakarta.persistence.*;

import java.util.*;

@Converter
@SuppressWarnings("unused")
public class DomainActionConverter implements AttributeConverter<DomainAction, String>
{
    @Override
    public String convertToDatabaseColumn(DomainAction attribute)
    {
        return attribute.getAction();
    }

    @Override
    public DomainAction convertToEntityAttribute(String dbData)
    {
        if (dbData == null)
            return null;

        return Arrays.stream(DomainAction.values())
                     .filter(item -> item.getAction().equals(dbData))
                     .findAny()
                     .orElseThrow();
    }
}
