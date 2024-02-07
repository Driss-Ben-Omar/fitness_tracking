package com.fitness_tracking.entities;

public class User {

    Long id;

    String email;

    String name;

    String password;

    double weight;

    double height;

    String sex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public User() {
        super();
    }

    public User(Long id, String email, String name, String password, double weight, double height, String sex) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.weight = weight;
        this.height = height;
        this.sex = sex;
    }
}
