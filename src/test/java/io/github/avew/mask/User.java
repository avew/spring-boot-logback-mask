package io.github.avew.mask;

import io.github.avew.mask.serializer.Asterisk;

public class User {

    private String username;

    @Asterisk
    private String password;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
