package de.cybine.management.util.converter;

import de.cybine.management.util.converter.data.*;
import de.cybine.management.util.converter.sample.*;
import org.junit.jupiter.api.*;

import java.util.*;
import java.util.stream.*;

@DisplayName("Converter")
class ConverterTest
{
    @Test
    @DisplayName("can convert a single item with a simple type")
    void testSingleItemConversionWithSimpleType( )
    {
        ConverterRegistry registry = new ConverterRegistry();
        registry.addConverter(new StringToIntConverter());

        ConversionProcessor<String, Integer> converter = Assertions.assertDoesNotThrow(
                ( ) -> registry.getProcessor(String.class, Integer.class));

        ConversionResult<Integer> result = Assertions.assertDoesNotThrow(( ) -> converter.toItem("3"));

        Assertions.assertEquals(3, result.result());
    }

    @Test
    @DisplayName("can convert a list of items with a simple type")
    void testItemListConversionWithSimpleType( )
    {
        ConverterRegistry registry = new ConverterRegistry();
        registry.addConverter(new StringToIntConverter());

        ConversionProcessor<String, Integer> converter = Assertions.assertDoesNotThrow(
                ( ) -> registry.getProcessor(String.class, Integer.class));

        ConversionResult<List<Integer>> result = Assertions.assertDoesNotThrow(
                ( ) -> converter.toList(List.of("3", "4", "5")));

        Assertions.assertEquals(List.of(3, 4, 5), result.result());
    }

    @Test
    @DisplayName("can convert a set of items with a simple type")
    void testItemSetConversionWithSimpleType( )
    {
        ConverterRegistry registry = new ConverterRegistry();
        registry.addConverter(new StringToIntConverter());

        ConversionProcessor<String, Integer> converter = Assertions.assertDoesNotThrow(
                ( ) -> registry.getProcessor(String.class, Integer.class));

        ConversionResult<Set<Integer>> result = Assertions.assertDoesNotThrow(
                ( ) -> converter.toSet(List.of("3", "4", "5")));

        Assertions.assertEquals(Set.of(3, 4, 5), result.result());
    }

    @Test
    @DisplayName("can convert a custom collection of items with a simple type")
    void testItemCollectionConversionWithSimpleType( )
    {
        ConverterRegistry registry = new ConverterRegistry();
        registry.addConverter(new StringToIntConverter());

        ConversionProcessor<String, Integer> converter = Assertions.assertDoesNotThrow(
                ( ) -> registry.getProcessor(String.class, Integer.class));

        ConversionResult<List<Integer>> result = Assertions.assertDoesNotThrow(
                ( ) -> converter.toCollection(List.of("3", "4", "5"), Collections.emptyList(), Collectors.toList()));

        Assertions.assertEquals(List.of(3, 4, 5), result.result());
    }

    @Test
    @DisplayName("can convert a complex type")
    void testComplexTypeConversion( )
    {
        ConverterRegistry registry = new ConverterRegistry();
        registry.addConverter(new PersonToStringConverter());
        registry.addConverter(new AddressToStringConverter());
        registry.addConverter(new ContactToStringConverter());

        ConversionProcessor<Person, String> converter = Assertions.assertDoesNotThrow(
                ( ) -> registry.getProcessor(Person.class, String.class));

        Address address = Address.builder().street("Sand Creek Rd").city("Laramie").country("USA").build();
        Contact contact = Contact.builder().email("john.doe@example.com").build();
        Person person = Person.builder().firstname("John").lastname("Doe").address(address).contact(contact).build();
        ConversionResult<String> result = Assertions.assertDoesNotThrow(( ) -> converter.toItem(person));

        String addressString = String.format("Address[street=%s, city=%s, country=%s]", address.getStreet(),
                address.getCity(), address.getCountry());
        String contactString = String.format("Contact[email=%s]", contact.getEmail());
        String personString = String.format("Person[firstname=%s, lastname=%s, address=%s, contact=%s]",
                person.getFirstname(), person.getLastname(), addressString, contactString);

        Assertions.assertEquals(personString, result.result());
    }

    @Test
    @DisplayName("can handle general max-depth constraints")
    void testComplexTypeConversionWithGeneralMaxDepthConstraint( )
    {
        ConverterRegistry registry = new ConverterRegistry();
        registry.addConverter(new PersonToStringConverter());
        registry.addConverter(new AddressToStringConverter());
        registry.addConverter(new ContactToStringConverter());

        ConverterConstraint constraint = ConverterConstraint.builder().maxDepth(1).build();
        ConverterTree tree = ConverterTree.builder().constraint(constraint).build();
        ConversionProcessor<Person, String> converter = Assertions.assertDoesNotThrow(
                ( ) -> registry.getProcessor(Person.class, String.class, tree));

        Address address = Address.builder().street("Sand Creek Rd").city("Laramie").country("USA").build();
        Contact contact = Contact.builder().email("john.doe@example.com").build();
        Person person = Person.builder().firstname("John").lastname("Doe").address(address).contact(contact).build();
        ConversionResult<String> result = Assertions.assertDoesNotThrow(( ) -> converter.toItem(person));

        String personString = String.format("Person[firstname=%s, lastname=%s, address=%s, contact=%s]",
                person.getFirstname(), person.getLastname(), null, null);

        Assertions.assertEquals(personString, result.result());
    }

    @Test
    @DisplayName("can handle type-specific max-depth constraints")
    void testComplexTypeConversionWithTypeSpecificMaxDepthConstraint( )
    {
        ConverterRegistry registry = new ConverterRegistry();
        registry.addConverter(new PersonToStringConverter());
        registry.addConverter(new AddressToStringConverter());
        registry.addConverter(new ContactToStringConverter());

        ConverterConstraint constraint = ConverterConstraint.builder().maxDepth(0).build();
        ConverterTree tree = ConverterTree.builder().typeConstraint(Contact.class, constraint).build();
        ConversionProcessor<Person, String> converter = Assertions.assertDoesNotThrow(
                ( ) -> registry.getProcessor(Person.class, String.class, tree));

        Address address = Address.builder().street("Sand Creek Rd").city("Laramie").country("USA").build();
        Contact contact = Contact.builder().email("john.doe@example.com").build();
        Person person = Person.builder().firstname("John").lastname("Doe").address(address).contact(contact).build();
        ConversionResult<String> result = Assertions.assertDoesNotThrow(( ) -> converter.toItem(person));

        String addressString = String.format("Address[street=%s, city=%s, country=%s]", address.getStreet(),
                address.getCity(), address.getCountry());
        String personString = String.format("Person[firstname=%s, lastname=%s, address=%s, contact=%s]",
                person.getFirstname(), person.getLastname(), addressString, null);

        Assertions.assertEquals(personString, result.result());
    }
}
