package de.cybine.management.service.action;

import lombok.*;

@Data
@Builder(builderClassName = "Generator")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ActionProcessorMetadata
{
    private final String namespace;
    private final String category;
    private final String name;

    private final String fromStatus;
    private final String toStatus;

    public String asString( )
    {
        return String.format("ns(%s) cat(%s) name(%s) from(%s) to(%s)", this.getNamespace(), this.getCategory(),
                this.getName(), this.getFromStatus(), this.getToStatus());
    }
}
