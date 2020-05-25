package com.example.try2.Files;

public class Loc {

    private double latitude;
    private double longitude;

    public Loc(double latitude, double longitude) {

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() { return this.latitude; }

    public double getLongitude() { return this.longitude; }

}