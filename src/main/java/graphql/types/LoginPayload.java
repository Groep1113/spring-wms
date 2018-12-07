package graphql.types;

import entity.User;

public class LoginPayload {

    private final String token;
    private final User user;

    public LoginPayload(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
