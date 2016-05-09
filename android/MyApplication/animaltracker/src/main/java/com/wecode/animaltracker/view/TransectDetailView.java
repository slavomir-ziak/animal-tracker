package com.wecode.animaltracker.view;

import android.app.Activity;
import android.location.Location;
import android.text.InputType;
import android.widget.TextView;
import com.wecode.animaltracker.R;
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

    public TransectDetailView(Activity context, Transect transect) {
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
        column.setText(transect.getColumn().toString());

        DateFormat dateTimeInstance = DateFormat.getDateTimeInstance();

        startDateTime.setText(dateTimeInstance.format(transect.getStartDateTime()));

        if (transect.getStartLocation() != null) {
            startLocation.setText(transect.getStartLocation().getLongitude() + " " + transect.getStartLocation().getLatitude());
        }

        routeName.setText(transect.getRouteName());

        if (transect.getEndDateTime() != null) {
            endDateTime.setText(dateTimeInstance.format(transect.getEndDateTime()));
        }

        if (transect.getEndLocation() != null) {
            endLocation.setText(transect.getEndLocation().getLongitude()
                    + " " + transect.getEndLocation().getLatitude());
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

            Location startLocation = new Location("");

            if (this.startLocation.getText().length() > 0) {
                startLocation.setLongitude(Double.parseDouble(this.startLocation.getText().toString().split(" ")[0]));
                startLocation.setLatitude(Double.parseDouble(this.startLocation.getText().toString().split(" ")[1]));
            }

            Transect transect = new Transect(
                    Long.parseLong(id.getText().toString()),
                    Integer.parseInt(column.getText().toString()),
                    dateTimeInstance.parse(startDateTime.getText().toString()),
                    startLocation,
                    routeName.getText().toString()
            );

            if (endLocation.getText().length() != 0) {

                Location endLocation = new Location("");
                endLocation.setLongitude(Double.parseDouble(this.endLocation.getText().toString().split(" ")[0]));
                endLocation.setLatitude(Double.parseDouble(this.endLocation.getText().toString().split(" ")[1]));
                transect.setEndLocation(endLocation);
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
}
