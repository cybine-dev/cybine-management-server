package de.cybine.management.util.converter.data;

import lombok.*;

@Data
@Builder(builderClassName = "Generator")
public class Contact
{
    private final String email;
}
