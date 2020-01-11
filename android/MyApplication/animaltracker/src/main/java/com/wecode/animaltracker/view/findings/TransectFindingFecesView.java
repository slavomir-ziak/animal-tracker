package com.wecode.animaltracker.view.findings;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.SpinnersHelper;
import com.wecode.animaltracker.model.CodeList;
import com.wecode.animaltracker.model.findings.TransectFindingFeces;
import com.wecode.animaltracker.service.TransectFindingFecesDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.ConverterUtil;
import com.wecode.animaltracker.view.ChangeableView;
import com.wecode.animaltracker.view.CodeListSpinnerView;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingFecesView implements ChangeableView {

    private Long id;
    private Long transectFindingId;

    private CodeListSpinnerView fecesPrey;
    private Spinner confidence;
    private CodeListSpinnerView fecesState;
    private CodeListSpinnerView age;
    private CodeListSpinnerView substract;
    private CheckBox collected;
    private TransectFindingFecesDataService service = TransectFindingFecesDataService.getInstance();
    private int initialHash;
    private TextView comment;

    public TransectFindingFecesView(Activity context, View view, TransectFindingFeces transectFindingFeces) {
        this(context, view, transectFindingFeces.getTransectFindingId());

        Assert.assertNotNull("transectFindingFeces cannot be null!", transectFindingFeces);
        bind(transectFindingFeces);
    }

    public TransectFindingFecesView(Activity context, View view, Long transectFindingId) {
        this.transectFindingId = transectFindingId;

        confidence = (Spinner) view.findViewById(R.id.findingConfidenceValue);
        SpinnersHelper.setSpinnerData(confidence, R.array.findingConfidenceTypes);

        fecesPrey = new CodeListSpinnerView(R.id.findingFecesPreySpinner, CodeList.Name.fecesPrey.name(), context, view);
        fecesState = new CodeListSpinnerView(R.id.findingFecesStateValue, CodeList.Name.fecesState.name(), context, view);
        age = new CodeListSpinnerView(R.id.age, CodeList.Name.findingAge.name(), context, view);
        substract = new CodeListSpinnerView(R.id.substract, CodeList.Name.findingSubstract.name(), context, view);
        collected = (CheckBox) view.findViewById(R.id.transect_finding_sample);
        comment = (TextView) view.findViewById(R.id.comment);
    }

    private void bind(TransectFindingFeces transectFindingFeces) {

        fecesPrey.select(transectFindingFeces.getPrey());
        age.select(transectFindingFeces.getAge());
        fecesState.select(transectFindingFeces.getState());
        substract.select(transectFindingFeces.getSubstract());
        SpinnersHelper.setSelected(confidence, transectFindingFeces.getConfidence());
        id = transectFindingFeces.getId();
        collected.setChecked(transectFindingFeces.isCollected());
        comment.setText(transectFindingFeces.getComment());

        initialHash = hashCode();
    }

    public TransectFindingFeces toFecesFinding(){

        TransectFindingFeces transectFindingFeces;

        if (id != null) {
            transectFindingFeces = service.find(id);
        } else {
            transectFindingFeces = new TransectFindingFeces();
        }

        transectFindingFeces.setTransectFindingId(transectFindingId);
        transectFindingFeces.setConfidence((String) confidence.getSelectedItem());
        transectFindingFeces.setPrey(ConverterUtil.toString(fecesPrey.getSelectedCodeListValue()));
        transectFindingFeces.setState((fecesState.getSelectedCodeListValue()));
        transectFindingFeces.setAge(age.getSelectedCodeListValue());
        transectFindingFeces.setSubstract(substract.getSelectedCodeListValue());
        transectFindingFeces.setCollected(collected.isChecked());
        transectFindingFeces.setComment(comment.getText().toString());

        initialHash = hashCode();
        return transectFindingFeces;
    }

    public void setId(Long id) {
        this.id = id;
        initialHash = hashCode();
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fecesPrey != null ? fecesPrey.getSelectedCodeListValue().hashCode() : 0);
        result = 31 * result + (confidence != null ? confidence.getSelectedItem().toString().hashCode() : 0);
        result = 31 * result + (fecesState != null ? fecesState.getSelectedCodeListValue().hashCode() : 0);
        result = 31 * result + (age != null ? age.getSelectedCodeListValue().hashCode() : 0);
        result = 31 * result + (substract != null ? substract.getSelectedCodeListValue().hashCode() : 0);
        result = 31 * result + (collected != null ? collected.isChecked() ? 1 : 0 : 0);
        result = 31 * result + (comment != null ? comment.getText().toString().hashCode() : 0);
        return result;
    }

    public boolean isChanged() {
        return initialHash != hashCode();
    }
}
