package com.thedebuggers.backend.common.util;


import lombok.Getter;

@Getter
public class Location {
    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
