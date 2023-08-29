package de.cybine.management.util.converter.data;

import lombok.*;

@Data
@Builder(builderClassName = "Generator")
public class Person
{
    private final String firstname;
    private final String lastname;

    private final Address address;
    private final Contact contact;
}
