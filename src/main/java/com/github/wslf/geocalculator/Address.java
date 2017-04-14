package com.github.wslf.geocalculator;

/**
 *
 * @author Wsl_F
 */
public class Address {

    String text;
    double latitude;
    double longitude;

    public Address(String text, double latitude, double longitude) {
        this.text = text;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Address(String text, String pos) {
        this.text = text;
        String[] coordinates = pos.split(" ");
        this.latitude = Double.valueOf(coordinates[1]);
        this.longitude = Double.valueOf(coordinates[0]);
    }

}
