package uz.khurozov.totp;

public class TOTP extends HOTP {
    private final int period;

    public static final int DEFAULT_PERIOD = 30;

    public TOTP(String secret) {
        super(secret);
        this.period = DEFAULT_PERIOD;
    }

    public TOTP(Algorithm algorithm, String secret, int digits, int period) {
        super(algorithm, secret, digits);
        this.period = period;
    }

    @Override
    public String getCode(long second) {
        return super.getCode(second / period);
    }

    public String getCode() {
        return this.getCode(System.currentTimeMillis() / 1000);
    }

    public int getPeriod() {
        return period;
    }
}
