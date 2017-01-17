package com.wecode.animaltracker.view;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

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

    private static final String FOREST_TYPE = "Forest";
    private Long id;

    private CodeListSpinnerView type;
    private CodeListSpinnerView track;
    private CodeListSpinnerView forestAge;
    private CodeListSpinnerView treeType;
    private CodeListSpinnerView forestType;

    private HabitatDataService service = HabitatDataService.getInstance();

    private View forestTypeContainer;
    private View forestAgeContainer;
    private View treeTypeContainer;
    private int initialHash;

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

        forestTypeContainer = view.findViewById(R.id.habitatForestTypeContainer);
        forestAgeContainer = view.findViewById(R.id.forestAgeContainer);
        treeTypeContainer = view.findViewById(R.id.habitatTreeTypeContainer);

        type.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showHideTreeProperties(type.getSelectedCodeListValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showHideTreeProperties(type.getSelectedCodeListValue());
            }
        });
   }

    private void bind(Habitat habitat) {
        id = habitat.getId();
        type.select(habitat.getType());
        track.select(habitat.getTrack());
        forestAge.select(habitat.getForestAge());
        treeType.select(habitat.getTreeType());
        forestType.select(habitat.getForestType());

        showHideTreeProperties(habitat.getType());

        initialHash = hashCode();
    }

    private void showHideTreeProperties(String type) {
        if (FOREST_TYPE.equals(type)) {
            forestTypeContainer.setVisibility(View.VISIBLE);
            forestAgeContainer.setVisibility(View.VISIBLE);
            treeTypeContainer.setVisibility(View.VISIBLE);
        } else {
            forestTypeContainer.setVisibility(View.INVISIBLE);
            forestAgeContainer.setVisibility(View.INVISIBLE);
            treeTypeContainer.setVisibility(View.INVISIBLE);
        }
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
        initialHash = hashCode();
        return habitat;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.getSelectedCodeListValue().hashCode() : 0);
        result = 31 * result + (track != null ? track.getSelectedCodeListValue().hashCode() : 0);
        result = 31 * result + (forestAge != null ? forestAge.getSelectedCodeListValue().hashCode() : 0);
        result = 31 * result + (treeType != null ? treeType.getSelectedCodeListValue().hashCode() : 0);
        result = 31 * result + (forestType != null ? forestType.getSelectedCodeListValue().hashCode() : 0);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isChanged() {
        return initialHash != hashCode();
    }
}
