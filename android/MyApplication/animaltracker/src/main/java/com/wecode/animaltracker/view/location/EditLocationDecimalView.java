package com.wecode.animaltracker.view.location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.LocalisationUtils;
import com.wecode.animaltracker.activity.util.ValidationException;
import com.wecode.animaltracker.activity.util.ValidationHelper;
import com.wecode.animaltracker.util.LocationUtil;
import com.wecode.animaltracker.util.StringUtils;

import java.util.Locale;

/**
 * Created by SZIAK on 8/28/2016.
 */
public class EditLocationDecimalView {

    private TextView latitude;

    private TextView longitude;

    private TextView elevation;

    public EditLocationDecimalView(String location, Activity context) {


        latitude = (TextView) context.findViewById(R.id.latitudeDecimal);
        longitude = (TextView) context.findViewById(R.id.longitudeDecimal);
        elevation = (TextView) context.findViewById(R.id.elevationDecimal);

        if (StringUtils.isNotEmpty(location)) {
            String[] coordinates = location.split(" ");

            latitude.setText(coordinates[0].trim());
            longitude.setText(coordinates[1].trim());
            elevation.setText(coordinates[2].trim());
        }
    }

    public boolean validate(Context context) {

        try {

            ValidationHelper.assertNotEmpty(context, latitude, longitude, elevation);
            ValidationHelper.assertDouble(context, latitude, longitude, elevation);

            ValidationHelper.assertMaxValue(context, latitude, 90);
            ValidationHelper.assertMaxValue(context, longitude, 180);

            ValidationHelper.assertMinValue(context, latitude, -90);
            ValidationHelper.assertMinValue(context, longitude, -180);

        } catch (ValidationException ex) {
            Log.i(Globals.APP_NAME, ex.getMessage());
            return false;
        } catch (Exception ex) {
            Log.e(Globals.APP_NAME, "gps validation", ex);
            throw ex;
        }

        return true;
    }

    public String getLocation() {
        double latitude = LocalisationUtils.parseDouble(this.latitude.getText().toString());
        double longitude = LocalisationUtils.parseDouble(this.longitude.getText().toString());
        double elevation = LocalisationUtils.parseDouble(this.elevation.getText().toString());
        return LocationUtil.formatLocation(latitude, longitude, elevation);
    }

    public void initFromLocation(Location location) {
        latitude.setText(String.format(Locale.getDefault(), "%.5f", location.getLatitude()));
        longitude.setText(String.format(Locale.getDefault(), "%.5f", location.getLongitude()));
        elevation.setText(String.format(Locale.getDefault(), "%.2f", location.getAltitude()));
    }
}
