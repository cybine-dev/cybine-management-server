package de.cybine.management.converter;

import de.cybine.management.data.mail.tls.*;
import jakarta.persistence.*;

import java.util.*;

@Converter
@SuppressWarnings("unused")
public class TLSPolicyTypeConverter implements AttributeConverter<MailTLSPolicyType, String>
{
    @Override
    public String convertToDatabaseColumn(MailTLSPolicyType attribute)
    {
        return attribute.getType();
    }

    @Override
    public MailTLSPolicyType convertToEntityAttribute(String dbData)
    {
        if (dbData == null)
            return null;

        return Arrays.stream(MailTLSPolicyType.values())
                     .filter(item -> item.getType().equals(dbData))
                     .findAny()
                     .orElseThrow();
    }
}
