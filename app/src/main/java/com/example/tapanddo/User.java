package com.example.tapanddo;

public class User {
    private String email;
    private String username;
    private String password;
    private String password2;

    public User(){

    }

    public User(String e, String u, String pw, String pw2){
        this.email = e;
        this.username = u;
        this.password = pw;
        this.password2 = pw2;
    } // User Constructor

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword2() {
        return password2;
    }
    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
