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

    private TextView otherEvidence;
    private TextView otherObservations;
    private Spinner confidence;
    private CodeListSpinnerView age;

    private TransectFindingOtherDataService service = TransectFindingOtherDataService.getInstance();

    public TransectFindingOtherView(Activity context, TransectFindingOther transectFindingOther) {
        this(context, transectFindingOther.getId());

        Assert.assertNotNull("transectFindingOther cannot be null!", transectFindingOther);
        bind(transectFindingOther);
    }

    public TransectFindingOtherView(Activity context, Long transectFindingId) {
        this.transectFindingId = transectFindingId;
        confidence = (Spinner) context.findViewById(R.id.findingConfidenceValue);
        SpinnersHelper.setSpinnerData(confidence, R.array.findingConfidenceTypes);

        otherEvidence = (TextView) context.findViewById(R.id.findingOtherEvidenceValue);
        otherObservations = (TextView) context.findViewById(R.id.findingOtherObservationsValue);
        age = new CodeListSpinnerView(R.id.age, "findingAge", context);
    }

    private void bind(TransectFindingOther transectFindingOther) {
        id = transectFindingOther.getId();
        otherEvidence.setText(transectFindingOther.getEvidence() == null? "" : transectFindingOther.getEvidence());
        otherObservations.setText(transectFindingOther.getObservations() == null? "" : transectFindingOther.getObservations());
        age.select(transectFindingOther.getAge());
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
        transectFindingOther.setEvidence(ConverterUtil.toString(otherEvidence.getText()));
        transectFindingOther.setObservations(ConverterUtil.toString(otherObservations.getText()));
        transectFindingOther.setAge(age.getSelectedCodeListValue());
        return transectFindingOther;
    }
}
