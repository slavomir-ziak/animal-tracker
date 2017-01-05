package com.wecode.animaltracker.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.model.TransectFindingSite;
import com.wecode.animaltracker.service.CodeListService;
import com.wecode.animaltracker.service.HabitatDataService;
import com.wecode.animaltracker.service.TransectFindingFecesDataService;
import com.wecode.animaltracker.service.TransectFindingFootprintsDataService;
import com.wecode.animaltracker.service.TransectFindingOtherDataService;

import java.util.List;

public class TransectFindingListViewDataAdapter extends ArrayAdapter<TransectFindingSite> {

    private Activity context;

    private List<TransectFindingSite> list;

    private CodeListService codeListService = CodeListService.getInstance();

    private HabitatDataService habitatService = HabitatDataService.getInstance();

    private TransectFindingFootprintsDataService footprintsService = TransectFindingFootprintsDataService.getInstance();

    private TransectFindingFecesDataService fecesService = TransectFindingFecesDataService.getInstance();

    private TransectFindingOtherDataService othersService = TransectFindingOtherDataService.getInstance();

    public TransectFindingListViewDataAdapter(Activity context, List<TransectFindingSite> list) {
        super(context, R.layout.single_item_layout, list);
        this.context = context;
        this.list = list;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView;
        if (view != null) {
            rowView = view;
        } else {
            rowView = inflater.inflate(R.layout.transect_finding_item_layout, null, true);
        }

        TransectFindingSite transectFindingSite = list.get(position);

        TextView idView = (TextView) rowView.findViewById(R.id.transectFindingListItemID);
        idView.setText(transectFindingSite.getId().toString());

        if (transectFindingSite.getHabitatId() != null) {
            initHabitat(rowView, transectFindingSite);
        }

        initFindingCounts(rowView, transectFindingSite);

        TextView speciesView = (TextView) rowView.findViewById(R.id.transectFindingListItemSpecies);
        speciesView.setText(codeListService.getLocalisedValueByNameAndValue("findingSpeciesTypes", transectFindingSite.getSpecies()));

        return rowView;
    }

    private void initFindingCounts(View rowView, TransectFindingSite transectFindingSite) {
        long footprintsCount = footprintsService.countByTransectFindingId(transectFindingSite.getId());
        long fecesCount = fecesService.countByTransectFindingId(transectFindingSite.getId());
        long othersCount = othersService.countByTransectFindingId(transectFindingSite.getId());

        TextView transectFindingListItemFootprints = (TextView) rowView.findViewById(R.id.transectFindingListItemFootprints);
        TextView transectFindingListItemFeces = (TextView) rowView.findViewById(R.id.transectFindingListItemFeces);
        TextView transectFindingListItemOther = (TextView) rowView.findViewById(R.id.transectFindingListItemOther);

        transectFindingListItemFootprints.setText(String.format("%d x " + context.getString(R.string.footprints), footprintsCount));
        transectFindingListItemFeces.setText(String.format("%d x " + context.getString(R.string.feces), fecesCount));
        transectFindingListItemOther.setText(String.format("%d x " + context.getString(R.string.other), othersCount));
    }

    private void initHabitat(View rowView, TransectFindingSite transectFindingSite) {
        Habitat habitat = habitatService.find(transectFindingSite.getHabitatId());

        TextView habitatView = (TextView) rowView.findViewById(R.id.transectFindingListItemHabitat);

        String value="";
        value += codeListService.getLocalisedValueByNameAndValue("habitatTypes", habitat.getType());
        value += (value.length() > 0 && habitat.getTrack() != null) ? ", " : "";
        value += codeListService.getLocalisedValueByNameAndValue("habitatTrackTypes", habitat.getTrack());

        habitatView.setText(value);
    }


}