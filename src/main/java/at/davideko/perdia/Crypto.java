package at.davideko.perdia;

import javax.crypto.spec.IvParameterSpec;
import io.github.cdimascio.dotenv.*;

public class Crypto {
    Dotenv dotenv = Dotenv.load();

    private final String pwd = dotenv.get("AES_KEY");
    private final String alg = "AES/CBC/PKCS5Padding";
    private final IvParameterSpec ivspec = new IvParameterSpec(new byte[16]);

    public static void encrypt() {

    }

    //public static void decrypt()
}
