package com.wecode.animaltracker.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.model.Persistable;
import com.wecode.animaltracker.model.findings.TransectFindingFeces;
import com.wecode.animaltracker.model.findings.TransectFindingFootprints;
import com.wecode.animaltracker.model.findings.TransectFindingOther;

import java.util.List;
import java.util.Locale;

/**
 * Created by SZIAK on 9/16/2016.
 */
public class TransectFindingDetailsListAdapter extends BaseAdapter {

    private final Activity context;

    private final List<Persistable> transectFindingDetails;

    public TransectFindingDetailsListAdapter(Activity context, List<Persistable> transectFindingDetails) {
        this.context = context;
        this.transectFindingDetails = transectFindingDetails;
    }

    @Override
    public int getCount() {
        return transectFindingDetails.size();
    }

    @Override
    public Object getItem(int i) {
        return transectFindingDetails.get(i);
    }

    @Override
    public long getItemId(int i) {
        return transectFindingDetails.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Persistable detail = transectFindingDetails.get(position);

        View rowView = null;
        if (view != null) {
            rowView = view;
        } else {
            int viewId = getViewId(detail);
            rowView = context.getLayoutInflater().inflate(viewId, parent, false);
        }

        fillRowView(rowView, detail);

        return rowView;
    }

    private void fillRowView(View rowView, Persistable detail) {
        if (detail instanceof TransectFindingFootprints) {
            fillRowView(rowView, ((TransectFindingFootprints) detail));
        } else if (detail instanceof TransectFindingFeces) {
            fillRowView(rowView, ((TransectFindingFeces) detail));
        } else if (detail instanceof TransectFindingOther) {
            fillRowView(rowView, ((TransectFindingOther) detail));
        } else {
            throw new RuntimeException("cannot handle " + detail);
        }
    }

    private void fillRowView(View rowView, TransectFindingFootprints detail) {
        TextView numOfAnimals = (TextView) rowView.findViewById(R.id.transectFindingFootprintsItemNumOfAnimals);
        TextView frontSize = (TextView) rowView.findViewById(R.id.transectFindingFootprintsItemFrontSize);
        TextView backSize = (TextView) rowView.findViewById(R.id.transectFindingFootprintsItemBackSize);

        numOfAnimals.setText(detail.getNumberOfAnimalsValue());
        frontSize.setText(String.format(Locale.US, "%.1f x %.1f", detail.getFrontLength(), detail.getFrontWidht()));
        backSize.setText(String.format(Locale.US, "%.1f x %.1f", detail.getBackLength(), detail.getBackWidht()));
    }

    private void fillRowView(View rowView, TransectFindingFeces detail) {
        TextView pray = (TextView) rowView.findViewById(R.id.transectFindingFecesItemPrey);
        TextView state = (TextView) rowView.findViewById(R.id.transectFindingFecesItemState);

        pray.setText(detail.getFecesPrey());
        state.setText(detail.getFecesState());
    }

    private void fillRowView(View rowView, TransectFindingOther detail) {
        TextView evidence = (TextView) rowView.findViewById(R.id.transectFindingOtherItemEvidence);
        TextView observations = (TextView) rowView.findViewById(R.id.transectFindingOtherItemObservations);

        evidence.setText(detail.getEvidence());
        observations.setText(detail.getObservations());
    }

    private int getViewId(Persistable detail) {
        int viewId;
        if (detail instanceof TransectFindingFootprints) {
            viewId = R.layout.activity_transect_finding_footprint_item;
        } else if (detail instanceof TransectFindingFeces) {
            viewId = R.layout.activity_transect_finding_feces_item;
        } else if (detail instanceof TransectFindingOther) {
            viewId = R.layout.activity_transect_finding_other_item;
        } else {
            throw new RuntimeException("cannot handle " + detail);
        } return viewId;
    }


}
