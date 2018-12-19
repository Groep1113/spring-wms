package base;

import org.mindrot.jbcrypt.BCrypt;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class TokenManager {
    private static final Random rand = new Random();

    private TokenManager() {}

    public static String generateToken() {
        byte[] randBytes = new byte[32];
        rand.nextBytes(randBytes);

        return BCrypt.hashpw(
            new String(randBytes, StandardCharsets.UTF_8),
            BCrypt.gensalt()
        );
    }

}
