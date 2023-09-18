package uz.khurozov.totp;

import org.apache.commons.codec.binary.Base32;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class Util {
    private static final Base32 BASE_32 = new Base32();
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int DEFAULT_SECRET_LENGTH = 32;

    public static String generateBase32Secret() {
        return new String(BASE_32.encode(getRandomBytes(DEFAULT_SECRET_LENGTH)));
    }

    public static String generateBase32Secret(int secretLength) {
        return new String(BASE_32.encode(getRandomBytes(secretLength)));
    }

    public static byte[] decodeBase32String(String s) {
        return BASE_32.decode(s.getBytes(StandardCharsets.UTF_8));
    }

    public static int powTen(int n) {
        int a = 1;
        while (--n >= 0) {
            a *= 10;
        }
        return a;
    }

    private static byte[] getRandomBytes(int secretLength) {
        byte[] bytes = new byte[secretLength * 5 / 8];
        RANDOM.nextBytes(bytes);
        return bytes;
    }
}
