package de.cybine.management.converter;

import de.cybine.management.data.mail.address.*;
import jakarta.persistence.*;

import java.util.*;

@Converter
@SuppressWarnings("unused")
public class AddressActionConverter implements AttributeConverter<MailAddressAction, String>
{
    @Override
    public String convertToDatabaseColumn(MailAddressAction attribute)
    {
        return attribute.getAction();
    }

    @Override
    public MailAddressAction convertToEntityAttribute(String dbData)
    {
        if (dbData == null)
            return null;

        return Arrays.stream(MailAddressAction.values())
                     .filter(item -> item.getAction().equals(dbData))
                     .findAny()
                     .orElseThrow();
    }
}
