package com.wecode.animaltracker.view.findings;

import android.app.Activity;
import android.widget.Spinner;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.SpinnersHelper;
import com.wecode.animaltracker.model.findings.TransectFindingFeces;
import com.wecode.animaltracker.service.TransectFindingDataService;
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

    private Spinner fecesState;
    private TextView fecesPrey;
    private Spinner confidence;
    private CodeListSpinnerView age;

    private TransectFindingFecesDataService service = TransectFindingFecesDataService.getInstance();

    public TransectFindingFecesView(Activity context, TransectFindingFeces transectFindingFeces) {
        this(context, transectFindingFeces.getTransectFindingId());

        Assert.assertNotNull("transectFindingFeces cannot be null!", transectFindingFeces);
        bind(transectFindingFeces);
    }

    public TransectFindingFecesView(Activity context, Long transectFindingId) {
        this.transectFindingId = transectFindingId;

        confidence = (Spinner) context.findViewById(R.id.findingConfidenceValue);
        SpinnersHelper.setSpinnerData(confidence, R.array.findingConfidenceTypes);

        fecesState = (Spinner) context.findViewById(R.id.findingFecesStateValue);
        fecesPrey = (TextView) context.findViewById(R.id.findingFecesPreyValue);
        age = new CodeListSpinnerView(R.id.age, "findingAge", context);

        SpinnersHelper.setSpinnerData(fecesState, R.array.findingFecesState);
    }

    private void bind(TransectFindingFeces transectFindingFeces) {

        SpinnersHelper.setSelected(fecesState, transectFindingFeces.getFecesState());
        fecesPrey.setText(transectFindingFeces.getFecesPrey() == null ? "" : transectFindingFeces.getFecesPrey());
        age.select(transectFindingFeces.getAge());
        id = transectFindingFeces.getId();
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
        transectFindingFeces.setFecesPrey(ConverterUtil.toString(fecesPrey.getText()));
        transectFindingFeces.setFecesState(((String) fecesState.getSelectedItem()));
        transectFindingFeces.setAge(age.getSelectedCodeListValue());
        return transectFindingFeces;
    }
}
