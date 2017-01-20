package com.wecode.animaltracker.view.findings;

import android.app.Activity;
import android.widget.Spinner;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.SpinnersHelper;
import com.wecode.animaltracker.model.findings.TransectFindingFeces;
import com.wecode.animaltracker.service.TransectFindingFecesDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.ConverterUtil;
import com.wecode.animaltracker.view.CodeListSpinnerView;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingFecesView {

    private Long id;
    private Long transectFindingId;

    private CodeListSpinnerView fecesPrey;
    private Spinner confidence;
    private CodeListSpinnerView fecesState;
    private CodeListSpinnerView age;
    private CodeListSpinnerView substract;

    private TransectFindingFecesDataService service = TransectFindingFecesDataService.getInstance();
    private int initialHash;

    public TransectFindingFecesView(Activity context, TransectFindingFeces transectFindingFeces) {
        this(context, transectFindingFeces.getTransectFindingId());

        Assert.assertNotNull("transectFindingFeces cannot be null!", transectFindingFeces);
        bind(transectFindingFeces);
    }

    public TransectFindingFecesView(Activity context, Long transectFindingId) {
        this.transectFindingId = transectFindingId;

        confidence = (Spinner) context.findViewById(R.id.findingConfidenceValue);
        SpinnersHelper.setSpinnerData(confidence, R.array.findingConfidenceTypes);

        fecesPrey = new CodeListSpinnerView(R.id.findingFecesPreySpinner, "fecesPrey", context);
        fecesState = new CodeListSpinnerView(R.id.findingFecesStateValue, "fecesState", context);
        age = new CodeListSpinnerView(R.id.age, "findingAge", context);
        substract = new CodeListSpinnerView(R.id.substract, "findingSubstract", context);

    }

    private void bind(TransectFindingFeces transectFindingFeces) {

        fecesPrey.select(transectFindingFeces.getPrey());
        age.select(transectFindingFeces.getAge());
        fecesState.select(transectFindingFeces.getState());
        substract.select(transectFindingFeces.getSubstract());
        SpinnersHelper.setSelected(confidence, transectFindingFeces.getConfidence());
        id = transectFindingFeces.getId();
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
        return result;
    }

    public boolean isChanged() {
        return initialHash != hashCode();
    }
}
