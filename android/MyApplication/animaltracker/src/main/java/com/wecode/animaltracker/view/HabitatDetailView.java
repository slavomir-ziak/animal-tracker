package com.wecode.animaltracker.view;

import android.app.Activity;
import android.widget.Spinner;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.SpinnersHelper;
import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.util.Assert;

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
        this(context);

        Assert.assertNotNull("habitat cannot be null!", habitat);
        bind(habitat);
    }

    public HabitatDetailView(Activity context) {
        type = (Spinner) context.findViewById(R.id.habitatTypeValue);
        track = (Spinner) context.findViewById(R.id.habitatTrackValue);
        forestAge = (Spinner) context.findViewById(R.id.habitatForestAgeValue);
        treeType = (Spinner) context.findViewById(R.id.habitatTreeTypeValue);
        forestType = (Spinner) context.findViewById(R.id.habitatForestTypeValue);
    }

    private void bind(Habitat habitat) {
        id = habitat.getId();
        SpinnersHelper.setSelected(type, habitat.getType());
        SpinnersHelper.setSelected(track, habitat.getTrack());
        SpinnersHelper.setSelected(forestAge, habitat.getForestAge());
        SpinnersHelper.setSelected(treeType, habitat.getTreeType());
        SpinnersHelper.setSelected(forestType, habitat.getForestType());
    }

    public Habitat toHabitat() {
        Habitat habitat = new Habitat();
        habitat.setId(id);
        habitat.setType(type.getSelectedItem().toString());
        habitat.setTrack(track.getSelectedItem().toString());
        habitat.setForestAge(forestAge.getSelectedItem().toString());
        habitat.setTreeType(treeType.getSelectedItem().toString());
        habitat.setForestType(forestType.getSelectedItem().toString());
        return habitat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
