package com.wecode.animaltracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.list.TransectFindingSamplesList;
import com.wecode.animaltracker.model.Sample;

import java.util.List;

/**
 * Created by SZIAK on 7/31/2016.
 */
public class TransectFindingSamplesListAdapter extends BaseAdapter {

    private TransectFindingSamplesList context;
    private List<Sample> samples;

    public TransectFindingSamplesListAdapter(TransectFindingSamplesList context, List<Sample> samples) {
        this.context = context;
        this.samples = samples;
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

        LayoutInflater inflater = context.getLayoutInflater();

        if (view == null) {
            view = inflater.inflate(R.layout.transect_finding_sample_layout, null, true);
        }

        Sample transectFindingSample = samples.get(position);

        TextView numberView = (TextView) view.findViewById(R.id.transectFindingSampleNumber);
        numberView.setText(transectFindingSample.getSampleNumber());

        return view;
    }
}
