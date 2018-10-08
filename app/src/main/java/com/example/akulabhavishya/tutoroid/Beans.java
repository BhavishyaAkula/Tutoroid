package com.example.akulabhavishya.tutoroid;

public class Beans {
    private String id;

    private String fullname;
    private String mobile;
    private String timing;
    private String adharnumber;
    private String city;
    private String address;
    private String email;
    private String classes;
    private String subject;

    public Beans(String fullname, String mobile, String timing, String adharnumber, String city, String address, String email, String classes, String subject) {
        this.fullname = fullname;
        this.mobile = mobile;
        this.timing = timing;
        this.adharnumber = adharnumber;
        this.city = city;
        this.address = address;
        this.email = email;
        this.classes = classes;
        this.subject = subject;
    }

    public Beans() {

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getAdharnumber() {
        return adharnumber;
    }

    public void setAdharnumber(String adharnumber) {
        this.adharnumber = adharnumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


}
