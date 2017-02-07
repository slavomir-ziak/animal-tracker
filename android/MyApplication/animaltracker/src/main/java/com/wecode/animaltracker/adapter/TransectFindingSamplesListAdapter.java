package com.wecode.animaltracker.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.model.EntityName;
import com.wecode.animaltracker.model.Sample;
import com.wecode.animaltracker.service.SampleDataService;

import java.util.List;

/**
 * Created by SZIAK on 7/31/2016.
 */
public class TransectFindingSamplesListAdapter extends BaseAdapter {

    private SampleDataService sampleDataService = SampleDataService.getInstance();

    private Activity activity;
    private View view;
    private final Long transectFindingId;
    private final EntityName entityName;
    private final int transectFindingDetailSamplesListView;

    private List<Sample> samples;

    public TransectFindingSamplesListAdapter(Activity activity, View view, Long transectFindingId, EntityName entityName, int transectFindingDetailSamplesListView) {
        this.activity = activity;
        this.view = view;
        this.transectFindingId = transectFindingId;
        this.entityName = entityName;
        this.transectFindingDetailSamplesListView = transectFindingDetailSamplesListView;

        refreshSamples();
    }

    public void refreshSamples() {
        samples = sampleDataService.findByTransectFindingId(transectFindingId, entityName);
        ListView listView = (ListView) view.findViewById(transectFindingDetailSamplesListView);
        listView.setAdapter(this);
    }

    @Override
    public int getCount() {
        return samples.size();
    }

    @Override
    public Object getItem(int i) {
        return samples.get(i);
    }

    @Override
    public long getItemId(int i) {
        return samples.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = activity.getLayoutInflater();

        if (view == null) {
            view = inflater.inflate(R.layout.transect_finding_sample_layout, null, true);
        }

        Sample transectFindingSample = samples.get(position);

        TextView numberView = (TextView) view.findViewById(R.id.transectFindingSampleNumber);
        numberView.setText(transectFindingSample.getSampleNumber());

        return view;
    }

}
