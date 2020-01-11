package com.wecode.animaltracker.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.LocalisationUtils;
import com.wecode.animaltracker.activity.util.ValidationException;
import com.wecode.animaltracker.activity.util.ValidationHelper;
import com.wecode.animaltracker.activity.util.Validator;
import com.wecode.animaltracker.model.CodeList;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.service.TransectDataService;
import com.wecode.animaltracker.util.LocationUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sziak on 10/31/2015.
 */
public class TransectDetailView  implements ChangeableView {


    private TextView id;
    private TextView column;
    private TextView startDateTime;
    private TextView endDateTime;
    private TextView startLocation;
    private TextView endLocation;
    private TextView routeName;

    private Long weatherId;

    private Button endTransectButton;
    private Button startTransectButton;
    private Button transectDetailAddFindingButton;

    private View startDateContainer;
    private View endDateContainer;
    private View startLocationContainer;
    private View endLocationContainer;

    private CodeListSpinnerView localisationSpinner;

    private TransectDataService service = TransectDataService.getInstance();

    private int initialHash;

    public TransectDetailView(View context, Activity activity, Transect transect) {
        id = (TextView) context.findViewById(R.id.transectIdValue);
        column = (TextView) context.findViewById(R.id.transectColumnValue);
        startDateTime = (TextView) context.findViewById(R.id.transectStartDateTimeValue);
        endDateTime = (TextView) context.findViewById(R.id.transectEndDateTimeValue);
        startLocation = (TextView) context.findViewById(R.id.transectStartLocationValue);
        endLocation = (TextView) context.findViewById(R.id.transectEndLocationValue);
        routeName = (TextView) context.findViewById(R.id.transectRouteNameValue);
        localisationSpinner = new CodeListSpinnerView(R.id.transectLocalisationSpinner, CodeList.Name.transectRegion.name(), activity, context, false);

        endTransectButton = (Button) context.findViewById(R.id.endTransectButton);
        startTransectButton = (Button) context.findViewById(R.id.startTransectButton);
        transectDetailAddFindingButton = (Button) context.findViewById(R.id.transectDetailAddFindingButton);

        startDateContainer = context.findViewById(R.id.startDateContainer);
        endDateContainer = context.findViewById(R.id.endDateContainer);
        startLocationContainer = context.findViewById(R.id.startLocationContainer);
        endLocationContainer = context.findViewById(R.id.endLocationContainer);

        if (transect != null) {
            bind(transect);
        }
    }

    @SuppressLint("SetTextI18n")
    private void bind(Transect transect) {

        id.setText(transect.getId().toString());
        column.setText(transect.getSquare() != null ? transect.getSquare().toString() : "");

        DateFormat dateTimeInstance = DateFormat.getDateTimeInstance();

        if (transect.getStartDateTime() != null) {
            startDateTime.setText(dateTimeInstance.format(transect.getStartDateTime()));
        }

        if (transect.getStartLongitude() != null) {
            startLocation.setText(
                    LocationUtil.formatLocation(transect.getStartLatitude(), transect.getStartLongitude(), transect.getStartElevation() )
            );
        }

        routeName.setText(transect.getRouteName());
        localisationSpinner.select(transect.getLocalisation());

        if (transect.getEndDateTime() != null) {
            endDateTime.setText(dateTimeInstance.format(transect.getEndDateTime()));
        }

        if (transect.getEndLongitude() != null) {
            endLocation.setText(
                    LocationUtil.formatLocation(transect.getEndLatitude(), transect.getEndLongitude(), transect.getEndElevation())
            );
        }

        weatherId = transect.getWatherId();
        initialHash = hashCode();
    }

    public void initGuiForView() {
        enableAllButtons(false);
        startDateTime.setVisibility(View.VISIBLE);
        endDateTime.setVisibility(View.VISIBLE);
        startLocation.setVisibility(View.VISIBLE);
        endLocation.setVisibility(View.VISIBLE);
    }

    public void initGuiForEdit() {
        enableAllButtons(false);

        startDateContainer.setVisibility(View.GONE);
        startLocationContainer.setVisibility(View.GONE);
        endDateContainer.setVisibility(View.GONE);
        endLocationContainer.setVisibility(View.GONE);

        if (!startDateTime.getText().toString().isEmpty()) {
            startDateContainer.setVisibility(View.VISIBLE);
            startLocationContainer.setVisibility(View.VISIBLE);
        } else {
            startTransectButton.setVisibility(View.VISIBLE);
        }

        if (!endDateTime.getText().toString().isEmpty()) {
            endDateContainer.setVisibility(View.VISIBLE);
            endLocationContainer.setVisibility(View.VISIBLE);
        }

        if (!startDateTime.getText().toString().isEmpty() && endDateTime.getText().toString().isEmpty()){
            endTransectButton.setVisibility(View.VISIBLE);
            transectDetailAddFindingButton.setVisibility(View.VISIBLE);
        }

    }

    public void initGuiForNew() {
        enableAllButtons(false);
        startTransectButton.setVisibility(View.VISIBLE);

        startDateContainer.setVisibility(View.GONE);
        startLocationContainer.setVisibility(View.GONE);

        endDateContainer.setVisibility(View.GONE);
        endLocationContainer.setVisibility(View.GONE);
    }

    private void enableAllButtons(boolean enable) {
        endTransectButton.setVisibility(enable ? View.VISIBLE : View.GONE);
        startTransectButton.setVisibility(enable ? View.VISIBLE : View.GONE);
        transectDetailAddFindingButton.setVisibility(enable ? View.VISIBLE : View.GONE);
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

            transect.setSquare(column.getText().length() > 0 ? Integer.parseInt(column.getText().toString()) : null);
            transect.setStartDateTime(startDateTime.getText().length() > 0 ? dateTimeInstance.parse(startDateTime.getText().toString()) : null);
            transect.setRouteName(routeName.getText().toString());
            transect.setLocalisation(localisationSpinner.getSelectedCodeListValue());

            if (this.startLocation.getText().length() > 0) {
                double[] location = LocationUtil.parseLocation(this.startLocation.getText().toString());
                transect.setStartLatitude(location[0]);
                transect.setStartLongitude(location[1]);
                transect.setStartElevation(location[2]);
            }

            if (endLocation.getText().length() != 0) {
                double[] location = LocationUtil.parseLocation(this.endLocation.getText().toString());
                transect.setEndLatitude(location[0]);
                transect.setEndLongitude(location[1]);
                transect.setEndElevation(location[2]);
            }

            if (endDateTime.getText().length() != 0) {
                transect.setEndDateTime(dateTimeInstance.parse(endDateTime.getText().toString()));
            }

            transect.setWeatherId(weatherId);
            initialHash = hashCode();
            return transect;

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    private String getRootDirectory(Transect transect) {
        return String.format(Locale.getDefault(), "%d_%s", transect.getId(), transect.getRouteName());
    }

    private double getLatitude(String location) {
        return LocalisationUtils.parseDouble(location.split(", ")[1]);
    }

    private double getLongitude(String location) {
        return LocalisationUtils.parseDouble(location.split(", ")[0]);
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

    public boolean isValid(final Context context) {
        return ValidationHelper.validate(new Validator() {
            @Override
            public void validate() throws ValidationException {
                ValidationHelper.assertNotEmpty(context, routeName, column);
            }
        });

    }

    @Override
    public String toString() {
        return "TransectDetailView{" +
                "id=" + id.getText() +
                ", column=" + column.getText() +
                ", startDateTime=" + startDateTime.getText() +
                ", endDateTime=" + endDateTime.getText() +
                ", startLocation=" + startLocation.getText() +
                ", endLocation=" + endLocation.getText() +
                ", routeName=" + routeName.getText() +
                '}';
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.getText().toString().hashCode() : 0;
        result = 31 * result + (column != null ? column.getText().toString().hashCode() : 0);
        result = 31 * result + (startDateTime != null ? startDateTime.getText().toString().hashCode() : 0);
        result = 31 * result + (endDateTime != null ? endDateTime.getText().toString().hashCode() : 0);
        result = 31 * result + (startLocation != null ? startLocation.getText().toString().hashCode() : 0);
        result = 31 * result + (endLocation != null ? endLocation.getText().toString().hashCode() : 0);
        result = 31 * result + (routeName != null ? routeName.getText().toString().hashCode() : 0);
        result = 31 * result + (localisationSpinner.getSelectedCodeListValue() != null ? localisationSpinner.getSelectedCodeListValue().hashCode() : 0);
        System.out.println("localisationSpinner.getSelectedCodeListValue().hashCode(): " + localisationSpinner.getSelectedCodeListValue().hashCode());
        System.out.println("localisationSpinner.getSelectedCodeListValue(): " + localisationSpinner.getSelectedCodeListValue());
        return result;
    }

    public boolean isChanged() {
        return initialHash != hashCode();
    }
}
