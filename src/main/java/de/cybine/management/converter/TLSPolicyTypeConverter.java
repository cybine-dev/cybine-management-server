package de.cybine.management.converter;

import de.cybine.management.data.mail.tls.*;
import jakarta.persistence.*;

import java.util.*;

@Converter
@SuppressWarnings("unused")
public class TLSPolicyTypeConverter implements AttributeConverter<TLSPolicyType, String>
{
    @Override
    public String convertToDatabaseColumn(TLSPolicyType attribute)
    {
        return attribute.getType();
    }

    @Override
    public TLSPolicyType convertToEntityAttribute(String dbData)
    {
        if (dbData == null)
            return null;

        return Arrays.stream(TLSPolicyType.values())
                     .filter(item -> item.getType().equals(dbData))
                     .findAny()
                     .orElseThrow();
    }
}
