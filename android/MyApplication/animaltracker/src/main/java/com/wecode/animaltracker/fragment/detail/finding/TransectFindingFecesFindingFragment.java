package com.wecode.animaltracker.fragment.detail.finding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.fragment.Fragment;
import com.wecode.animaltracker.model.findings.TransectFindingFeces;
import com.wecode.animaltracker.service.TransectFindingFecesDataService;
import com.wecode.animaltracker.view.findings.TransectFindingFecesView;

/**
 * Created by slavo on 1/22/2017.
 */

public class TransectFindingFecesFindingFragment extends CommonFindingFragment implements Fragment {

    private TransectFindingFecesDataService transectFindingFecesDataService = TransectFindingFecesDataService.getInstance();

    private TransectFindingFecesView transectFindingFecesView;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_transect_finding_feces, container, false);

        extractParams(getArguments());

        if (id != null) {
            TransectFindingFeces transectFinding = transectFindingFecesDataService.find(id);
            transectFindingFecesView = new TransectFindingFecesView(getActivity(), view, transectFinding);
        } else {
            transectFindingFecesView = new TransectFindingFecesView(getActivity(), view, transectFindingSiteId);
        }

        return view;
    }


    @Override
    public int getNameResourceId() {
        return R.string.feces_finding_detail;
    }

    @Override
    public boolean isChangedByUser() {
        return transectFindingFecesView.isChanged();
    }

    @Override
    public String getName() {
        return null;
    }

    public Long saveTransectFinding() {
        TransectFindingFeces transectFindingFeces = transectFindingFecesDataService.save(transectFindingFecesView.toFecesFinding());
        transectFindingFecesView.setId(transectFindingFeces.getId());

        this.id = transectFindingFeces.getId();
        Toast.makeText(getActivity(), getString(R.string.saved), Toast.LENGTH_SHORT).show();
        return id;
    }

}
