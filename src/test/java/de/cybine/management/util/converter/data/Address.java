package de.cybine.management.util.converter.data;

import lombok.*;

@Data
@Builder(builderClassName = "Generator")
public class Address
{
    private final String street;
    private final String city;
    private final String country;
}
