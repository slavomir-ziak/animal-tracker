package com.wecode.animaltracker.view;

import android.location.Location;
import android.view.View;
import android.widget.Button;
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
    private Spinner type;

    private Spinner species;
    private Spinner confidence;
    private TextView count;
    private TextView location;
    private Spinner beforeAfterRecentSnow;
    private Spinner fecesState;

    private TextView fecesPrey;
    private Spinner footprintsFrontBack;

    private Spinner footprintsDirection;
    private TextView footprintsLength;
    private TextView footprintsWidht;
    private TextView footprintsAge;
    private TextView footprintsStride;
    private TransectFinding transectFinding;

    private final Button transectFindingHabitatButton;
    private final Button transectFindingSamplesButton;
    private final Button transectFindingAddPhotoButton;
    private final Button transectFindingListPhotosButton;
    private final Button transectFindingAddSampleButton;
    private final Button transectFindingSaveButton;

    public TransectFindingDetailView(TransectFindingDetailActivity context, TransectFinding transectFinding) {
        this(context, transectFinding.getId());
        this.transectFinding = transectFinding;

        Assert.assertNotNull("transectFinding cannot be null!", transectFinding);
        bind(transectFinding);
    }

    public TransectFindingDetailView(TransectFindingDetailActivity context, Long transectId) {

        SpinnersHelper.setSpinnerData(context, R.id.findingTypeValue, R.array.findingTypes);
        SpinnersHelper.setSpinnerData(context, R.id.findingSpeciesValue, R.array.findingSpeciesTypes);
        SpinnersHelper.setSpinnerData(context, R.id.findingConfidenceValue, R.array.findingConfidenceTypes);
        SpinnersHelper.setSpinnerData(context, R.id.findingBeforeAfterRecentSnowValue, R.array.findingBeforeAfterRecentSnowValues);

        type = (Spinner) context.findViewById(R.id.findingTypeValue);
        species = (Spinner) context.findViewById(R.id.findingSpeciesValue);
        confidence = (Spinner) context.findViewById(R.id.findingConfidenceValue);
        count = (TextView) context.findViewById(R.id.findingCountValue);
        location = (TextView) context.findViewById(R.id.findingLocationValue);
        beforeAfterRecentSnow = (Spinner) context.findViewById(R.id.findingBeforeAfterRecentSnowValue);

        this.transectId = transectId;

        transectFindingHabitatButton = (Button) context.findViewById(R.id.transectFindingHabitatButton);
        transectFindingSamplesButton = (Button) context.findViewById(R.id.transectFindingListSamplesButton);
        transectFindingAddPhotoButton = (Button) context.findViewById(R.id.transectFindingAddPhotoButton);
        transectFindingListPhotosButton = (Button) context.findViewById(R.id.transectFindingListPhotosButton);
        transectFindingAddSampleButton = (Button) context.findViewById(R.id.transectFindingAddSampleButton);
        transectFindingSaveButton = (Button) context.findViewById(R.id.transectFindingSaveButton);



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

        SpinnersHelper.setSelected(type, transectFinding.getType());
        SpinnersHelper.setSelected(species, transectFinding.getSpecies());
        SpinnersHelper.setSelected(confidence, transectFinding.getConfidence());

        count.setText(transectFinding.getCount() == null ? "" : transectFinding.getCount().toString());

        if (transectFinding.hasLocation()) {
            location.setText(LocationUtil.formatLocation(transectFinding.getLocationLongitude(), transectFinding.getLocationLatitude()));
        }

        SpinnersHelper.setSelected(beforeAfterRecentSnow, transectFinding.getBeforeAfterRecentSnow());

        habitatId = transectFinding.getHabitatId();
        transectId = transectFinding.getTransectId();
        id = transectFinding.getId();
    }

    public void setLocation(Location location) {
        this.location.setText(LocationUtil.formatLocation(location));
    }

    public TransectFinding toTransectFinding() {
        TransectFinding transectFinding = new TransectFinding();

        transectFinding.setType((String) type.getSelectedItem());
        transectFinding.setSpecies((String) species.getSelectedItem());
        transectFinding.setConfidence((String) confidence.getSelectedItem());
        transectFinding.setCount(ConverterUtil.toInteger(count.getText().toString()));

        if (location.getText().toString().length() > 0) {
            transectFinding.setLocationLongitude(Double.parseDouble(this.location.getText().toString().split(",")[0]));
            transectFinding.setLocationLatitude(Double.parseDouble(this.location.getText().toString().split(",")[1]));
        }

        transectFinding.setBeforeAfterRecentSnow((String) beforeAfterRecentSnow.getSelectedItem());

        if (type.getSelectedItem().equals("Footprints")) {

            transectFinding.setFootprintsAge(ConverterUtil.toInteger(footprintsAge.getText().toString()));
            transectFinding.setFootprintsDirection((String) footprintsDirection.getSelectedItem());
            transectFinding.setFootprintsFrontBack((String) footprintsFrontBack.getSelectedItem());
            transectFinding.setFootprintsLength(ConverterUtil.toInteger(footprintsLength.getText().toString()));
            transectFinding.setFootprintsWidht(ConverterUtil.toInteger(footprintsWidht.getText().toString()));
            transectFinding.setFootprintsStride(ConverterUtil.toInteger(footprintsStride.getText().toString()));
        }

        if (type.getSelectedItem().equals("Feces")) {
            transectFinding.setFecesPrey(ConverterUtil.toString(fecesPrey.getText()));
            transectFinding.setFecesState(((String) fecesState.getSelectedItem()));

        }

        transectFinding.setHabitatId(habitatId);
        transectFinding.setTransectId(transectId);
        transectFinding.setId(id);

        return transectFinding;
    }

    public void enableAllButtons(boolean enable) {
        transectFindingHabitatButton.setEnabled(enable);
        transectFindingSamplesButton.setEnabled(enable);
        transectFindingAddPhotoButton.setEnabled(enable);
        transectFindingListPhotosButton.setEnabled(enable);
        transectFindingAddSampleButton.setEnabled(enable);
        transectFindingSaveButton.setEnabled(enable);
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
        transectFindingSaveButton.setEnabled(true);
    }
}
