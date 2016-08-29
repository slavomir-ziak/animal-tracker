package com.wecode.animaltracker.view;

import android.app.Activity;
import android.location.Location;
import android.widget.Spinner;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.SpinnersHelper;
import com.wecode.animaltracker.activity.util.ValidationHelper;
import com.wecode.animaltracker.util.LocationUtil;

import java.util.Locale;

/**
 * Created by SZIAK on 8/28/2016.
 */
public class EditLocationView {

    private TextView degreesLatitude;
    private TextView minutesLatitude;
    private TextView secondsLatitude;
    private Spinner directionLatitude;

    private TextView degreesLongitude;
    private TextView minutesLongitude;
    private TextView secondsLongitude;
    private Spinner directionLongitude;

    public EditLocationView(String location, Activity context) {

        degreesLatitude = (TextView) context.findViewById(R.id.editLocationLatitudeDegreesValue);
        minutesLatitude = (TextView) context.findViewById(R.id.editLocationLatitudeMinutesValue);
        secondsLatitude = (TextView) context.findViewById(R.id.editLocationLatitudeSecondsValue);
        directionLatitude = (Spinner) context.findViewById(R.id.editLocationLatitudeDirectionSpinner);

        SpinnersHelper.setSpinnerData(directionLatitude, R.array.latitudeDirection);

        degreesLongitude = (TextView) context.findViewById(R.id.editLocationLongitudeDegreesValue);
        minutesLongitude = (TextView) context.findViewById(R.id.editLocationLongitudeMinutesValue);
        secondsLongitude = (TextView) context.findViewById(R.id.editLocationLongitudeSecondsValue);
        directionLongitude = (Spinner) context.findViewById(R.id.editLocationLongitudeDirectionSpinner);

        SpinnersHelper.setSpinnerData(directionLongitude, R.array.longitudeDirection);

        if (location == null || location.isEmpty()) {
            return;
        }

        String[] coordinates = location.split(",");

        init(coordinates[0].trim(), degreesLatitude, minutesLatitude, secondsLatitude, directionLatitude);
        init(coordinates[1].trim(), degreesLongitude, minutesLongitude, secondsLongitude, directionLongitude);

    }

    private void init(String coordinate, TextView degrees, TextView minutes, TextView seconds, Spinner direction) {

        String[] parts = coordinate.split("°|'|\"");

        if (parts.length != 4) {
            throw new RuntimeException("invalid location format: " + coordinate);
        }

        degrees.setText(parts[0]);
        minutes.setText(parts[1]);
        seconds.setText(parts[2]);

        SpinnersHelper.setSelected(direction, parts[3]);
    }


    public boolean validate() {

        try {

            ValidationHelper.assertNotEmpty(
                    degreesLatitude, minutesLatitude, secondsLatitude,
                    degreesLongitude, minutesLongitude, secondsLongitude
            );

            ValidationHelper.assertMaxValue(degreesLatitude, 90);
            ValidationHelper.assertMaxValue(minutesLatitude, 59);
            ValidationHelper.assertMaxValue(secondsLatitude, 99.99);

            ValidationHelper.assertMaxValue(degreesLongitude, 180);
            ValidationHelper.assertMaxValue(minutesLongitude, 59);
            ValidationHelper.assertMaxValue(secondsLongitude, 99.99);

        } catch(Exception ex) {
            return false;
        }

        return true;
    }

    public String getLocation() {

        double latitudeSeconds = Double.parseDouble(secondsLatitude.getText().toString());
        double longitudeSeconds = Double.parseDouble(secondsLongitude.getText().toString());

        return String.format(Locale.ENGLISH, "%s°%s'%.2f\"%s, %s°%s'%.2f\"%s",
                degreesLatitude.getText(), minutesLatitude.getText(), latitudeSeconds, directionLatitude.getSelectedItem(),
                degreesLongitude.getText(), minutesLongitude.getText(), longitudeSeconds, directionLongitude.getSelectedItem()
                );
    }

    public void initFromLocation(Location location) {
        String locationFormatted = LocationUtil.formatLocation(location.getLatitude(), location.getLongitude());

        String[] coordinates = locationFormatted.split(",");

        init(coordinates[0].trim(), degreesLatitude, minutesLatitude, secondsLatitude, directionLatitude);
        init(coordinates[1].trim(), degreesLongitude, minutesLongitude, secondsLongitude, directionLongitude);
    }
}
