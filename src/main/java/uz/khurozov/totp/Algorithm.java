package uz.khurozov.totp;

public enum Algorithm {
    MD5     ("HmacMD5"),
    SHA1    ("HmacSHA1"),
    SHA256  ("HmacSHA256"),
    SHA384  ("HmacSHA384"),
    SHA512  ("HmacSHA512");

    private final String hmac;

    Algorithm(String hmac) {
        this.hmac = hmac;
    }

    public String getHmac() {
        return hmac;
    }
}
