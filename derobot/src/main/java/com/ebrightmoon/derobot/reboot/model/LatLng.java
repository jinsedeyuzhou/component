package com.ebrightmoon.derobot.reboot.model;

import java.io.Serializable;

/**
 *  on 2019-07-19
 */
public class LatLng implements Serializable {
    public double latitude;

    public double longitude;

    public LatLng(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
