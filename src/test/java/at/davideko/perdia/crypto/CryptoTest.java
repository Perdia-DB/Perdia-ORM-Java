package at.davideko.perdia.crypto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Java Unit Testing class for the Crypto class
 * Since the AES key used for encrypting and decrypting is supposed to be different each time on purpose, these tests
 * are very simple on purpose, since there is no predictable outcome that is the same each time
 */
class CryptoTest {
    private final Crypto c = new Crypto();
    private final byte[] testBytes = {1, 2, 3, 4};

    @Test
    void encryptionTest() {
        // Check if encrypt() actually changes something
        assertNotEquals(Arrays.hashCode(testBytes), Arrays.hashCode(c.encrypt(testBytes)));

        // Check if encrypt() works correctly by decrypting and asserting afterwards
        assertEquals(Arrays.hashCode(testBytes), Arrays.hashCode(c.decrypt(c.encrypt(testBytes))));
    }

    @Test
    void decryptionTest() {
        // Check if decrypt actually changes something
        assertNotEquals(Arrays.hashCode(c.encrypt(testBytes)), Arrays.hashCode(c.decrypt(c.encrypt(testBytes))));

        // Check if decrypt() works correctly by encrypting first and asserting afterwards
        assertEquals(Arrays.hashCode(testBytes), Arrays.hashCode(c.decrypt(c.encrypt(testBytes))));
    }
}