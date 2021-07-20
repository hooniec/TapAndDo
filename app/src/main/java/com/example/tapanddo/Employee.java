package com.example.tapanddo;

public class Employee {
    private String id;
    private String name;
    private String position;
    private String phone;
    private String email;
    private int profile;

    public Employee(){

    }

    public Employee(String id, String n, String p, String ph, String e, int pr){
        this.id = id;
        this.name = n;
        this.position = p;
        this.phone = ph;
        this.email = e;
        this.profile = pr;
    } // Employee Constructor


    public String getId() {return id; }
    public void setId(String id) { this.id = id; }
    public String getName(){
        return name;
    }
    public void setName(String n){
        this.name = n;
    }
    public String getPosition(){
        return position;
    }
    public void setPosition(String p){
        this.position = p;
    }
    public String getPhone() { return phone; }
    public void setPhone(String p) { this.phone = p; }
    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }
    public int getProfile() {
        return profile;
    }
    public void setProfile(int pr) {
        this.profile = pr;
    }
}
