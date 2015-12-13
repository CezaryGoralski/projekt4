package com.example.cezar.projekt4;

/**
 * Created by Ruben on 12/12/2015.
 */
public class UserDto {
    private String username;
    private String password;

    public UserDto (String email, String password){
        this.username = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String email) {
        this.username = email;
    }
}
