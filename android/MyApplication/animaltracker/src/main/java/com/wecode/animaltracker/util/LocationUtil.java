package com.wecode.animaltracker.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.widget.Toast;

import java.math.BigDecimal;

/**
 * Created by SZIAK on 8/28/2016.
 */
public class LocationUtil {

    private static final long MIN_TIME_CHANGE = 60000;

    private static final float MIN_DISTANCE_CHANGED = 10;

    public static void initLocationManager(Activity context, int requestCode) {

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (!Permissions.grantedOrRequestPermissions(context, requestCode, Manifest.permission.ACCESS_FINE_LOCATION)) {
            return;
        }

        LocationProvider gps = locationManager.getProvider("gps");
        if (gps != null) {
            locationManager.requestLocationUpdates("gps", MIN_TIME_CHANGE, MIN_DISTANCE_CHANGED, (LocationListener) context);
        } else {
            Toast.makeText(context, "Gps provider not available.", Toast.LENGTH_SHORT).show();
        }

    }

    public static String formatLocation(Location location) {
        return formatLocation(location.getLatitude(), location.getLongitude());
    }

    public static String formatLocation(Double locationLatitude, Double locationLongitude) {

        BigDecimal startLocationLatitude = new BigDecimal(locationLatitude);
        startLocationLatitude = startLocationLatitude.setScale(6, BigDecimal.ROUND_DOWN);

        BigDecimal startLocationLongitude = new BigDecimal(locationLongitude);
        startLocationLongitude = startLocationLongitude.setScale(6, BigDecimal.ROUND_DOWN);

        return String.format("%s, %s", startLocationLatitude, startLocationLongitude);
    }
}
