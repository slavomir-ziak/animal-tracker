package com.wecode.animaltracker.activity.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.model.Transect;

import java.text.DateFormat;
import java.util.List;

public class TransectListViewDataAdapter extends ArrayAdapter<Transect> {

    private Activity context;
    private List<Transect> list;

    public TransectListViewDataAdapter(Activity context, List<Transect> list) {
        super(context, R.layout.single_item_layout, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        System.out.println("position: " + position);

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.transect_item_layout, null, true);
        Transect transect = list.get(position);
        DateFormat dateTimeInstance = DateFormat.getDateTimeInstance();

        TextView transectId = (TextView) rowView.findViewById(R.id.transectId);
        transectId.setText(transect.getId().toString());

        TextView startDateTime = (TextView) rowView.findViewById(R.id.transectListItemStartDateTime);
        if (transect.getStartDateTime() != null) {
            startDateTime.setText(dateTimeInstance.format(transect.getStartDateTime()));
        }

        TextView endDateTime = (TextView) rowView.findViewById(R.id.transectListItemEndDateTime);
        if (transect.getEndDateTime() != null) {
            endDateTime.setText(dateTimeInstance.format(transect.getEndDateTime()));
        }

        TextView routeName = (TextView) rowView.findViewById(R.id.transectListItemRouteName);
        routeName.setText(transect.getRouteName());

        TextView column = (TextView) rowView.findViewById(R.id.transectListItemColumn);
        column.setText(transect.getColumn().toString());

        System.out.println("rowView: " + rowView);

        return rowView;
    }
}