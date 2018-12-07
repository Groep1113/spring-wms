package graphql;

import entity.User;
import graphql.servlet.GraphQLContext;
import graphql.servlet.GraphQLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;

@Component
public class CustomContextBuilder implements GraphQLContextBuilder {

    private final UserRepository userRepository;

    @Autowired
    public CustomContextBuilder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private String getTokenFromCookies(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (!cookie.getName().equals("token")) continue;
            return cookie.getValue();
        }

        return null;
    }

    private boolean inLocalDevContext(HttpServletRequest request) {
        return request.getRemoteHost().equals("0:0:0:0:0:0:0:1");
    }

    @Override
    public GraphQLContext build(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            // This if statement makes it so that during local development
            // we can use a cookie tagged as `token`
            if (inLocalDevContext(request)) {
                String token = getTokenFromCookies(request.getCookies());
                if (token == null) return new AuthContext(null, request, response);

                User user = userRepository.findByToken(token).orElse(null);
                return new AuthContext(user, request, response);
            }
            return new AuthContext(null, request, response);
        }

        String token = header.substring("Bearer ".length());
        User user = userRepository.findByToken(token).orElse(null);

        return new AuthContext(user, request, response);
    }

    @Override
    public GraphQLContext build(Session session, HandshakeRequest handshakeRequest) {
        return null;
    }

    @Override
    public GraphQLContext build() {
        return null;
    }

}
