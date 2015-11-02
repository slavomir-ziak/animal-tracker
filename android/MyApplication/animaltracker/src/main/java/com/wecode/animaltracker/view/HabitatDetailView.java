package com.wecode.animaltracker.view;

import android.app.Activity;
import android.widget.Spinner;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.SpinnersHelper;
import com.wecode.animaltracker.model.Habitat;

/**
 * Created by sziak on 11/1/2015.
 */
public class HabitatDetailView {

    private Long id;
    private Spinner type;
    private Spinner track;
    private Spinner forestAge;
    private Spinner treeType;
    private Spinner forestType;

    public HabitatDetailView(Activity context, Habitat habitat) {

        type = (Spinner) context.findViewById(R.id.habitatTypeValue);
        track = (Spinner) context.findViewById(R.id.habitatTrackValue);
        forestAge = (Spinner) context.findViewById(R.id.habitatForestAgeValue);
        treeType = (Spinner) context.findViewById(R.id.habitatTreeTypeValue);
        forestType = (Spinner) context.findViewById(R.id.habitatForestTypeValue);

        if (habitat != null) {
            bind(habitat);
        }
    }

    private void bind(Habitat habitat) {

        id = habitat.getId();
        SpinnersHelper.setSelected(type, habitat.getType());
        SpinnersHelper.setSelected(track, habitat.getTrack());
        SpinnersHelper.setSelected(forestAge, habitat.getForestAge());
        SpinnersHelper.setSelected(treeType, habitat.getTreeType());
        SpinnersHelper.setSelected(forestType, habitat.getForestType());

    }


}
