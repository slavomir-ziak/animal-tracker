package com.wecode.animaltracker.fragment.detail.finding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.fragment.Fragment;
import com.wecode.animaltracker.model.findings.TransectFindingOther;
import com.wecode.animaltracker.service.TransectFindingOtherDataService;
import com.wecode.animaltracker.view.findings.TransectFindingOtherView;

/**
 * Created by slavo on 1/22/2017.
 */

public class TransectFindingOtherFindingFragment extends CommonFindingFragment implements Fragment {

    private TransectFindingOtherDataService transectFindingOtherDataService = TransectFindingOtherDataService.getInstance();

    private TransectFindingOtherView transectFindingOtherView;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_transect_finding_other, container, false);

        extractParams(getArguments());

        if (id != null) {
            TransectFindingOther transectFinding = transectFindingOtherDataService.find(id);
            transectFindingOtherView = new TransectFindingOtherView(getActivity(), view, transectFinding);
        } else {
            transectFindingOtherView = new TransectFindingOtherView(getActivity(), view, transectFindingSiteId);
            String evidence = getArguments().getString("evidence");
            if (evidence != null) {
                transectFindingOtherView.setEvidence(evidence);
            }
        }

        return view;
    }


    @Override
    public int getNameResourceId() {
        return R.string.other_finding_detail;
    }

    @Override
    public boolean isChangedByUser() {
        return transectFindingOtherView.isChanged();
    }

    public Long saveTransectFinding() {
        TransectFindingOther transectFindingOther = transectFindingOtherDataService.save(transectFindingOtherView.toOtherFinding());
        transectFindingOtherView.setId(transectFindingOther.getId());

        this.id = transectFindingOther.getId();
        Toast.makeText(getActivity(), getString(R.string.saved), Toast.LENGTH_SHORT).show();
        return id;
    }
}
