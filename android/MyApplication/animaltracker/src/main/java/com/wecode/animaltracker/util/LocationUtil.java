package com.wecode.animaltracker.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.widget.Toast;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.LocalisationUtils;
import com.wecode.animaltracker.service.SettingsDataService;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * Created by SZIAK on 8/28/2016.
 */
public class LocationUtil {

    private static final long MIN_TIME_CHANGE = 5000;

    private static final float MIN_DISTANCE_CHANGED = 1;

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
            Toast.makeText(context, R.string.gps_provider_not_available, Toast.LENGTH_SHORT).show();
        }
    }

    public static String formatLocation(Location location){
        if (SettingsDataService.getInstance().get().isLocationDMS()) {
            return formatLocationToMinutesAndSeconds(location);
        } else {
            return formatLocationToDecimals(location);
        }
    }

    public static double[] parseLocation(String location) {
        if (SettingsDataService.getInstance().get().isLocationDMS()) {
            return parseLocationDMS(location);
        } else {
            return parseLocationDecimals(location);
        }
    }

    public static String formatLocation(Double latitude, Double longitude, Double altitude){
        if (SettingsDataService.getInstance().get().isLocationDMS()) {
            return formatLocationToMinutesAndSeconds(latitude, longitude, altitude);
        } else {
            return formatLocationToDecimals(latitude, longitude, altitude);
        }
    }

    private static String formatLocationToDecimals(Location location) {
        return formatLocationToDecimals(location.getLatitude(), location.getLongitude(), location.getAltitude());
    }

    private static String formatLocationToMinutesAndSeconds(Location location) {
        return formatLocationToMinutesAndSeconds(location.getLatitude(), location.getLongitude(), location.getAltitude());
    }

    static String formatLocationToMinutesAndSeconds(Double locationLatitude, Double locationLongitude, Double elevation) {
        String latitudeStr = convertLocation(locationLatitude, false);
        String longitudeStr = convertLocation(locationLongitude, true);

        return String.format(Locale.getDefault(), "%s %s %.2f", latitudeStr, longitudeStr, elevation);
    }

    private static String formatLocationToDecimals(Double locationLatitude, Double locationLongitude, Double elevation) {
        return String.format(Locale.getDefault(), "%.5f %.5f %.2f", locationLatitude, locationLongitude, elevation);
    }

    static double[] parseLocationDMS(String location) {
        String[] split = location.split(" ");
        if (split.length != 3) throw new RuntimeException("cannot parse location: " + location);

        double latitude = convertLocation(split[0].trim());
        double longitude = convertLocation(split[1].trim());

        latitude = new BigDecimal(latitude).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        longitude = new BigDecimal(longitude).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        double elevation = new BigDecimal(split[2].trim()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        return new double[]{latitude, longitude, elevation};
    }

    private static double[] parseLocationDecimals(String location) {
        String[] split = location.split(" ");
        if (split.length != 3) throw new RuntimeException("cannot parse location: " + location);

        double latitude = LocalisationUtils.parseDouble(split[0].trim());
        double longitude = LocalisationUtils.parseDouble(split[1].trim());
        double elevation = LocalisationUtils.parseDouble(split[2].trim());

        latitude = new BigDecimal(latitude).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
        longitude = new BigDecimal(longitude).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
        elevation = new BigDecimal(elevation).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        return new double[]{latitude, longitude, elevation};
    }

    private static String convertLocation(Double coordinate, boolean longitude) {

        if (coordinate == null) {
            return String.format(Locale.getDefault(), "%d°%d'%.2f\"%s", 0, 0, 0.0, "");
        }

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

        return String.format(Locale.getDefault(), "%d°%d'%.2f\"%s", degrees, minutes, seconds, direction);
    }

    private static double convertLocation(String coordinate) {

        String[] split = coordinate.split("°|'|\"");

        if (split.length != 4) {
            throw new RuntimeException("invalid location format: " + coordinate);
        }

        double degrees = Integer.valueOf(split[0]);
        double minutes = Integer.valueOf(split[1]);
        double seconds = LocalisationUtils.parseDouble(split[2]);

        double result = degrees + (minutes/60) + (seconds / 3600);

        if (split[3].matches("W|S")) result *= -1;

        return result;
    }



}
