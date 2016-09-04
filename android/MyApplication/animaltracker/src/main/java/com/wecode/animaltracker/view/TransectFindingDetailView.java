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
    private TextView urineLocation;
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

    private TextView otherEvidence;
    private TextView otherObservations;

    private TransectFinding transectFinding;

    private final ScrollView scrollView;

    private View findingsFecesView;
    private View findingsOtherView;

    private Switch findingFecesSwitch;
    private Switch findingOtherSwitch;

    public TransectFindingDetailView(Activity context, TransectFinding transectFinding) {
        this(context, transectFinding.getId());
        this.transectFinding = transectFinding;

        Assert.assertNotNull("transectFinding cannot be null!", transectFinding);
        bind(transectFinding);
    }

    public TransectFindingDetailView(Activity context, Long transectId) {

        this.transectId = transectId;

        species = new CodeListSpinnerView(R.id.findingSpeciesValue, "findingSpeciesTypes", context);
        confidence = (Spinner) context.findViewById(R.id.findingConfidenceValue);
        numberOfAnimals = (TextView) context.findViewById(R.id.findingCountValue);
        urineLocation = (TextView) context.findViewById(R.id.findingUrineLocation);
        location = (TextView) context.findViewById(R.id.findingLocationValue);
        findingBeforeRecentSnow = (RadioButton) context.findViewById(R.id.findingBeforeRecentSnow);
        findingAfterRecentSnow = (RadioButton) context.findViewById(R.id.findingAfterRecentSnow);
        footprintsDirection = (Spinner) context.findViewById(R.id.footprintsDirectionValue);
        footprintsFrontLength = (TextView) context.findViewById(R.id.footprintsFrontLengthValue);
        footprintsFrontWidht = (TextView) context.findViewById(R.id.footprintsFrontWidthValue);
        footprintsBackLength = (TextView) context.findViewById(R.id.footprintsBackLengthValue);
        footprintsBackWidht = (TextView) context.findViewById(R.id.footprintsBackWidthValue);
        footprintsAge = (TextView) context.findViewById(R.id.footprintsAgeValue);
        footprintsStride = (TextView) context.findViewById(R.id.footprintsStrideValue);

        fecesState = (Spinner) context.findViewById(R.id.findingFecesStateValue);
        fecesPrey = (TextView) context.findViewById(R.id.findingFecesPreyValue);

        SpinnersHelper.setSpinnerData(confidence, R.array.findingConfidenceTypes);
        SpinnersHelper.setSpinnerData(footprintsDirection, R.array.generalDirection);
        SpinnersHelper.setSpinnerData(fecesState, R.array.findingFecesState);

        findingsFecesView = context.findViewById(R.id.findingsFecesView);
        findingsOtherView = context.findViewById(R.id.findingsOtherVIew);
        scrollView = (ScrollView) context.findViewById(R.id.scrollView);

        findingFecesSwitch = (Switch) context.findViewById(R.id.findingFecesSwitch);
        findingOtherSwitch = (Switch) context.findViewById(R.id.findingOtherSwitch);

        otherEvidence = (TextView) context.findViewById(R.id.findingOtherEvidenceValue);
        otherObservations = (TextView) context.findViewById(R.id.findingOtherObservationsValue);
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

        if (transectFinding.hasFecesData()) {
            toggleVisibility(findingsFecesView);
            findingFecesSwitch.setChecked(true);
        }

        if (transectFinding.hasOtherData()) {
            toggleVisibility(findingsOtherView);
            findingOtherSwitch.setChecked(true);
        }

        urineLocation.setText(transectFinding.getUrineLocation() == null? "" : transectFinding.getUrineLocation());
        otherEvidence.setText(transectFinding.getOtherEvidence() == null? "" : transectFinding.getOtherEvidence());
        otherObservations.setText(transectFinding.getOtherObservations() == null? "" : transectFinding.getOtherObservations());

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
        transectFinding.setUrineLocation(urineLocation.getText().toString());
        transectFinding.setOtherEvidence(ConverterUtil.toString(otherEvidence.getText()));
        transectFinding.setOtherObservations(ConverterUtil.toString(otherObservations.getText()));
        transectFinding.setHabitatId(habitatId);
        transectFinding.setTransectId(transectId);
        transectFinding.setId(id);

        return transectFinding;
    }


    public void toggleFecesView(){
        toggleVisibility(findingsFecesView);
        scrollToBotom();
    }

    public void toggleOtherView(){
        toggleVisibility(findingsOtherView);
        scrollToBotom();
    }

    private void scrollToBotom() {
        scrollView.postOnAnimationDelayed(new Runnable() {
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN );
            }
        }, 300L);
    }

    private void toggleVisibility(View view) {
        switch(view.getVisibility()) {
            case View.VISIBLE:
                view.setVisibility(View.GONE);
                break;
            case View.GONE:
                view.setVisibility(View.VISIBLE);
                break;
            default:
                view.setVisibility(View.VISIBLE);
        }
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
