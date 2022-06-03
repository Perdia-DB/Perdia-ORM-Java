package at.davideko.perdia.tcp;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.github.aelstad.keccakj.core.KeccakSponge;
import com.github.aelstad.keccakj.fips202.Shake128;
import io.github.cdimascio.dotenv.*;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Class for handling the encryption and decryption of queries
 */
public class Crypto {
    // Loading the .env file for the AES key
    Dotenv dotenv = Dotenv.load();

    // Byte array for storing the single characters of the hashed AES key encoded in UTF-8
    private final byte[] pwd;
    // Specifying which AES algorithm is to be used
    private final String alg = "AES/CBC/NoPadding";
    // Specifying the IV buffer size
    private final IvParameterSpec ivspec = new IvParameterSpec(new byte[16]);

    /**
     * Constructor for the Crypto class, in which the AES key gets hashed using the SHA3-Shake128 algorithm and then
     * stored in an instance variable.
     */
    public Crypto() {
        KeccakSponge sponge = new Shake128();

        // Fetching secret Key from the .env file
        sponge.getAbsorbStream().write(dotenv.get("AES_KEY").getBytes(StandardCharsets.UTF_8));

        // Hashing and storing the key
        byte[] digest = new byte[16];
        sponge.getSqueezeStream().read(digest);
        this.pwd = digest;
    }

    /**
     * Encrypts the given byte array using AES encryption with the hashed key, also adds a buffer of zeroes to the
     * end of the array so the length becomes a multiple of 16.
     * @param bytes Byte array to be encrypted
     * @return Encrypted byte array including the buffer
     * @exception InvalidKeyException If the key given in the .env file is invalid, an InvalidKeyException is thrown
     * and caught.
     * @throws AssertionError An AssertionError gets thrown if any of the following exceptions get thrown and caught:
     * InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException
     * or BadPaddingException. This is because the following exceptions are made impossible to be thrown by how the
     * code is written.
     */
    public byte[] encrypt(byte[] bytes) {
        byte[] encrypted = new byte[0];

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKey key = new SecretKeySpec(this.pwd, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);


            for (int i = 0; i < bytes.length / 16 + 1; i++) {
                byte[] chunk = Arrays.copyOfRange(bytes, i*16, 16 + i*16);

                chunk = cipher.doFinal(chunk);
                encrypted = ArrayUtils.addAll(encrypted, chunk);
            }

        } catch (InvalidKeyException e) {
            System.out.println("Invalid Key: " + e.getMessage());
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchPaddingException |
                 IllegalBlockSizeException | BadPaddingException e) {
            // This shouldn't be able to happen
            throw new AssertionError(e);
        }

        return encrypted;
    }

    /**
     * Decrypts the given byte array using AES decryption with the hashed key, also removes the buffer of zeroes at
     * the end of the array.
     * @param bytes Byte array to be decrypted
     * @return Decrypted byte array
     * @exception BadPaddingException If the padding at the end of the encrypted byte array is incorrect, a
     * BadPaddingException is thrown and caught.
     * @exception IllegalBlockSizeException If the length of the encrypted byte array is not a multiple of 16, an
     * IllegalBlockSizeException is thrown and caught. This may be because of a server-side error.
     * @exception InvalidKeyException If the key given in the .env file is invalid, an InvalidKeyException is thrown
     * and caught.
     * @throws AssertionError An AssertionError gets thrown if any of the following exceptions get thrown and caught:
     * InvalidAlgorithmParameterException, NoSuchAlgorithmException or NoSuchPaddingException. This is because the
     * following exceptions are made impossible to be thrown by how the code is written.
     */
    public byte[] decrypt(byte[] bytes) {
        byte[] decrypted = new byte[0];

        try {
            Cipher cipher = Cipher.getInstance(alg);
            SecretKey key = new SecretKeySpec(this.pwd, "AES");
            cipher.init(Cipher.DECRYPT_MODE, key, ivspec);

            for (int i = 0; i < bytes.length / 16; i++) {
                byte[] chunk = Arrays.copyOfRange(bytes, i*16, 16 + i*16);

                chunk = cipher.doFinal(chunk);
                decrypted = ArrayUtils.addAll(decrypted, chunk);
            }

            // Removing trailing padding 0's from decrypted plaintext
            while ((decrypted.length != 0) && (decrypted[decrypted.length-1] == (byte) 0)) {
                decrypted = ArrayUtils.removeElement(decrypted, (byte) 0);
            }

        } catch (InvalidKeyException e) {
            System.out.println("Invalid Key: " + e.getMessage());
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            // This shouldn't be able to happen
            throw new AssertionError(e);
        } catch (IllegalBlockSizeException e) {
            System.out.println("Invalid data length: " + e.getMessage());
        } catch (BadPaddingException e) {
            System.out.println("Invalid padding: " + e.getMessage());
        }

        return decrypted;
    }
}
