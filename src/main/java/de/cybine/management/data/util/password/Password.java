package de.cybine.management.data.util.password;

import com.fasterxml.jackson.annotation.*;
import de.cybine.management.data.util.primitive.*;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.enums.*;
import org.eclipse.microprofile.openapi.annotations.media.*;

@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "of")
@Schema(type = SchemaType.STRING, implementation = String.class)
public class Password implements StringPrimitive
{
    @JsonIgnore
    @Schema(hidden = true)
    private final String value;

    public String asString( )
    {
        return this.value;
    }

    public BCryptPasswordHash toBCryptHash( )
    {
        return BCryptPasswordHash.of(this, false);
    }

    public BCryptPasswordHash toBCryptHash(boolean ignoreLengthConstraint)
    {
        return BCryptPasswordHash.of(this, ignoreLengthConstraint);
    }

    public EnhancedBCryptPasswordHash toEnhancedBCryptHash( )
    {
        return EnhancedBCryptPasswordHash.of(this);
    }

    @Override
    public String toString( )
    {
        return "******";
    }
}
