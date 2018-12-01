package base;

import entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Random;

public class TokenManager {

    private static final HashMap<User, String> tokens = new HashMap<>();

    public static String generateToken(User user) {
        String token = tokens.get(user);
        if (token != null) return token;

        Random rand = new Random();
        byte[] randBytes = new byte[32];
        rand.nextBytes(randBytes);
        token = BCrypt.hashpw(
            new String(randBytes, StandardCharsets.UTF_8),
            BCrypt.gensalt()
        );

        tokens.put(user, token);
        return token;
    }

    public static String getToken(User user) {
        return tokens.get(user);
    }

    public static User getUser(String token) {
        return tokens
            .keySet()
            .stream()
            .filter(u -> tokens.get(u).equals(token))
            .findFirst()
            .orElse(null);
    }

    public static String invalidateToken(User user) {
        return tokens.remove(user);
    }

}
