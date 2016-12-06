package com.wecode.animaltracker.view;

import android.app.Activity;
import android.location.Location;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.model.TransectFindingSite;
import com.wecode.animaltracker.service.TransectFindingSiteDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.LocationUtil;

/**
 * Created by sziak on 10-Apr-16.
 */
public class TransectFindingSiteDetailView {

    private TransectFindingSiteDataService service = TransectFindingSiteDataService.getInstance();

    private Long id;
    private Long habitatId;
    private Long transectId;
    private CodeListSpinnerView species;

    private TextView location;
    //private RadioButton findingBeforeRecentSnow;
    //private RadioButton findingAfterRecentSnow;

    private final Button addFececButton;
    private final Button addFootprintsButton;
    private final Button addUrineButton;
    private final Button addHairsButton;
    private final Button addScratchesButton;
    private final Button addOtherButton;

    private TransectFindingSite transectFindingSite;

    public TransectFindingSiteDetailView(Activity context, TransectFindingSite transectFindingSite) {
        this(context, transectFindingSite.getId());
        this.transectFindingSite = transectFindingSite;

        Assert.assertNotNull("transectFindingSite cannot be null!", transectFindingSite);
        bind(transectFindingSite);
    }

    public TransectFindingSiteDetailView(Activity context, Long transectId) {

        this.transectId = transectId;

        species = new CodeListSpinnerView(R.id.findingSpeciesValue, "findingSpeciesTypes", context, false, "Wolf");
        location = (TextView) context.findViewById(R.id.findingLocationValue);
        //findingBeforeRecentSnow = (RadioButton) context.findViewById(R.id.findingBeforeRecentSnow);
        //findingAfterRecentSnow = (RadioButton) context.findViewById(R.id.findingAfterRecentSnow);
        addFececButton = (Button) context.findViewById(R.id.transectFindingAddFecesButton);
        addFootprintsButton = (Button) context.findViewById(R.id.transectFindingAddFootprintsButton);
        addOtherButton = (Button) context.findViewById(R.id.transectFindingAddOtherButton);
        addUrineButton = (Button) context.findViewById(R.id.transectFindingAddUrineButton);
        addHairsButton = (Button) context.findViewById(R.id.transectFindingAddHairsButton);
        addScratchesButton = (Button) context.findViewById(R.id.transectFindingAddScratchesButton);
    }

    private void bind(TransectFindingSite transectFindingSite) {

        species.select(transectFindingSite.getSpecies());

        //findingBeforeRecentSnow.setChecked("BEFORE".equals(transectFindingSite.getBeforeAfterRecentSnow()));
        //findingAfterRecentSnow.setChecked("AFTER".equals(transectFindingSite.getBeforeAfterRecentSnow()));

        if (transectFindingSite.hasLocation()) {
            setLocation(transectFindingSite.getLocationLatitude(), transectFindingSite.getLocationLongitude(), transectFindingSite.getLocationElevation());
        }

        habitatId = transectFindingSite.getHabitatId();
        transectId = transectFindingSite.getTransectId();
        id = transectFindingSite.getId();
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

    public TransectFindingSite toTransectFinding() {

        TransectFindingSite transectFindingFeces;

        if (id != null) {
            transectFindingSite = service.find(id);
        } else {
            transectFindingSite = new TransectFindingSite();
        }
        transectFindingSite.setSpecies(species.getSelectedCodeListValue());

        if (location.getText().toString().length() > 0) {
            double[] parsed = getLocationParsed();

            transectFindingSite.setLocationLatitude(parsed[0]);
            transectFindingSite.setLocationLongitude(parsed[1]);
            transectFindingSite.setLocationElevation(parsed[2]);
        }

        /*if (findingBeforeRecentSnow.isChecked()) {
            transectFindingSite.setBeforeAfterRecentSnow("BEFORE");
        }

        if (findingAfterRecentSnow.isChecked()) {
            transectFindingSite.setBeforeAfterRecentSnow("AFTER");
        }*/

        transectFindingSite.setHabitatId(habitatId);
        transectFindingSite.setTransectId(transectId);
        transectFindingSite.setId(id);

        return transectFindingSite;
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
        addUrineButton.setEnabled(enable);
        addHairsButton.setEnabled(enable);
        addScratchesButton.setEnabled(enable);
    }

    public TextView getLocation() {
        return location;
    }

    public TransectFindingSite getTransectFindingSite() {
        return transectFindingSite;
    }
}
