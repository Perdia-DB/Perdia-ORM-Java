package at.davideko.perdia;

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

public class Crypto {
    Dotenv dotenv = Dotenv.load();

    private final byte[] pwd;
    //private final String alg = "AES/CBC/PKCS5Padding";
    private final String alg = "AES/CBC/NoPadding";
    private final IvParameterSpec ivspec = new IvParameterSpec(new byte[16]);

    public Crypto() {
        KeccakSponge sponge = new Shake128();

        // Fetching secret Key from the .env file
        sponge.getAbsorbStream().write(dotenv.get("AES_KEY").getBytes(StandardCharsets.UTF_8));

        // Hashing the key
        byte[] digest = new byte[16];
        sponge.getSqueezeStream().read(digest);
        this.pwd = digest;
    }

    public byte[] encrypt(byte[] bytes) {
        byte[] encrypted = new byte[0];

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKey key = new SecretKeySpec(this.pwd, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);

            for (int i = 0; i < bytes.length / 16; i++) {
                byte[] chunk = Arrays.copyOfRange(bytes, i*16, 16 + i*16);

                chunk = cipher.doFinal(chunk);
                encrypted = ArrayUtils.addAll(encrypted, chunk);
            }

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return encrypted;
    }

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
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return decrypted;
    }
}
