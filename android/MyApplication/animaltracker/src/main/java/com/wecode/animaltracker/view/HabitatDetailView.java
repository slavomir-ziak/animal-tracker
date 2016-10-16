package com.wecode.animaltracker.view;

import android.app.Activity;
import android.view.View;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.model.findings.TransectFindingFeces;
import com.wecode.animaltracker.service.HabitatDataService;
import com.wecode.animaltracker.service.TransectFindingFecesDataService;
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

    private HabitatDataService service = HabitatDataService.getInstance();

    public HabitatDetailView(Activity context, View view, Habitat habitat) {
        this(context, view);

        Assert.assertNotNull("habitat cannot be null!", habitat);
        bind(habitat);
    }

    public HabitatDetailView(Activity context, View view) {
        type = new CodeListSpinnerView(R.id.habitatTypeValue, "habitatTypes", context, view);
        track = new CodeListSpinnerView(R.id.habitatTrackValue, "habitatTrackTypes", context, view);
        forestAge = new CodeListSpinnerView(R.id.habitatForestAgeValue, "habitatForestAgeTypes", context, view);
        treeType = new CodeListSpinnerView(R.id.habitatTreeTypeValue, "habitatTreeTypes", context, view);
        forestType = new CodeListSpinnerView(R.id.habitatForestTypeValue, "habitatForestTypes", context, view);
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
        Habitat habitat;

        if (id != null) {
            habitat = service.find(id);
        } else {
            habitat = new Habitat();
        }
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
