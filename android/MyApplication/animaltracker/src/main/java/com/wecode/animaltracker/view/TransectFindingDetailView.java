package com.wecode.animaltracker.view;

import android.location.Location;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.detail.TransectFindingDetailActivity;
import com.wecode.animaltracker.activity.util.LocationFormatter;
import com.wecode.animaltracker.activity.util.SpinnersHelper;
import com.wecode.animaltracker.model.TransectFinding;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.ConverterUtil;

/**
 * Created by sziak on 10-Apr-16.
 */
public class TransectFindingDetailView {

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
            
    public TransectFindingDetailView(TransectFindingDetailActivity context, TransectFinding transectFinding) {
        this(context);
        this.transectFinding = transectFinding;

        Assert.assertNotNull("transectFinding cannot be null!", transectFinding);
        bind(transectFinding);
    }

    public TransectFindingDetailView(TransectFindingDetailActivity context) {

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
            location.setText(LocationFormatter.formatLocation(transectFinding.getLocationLongitude(), transectFinding.getLocationLatitude()));
        }

        SpinnersHelper.setSelected(beforeAfterRecentSnow, transectFinding.getBeforeAfterRecentSnow());

    }

    public void setLocation(Location location) {
        this.location.setText(LocationFormatter.formatLocation(location));
    }

    public TransectFinding toTransectFinding() {
        TransectFinding transectFinding = new TransectFinding();
        transectFinding.setBeforeAfterRecentSnow((String) beforeAfterRecentSnow.getSelectedItem());
        transectFinding.setConfidence((String) confidence.getSelectedItem());

        transectFinding.setCount(ConverterUtil.toInteger(count.getText().toString()));

        transectFinding.setFecesPrey(fecesPrey.getText().toString());
        transectFinding.setFecesState(((String) fecesState.getSelectedItem()));

        transectFinding.setFootprintsAge(ConverterUtil.toInteger(footprintsAge.getText().toString()));
        transectFinding.setFootprintsDirection((String) footprintsDirection.getSelectedItem());
        transectFinding.setFootprintsFrontBack((String) footprintsFrontBack.getSelectedItem());
        transectFinding.setFootprintsLength(ConverterUtil.toInteger(footprintsLength.getText().toString()));
        transectFinding.setFootprintsWidht(ConverterUtil.toInteger(footprintsWidht.getText().toString()));
        transectFinding.setFootprintsStride(ConverterUtil.toInteger(footprintsStride.getText().toString()));

        return transectFinding;
    }
}
