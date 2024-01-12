package uz.khurozov.totp;

public class TOTP extends HOTP {
    private final long period;

    public static final long DEFAULT_PERIOD = 30_000L;

    public TOTP(String secret) {
        super(secret);
        this.period = DEFAULT_PERIOD;
    }

    public TOTP(Algorithm algorithm, String secret, int digits, long period) {
        super(algorithm, secret, digits);
        this.period = period;
    }

    @Override
    public String getCode(long millis) {
        return super.getCode(millis / period);
    }

    public String getCode() {
        return this.getCode(System.currentTimeMillis());
    }

    public long getPeriod() {
        return period;
    }
}
