# Java implementation of TOTP

TOTP (Time-based One-Time Password) algorithm is extension of HOTP (HMAC-based One-Time Password) and additional info can be more about it [here](https://datatracker.ietf.org/doc/html/rfc6238).
 
## Installation

Add maven dependency to your project
```xml
<dependency>
    <groupId>uz.khurozov</groupId>
    <artifactId>totp</artifactId>
    <version>1.0.2</version>
</dependency>
```

## Defaults

- Algorithm: SHA1
- Digits: 6
- Period: 30 seconds

## Example

```java
import uz.khurozov.totp.*;

public class Demo {
    public static void main(String[] args) {
        // previously generated BASE32 secret
        String secret = "6GCM7ZFJJGEMHIWZSPQRXV2B66EO7PUS";
        System.out.println("secret: " + secret);

        // TOTP instance can be created for a secret once
        // and generate codes repeatedly
        TOTP totp = new TOTP(secret);
        for (int i = 0; i < 10; ++i) {
            System.out.println(totp.getCode());

            try {
                Thread.sleep(totp.getPeriod());
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