package com.wecode.animaltracker.view.findings;

import android.app.Activity;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.SpinnersHelper;
import com.wecode.animaltracker.model.findings.TransectFindingOther;
import com.wecode.animaltracker.service.TransectFindingOtherDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.view.ChangeableView;
import com.wecode.animaltracker.view.CodeListSpinnerView;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingOtherView  implements ChangeableView {

    private Long id;
    private Long transectFindingId;

    private CodeListSpinnerView otherEvidence;
    private CodeListSpinnerView otherObservations;
    private Spinner confidence;
    private CodeListSpinnerView age;
    private CodeListSpinnerView substract;
    private TextView comment;

    private TransectFindingOtherDataService service = TransectFindingOtherDataService.getInstance();

    private int initialHash;

    public TransectFindingOtherView(Activity context, View view, TransectFindingOther transectFindingOther) {
        this(context, view, transectFindingOther.getTransectFindingId());

        Assert.assertNotNull("transectFindingOther cannot be null!", transectFindingOther);
        bind(transectFindingOther);
    }

    public TransectFindingOtherView(Activity context, View view, Long transectFindingId) {
        this.transectFindingId = transectFindingId;
        confidence = (Spinner) view.findViewById(R.id.findingConfidenceValue);
        SpinnersHelper.setSpinnerData(confidence, R.array.findingConfidenceTypes);

        otherEvidence = new CodeListSpinnerView(R.id.findingOtherEvidenceValue, "findingOtherEvidence", context, view);
        otherObservations = new CodeListSpinnerView(R.id.findingOtherObservationsValue, "findingOtherObservations", context, view);
        age = new CodeListSpinnerView(R.id.age, "findingAge", context, view);
        substract = new CodeListSpinnerView(R.id.substract, "findingSubstract", context, view);
        comment = (TextView) view.findViewById(R.id.comment);
    }

    private void bind(TransectFindingOther transectFindingOther) {
        id = transectFindingOther.getId();
        otherEvidence.select(transectFindingOther.getEvidence());
        otherObservations.select(transectFindingOther.getObservations());
        age.select(transectFindingOther.getAge());
        substract.select(transectFindingOther.getSubstract());
        SpinnersHelper.setSelected(confidence, transectFindingOther.getConfidence());
        initialHash = hashCode();
        comment.setText(transectFindingOther.getComment());
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
        transectFindingOther.setComment(comment.getText().toString());
        initialHash = hashCode();
        return transectFindingOther;
    }

    public void setEvidence(String evidence) {
        otherEvidence.select(evidence);
        initialHash = hashCode();
    }

    public void setId(Long id) {
        this.id = id;
        initialHash = hashCode();
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (otherEvidence != null ? otherEvidence.getSelectedCodeListValue().hashCode() : 0);
        result = 31 * result + (otherObservations != null ? otherObservations.getSelectedCodeListValue().hashCode() : 0);
        result = 31 * result + (confidence != null ? confidence.getSelectedItem().hashCode() : 0);
        result = 31 * result + (age != null ? age.getSelectedCodeListValue().hashCode() : 0);
        result = 31 * result + (substract != null ? substract.getSelectedCodeListValue().hashCode() : 0);
        result = 31 * result + (comment != null ? comment.getText().toString().hashCode() : 0);
        return result;
    }

    public boolean isChanged() {
        return initialHash != hashCode();
    }
}
