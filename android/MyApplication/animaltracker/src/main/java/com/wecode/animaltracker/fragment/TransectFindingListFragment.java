package com.wecode.animaltracker.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.detail.TransectDetailActivity;
import com.wecode.animaltracker.activity.detail.findings.TransectFindingDetailActivity;
import com.wecode.animaltracker.activity.list.TransectFindingsList;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.adapter.TransectFindingListViewDataAdapter;
import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.model.findings.TransectFinding;
import com.wecode.animaltracker.service.HabitatDataService;
import com.wecode.animaltracker.service.TransectDataService;
import com.wecode.animaltracker.service.TransectFindingDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.view.HabitatDetailView;

import java.util.List;

/**
 * Created by SZIAK on 10/2/2016.
 */

public class TransectFindingListFragment extends Fragment implements IFragment {

    private static final int DISPLAY_TRANSECT_FINDING_DETAIL = 0;

    private static TransectFindingDataService transectFindingDataService = TransectFindingDataService.getInstance();

    private Long transectId;

    private Action action;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_transect_finding_list, container, false);

        transectId = getArguments().getLong("transectId", 0);
        transectId = transectId == 0 ? null : transectId;

        if (transectId == null) {
            return view;
        }

        action = Action.fromString(getArguments().getString("action"));

        refreshTransectFindings(view);
        return view;
    }

    private void refreshTransectFindings(View view) {
        final List<TransectFinding> list = transectFindingDataService.findByTransectId(transectId);

        TransectFindingListViewDataAdapter adapter = new TransectFindingListViewDataAdapter(getActivity(), list);
        ListView itemsListView = (ListView) view.findViewById(R.id.transectFindingsList);

        itemsListView.setAdapter(adapter);
        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TextView transectFindingId = (TextView) view.findViewById(R.id.transectFindingListItemID);

                Intent intent = new Intent(TransectFindingListFragment.this.getActivity(), TransectFindingDetailActivity.class);
                intent.putExtra(Constants.PARENT_ACTIVITY, TransectDetailActivity.class);
                intent.putExtra("id", Long.valueOf(transectFindingId.getText().toString()));
                intent.putExtra("transectId", transectId);
                intent.setAction(action.toString());
                startActivityForResult(intent, DISPLAY_TRANSECT_FINDING_DETAIL);

            }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        refreshTransectFindings(view);
        if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Operation canceled.", Toast.LENGTH_LONG).show();
            return;
        }

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(getActivity(), "Problem with displying tranect finding detail.", Toast.LENGTH_LONG).show();
            return;
        }

        switch(requestCode) {
            case DISPLAY_TRANSECT_FINDING_DETAIL:
                Long id = data.getExtras().getLong("id");
                Toast.makeText(getActivity(), "Transect finding saved, ID = " + id, Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public String getName() {
        return "Finding sites";
    }

    public void refreshFindings() {
        refreshTransectFindings(view);
    }

    public Long getTransectId() {
        return transectId;
    }

    public void setTransectId(Long transectId) {
        this.transectId = transectId;
    }
}
