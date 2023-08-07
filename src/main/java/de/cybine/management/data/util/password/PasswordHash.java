package de.cybine.management.data.util.password;

import de.cybine.management.data.util.primitive.*;

public interface PasswordHash extends StringPrimitive
{
    String BCRYPT_REGEX = "^\\$2[ayb]\\$[0-9]{2}\\$[A-Za-z0-9./]{53}$";

    boolean isSame(Password password);
}
