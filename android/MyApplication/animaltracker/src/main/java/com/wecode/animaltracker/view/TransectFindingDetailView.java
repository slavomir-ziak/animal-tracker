package com.wecode.animaltracker.view;

import android.app.Activity;
import android.location.Location;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.model.findings.TransectFinding;
import com.wecode.animaltracker.service.SettingsDataService;
import com.wecode.animaltracker.service.TransectFindingDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.LocationUtil;

/**
 * Created by sziak on 10-Apr-16.
 */
public class TransectFindingDetailView {

    private TransectFindingDataService service = TransectFindingDataService.getInstance();

    private Long id;
    private Long habitatId;
    private Long transectId;
    private CodeListSpinnerView species;

    private TextView location;
    private RadioButton findingBeforeRecentSnow;
    private RadioButton findingAfterRecentSnow;

    private final Button addFececButton;
    private final Button addFootprintsButton;
    private final Button addOtherButton;

    private TransectFinding transectFinding;

    public TransectFindingDetailView(Activity context, TransectFinding transectFinding) {
        this(context, transectFinding.getId());
        this.transectFinding = transectFinding;

        Assert.assertNotNull("transectFinding cannot be null!", transectFinding);
        bind(transectFinding);
    }

    public TransectFindingDetailView(Activity context, Long transectId) {

        this.transectId = transectId;

        species = new CodeListSpinnerView(R.id.findingSpeciesValue, "findingSpeciesTypes", context);
        location = (TextView) context.findViewById(R.id.findingLocationValue);
        findingBeforeRecentSnow = (RadioButton) context.findViewById(R.id.findingBeforeRecentSnow);
        findingAfterRecentSnow = (RadioButton) context.findViewById(R.id.findingAfterRecentSnow);
        addFececButton = (Button) context.findViewById(R.id.transectFindingAddFecesButton);
        addFootprintsButton = (Button) context.findViewById(R.id.transectFindingAddFootprintsButton);
        addOtherButton = (Button) context.findViewById(R.id.transectFindingAddOtherButton);
    }

    private void bind(TransectFinding transectFinding) {

        species.select(transectFinding.getSpecies());

        findingBeforeRecentSnow.setChecked("BEFORE".equals(transectFinding.getBeforeAfterRecentSnow()));
        findingAfterRecentSnow.setChecked("AFTER".equals(transectFinding.getBeforeAfterRecentSnow()));

        if (transectFinding.hasLocation()) {
            setLocation(transectFinding.getLocationLatitude(), transectFinding.getLocationLongitude(), transectFinding.getLocationElevation());
        }

        habitatId = transectFinding.getHabitatId();
        transectId = transectFinding.getTransectId();
        id = transectFinding.getId();
    }

    private void setLocation(Double latitude, Double longiture, Double altitude) {
        location.setText(LocationUtil.formatLocation(latitude, longiture, altitude));
    }

    private double[] getLocationParsed() {
        return LocationUtil.parseLocation(location.getText().toString());
    }

    public void setLocation(Location location) {
        setLocation(location.getLatitude(), location.getLongitude(), location.getAltitude());
    }

    public TransectFinding toTransectFinding() {

        TransectFinding transectFindingFeces;

        if (id != null) {
            transectFinding = service.find(id);
        } else {
            transectFinding = new TransectFinding();
        }
        transectFinding.setSpecies(species.getSelectedCodeListValue());

        if (location.getText().toString().length() > 0) {
            double[] parsed = getLocationParsed();

            transectFinding.setLocationLatitude(parsed[0]);
            transectFinding.setLocationLongitude(parsed[1]);
            transectFinding.setLocationElevation(parsed[2]);
        }

        if (findingBeforeRecentSnow.isChecked()) {
            transectFinding.setBeforeAfterRecentSnow("BEFORE");
        }

        if (findingAfterRecentSnow.isChecked()) {
            transectFinding.setBeforeAfterRecentSnow("AFTER");
        }

        transectFinding.setHabitatId(habitatId);
        transectFinding.setTransectId(transectId);
        transectFinding.setId(id);

        return transectFinding;
    }

    public Long getHabitatId() {
        return habitatId;
    }

    public void setHabitatId(Long habitatId) {
        this.habitatId = habitatId;
    }

    public Long getTransectId() {
        return transectId;
    }

    public void setTransectId(Long transectId) {
        this.transectId = transectId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void initGuiForEdit() {
        enableButtons(true);
    }

    public void initGuiForView() {
        enableButtons(false);
    }

    public void initGuiForNew() {
        enableButtons(false);
    }

    private void enableButtons(boolean enable) {
        addFececButton.setEnabled(enable);
        addFootprintsButton.setEnabled(enable);
        addOtherButton.setEnabled(enable);
    }

    public TextView getLocation() {
        return location;
    }

    public TransectFinding getTransectFinding() {
        return transectFinding;
    }
}
