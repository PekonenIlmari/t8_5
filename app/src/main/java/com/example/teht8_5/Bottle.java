package com.example.teht8_5;

public class Bottle {
    private String name;
    private String manufacturer;
    private double total_energy;
    private float size;
    private float price;

    public Bottle(String n, String manuf, float totE, float s, float p){
        name = n;
        manufacturer = manuf;
        total_energy = totE;
        size = s;
        price = p;
    }
    public String getName(){
        return name;
    }

    public String getManufacturer(){
        return manufacturer;
    }

    public double getEnergy(){
        return total_energy;
    }

    public float getSize() {
        return size;
    }

    public float getPrice() {
        return price;
    }

}
