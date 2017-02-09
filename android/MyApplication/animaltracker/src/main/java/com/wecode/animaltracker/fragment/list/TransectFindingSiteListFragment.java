package com.wecode.animaltracker.fragment.list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.detail.TransectDetailActivity;
import com.wecode.animaltracker.activity.detail.TransectFindingSiteDetailActivity;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.adapter.TransectFindingListViewDataAdapter;
import com.wecode.animaltracker.fragment.Fragment;
import com.wecode.animaltracker.model.TransectFindingSite;
import com.wecode.animaltracker.service.TransectFindingSiteDataService;

import java.util.List;

/**
 * Created by SZIAK on 10/2/2016.
 */

public class TransectFindingSiteListFragment extends android.support.v4.app.Fragment implements Fragment {

    private static final int DISPLAY_TRANSECT_FINDING_DETAIL = 100;

    private static TransectFindingSiteDataService transectFindingSiteDataService = TransectFindingSiteDataService.getInstance();

    private Long transectId;

    private Action action;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_transect_finding_sites_list, container, false);

        transectId = getArguments().getLong("transectId", 0);
        transectId = transectId == 0 ? null : transectId;

        if (transectId == null) {
            return view;
        }

        action = Action.fromString(getArguments().getString("action"));

        refreshTransectFindingSites(view);
        return view;
    }

    private void refreshTransectFindingSites(View view) {
        final List<TransectFindingSite> list = transectFindingSiteDataService.findByTransectId(transectId);

        TransectFindingListViewDataAdapter adapter = new TransectFindingListViewDataAdapter(getActivity(), list);
        ListView itemsListView = (ListView) view.findViewById(R.id.transectFindingsList);

        itemsListView.setAdapter(adapter);
        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TextView transectFindingId = (TextView) view.findViewById(R.id.transectFindingListItemID);

                Intent intent = new Intent(TransectFindingSiteListFragment.this.getActivity(), TransectFindingSiteDetailActivity.class);
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

        refreshTransectFindingSites(view);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(getActivity(), R.string.detail_display_problem, Toast.LENGTH_SHORT).show();
            return;
        }

    }


    @Override
    public int getNameResourceId() {
        return R.string.finding_sites_fragment_name;
    }

    @Override
    public boolean isChangedByUser() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }


    public void refreshFindings() {
        refreshTransectFindingSites(view);
    }

    public Long getTransectId() {
        return transectId;
    }

    public void setTransectId(Long transectId) {
        this.transectId = transectId;
    }
}
