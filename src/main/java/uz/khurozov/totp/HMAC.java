package uz.khurozov.totp;

public enum HMAC {
    MD5     ("HmacMD5"),
    SHA1    ("HmacSHA1"),
    SHA256  ("HmacSHA256"),
    SHA384  ("HmacSHA384"),
    SHA512  ("HmacSHA512");

    private final String algorithm;

    HMAC(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }
}
