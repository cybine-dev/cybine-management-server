package de.cybine.management.data.util.password;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.enums.*;
import org.eclipse.microprofile.openapi.annotations.media.*;
import org.mindrot.jbcrypt.*;

import java.nio.charset.*;
import java.security.*;
import java.util.*;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "of")
@Schema(type = SchemaType.STRING, implementation = String.class, pattern = PasswordHash.BCRYPT_REGEX)
public class EnhancedBCryptPasswordHash implements PasswordHash
{
    @JsonValue
    @Schema(hidden = true)
    @Pattern(regexp = PasswordHash.BCRYPT_REGEX)
    private final String value;

    public String getSalt( )
    {
        return EnhancedBCryptPasswordHash.getSalt(this.value);
    }

    @Override
    public String asString( )
    {
        return this.value;
    }

    @Override
    public boolean isSame(Password password)
    {
        String hash = EnhancedBCryptPasswordHash.hash(password.asString(), this.getSalt());
        return BCrypt.checkpw(hash, this.value);
    }

    public static EnhancedBCryptPasswordHash of(Password password)
    {
        String salt = BCrypt.gensalt();
        String hash = EnhancedBCryptPasswordHash.hash(password.asString(), EnhancedBCryptPasswordHash.getSalt(salt));

        return EnhancedBCryptPasswordHash.of(BCrypt.hashpw(hash, salt));
    }

    private static String hash(String data, String salt)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes(StandardCharsets.UTF_8));

            byte[] bytes = md.digest(data.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(bytes);
        }
        catch (NoSuchAlgorithmException exception)
        {
            throw new IllegalArgumentException(exception);
        }
    }

    private static String getSalt(String hash)
    {
        return hash.split("\\$")[ 3 ].substring(0, 22);
    }
}
