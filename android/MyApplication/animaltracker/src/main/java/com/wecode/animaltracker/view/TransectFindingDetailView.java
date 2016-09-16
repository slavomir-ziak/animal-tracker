package com.wecode.animaltracker.view;

import android.app.Activity;
import android.location.Location;
import android.view.View;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.SpinnersHelper;
import com.wecode.animaltracker.model.findings.TransectFinding;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.ConverterUtil;
import com.wecode.animaltracker.util.LocationUtil;

/**
 * Created by sziak on 10-Apr-16.
 */
public class TransectFindingDetailView {

    private Long id;
    private Long habitatId;
    private Long transectId;

    private CodeListSpinnerView species;
    private TextView location;
    private RadioButton findingBeforeRecentSnow;
    private RadioButton findingAfterRecentSnow;

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

    }

    private void bind(TransectFinding transectFinding) {

        species.select(transectFinding.getSpecies());

        findingBeforeRecentSnow.setChecked("BEFORE".equals(transectFinding.getBeforeAfterRecentSnow()));
        findingAfterRecentSnow.setChecked("AFTER".equals(transectFinding.getBeforeAfterRecentSnow()));

        if (transectFinding.hasLocation()) {
            location.setText(LocationUtil.formatLocation(transectFinding.getLocationLatitude(), transectFinding.getLocationLongitude()));
        }

        habitatId = transectFinding.getHabitatId();
        transectId = transectFinding.getTransectId();
        id = transectFinding.getId();
    }

    public void setLocation(Location location) {
        this.location.setText(LocationUtil.formatLocation(location));
    }

    public TransectFinding toTransectFinding() {

        TransectFinding transectFinding = new TransectFinding();
        transectFinding.setSpecies(species.getSelectedCodeListValue());

        if (location.getText().toString().length() > 0) {
            double[] parsed = LocationUtil.parseLocation(location.getText().toString());
            transectFinding.setLocationLatitude(parsed[0]);
            transectFinding.setLocationLongitude(parsed[1]);
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

    }

    public void initGuiForView() {

    }

    public void initGuiForNew() {

    }

    public TextView getLocation() {
        return location;
    }

    public TransectFinding getTransectFinding() {
        return transectFinding;
    }
}
