package com.whu.tools;

public class LocateInfo {
    private double longitude;
    private double Latitude;
    private boolean isChina;

    public LocateInfo() {

    }

    public LocateInfo(double longitude, double latitude) {
        this.longitude = longitude;
        this.Latitude = latitude;
        this.isChina = true;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public boolean isChina() {
        return isChina;
    }

    public void setChina(boolean china) {
        isChina = china;
    }
}