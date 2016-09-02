package com.wecode.animaltracker.view;

import android.app.Activity;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.util.Assert;

/**
 * Created by sziak on 11/1/2015.
 */
public class HabitatDetailView {

    private Long id;

    private CodeListSpinnerView type;
    private CodeListSpinnerView track;
    private CodeListSpinnerView forestAge;
    private CodeListSpinnerView treeType;
    private CodeListSpinnerView forestType;

    public HabitatDetailView(Activity context, Habitat habitat) {
        this(context);

        Assert.assertNotNull("habitat cannot be null!", habitat);
        bind(habitat);
    }

    public HabitatDetailView(Activity context) {
        type = new CodeListSpinnerView(R.id.habitatTypeValue, "habitatTypes", context);
        track = new CodeListSpinnerView(R.id.habitatTrackValue, "habitatTrackTypes", context);
        forestAge = new CodeListSpinnerView(R.id.habitatForestAgeValue, "habitatForestAgeTypes", context);
        treeType = new CodeListSpinnerView(R.id.habitatTreeTypeValue, "habitatTreeTypes", context);
        forestType = new CodeListSpinnerView(R.id.habitatForestTypeValue, "habitatForestTypes", context);
   }

    private void bind(Habitat habitat) {
        id = habitat.getId();
        type.select(habitat.getType());
        track.select(habitat.getTrack());
        forestAge.select(habitat.getForestAge());
        treeType.select(habitat.getTreeType());
        forestType.select(habitat.getForestType());
    }

    public Habitat toHabitat() {
        Habitat habitat = new Habitat();
        habitat.setId(id);
        habitat.setType(type.getSelectedCodeListValue());
        habitat.setTrack(track.getSelectedCodeListValue());
        habitat.setForestAge(forestAge.getSelectedCodeListValue());
        habitat.setTreeType(treeType.getSelectedCodeListValue());
        habitat.setForestType(forestType.getSelectedCodeListValue());
        return habitat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
