package uz.khurozov.totp;

public class TOTP extends HOTP {
    private final long timeStep;

    public static final long DEFAULT_TIME_STEP = 30_000;

    public TOTP(String secret) {
        super(secret);
        this.timeStep = DEFAULT_TIME_STEP;
    }

    public TOTP(HMAC hmac, String secret, int passwordLength, long timeStep) {
        super(hmac, secret, passwordLength);
        this.timeStep = timeStep;
    }

    public String getCode(long millis) {
        return super.getCode(millis / timeStep);
    }
}
