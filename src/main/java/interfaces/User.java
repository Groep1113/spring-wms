package interfaces;

import java.util.ArrayList;
import java.util.List;

public class User {

    private final long id;
    private final String name;
    private final String email;
    private final ArrayList<String> roles;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
        this.roles = new ArrayList<>();
        this.roles.add("admin");
        this.roles.add("beheerder");
        this.email = "admin@htg-it.nl";
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List getRoles() {
        return this.roles;
    }

    public String getEmail() {
        return this.email;
    }
}