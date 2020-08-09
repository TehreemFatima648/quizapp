package com.example.projject;

public class User {
    private int id;
    private String email;
    private String password;
    public User()
    {}
    public User(String e, String p)
    {email = e;
    password = p;}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
