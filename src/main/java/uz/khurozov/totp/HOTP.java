package uz.khurozov.totp;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HOTP {
    private final Mac mac;
    private final int modDivisor;
    private final String format;

    private static final int DEFAULT_PASSWORD_LENGTH = 6;
    private static final String DEFAULT_HMAC_ALGORITHM = "HmacSHA1";

    public HOTP(String secret) {
        this(DEFAULT_HMAC_ALGORITHM, secret, DEFAULT_PASSWORD_LENGTH);
    }

    public HOTP(String algorithm, String secret) {
        this(algorithm, secret, DEFAULT_PASSWORD_LENGTH);
    }

    public HOTP(String secret, int passwordLength) {
        this(DEFAULT_HMAC_ALGORITHM, secret, passwordLength);
    }

    public HOTP(String algorithm, String secret, int passwordLength) {
        try {
            mac =Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(Util.decodeBase32String(secret), algorithm));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }

        this.modDivisor = Util.powTen(passwordLength);
        this.format = "%0"+passwordLength+"d";
    }

    public String getCode(long counter) {
        byte[] hmacHash = mac.doFinal(longToByteArray(counter));

        int offset = hmacHash[hmacHash.length - 1] & 0xf;
        int truncatedHash = (hmacHash[offset++] & 0x7f) << 24
                |(hmacHash[offset++] & 0xff) << 16
                | (hmacHash[offset++] & 0xff) << 8
                | (hmacHash[offset] & 0xff);

        return String.format(format, truncatedHash % modDivisor) ;
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
}
