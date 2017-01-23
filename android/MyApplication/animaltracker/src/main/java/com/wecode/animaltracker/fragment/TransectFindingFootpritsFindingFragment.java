package com.wecode.animaltracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.model.findings.TransectFindingFootprints;
import com.wecode.animaltracker.service.TransectFindingFootprintsDataService;
import com.wecode.animaltracker.view.findings.TransectFindingFootprintsView;

/**
 * Created by slavo on 1/22/2017.
 */

public class TransectFindingFootpritsFindingFragment extends CommonFindingFragment implements Fragment {

    private TransectFindingFootprintsDataService transectFindingFootprintsDataService = TransectFindingFootprintsDataService.getInstance();

    private TransectFindingFootprintsView transectFindingFootprintsView;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_transect_finding_footprints, container, false);

        extractParams(getArguments());

        if (id != null) {
            TransectFindingFootprints transectFinding = transectFindingFootprintsDataService.find(id);
            transectFindingFootprintsView = new TransectFindingFootprintsView(getActivity(), view, transectFinding);
        } else {
            transectFindingFootprintsView = new TransectFindingFootprintsView(getActivity(), view, transectFindingSiteId);
        }

        // entityName = Photo.EntityName.TRANECT_FINDING_FOOTPRINT;
        return view;
    }


    @Override
    public int getNameResourceId() {
        return R.string.footprints_finding_detail;
    }

    @Override
    public boolean isChangedByUser() {
        return transectFindingFootprintsView.isChanged();
    }

    public void saveTransectFinding() {
        TransectFindingFootprints transectFindingFootprints = transectFindingFootprintsDataService.save(transectFindingFootprintsView.toFootprintsFinding());
        transectFindingFootprintsView.setId(transectFindingFootprints.getId());

        this.id = transectFindingFootprints.getId();
        Toast.makeText(getActivity(), getString(R.string.saved), Toast.LENGTH_SHORT).show();
    }
}
