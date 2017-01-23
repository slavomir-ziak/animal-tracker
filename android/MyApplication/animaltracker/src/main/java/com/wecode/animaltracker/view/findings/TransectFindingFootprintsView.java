package com.wecode.animaltracker.view.findings;

import android.app.Activity;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.SpinnersHelper;
import com.wecode.animaltracker.model.findings.TransectFindingFootprints;
import com.wecode.animaltracker.service.TransectFindingFootprintsDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.ConverterUtil;
import com.wecode.animaltracker.view.CodeListSpinnerView;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingFootprintsView {


    private Long id;
    private Long transectFindingId;

    private TextView numberOfAnimals;
    private Spinner footprintsDirection;
    private Spinner footprintsGroup;
    private TextView footprintsBackLength;
    private TextView footprintsBackWidht;
    private TextView footprintsFrontLength;
    private TextView footprintsFrontWidht;
    private TextView footprintsStride;
    private Spinner confidence;
    private CodeListSpinnerView age;
    private CodeListSpinnerView substract;

    private TransectFindingFootprintsDataService service = TransectFindingFootprintsDataService.getInstance();

    private int initialHash;

    public TransectFindingFootprintsView(Activity context, View view, TransectFindingFootprints transectFindingFootprints) {
        this(context, view, transectFindingFootprints.getTransectFindingId());

        Assert.assertNotNull("transectFindingFootprints cannot be null!", transectFindingFootprints);
        bind(transectFindingFootprints);
    }

    public TransectFindingFootprintsView(Activity context, View view, Long transectFindingId) {
        this.transectFindingId = transectFindingId;
        confidence = (Spinner) view.findViewById(R.id.findingConfidenceValue);
        numberOfAnimals = (TextView) view.findViewById(R.id.findingCountValue);
        footprintsDirection = (Spinner) view.findViewById(R.id.footprintsDirectionValue);
        footprintsFrontLength = (TextView) view.findViewById(R.id.footprintsFrontLengthValue);
        footprintsFrontWidht = (TextView) view.findViewById(R.id.footprintsFrontWidthValue);
        footprintsBackLength = (TextView) view.findViewById(R.id.footprintsBackLengthValue);
        footprintsBackWidht = (TextView) view.findViewById(R.id.footprintsBackWidthValue);
        age = new CodeListSpinnerView(R.id.age, "findingAge", context, view);
        footprintsStride = (TextView) view.findViewById(R.id.footprintsStrideValue);
        substract = new CodeListSpinnerView(R.id.substract, "findingSubstract", context, view);
        footprintsGroup = (Spinner) view.findViewById(R.id.footprintsGroupValue);
        SpinnersHelper.setSpinnerData(footprintsGroup, R.array.footprintFindingGroupValues);
        SpinnersHelper.setSpinnerData(footprintsDirection, R.array.generalDirection);
        SpinnersHelper.setSpinnerData(confidence, R.array.findingConfidenceTypes);
    }

    private void bind(TransectFindingFootprints transectFindingFootprints) {
        id = transectFindingFootprints.getId();
        numberOfAnimals.setText(transectFindingFootprints.getNumberOfAnimals() == null ? "" : transectFindingFootprints.getNumberOfAnimals().toString());
        footprintsFrontLength.setText(transectFindingFootprints.getFrontLengthValue());
        footprintsFrontWidht.setText(transectFindingFootprints.getFrontWidthValue());
        footprintsBackLength.setText(transectFindingFootprints.getBackLengthValue());
        footprintsBackWidht.setText(transectFindingFootprints.getBackWidthValue());
        age.select(transectFindingFootprints.getAge());
        footprintsStride.setText(transectFindingFootprints.getStride() == null ? "" : transectFindingFootprints.getStride().toString());
        substract.select(transectFindingFootprints.getSubstract());
        SpinnersHelper.setSelected(footprintsGroup, transectFindingFootprints.getGroupValue());
        SpinnersHelper.setSelected(footprintsDirection, transectFindingFootprints.getDirection());
        SpinnersHelper.setSelected(confidence, transectFindingFootprints.getConfidence());


        initialHash = hashCode();
    }

    public TransectFindingFootprints toFootprintsFinding(){

        TransectFindingFootprints transectFindingFootprints;

        if (id != null) {
            transectFindingFootprints = service.find(id);
        } else {
            transectFindingFootprints = new TransectFindingFootprints();
        }

        transectFindingFootprints.setTransectFindingId(transectFindingId);
        transectFindingFootprints.setConfidence((String) confidence.getSelectedItem());
        transectFindingFootprints.setNumberOfAnimals(ConverterUtil.toInteger(numberOfAnimals.getText().toString()));
        transectFindingFootprints.setAge(age.getSelectedCodeListValue());
        transectFindingFootprints.setDirection((String) footprintsDirection.getSelectedItem());
        transectFindingFootprints.setFrontLength(ConverterUtil.toFloat(footprintsFrontLength.getText().toString()));
        transectFindingFootprints.setFrontWidht(ConverterUtil.toFloat(footprintsFrontWidht.getText().toString()));
        transectFindingFootprints.setBackLength(ConverterUtil.toFloat(footprintsBackLength.getText().toString()));
        transectFindingFootprints.setBackWidht(ConverterUtil.toFloat(footprintsBackWidht.getText().toString()));
        transectFindingFootprints.setStride(ConverterUtil.toFloat(footprintsStride.getText().toString()));
        transectFindingFootprints.setSubstract(substract.getSelectedCodeListValue());
        transectFindingFootprints.setGroupValue(((String) footprintsGroup.getSelectedItem()));
        initialHash = hashCode();
        return transectFindingFootprints;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        initialHash = hashCode();
    }


    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (numberOfAnimals != null ? numberOfAnimals.getText().toString().hashCode() : 0);
        result = 31 * result + (footprintsDirection != null ? footprintsDirection.getSelectedItem().toString().hashCode() : 0);
        result = 31 * result + (footprintsBackLength != null ? footprintsBackLength.getText().toString().hashCode() : 0);
        result = 31 * result + (footprintsBackWidht != null ? footprintsBackWidht.getText().toString().hashCode() : 0);
        result = 31 * result + (footprintsFrontLength != null ? footprintsFrontLength.getText().toString().hashCode() : 0);
        result = 31 * result + (footprintsFrontWidht != null ? footprintsFrontWidht.getText().toString().hashCode() : 0);
        result = 31 * result + (footprintsStride != null ? footprintsStride.getText().toString().hashCode() : 0);
        result = 31 * result + (confidence != null ? confidence.getSelectedItem().toString().hashCode() : 0);
        result = 31 * result + (age != null ? age.getSelectedCodeListValue().hashCode() : 0);
        result = 31 * result + (substract != null ? substract.getSelectedCodeListValue().hashCode() : 0);
        result = 31 * result + (footprintsGroup != null ? footprintsGroup.getSelectedItem().toString().hashCode() : 0);
        return result;
    }

    public boolean isChanged() {
        return initialHash != hashCode();
    }
}
