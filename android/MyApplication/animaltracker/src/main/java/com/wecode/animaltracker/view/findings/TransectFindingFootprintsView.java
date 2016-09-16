package com.wecode.animaltracker.view.findings;

import android.app.Activity;
import android.widget.Spinner;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.SpinnersHelper;
import com.wecode.animaltracker.model.findings.TransectFindingFootprints;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.ConverterUtil;
import com.wecode.animaltracker.view.CodeListSpinnerView;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingFootprintsView {


    private Long id;
    private Long footprintsFindingId;

    private TextView numberOfAnimals;
    private Spinner footprintsDirection;
    private TextView footprintsBackLength;
    private TextView footprintsBackWidht;
    private TextView footprintsFrontLength;
    private TextView footprintsFrontWidht;
    private TextView footprintsStride;
    private Spinner confidence;
    private CodeListSpinnerView age;


    public TransectFindingFootprintsView(Activity context, TransectFindingFootprints transectFindingFootprints) {
        this(context, transectFindingFootprints.getId());

        Assert.assertNotNull("transectFindingFootprints cannot be null!", transectFindingFootprints);
        bind(transectFindingFootprints);
    }

    public TransectFindingFootprintsView(Activity context, Long footprintsFindingId) {
        this.footprintsFindingId = footprintsFindingId;
        confidence = (Spinner) context.findViewById(R.id.findingConfidenceValue);
        SpinnersHelper.setSpinnerData(confidence, R.array.findingConfidenceTypes);
        numberOfAnimals = (TextView) context.findViewById(R.id.findingCountValue);
        footprintsDirection = (Spinner) context.findViewById(R.id.footprintsDirectionValue);
        footprintsFrontLength = (TextView) context.findViewById(R.id.footprintsFrontLengthValue);
        footprintsFrontWidht = (TextView) context.findViewById(R.id.footprintsFrontWidthValue);
        footprintsBackLength = (TextView) context.findViewById(R.id.footprintsBackLengthValue);
        footprintsBackWidht = (TextView) context.findViewById(R.id.footprintsBackWidthValue);
        age = new CodeListSpinnerView(R.id.age, "findingAge", context);
        footprintsStride = (TextView) context.findViewById(R.id.footprintsStrideValue);

        SpinnersHelper.setSpinnerData(footprintsDirection, R.array.generalDirection);
    }

    private void bind(TransectFindingFootprints transectFindingFootprints) {
        SpinnersHelper.setSelected(confidence, transectFindingFootprints.getConfidence());

        numberOfAnimals.setText(transectFindingFootprints.getNumberOfAnimals() == null ? "" : transectFindingFootprints.getNumberOfAnimals().toString());

        SpinnersHelper.setSelected(footprintsDirection, transectFindingFootprints.getDirection());

        footprintsFrontLength.setText(transectFindingFootprints.getFootprintsFrontLengthValue());
        footprintsFrontWidht.setText(transectFindingFootprints.getFootprintsFrontWidthValue());
        footprintsBackLength.setText(transectFindingFootprints.getFootprintsBackLengthValue());
        footprintsBackWidht.setText(transectFindingFootprints.getFootprintsBackWidthValue());
        age.select(transectFindingFootprints.getAge());
        footprintsStride.setText(transectFindingFootprints.getStride() == null ? "" : transectFindingFootprints.getStride().toString());

    }

    public TransectFindingFootprints toFootprintsFinding(){
        TransectFindingFootprints transectFindingFootprints = new TransectFindingFootprints();
        transectFindingFootprints.setConfidence((String) confidence.getSelectedItem());
        transectFindingFootprints.setNumberOfAnimals(ConverterUtil.toInteger(numberOfAnimals.getText().toString()));
        transectFindingFootprints.setAge(age.getSelectedCodeListValue());
        transectFindingFootprints.setDirection((String) footprintsDirection.getSelectedItem());
        transectFindingFootprints.setFrontLength(ConverterUtil.toFloat(footprintsFrontLength.getText().toString()));
        transectFindingFootprints.setFrontWidht(ConverterUtil.toFloat(footprintsFrontWidht.getText().toString()));
        transectFindingFootprints.setBackLength(ConverterUtil.toFloat(footprintsBackLength.getText().toString()));
        transectFindingFootprints.setBackWidht(ConverterUtil.toFloat(footprintsBackWidht.getText().toString()));
        transectFindingFootprints.setStride(ConverterUtil.toFloat(footprintsStride.getText().toString()));
        return transectFindingFootprints;
    }

}
