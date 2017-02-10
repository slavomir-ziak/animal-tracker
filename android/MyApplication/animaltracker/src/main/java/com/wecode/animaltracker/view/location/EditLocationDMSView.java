package com.wecode.animaltracker.view.location;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.widget.Spinner;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.LocalisationUtils;
import com.wecode.animaltracker.activity.util.SpinnersHelper;
import com.wecode.animaltracker.activity.util.ValidationException;
import com.wecode.animaltracker.activity.util.ValidationHelper;
import com.wecode.animaltracker.activity.util.Validator;
import com.wecode.animaltracker.util.LocationUtil;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by SZIAK on 8/28/2016.
 */
public class EditLocationDMSView {

    private TextView degreesLatitude;
    private TextView minutesLatitude;
    private TextView secondsLatitude;
    private Spinner directionLatitude;

    private TextView degreesLongitude;
    private TextView minutesLongitude;
    private TextView secondsLongitude;
    private Spinner directionLongitude;

    private TextView elevation;

    private int initialHash;

    public EditLocationDMSView(String location, Activity context) {

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

        elevation = (TextView) context.findViewById(R.id.editLocationElevationDecimal);

        if (location == null || location.isEmpty()) {
            return;
        }

        String[] coordinates = location.split(" ");

        init(coordinates[0].trim(), degreesLatitude, minutesLatitude, secondsLatitude, directionLatitude);
        init(coordinates[1].trim(), degreesLongitude, minutesLongitude, secondsLongitude, directionLongitude);

        elevation.setText(coordinates[2].trim());

        initialHash =  hashCode();
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
        initialHash =  hashCode();
    }


    public boolean validate(final Context context) {

        return ValidationHelper.validate(new Validator() {

            @Override
            public void validate() throws ValidationException {

                ValidationHelper.assertNotEmpty(context,
                        degreesLatitude, minutesLatitude, secondsLatitude,
                        degreesLongitude, minutesLongitude, secondsLongitude
                );

                ValidationHelper.assertMaxValue(context, degreesLatitude, 90);
                ValidationHelper.assertMaxValue(context, minutesLatitude, 59);
                ValidationHelper.assertMaxValue(context, secondsLatitude, 99.99);

                ValidationHelper.assertMaxValue(context, degreesLongitude, 180);
                ValidationHelper.assertMaxValue(context, minutesLongitude, 59);
                ValidationHelper.assertMaxValue(context, secondsLongitude, 99.99);
            }
        });
    }

    public String getLocation() {

        double latitudeSeconds = LocalisationUtils.parseDouble(secondsLatitude.getText());
        double longitudeSeconds = LocalisationUtils.parseDouble(secondsLongitude.getText());
        String elevationValue = this.elevation.getText().toString();
        double elevation = LocalisationUtils.parseDouble(elevationValue.isEmpty() ? "0" : elevationValue);

        initialHash =  hashCode();
        return String.format(Locale.getDefault(), "%s°%s'%.2f\"%s, %s°%s'%.2f\"%s, %.2f",
                degreesLatitude.getText(), minutesLatitude.getText(), latitudeSeconds, directionLatitude.getSelectedItem(),
                degreesLongitude.getText(), minutesLongitude.getText(), longitudeSeconds, directionLongitude.getSelectedItem(),
                elevation
                );
    }

    public void initFromLocation(Location location) {
        String locationFormatted = LocationUtil.formatLocation(location.getLatitude(), location.getLongitude(), location.getAltitude());

        String[] coordinates = locationFormatted.split(" ");

        init(coordinates[0].trim(), degreesLatitude, minutesLatitude, secondsLatitude, directionLatitude);
        init(coordinates[1].trim(), degreesLongitude, minutesLongitude, secondsLongitude, directionLongitude);

        elevation.setText(coordinates[2].trim());
        initialHash =  hashCode();
    }

    @Override
    public int hashCode() {
        int result = degreesLatitude != null ? degreesLatitude.getText().toString().hashCode() : 0;
        result = 31 * result + (minutesLatitude != null ? minutesLatitude.getText().toString().hashCode() : 0);
        result = 31 * result + (secondsLatitude != null ? secondsLatitude.getText().toString().hashCode() : 0);
        result = 31 * result + (directionLatitude != null ? directionLatitude.getSelectedItem().toString().hashCode() : 0);
        result = 31 * result + (degreesLongitude != null ? degreesLongitude.getText().toString().hashCode() : 0);
        result = 31 * result + (minutesLongitude != null ? minutesLongitude.getText().toString().hashCode() : 0);
        result = 31 * result + (secondsLongitude != null ? secondsLongitude.getText().toString().hashCode() : 0);
        result = 31 * result + (directionLongitude != null ? directionLongitude.getSelectedItem().toString().hashCode() : 0);
        result = 31 * result + (elevation != null ? elevation.getText().toString().hashCode() : 0);
        return result;
    }

    public boolean isChanged() {
        return initialHash != hashCode();
    }
}
