package at.davideko.perdia;

import javax.crypto.spec.IvParameterSpec;

public class Crypto {
    private static final String pwd = System.getenv("AES_KEY");
    private static final String alg = "AES/CBC/PKCS5Padding";
    private static final IvParameterSpec ivspec = new IvParameterSpec(new byte[16]);

    //public static decrypt()

    public static void main(String[] args) {
        System.out.println(pwd);
    }
}
