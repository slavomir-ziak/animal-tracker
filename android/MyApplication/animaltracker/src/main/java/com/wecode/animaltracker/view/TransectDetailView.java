package com.wecode.animaltracker.view;

import android.app.Activity;
import android.text.InputType;
import android.widget.Button;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.ValidationHelper;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.model.findings.TransectFindingFeces;
import com.wecode.animaltracker.service.TransectDataService;
import com.wecode.animaltracker.service.TransectFindingFecesDataService;
import com.wecode.animaltracker.util.LocationUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

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

    private Button endTransectButton;
    private Button startTransectButton;
    private Button transectDetailAddFindingButton;
    private Button transectDetailSetHabitatButton;
    private Button transectDetailSetWeatherButton;
    private Button transectDetailViewFindingsButton;
    private Button transectDetailSaveButton;

    private TransectDataService service = TransectDataService.getInstance();

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
        endTransectButton = (Button) context.findViewById(R.id.endTransectButton);
        startTransectButton = (Button) context.findViewById(R.id.startTransectButton);
        transectDetailAddFindingButton = (Button) context.findViewById(R.id.transectDetailAddFindingButton);
        transectDetailSetHabitatButton = (Button) context.findViewById(R.id.transectDetailSetHabitatButton);
        transectDetailSetWeatherButton = (Button) context.findViewById(R.id.transectDetailSetWeatherButton);
        transectDetailViewFindingsButton = (Button) context.findViewById(R.id.transectDetailViewFindingsButton);
        transectDetailSaveButton = (Button) context.findViewById(R.id.transectDetailSaveButton);
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
                    LocationUtil.formatLocation(transect.getStartLatitude(), transect.getStartLongitude())
            );
        }

        routeName.setText(transect.getRouteName());

        if (transect.getEndDateTime() != null) {
            endDateTime.setText(dateTimeInstance.format(transect.getEndDateTime()));
        }

        if (transect.getEndLongitude() != null) {
            endLocation.setText(
                    LocationUtil.formatLocation(transect.getEndLatitude(), transect.getEndLongitude())
            );
        }

        habitatId = transect.getHabitatId();
        weatherId = transect.getWatherId();
    }

    public void initGuiForView() {
         id.setInputType(InputType.TYPE_NULL);
         column.setInputType(InputType.TYPE_NULL);
         startDateTime.setInputType(InputType.TYPE_NULL);
         endDateTime.setInputType(InputType.TYPE_NULL);
         startLocation.setInputType(InputType.TYPE_NULL);
         endLocation.setInputType(InputType.TYPE_NULL);
         routeName.setInputType(InputType.TYPE_NULL);

        enableAllButtons(true);
    }

    public void initGuiForEdit() {
        enableAllButtons(true);
        id.setInputType(InputType.TYPE_NULL);
    }

    public void initGuiForNew() {
        enableAllButtons(false);
        transectDetailSaveButton.setEnabled(true);
    }

    private void enableAllButtons(boolean enable) {
        endTransectButton.setEnabled(enable);
        startTransectButton.setEnabled(enable);
        transectDetailAddFindingButton.setEnabled(enable);
        transectDetailSetHabitatButton.setEnabled(enable);
        transectDetailSetWeatherButton.setEnabled(enable);
        transectDetailViewFindingsButton.setEnabled(enable);
        transectDetailSaveButton.setEnabled(enable);
    }


    public Transect toTransect() {

        DateFormat dateTimeInstance = DateFormat.getDateTimeInstance();

        try {
            Long transectId = id.getText().length() > 0 ? Long.parseLong(id.getText().toString()) : null;
            Transect transect;

            if (transectId != null) {
                transect = service.find(transectId);
            } else {
                transect = new Transect();
            }

            transect.setColumn(column.getText().length() > 0 ? Integer.parseInt(column.getText().toString()) : null);
            transect.setStartDateTime(startDateTime.getText().length() > 0 ? dateTimeInstance.parse(startDateTime.getText().toString()) : null);
            transect.setRouteName(routeName.getText().toString());

            if (this.startLocation.getText().length() > 0) {
                double[] location = LocationUtil.parseLocation(this.startLocation.getText().toString());
                transect.setStartLatitude(location[0]);
                transect.setStartLongitude(location[1]);
            }

            if (endLocation.getText().length() != 0) {
                double[] location = LocationUtil.parseLocation(this.endLocation.getText().toString());
                transect.setEndLatitude(location[0]);
                transect.setEndLongitude(location[1]);
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

    public Date getStartDateTimeParsed() {
        DateFormat dateTimeInstance = DateFormat.getDateTimeInstance();
        try {
            return startDateTime.getText().length() > 0 ? dateTimeInstance.parse(startDateTime.getText().toString()) : null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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
        return ValidationHelper.isNotEmpty(routeName);
    }

}
