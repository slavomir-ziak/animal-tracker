package com.wecode.animaltracker.view;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.text.InputType;
import android.widget.TextView;
import android.widget.Toast;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.LocationFormatter;
import com.wecode.animaltracker.model.Transect;

import java.text.DateFormat;
import java.text.ParseException;

/**
 * Created by sziak on 10/31/2015.
 */
public class TransectDetailView {

    private TextView id;
    private TextView column;
    private TextView startDateTime;
    private TextView endDateTime;
    private TextView startLocation;
    private TextView endLocation;
    private TextView routeName;

    private Long habitatId;
    private Long weatherId;

    private Activity parentActivity;

    public TransectDetailView(Activity context, Transect transect) {
        this.parentActivity = context;
        id = (TextView) context.findViewById(R.id.transectIdValue);
        column = (TextView) context.findViewById(R.id.transectColumnValue);
        startDateTime = (TextView) context.findViewById(R.id.transectStartDateTimeValue);
        endDateTime = (TextView) context.findViewById(R.id.transectEndDateTimeValue);
        startLocation = (TextView) context.findViewById(R.id.transectStartLocationValue);
        endLocation = (TextView) context.findViewById(R.id.transectEndLocationValue);
        routeName = (TextView) context.findViewById(R.id.transectRouteNameValue);

        if (transect != null) {
            bind(transect);
        }
    }

    public void bind(Transect transect) {

        id.setText(transect.getId().toString());
        column.setText(transect.getColumn() != null ? transect.getColumn().toString() : "");

        DateFormat dateTimeInstance = DateFormat.getDateTimeInstance();

        if (transect.getStartDateTime() != null) {
            startDateTime.setText(dateTimeInstance.format(transect.getStartDateTime()));
        }

        if (transect.getStartLongitude() != null) {
            startLocation.setText(
                    LocationFormatter.formatLocation(transect.getStartLongitude(), transect.getStartLatitude())
            );
        }

        routeName.setText(transect.getRouteName());

        if (transect.getEndDateTime() != null) {
            endDateTime.setText(dateTimeInstance.format(transect.getEndDateTime()));
        }

        if (transect.getEndLongitude() != null) {
            endLocation.setText(
                    LocationFormatter.formatLocation(transect.getEndLongitude(), transect.getEndLatitude())
            );
        }

        habitatId = transect.getHabitatId();
        weatherId = transect.getWatherId();
    }

    public void disableAllForView() {
         id.setInputType(InputType.TYPE_NULL);
         column.setInputType(InputType.TYPE_NULL);
         startDateTime.setInputType(InputType.TYPE_NULL);
         endDateTime.setInputType(InputType.TYPE_NULL);
         startLocation.setInputType(InputType.TYPE_NULL);
         endLocation.setInputType(InputType.TYPE_NULL);
         routeName.setInputType(InputType.TYPE_NULL);
    }

    public void disableAllForEdit() {
        id.setInputType(InputType.TYPE_NULL);
        startLocation.setInputType(InputType.TYPE_NULL);
        endLocation.setInputType(InputType.TYPE_NULL);
    }

    public Transect toTransect() {

        DateFormat dateTimeInstance = DateFormat.getDateTimeInstance();

        try {



            Transect transect = new Transect(
                    id.getText().length() > 0 ? Long.parseLong(id.getText().toString()) : null,
                    column.getText().length() > 0 ? Integer.parseInt(column.getText().toString()) : null,
                    startDateTime.getText().length() > 0 ? dateTimeInstance.parse(startDateTime.getText().toString()) : null,
                    routeName.getText().toString()
            );

            if (this.startLocation.getText().length() > 0) {
                transect.setStartLongitude(getLongitude(this.startLocation.getText().toString()));
                transect.setStartLatitude(getLatitude(this.startLocation.getText().toString()));

            }

            if (endLocation.getText().length() != 0) {
                transect.setEndLongitude(getLongitude(this.endLocation.getText().toString()));
                transect.setEndLatitude(getLatitude(this.endLocation.getText().toString()));
            }

            if (endDateTime.getText().length() != 0) {
                transect.setEndDateTime(dateTimeInstance.parse(endDateTime.getText().toString()));
            }

            transect.setHabitatId(habitatId);
            transect.setWeatherId(weatherId);
            return transect;

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    private double getLatitude(String location) {
        return Double.parseDouble(location.split(", ")[1]);
    }

    private double getLongitude(String location) {
        return Double.parseDouble(location.split(", ")[0]);
    }

    public Long getHabitatId() {
        return habitatId;
    }

    public void setHabitatId(Long habitatId) {
        this.habitatId = habitatId;
    }

    public TextView getId() {
        return id;
    }

    public void setId(TextView id) {
        this.id = id;
    }

    public TextView getColumn() {
        return column;
    }

    public void setColumn(TextView column) {
        this.column = column;
    }

    public TextView getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(TextView startDateTime) {
        this.startDateTime = startDateTime;
    }

    public TextView getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(TextView endDateTime) {
        this.endDateTime = endDateTime;
    }

    public TextView getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(TextView startLocation) {
        this.startLocation = startLocation;
    }

    public TextView getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(TextView endLocation) {
        this.endLocation = endLocation;
    }

    public TextView getRouteName() {
        return routeName;
    }

    public void setRouteName(TextView routeName) {
        this.routeName = routeName;
    }

    public long getIdValue() {
        return Long.valueOf(id.getText().toString());
    }

    public Long getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(Long weatherId) {
        this.weatherId = weatherId;
    }

    public void setIdValue(Long idValue) {
        this.id.setText(idValue.toString());
    }

    public boolean isValid() {
        if (getRouteName().getText().length() == 0) {
            Toast.makeText(parentActivity, "Enter Route name before saving.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
