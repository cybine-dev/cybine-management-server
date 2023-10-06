package de.cybine.management.util.api.query;

import com.fasterxml.jackson.annotation.*;
import jakarta.enterprise.context.*;
import lombok.*;
import lombok.experimental.*;

import java.util.*;

@Data
@RequestScoped
public class ApiPaginationInfo
{
    private Integer size;

    private Integer offset;

    @Accessors(fluent = true)
    private boolean includeTotal = false;

    @JsonProperty("total")
    private Long total;

    public Optional<Integer> getSize( )
    {
        return Optional.ofNullable(this.size);
    }

    @JsonIgnore
    public Optional<Long> getSizeAsLong( )
    {
        return this.getSize().map(Long::valueOf);
    }

    public Optional<Integer> getOffset( )
    {
        return Optional.ofNullable(this.offset);
    }

    @JsonIgnore
    public Optional<Long> getOffsetAsLong( )
    {
        return this.getOffset().map(Long::valueOf);
    }

    public Optional<Long> getTotal( )
    {
        return Optional.ofNullable(this.total);
    }
}
