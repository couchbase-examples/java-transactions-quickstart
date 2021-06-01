package com.couchbase.example.model;


public class DemoUser {

    private String id;
    private String name;
    private String surname;
    private int credits;


    public DemoUser(){}

    public DemoUser(String id, String name, String surname, int credits) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.credits = credits;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
