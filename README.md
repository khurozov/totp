# Java implementation of TOTP

TOTP (Time-based One-Time Password) algorithm is extension of HOTP (HMAC-based One-Time Password) and additional info can be more about it [here](https://datatracker.ietf.org/doc/html/rfc6238).
 
## Installation

Add maven dependency to your project
```xml
<dependency>
    <groupId>uz.khurozov</groupId>
    <artifactId>totp</artifactId>
    <version>1.0</version>
</dependency>
```

## Defaults

- HMAC algorithm: HmacSHA1
- Length of generated OTP code: 6
- Time step: 30 seconds
- Generated secret length: 32

## Example

```java
import uz.khurozov.totp.*;

public class Demo {
    public static void main(String[] args) {
        // previously generated secrets can be used
        String secret1 = "6GCM7ZFJJGEMHIWZSPQRXV2B66EO7PUS";

        // new secrets can be generated using Util class
        // recommended to use multiples of 8
        String secret2 = Util.generateBase32Secret(64);


        // TOTP instance can be created for a secret once
        // and generate codes continuously

        System.out.println("secret1: " + secret1);
        TOTP totp1 = new TOTP(secret1);
        for (int i = 0; i < 10; ++i) {
            System.out.println(totp1.getCode());

            try {
                Thread.sleep(totp1.getTimeStep());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        System.out.println("secret2: " + secret2);
        TOTP totp2 = new TOTP(HMAC.SHA512, secret2, 8, TOTP.DEFAULT_TIME_STEP);
        for (int i = 0; i < 10; ++i) {
            System.out.println(totp2.getCode());

            try {
                Thread.sleep(totp2.getTimeStep());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
```

## NOTES

Checked with Google Authenticator app and worked correctly.

Feel free to report about issues or contribute.