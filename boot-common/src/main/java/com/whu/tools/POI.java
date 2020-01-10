package com.whu.tools;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;

public class POI {

    private String name;
    private double longitude;
    private double latitude;
    private String address;
    private String type;
    private String area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString(){
        return "{\"name\":\"" + this.name + "\",\"location\":[{\"xn\":\"" + this.latitude + "\"},{\"yn\":\""+
                this.longitude + "\"},{\"zn\":\"0\"}]"
                 + ",\"address\":\"" + this.address +
                "\",\"type\":\"" + this.type + "\",\"area\":\"" + this.area + "\"}";
    }
}
