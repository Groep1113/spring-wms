package base;

import org.mindrot.jbcrypt.BCrypt;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class TokenManager {
    private TokenManager() {}

    public static String generateToken() {
        Random rand = new Random();
        byte[] randBytes = new byte[32];
        rand.nextBytes(randBytes);

        return BCrypt.hashpw(
            new String(randBytes, StandardCharsets.UTF_8),
            BCrypt.gensalt()
        );
    }

}
