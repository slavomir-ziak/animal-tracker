package com.wecode.animaltracker.view.findings;

import android.app.Activity;
import android.widget.Spinner;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.SpinnersHelper;
import com.wecode.animaltracker.model.findings.TransectFindingOther;
import com.wecode.animaltracker.model.findings.TransectFindingOther;
import com.wecode.animaltracker.service.TransectFindingOtherDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.ConverterUtil;
import com.wecode.animaltracker.view.CodeListSpinnerView;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingOtherView {

    private Long id;
    private Long transectFindingId;

    private CodeListSpinnerView otherEvidence;
    private CodeListSpinnerView otherObservations;
    private Spinner confidence;
    private CodeListSpinnerView age;
    private CodeListSpinnerView substract;

    private TransectFindingOtherDataService service = TransectFindingOtherDataService.getInstance();

    public TransectFindingOtherView(Activity context, TransectFindingOther transectFindingOther) {
        this(context, transectFindingOther.getTransectFindingId());

        Assert.assertNotNull("transectFindingOther cannot be null!", transectFindingOther);
        bind(transectFindingOther);
    }

    public TransectFindingOtherView(Activity context, Long transectFindingId) {
        this.transectFindingId = transectFindingId;
        confidence = (Spinner) context.findViewById(R.id.findingConfidenceValue);
        SpinnersHelper.setSpinnerData(confidence, R.array.findingConfidenceTypes);

        otherEvidence = new CodeListSpinnerView(R.id.findingOtherEvidenceValue, "findingOtherEvidence", context);
        otherObservations = new CodeListSpinnerView(R.id.findingOtherObservationsValue, "findingOtherObservations", context);
        age = new CodeListSpinnerView(R.id.age, "findingAge", context);
        substract = new CodeListSpinnerView(R.id.substract, "findingSubstract", context);
    }

    private void bind(TransectFindingOther transectFindingOther) {
        id = transectFindingOther.getId();
        otherEvidence.select(transectFindingOther.getEvidence());
        otherObservations.select(transectFindingOther.getObservations());
        age.select(transectFindingOther.getAge());
        substract.select(transectFindingOther.getSubstract());
    }

    public TransectFindingOther toOtherFinding(){

        TransectFindingOther transectFindingOther;

        if (id != null) {
            transectFindingOther = service.find(id);
        } else {
            transectFindingOther = new TransectFindingOther();
        }
        transectFindingOther.setTransectFindingId(transectFindingId);
        transectFindingOther.setConfidence((String) confidence.getSelectedItem());
        transectFindingOther.setEvidence(otherEvidence.getSelectedCodeListValue());
        transectFindingOther.setObservations(otherObservations.getSelectedCodeListValue());
        transectFindingOther.setAge(age.getSelectedCodeListValue());
        transectFindingOther.setSubstract(substract.getSelectedCodeListValue());
        return transectFindingOther;
    }
}
