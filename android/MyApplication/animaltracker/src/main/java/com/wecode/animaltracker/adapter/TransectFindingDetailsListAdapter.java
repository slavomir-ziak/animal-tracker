package com.wecode.animaltracker.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.model.CodeList;
import com.wecode.animaltracker.model.Persistable;
import com.wecode.animaltracker.model.findings.TransectFindingFeces;
import com.wecode.animaltracker.model.findings.TransectFindingFootprints;
import com.wecode.animaltracker.model.findings.TransectFindingOther;
import com.wecode.animaltracker.service.CodeListService;

import java.util.List;

/**
 * Created by SZIAK on 9/16/2016.
 */
public class TransectFindingDetailsListAdapter extends BaseAdapter {

    private final Activity context;

    private final List<Persistable> transectFindingDetails;

    private final CodeListService codeListService = CodeListService.getInstance();

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
/*
        View rowView;
        if (view != null) {
            rowView = view;
        } else {
            int viewId = getViewId(detail);
            rowView = context.getLayoutInflater().inflate(viewId, parent, false);
        }*/

        int viewId = getViewId(detail);
        @SuppressLint("ViewHolder")
        View rowView = context.getLayoutInflater().inflate(viewId, parent, false);
        fillRowView(rowView, detail);

        return rowView;
    }

    private void fillRowView(View rowView, Object detail) {
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

        if (numOfAnimals!=null) numOfAnimals.setText(detail.getNumberOfAnimalsValue());
        if (frontSize!=null) frontSize.setText(detail.getFrontSizeFormatted());
        if (backSize!=null) backSize.setText(detail.getBackSizeFormatted());
    }

    private void fillRowView(View rowView, TransectFindingFeces detail) {
        TextView pray = (TextView) rowView.findViewById(R.id.transectFindingFecesItemPrey);
        TextView state = (TextView) rowView.findViewById(R.id.transectFindingFecesItemState);
        if (pray != null) {
            pray.setText(codeListService.getLocalisedValueByNameAndValue(CodeList.Name.fecesPrey, detail.getPrey()));
        }
        if (state!=null) {
            state.setText(codeListService.getLocalisedValueByNameAndValue(CodeList.Name.fecesState, detail.getState()));
        }
    }

    private void fillRowView(View rowView, TransectFindingOther detail) {
        TextView evidence = (TextView) rowView.findViewById(R.id.transectFindingOtherItemEvidence);
        TextView observations = (TextView) rowView.findViewById(R.id.transectFindingOtherItemObservations);

        if (evidence!=null) {
            evidence.setText(codeListService.getLocalisedValueByNameAndValue(CodeList.Name.findingOtherEvidence, detail.getEvidence()));
        }
        if (observations!=null) {
            observations.setText(codeListService.getLocalisedValueByNameAndValue(CodeList.Name.findingObservation, detail.getObservations()));
        }
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
