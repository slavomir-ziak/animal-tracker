package com.wecode.animaltracker.activity.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.model.TransectFinding;

import java.util.List;

public class TransectFindingListViewDataAdapter extends ArrayAdapter<TransectFinding> {

    private Activity context;
    private List<TransectFinding> list;

    public TransectFindingListViewDataAdapter(Activity context, List<TransectFinding> list) {
        super(context, R.layout.single_item_layout, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.transect_finding_item_layout, null, true);

        TransectFinding transectFinding = list.get(position);

        TextView idView = (TextView) rowView.findViewById(R.id.transectFindingListItemID);
        idView.setText(transectFinding.getId().toString());

        TextView habitatView = (TextView) rowView.findViewById(R.id.transectFindingListItemHabitat);
        habitatView.setText("Forest(TODO)");

        TextView speciesView = (TextView) rowView.findViewById(R.id.transectFindingListItemSpecies);
        speciesView.setText(transectFinding.getSpecies());

        TextView typeView = (TextView) rowView.findViewById(R.id.transectFindingListItemType);
        typeView.setText(transectFinding.getType());

        return rowView;
    }
}