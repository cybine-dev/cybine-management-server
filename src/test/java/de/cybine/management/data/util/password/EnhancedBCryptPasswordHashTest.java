package de.cybine.management.data.util.password;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class EnhancedBCryptPasswordHashTest
{
    @Test
    void testValidation()
    {
        Password password = Password.of("test1234");
        EnhancedBCryptPasswordHash hash = EnhancedBCryptPasswordHash.of(password);

        assertTrue(hash.isSame(password));
        assertFalse(hash.isSame(Password.of("test123")));
    }
}