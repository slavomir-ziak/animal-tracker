package com.wecode.animaltracker.activity.util;

import android.location.Location;

import java.math.BigDecimal;

/**
 * Created by sziak on 10-Apr-16.
 */
public class LocationFormatter {

    public static String formatLocation(Location location) {
        return formatLocation(location.getLatitude(), location.getLongitude());
    }

    public static String formatLocation(Double locationLatitude, Double locationLongitude) {

        BigDecimal startLocationLatitude = new BigDecimal(locationLatitude);
        startLocationLatitude = startLocationLatitude.setScale(6, BigDecimal.ROUND_DOWN);

        BigDecimal startLocationLongitude = new BigDecimal(locationLongitude);
        startLocationLongitude = startLocationLongitude.setScale(6, BigDecimal.ROUND_DOWN);

        return String.format("%s %s", startLocationLatitude, startLocationLongitude);
    }
}
