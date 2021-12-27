package com.carpartstorage.bean;

public class Part {
    private int id;
    private String name;
    private String type;
    private String brand;

    public Part(int id, String name, String type, String brand) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.brand = brand;
    }

    public Part(String name, String type, String brand) {
        this.name = name;
        this.type = type;
        this.brand = brand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
