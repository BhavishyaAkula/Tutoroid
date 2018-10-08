package com.example.akulabhavishya.tutoroid;

public class Jobsbeans {
    private String id;
    private String from;
    private String address;
    private String city;
    private String mob;
    private String email;
    private String description;

    public Jobsbeans(String from,String description) {
       this.from = from;
       this.description = description;
    }
    public Jobsbeans() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Jobsbeans(String id,String address, String city, String mob, String email) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.mob = mob;
        this.email = email;
    }
}
