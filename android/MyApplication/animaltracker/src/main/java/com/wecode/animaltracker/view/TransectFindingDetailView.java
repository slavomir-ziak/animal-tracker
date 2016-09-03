package com.wecode.animaltracker.view;

import android.app.Activity;
import android.location.Location;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.detail.TransectFindingDetailActivity;
import com.wecode.animaltracker.activity.util.SpinnersHelper;
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

    private CodeListSpinnerView species;

    private Spinner confidence;
    private TextView numberOfAnimals;
    private TextView location;

    private Spinner fecesState;
    private TextView fecesPrey;

    private Spinner footprintsDirection;

    private TextView footprintsBackLength;
    private TextView footprintsBackWidht;
    private TextView footprintsFrontLength;
    private TextView footprintsFrontWidht;

    private TextView footprintsAge;
    private TextView footprintsStride;

    private RadioButton findingBeforeRecentSnow;
    private RadioButton findingAfterRecentSnow;

    private TransectFinding transectFinding;


    /*

    private final Button transectFindingHabitatButton;
    private final Button transectFindingSamplesButton;
    private final Button transectFindingAddPhotoButton;
    private final Button transectFindingListPhotosButton;
    private final Button transectFindingAddSampleButton;
    private final Button transectFindingSaveButton;
*/
    public TransectFindingDetailView(Activity context, TransectFinding transectFinding) {
        this(context, transectFinding.getId());
        this.transectFinding = transectFinding;

        Assert.assertNotNull("transectFinding cannot be null!", transectFinding);
        bind(transectFinding);
    }

    public TransectFindingDetailView(Activity context, Long transectId) {

        species = new CodeListSpinnerView(R.id.findingSpeciesValue, "findingSpeciesTypes", context);

        confidence = (Spinner) context.findViewById(R.id.findingConfidenceValue);
        numberOfAnimals = (TextView) context.findViewById(R.id.findingCountValue);
        location = (TextView) context.findViewById(R.id.findingLocationValue);
        findingBeforeRecentSnow = (RadioButton) context.findViewById(R.id.findingBeforeRecentSnow);
        findingAfterRecentSnow = (RadioButton) context.findViewById(R.id.findingAfterRecentSnow);

        footprintsDirection = (Spinner) context.findViewById(R.id.footprintsDirectionValue);
        footprintsFrontLength = (TextView) context.findViewById(R.id.footprintsLengthValue);
        footprintsFrontWidht = (TextView) context.findViewById(R.id.footprintsWidthValue);
        footprintsBackLength = (TextView) context.findViewById(R.id.footprintsLengthValue);
        footprintsBackWidht = (TextView) context.findViewById(R.id.footprintsWidthValue);
        footprintsAge = (TextView) context.findViewById(R.id.footprintsAgeValue);
        footprintsStride = (TextView) context.findViewById(R.id.footprintsStrideValue);

        SpinnersHelper.setSpinnerData(confidence, R.array.findingConfidenceTypes);

        Spinner footprintsDirectionValue = (Spinner) context.findViewById(R.id.footprintsDirectionValue);
        SpinnersHelper.setSpinnerData(footprintsDirectionValue, R.array.generalDirection);

        fecesState = (Spinner) context.findViewById(R.id.findingFecesStateValue);
        fecesPrey = (TextView) context.findViewById(R.id.findingFecesPreyValue);


        SpinnersHelper.setSpinnerData(fecesState, R.array.findingFecesState);

        this.transectId = transectId;

       /* transectFindingHabitatButton = (Button) toolbar.findViewById(R.id.transect_finding_action_habitat);
        transectFindingSamplesButton = (Button) toolbar.findViewById(R.id.transect_finding_action_samples);
        transectFindingAddPhotoButton = (Button) toolbar.findViewById(R.id.transect_finding_action_add_photo);
        transectFindingListPhotosButton = (Button) toolbar.findViewById(R.id.transect_finding_action_photos);
        transectFindingAddSampleButton = (Button) toolbar.findViewById(R.id.transect_finding_action_add_sample);
        transectFindingSaveButton = (Button) toolbar.findViewById(R.id.action_save);*/

    }

    private void bind(TransectFinding transectFinding) {

        species.select(transectFinding.getSpecies());
        SpinnersHelper.setSelected(confidence, transectFinding.getConfidence());

        numberOfAnimals.setText(transectFinding.getNumberOfAnimals() == null ? "" : transectFinding.getNumberOfAnimals().toString());

        findingBeforeRecentSnow.setChecked("BEFORE".equals(transectFinding.getBeforeAfterRecentSnow()));
        findingAfterRecentSnow.setChecked("AFTER".equals(transectFinding.getBeforeAfterRecentSnow()));

        if (transectFinding.hasLocation()) {
            location.setText(LocationUtil.formatLocation(transectFinding.getLocationLatitude(), transectFinding.getLocationLongitude()));
        }

        SpinnersHelper.setSelected(footprintsDirection, transectFinding.getFootprintsDirection());

        footprintsFrontLength.setText(transectFinding.getFootprintsFrontLengthValue());
        footprintsFrontWidht.setText(transectFinding.getFootprintsFrontWidthValue());
        footprintsBackLength.setText(transectFinding.getFootprintsBackLengthValue());
        footprintsBackWidht.setText(transectFinding.getFootprintsBackWidthValue());
        footprintsAge.setText(transectFinding.getFootprintsAge() == null ? "" : transectFinding.getFootprintsAge().toString());
        footprintsStride.setText(transectFinding.getFootprintsStride() == null ? "" : transectFinding.getFootprintsStride().toString());

        SpinnersHelper.setSelected(fecesState, transectFinding.getFecesState());
        fecesPrey.setText(transectFinding.getFecesPrey() == null ? "" : transectFinding.getFecesPrey());

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
        transectFinding.setConfidence((String) confidence.getSelectedItem());
        transectFinding.setNumberOfAnimals(ConverterUtil.toInteger(numberOfAnimals.getText().toString()));

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

        transectFinding.setFootprintsAge(ConverterUtil.toFloat(footprintsAge.getText().toString()));
        transectFinding.setFootprintsDirection((String) footprintsDirection.getSelectedItem());

        transectFinding.setFootprintsFrontLength(ConverterUtil.toFloat(footprintsFrontLength.getText().toString()));
        transectFinding.setFootprintsFrontWidht(ConverterUtil.toFloat(footprintsFrontWidht.getText().toString()));
        transectFinding.setFootprintsBackLength(ConverterUtil.toFloat(footprintsBackLength.getText().toString()));
        transectFinding.setFootprintsBackWidht(ConverterUtil.toFloat(footprintsBackWidht.getText().toString()));

        transectFinding.setFootprintsStride(ConverterUtil.toFloat(footprintsStride.getText().toString()));

        transectFinding.setFecesPrey(ConverterUtil.toString(fecesPrey.getText()));
        transectFinding.setFecesState(((String) fecesState.getSelectedItem()));

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
