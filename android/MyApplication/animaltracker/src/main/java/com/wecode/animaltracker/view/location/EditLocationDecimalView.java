package com.wecode.animaltracker.view.location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.ValidationHelper;
import com.wecode.animaltracker.util.LocationUtil;
import com.wecode.animaltracker.util.StringUtils;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * Created by SZIAK on 8/28/2016.
 */
public class EditLocationDecimalView {

    private TextView latitude;

    private TextView longitude;

    private TextView elevation;

    @SuppressLint("DefaultLocale")
    public EditLocationDecimalView(String location, Activity context) {


        latitude = (TextView) context.findViewById(R.id.latitudeDecimal);
        longitude = (TextView) context.findViewById(R.id.longitudeDecimal);
        elevation = (TextView) context.findViewById(R.id.elevationDecimal);

        if (StringUtils.isNotEmpty(location)) {
            String[] coordinates = location.split(",");

            latitude.setText(coordinates[0].trim());
            longitude.setText(coordinates[1].trim());
            elevation.setText(coordinates[2].trim());
        }
    }

    public boolean validate() {

        try {

            ValidationHelper.assertNotEmpty(latitude, longitude);

            ValidationHelper.assertMaxValue(latitude, 90);
            ValidationHelper.assertMaxValue(longitude, 180);

            ValidationHelper.assertMinValue(latitude, -90);
            ValidationHelper.assertMinValue(longitude, -180);

        } catch(Exception ex) {
            return false;
        }

        return true;
    }

    public String getLocation() {

        double latitude = Double.parseDouble(this.latitude.getText().toString());
        double longitude = Double.parseDouble(this.longitude.getText().toString());
        double elevation = Double.parseDouble(this.elevation.getText().toString());

        return LocationUtil.formatLocation(latitude, longitude, elevation);
    }

    @SuppressLint("DefaultLocale")
    public void initFromLocation(Location location) {
        latitude.setText(String.format("%.5f", location.getLatitude()));
        longitude.setText(String.format("%.5f", location.getLongitude()));
        elevation.setText(String.format("%.2f", location.getAltitude()));
    }
}
