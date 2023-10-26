package de.cybine.management.service.action;

import com.fasterxml.jackson.databind.*;
import jakarta.enterprise.context.*;
import lombok.*;

import java.util.*;

@ApplicationScoped
@RequiredArgsConstructor
public class ActionDataTypeRegistry
{
    private final Map<String, JavaType> dataTypes = new HashMap<>();

    public void registerType(String name, JavaType type)
    {
        this.dataTypes.put(name, type);
    }

    public Optional<JavaType> findType(String name)
    {
        return Optional.ofNullable(this.dataTypes.get(name));
    }

    public Optional<String> findTypeName(JavaType type)
    {
        System.out.println(type);
        System.out.println(this.dataTypes.entrySet());
        return this.dataTypes.entrySet()
                             .stream()
                             .filter(entry -> entry.getValue().equals(type))
                             .map(Map.Entry::getKey)
                             .findFirst();
    }
}
