package uz.khurozov.totp;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HOTP {
    private final HMAC hmac;
    private final int passwordLength;
    private final Mac mac;
    private final int modDivisor;
    private final String format;

    public static final int DEFAULT_PASSWORD_LENGTH = 6;
    public static final HMAC DEFAULT_HMAC = HMAC.SHA1;

    public HOTP(String secret) {
        this(DEFAULT_HMAC, secret, DEFAULT_PASSWORD_LENGTH);
    }

    public HOTP(HMAC hmac, String secret, int passwordLength) {
        this.hmac = hmac;
        this.passwordLength = passwordLength;
        try {
            mac =Mac.getInstance(hmac.getAlgorithm());
            mac.init(new SecretKeySpec(Util.decodeBase32String(secret), hmac.getAlgorithm()));
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

    public HMAC getHmac() {
        return hmac;
    }

    public int getPasswordLength() {
        return passwordLength;
    }
}
