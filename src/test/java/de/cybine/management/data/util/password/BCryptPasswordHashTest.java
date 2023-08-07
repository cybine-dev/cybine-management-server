package de.cybine.management.data.util.password;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BCryptPasswordHashTest
{
    @Test
    void testValidation( )
    {
        Password password = Password.of("test1234");
        BCryptPasswordHash hash = BCryptPasswordHash.of(password);

        assertTrue(hash.isSame(password));
        assertFalse(hash.isSame(Password.of("test123")));
    }

    @Test
    void testMaxLength( )
    {
        String password = "a".repeat(72);

        Password validPassword = Password.of(password);
        assertDoesNotThrow(( ) -> BCryptPasswordHash.of(validPassword));

        Password tooLongPassword = Password.of(password + "a");
        assertThrows(IllegalArgumentException.class, ( ) -> BCryptPasswordHash.of(tooLongPassword));
        assertDoesNotThrow(( ) -> BCryptPasswordHash.of(tooLongPassword, true));
    }
}