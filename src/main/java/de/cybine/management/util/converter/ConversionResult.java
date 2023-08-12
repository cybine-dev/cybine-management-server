package de.cybine.management.util.converter;

public record ConversionResult<T>(ConverterTree metadata, T result)
{ }
