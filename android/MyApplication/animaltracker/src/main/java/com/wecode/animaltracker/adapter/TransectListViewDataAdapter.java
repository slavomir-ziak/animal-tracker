package com.wecode.animaltracker.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.model.Transect;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

public class TransectListViewDataAdapter extends ArrayAdapter<Transect> {

    private Activity context;

    private List<Transect> transects;

    public TransectListViewDataAdapter(Activity context, List<Transect> transects) {
        super(context, R.layout.single_item_layout, transects);
        this.context = context;
        this.transects = transects;
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        @SuppressLint("ViewHolder")
        View rowView = inflater.inflate(R.layout.transect_item_layout2, null, true);
        Transect transect = transects.get(position);

        TextView routeName = (TextView) rowView.findViewById(R.id.transectListItemRouteName);
        routeName.setText(transect.getRouteName());

        TextView column = (TextView) rowView.findViewById(R.id.transectListItemColumn);
        column.setText(String.format(Locale.getDefault(), "# %d", transect.getSquare()));

        TextView transectListItemLocalisation = (TextView) rowView.findViewById(R.id.transectListItemLocalisation);
        transectListItemLocalisation.setText(transect.getLocalisation());

        TextView transectId = (TextView) rowView.findViewById(R.id.transectListItemId);
        transectId.setText(String.format(Locale.getDefault(), "ID: %d", transect.getId()));

        TextView startDateTime = (TextView) rowView.findViewById(R.id.transectListItemDate);
        if (transect.getStartDateTime() != null) {
            startDateTime.setText(DateFormat.getDateInstance().format(transect.getStartDateTime()));
        }
        rowView.setId(transect.getId().intValue());
        return rowView;
    }

    @Override
    public long getItemId(int position) {
        return transects.get(position).getId();
    }
}