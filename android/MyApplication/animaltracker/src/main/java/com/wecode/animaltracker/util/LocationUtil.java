package com.wecode.animaltracker.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Locale;

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
        String latitudeStr = convertLocation(locationLatitude, false);
        String longitudeStr = convertLocation(locationLongitude, true);

        return String.format("%s, %s", latitudeStr, longitudeStr);
    }

    public static double[] parseLocation(String location) {
        String[] split = location.split(",");
        double latitude = convertLocation(split[0].trim());
        double longitude = convertLocation(split[1].trim());

        latitude = new BigDecimal(latitude).setScale(6, BigDecimal.ROUND_DOWN).doubleValue();
        longitude = new BigDecimal(longitude).setScale(6, BigDecimal.ROUND_DOWN).doubleValue();

        return new double[]{latitude, longitude};
    }
    private static String convertLocation(double coordinate, boolean longitude) {

        String direction;

        if (longitude) {
            direction = coordinate > 0 ? "E" : "W";
        } else {
            direction = coordinate > 0 ? "N" : "S";
        }

        coordinate = Math.abs(coordinate);

        int degrees = (int) Math.floor(coordinate);

        double minutesDegrees = 60 * (coordinate - degrees);

        int minutes = (int) Math.floor(minutesDegrees);

        double seconds = 3600 * (coordinate - degrees) - (60 * minutes);

        return String.format(Locale.ENGLISH, "%d°%d'%.2f\"%s", degrees, minutes, seconds, direction);
    }

    private static double convertLocation(String coordinate) {

        String[] split = coordinate.split("°|'|\"");

        if (split.length != 4) {
            throw new RuntimeException("invalid location format: " + coordinate);
        }

        double degrees = Integer.valueOf(split[0]);
        double minutes = Integer.valueOf(split[1]);
        double seconds = Double.valueOf(split[2]);

        double result = degrees + (minutes/60) + (seconds / 3600);

        if (split[3].matches("W|S")) result *= -1;

        return result;
    }



}
