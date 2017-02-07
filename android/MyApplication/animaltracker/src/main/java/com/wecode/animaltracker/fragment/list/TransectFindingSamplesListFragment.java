package com.wecode.animaltracker.fragment.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.adapter.TransectFindingSamplesListAdapter;
import com.wecode.animaltracker.fragment.Fragment;
import com.wecode.animaltracker.model.EntityName;
import com.wecode.animaltracker.model.Sample;
import com.wecode.animaltracker.service.SampleDataService;

import java.util.List;

/**
 * Created by SZIAK on 7/31/2016.
 */
public class TransectFindingSamplesListFragment extends android.support.v4.app.Fragment implements Fragment {


    private Long transectFindingId;

    private EntityName entityName;

    private TransectFindingSamplesListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_transect_finding_sample_list, container, false);

        if (transectFindingId == null) {
            transectFindingId = getArguments().getLong("transectFindingId", 0);
            transectFindingId = transectFindingId == 0 ? null : transectFindingId;
            entityName = EntityName.valueOf(getArguments().getString("entityName"));
        }

        adapter = new TransectFindingSamplesListAdapter(getActivity(), view, transectFindingId, entityName, R.id.transectFindingDetailSamplesListView);
        return view;
    }

    @Override
    public int getNameResourceId() {
        return R.string.samples;
    }

    @Override
    public boolean isChangedByUser() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    public void refreshSamples() {
        adapter.refreshSamples();
    }
}
