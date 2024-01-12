package uz.khurozov.totp;

import org.apache.commons.codec.binary.Base32;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HOTP {
    private final Algorithm algorithm;
    private final int digits;
    private final Mac mac;
    private final int modDivisor;

    public static final int DEFAULT_DIGITS = 6;
    public static final Algorithm DEFAULT_ALGORITHM = Algorithm.SHA1;
    private static final Base32 BASE_32 = new Base32();

    public HOTP(String secret) {
        this(DEFAULT_ALGORITHM, secret, DEFAULT_DIGITS);
    }

    public HOTP(Algorithm algorithm, String secret, int digits) {
        this.algorithm = algorithm;
        this.digits = digits;
        try {
            mac =Mac.getInstance(algorithm.getHmac());
            mac.init(new SecretKeySpec(
                    BASE_32.decode(secret.getBytes(StandardCharsets.UTF_8)),
                    algorithm.getHmac()
            ));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }

        this.modDivisor = (int) Math.pow(10, digits);
    }

    public String getCode(long counter) {
        byte[] hmacHash = mac.doFinal(longToByteArray(counter));

        int offset = hmacHash[hmacHash.length - 1] & 0xf;
        int truncatedHash = (hmacHash[offset++] & 0x7f) << 24
                |(hmacHash[offset++] & 0xff) << 16
                | (hmacHash[offset++] & 0xff) << 8
                | (hmacHash[offset] & 0xff);

        return String.format("%0"+digits+"d", truncatedHash % modDivisor) ;
    }

    private static byte[] longToByteArray(long n) {
        return new byte[] {
                (byte) (n >>> 56),
                (byte) (n >>> 48),
                (byte) (n >>> 40),
                (byte) (n >>> 32),
                (byte) (n >>> 24),
                (byte) (n >>> 16),
                (byte) (n >>> 8),
                (byte) n,
        };
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public int getDigits() {
        return digits;
    }
}
