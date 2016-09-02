package com.wecode.animaltracker.view;

import android.location.Location;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.detail.TransectFindingDetailActivity;
import com.wecode.animaltracker.activity.util.SpinnersHelper;
import com.wecode.animaltracker.model.CodeList;
import com.wecode.animaltracker.model.TransectFinding;
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

    private CodeListSpinnerView type;
    private CodeListSpinnerView species;

    private Spinner confidence;
    private TextView count;
    private TextView location;
    private Spinner fecesState;

    private TextView fecesPrey;
    private Spinner footprintsFrontBack;

    private Spinner footprintsDirection;
    private TextView footprintsLength;
    private TextView footprintsWidht;
    private TextView footprintsAge;
    private TextView footprintsStride;

    private RadioButton findingBeforeRecentSnow;
    private RadioButton findingAfterRecentSnow;

    private TransectFinding transectFinding;/*

    private final Button transectFindingHabitatButton;
    private final Button transectFindingSamplesButton;
    private final Button transectFindingAddPhotoButton;
    private final Button transectFindingListPhotosButton;
    private final Button transectFindingAddSampleButton;
    private final Button transectFindingSaveButton;
*/
    public TransectFindingDetailView(TransectFindingDetailActivity context, TransectFinding transectFinding) {
        this(context, transectFinding.getId());
        this.transectFinding = transectFinding;

        Assert.assertNotNull("transectFinding cannot be null!", transectFinding);
        bind(transectFinding);
    }

    public TransectFindingDetailView(TransectFindingDetailActivity context, Long transectId) {

        type = new CodeListSpinnerView(R.id.findingTypeValue, "findingTypes", context);
        species = new CodeListSpinnerView(R.id.findingSpeciesValue, "findingSpeciesTypes", context);

        confidence = (Spinner) context.findViewById(R.id.findingConfidenceValue);
        count = (TextView) context.findViewById(R.id.findingCountValue);
        location = (TextView) context.findViewById(R.id.findingLocationValue);
        findingBeforeRecentSnow = (RadioButton) context.findViewById(R.id.findingBeforeRecentSnow);
        findingAfterRecentSnow = (RadioButton) context.findViewById(R.id.findingAfterRecentSnow);

        SpinnersHelper.setSpinnerData(confidence, R.array.findingConfidenceTypes);

        this.transectId = transectId;

       /* transectFindingHabitatButton = (Button) toolbar.findViewById(R.id.transect_finding_action_habitat);
        transectFindingSamplesButton = (Button) toolbar.findViewById(R.id.transect_finding_action_samples);
        transectFindingAddPhotoButton = (Button) toolbar.findViewById(R.id.transect_finding_action_add_photo);
        transectFindingListPhotosButton = (Button) toolbar.findViewById(R.id.transect_finding_action_photos);
        transectFindingAddSampleButton = (Button) toolbar.findViewById(R.id.transect_finding_action_add_sample);
        transectFindingSaveButton = (Button) toolbar.findViewById(R.id.action_save);*/

    }

    public void initFootprintsFragment(View context) {
        footprintsFrontBack = (Spinner) context.findViewById(R.id.footprintsFrontBackValue);
        footprintsDirection = (Spinner) context.findViewById(R.id.footprintsDirectionValue);
        footprintsLength = (TextView) context.findViewById(R.id.footprintsLengthValue);
        footprintsWidht = (TextView) context.findViewById(R.id.footprintsWidthValue);
        footprintsAge = (TextView) context.findViewById(R.id.footprintsAgeValue);
        footprintsStride = (TextView) context.findViewById(R.id.footprintsStrideValue);

        if (transectFinding == null) {
            return;
        }

        SpinnersHelper.setSelected(footprintsDirection, transectFinding.getFootprintsDirection());
        SpinnersHelper.setSelected(footprintsFrontBack, transectFinding.getFootprintsFrontBack());

        footprintsLength.setText(transectFinding.getFootprintsLength() == null ? "" : transectFinding.getFootprintsWidht().toString());
        footprintsWidht.setText(transectFinding.getFootprintsWidht() == null ? "" : transectFinding.getFootprintsWidht().toString());
        footprintsAge.setText(transectFinding.getFootprintsAge() == null ? "" : transectFinding.getFootprintsAge().toString());
        footprintsStride.setText(transectFinding.getFootprintsStride() == null ? "" : transectFinding.getFootprintsStride().toString());
    }

    public void initFecesFragment(View context) {
        fecesState = (Spinner) context.findViewById(R.id.findingFecesStateValue);
        fecesPrey = (TextView) context.findViewById(R.id.findingFecesPreyValue);

        if (transectFinding == null) {
            return;
        }

        SpinnersHelper.setSelected(fecesState, transectFinding.getFecesState());
        fecesPrey.setText(transectFinding.getFecesPrey() == null ? "" : transectFinding.getFecesPrey());
    }

    private void bind(TransectFinding transectFinding) {

        type.select(transectFinding.getType());
        species.select(transectFinding.getSpecies());
        SpinnersHelper.setSelected(confidence, transectFinding.getConfidence());

        count.setText(transectFinding.getCount() == null ? "" : transectFinding.getCount().toString());

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

        transectFinding.setType(type.getSelectedCodeListValue());
        transectFinding.setSpecies(species.getSelectedCodeListValue());
        transectFinding.setConfidence((String) confidence.getSelectedItem());
        transectFinding.setCount(ConverterUtil.toInteger(count.getText().toString()));

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

        if (type.getSelectedCodeListValue().equals("Footprints")) {

            transectFinding.setFootprintsAge(ConverterUtil.toInteger(footprintsAge.getText().toString()));
            transectFinding.setFootprintsDirection((String) footprintsDirection.getSelectedItem());
            transectFinding.setFootprintsFrontBack((String) footprintsFrontBack.getSelectedItem());
            transectFinding.setFootprintsLength(ConverterUtil.toInteger(footprintsLength.getText().toString()));
            transectFinding.setFootprintsWidht(ConverterUtil.toInteger(footprintsWidht.getText().toString()));
            transectFinding.setFootprintsStride(ConverterUtil.toInteger(footprintsStride.getText().toString()));
        }

        if (type.getSelectedCodeListValue().equals("Feces")) {
            transectFinding.setFecesPrey(ConverterUtil.toString(fecesPrey.getText()));
            transectFinding.setFecesState(((String) fecesState.getSelectedItem()));

        }

        transectFinding.setHabitatId(habitatId);
        transectFinding.setTransectId(transectId);
        transectFinding.setId(id);

        return transectFinding;
    }

    public void enableAllButtons(boolean enable) {
        /*transectFindingHabitatButton.setEnabled(enable);
        transectFindingSamplesButton.setEnabled(enable);
        transectFindingAddPhotoButton.setEnabled(enable);
        transectFindingListPhotosButton.setEnabled(enable);
        transectFindingAddSampleButton.setEnabled(enable);
        transectFindingSaveButton.setEnabled(enable);*/
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
        enableAllButtons(true);
    }

    public void initGuiForView() {
        enableAllButtons(false);
    }

    public void initGuiForNew() {
        enableAllButtons(false);
        //transectFindingSaveButton.setEnabled(true);
    }

    public TextView getLocation() {
        return location;
    }

    public TransectFinding getTransectFinding() {
        return transectFinding;
    }
}
