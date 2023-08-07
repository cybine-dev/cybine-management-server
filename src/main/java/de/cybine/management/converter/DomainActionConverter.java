package de.cybine.management.converter;

import de.cybine.management.data.mail.domain.*;
import jakarta.persistence.*;

import java.util.*;

@Converter
@SuppressWarnings("unused")
public class DomainActionConverter implements AttributeConverter<MailDomainAction, String>
{
    @Override
    public String convertToDatabaseColumn(MailDomainAction attribute)
    {
        return attribute.getAction();
    }

    @Override
    public MailDomainAction convertToEntityAttribute(String dbData)
    {
        if (dbData == null)
            return null;

        return Arrays.stream(MailDomainAction.values())
                     .filter(item -> item.getAction().equals(dbData))
                     .findAny()
                     .orElseThrow();
    }
}
