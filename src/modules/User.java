package modules;

import java.util.*;

import static Components.PasswordHasher.hashPassword;

public class User {
    private UUID id ;
    private String fullName;
    private String email;
    private String address;
    private String password;


    public User(UUID id ,String fullName,String email,String address,String password){
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.password = hashPassword(password);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}