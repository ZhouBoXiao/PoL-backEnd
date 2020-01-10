package com.whu.spatialIndex.geohash;

public class Point {
    private final double longitude;
    private final double latitude;

    public Point(double longitude,double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        if (Math.abs(latitude) > 90 || Math.abs(longitude) > 180) {
            throw new IllegalArgumentException("The supplied coordinates " + this + " are out of range.");
        }
    }

    public double getLon() {
        return longitude;
    }

    public double getLat() {
        return latitude;
    }

}
