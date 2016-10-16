package com.wecode.animaltracker.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.sax.RootElement;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.model.findings.TransectFinding;
import com.wecode.animaltracker.model.findings.TransectFindingFootprints;
import com.wecode.animaltracker.service.HabitatDataService;
import com.wecode.animaltracker.service.TransectFindingFecesDataService;
import com.wecode.animaltracker.service.TransectFindingFootprintsDataService;
import com.wecode.animaltracker.service.TransectFindingOtherDataService;

import java.util.List;

public class TransectFindingListViewDataAdapter extends ArrayAdapter<TransectFinding> {

    private Activity context;

    private List<TransectFinding> list;

    private HabitatDataService habitatService = HabitatDataService.getInstance();

    private TransectFindingFootprintsDataService footprintsService = TransectFindingFootprintsDataService.getInstance();

    private TransectFindingFecesDataService fecesService = TransectFindingFecesDataService.getInstance();

    private TransectFindingOtherDataService othersService = TransectFindingOtherDataService.getInstance();

    public TransectFindingListViewDataAdapter(Activity context, List<TransectFinding> list) {
        super(context, R.layout.single_item_layout, list);
        this.context = context;
        this.list = list;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = null;
        if (view != null) {
            rowView = view;
        } else {
            rowView = inflater.inflate(R.layout.transect_finding_item_layout, null, true);
        }

        TransectFinding transectFinding = list.get(position);

        TextView idView = (TextView) rowView.findViewById(R.id.transectFindingListItemID);
        idView.setText(transectFinding.getId().toString());

        if (transectFinding.getHabitatId() != null) {
            initHabitat(rowView, transectFinding);
        }

        initFindingCounts(rowView, transectFinding);

        TextView speciesView = (TextView) rowView.findViewById(R.id.transectFindingListItemSpecies);
        speciesView.setText(transectFinding.getSpecies());

        return rowView;
    }

    private void initFindingCounts(View rowView, TransectFinding transectFinding) {
        long footprintsCount = footprintsService.countByTransectFindingId(transectFinding.getId());
        long fecesCount = fecesService.countByTransectFindingId(transectFinding.getId());
        long othersCount = othersService.countByTransectFindingId(transectFinding.getId());

        TextView transectFindingListItemFootprints = (TextView) rowView.findViewById(R.id.transectFindingListItemFootprints);
        TextView transectFindingListItemFeces = (TextView) rowView.findViewById(R.id.transectFindingListItemFeces);
        TextView transectFindingListItemOther = (TextView) rowView.findViewById(R.id.transectFindingListItemOther);

        transectFindingListItemFootprints.setText(String.format("%d x footprints", footprintsCount));
        transectFindingListItemFeces.setText(String.format("%d x feces", fecesCount));
        transectFindingListItemOther.setText(String.format("%d x other", othersCount));
    }

    private void initHabitat(View rowView, TransectFinding transectFinding) {
        Habitat habitat = habitatService.find(transectFinding.getHabitatId());

        TextView habitatView = (TextView) rowView.findViewById(R.id.transectFindingListItemHabitat);

        String value="";
        value += habitat.getType() != null ? habitat.getType() : "";
        value += (value.length() > 0 && habitat.getTrack() != null) ? ", " : "";
        value += habitat.getTrack() != null ? habitat.getTrack() : "";

        habitatView.setText(value);
    }


}