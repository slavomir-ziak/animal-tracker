package com.wecode.animaltracker.view;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.model.CodeList;
import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.service.HabitatDataService;
import com.wecode.animaltracker.util.Assert;

/**
 * Created by sziak on 11/1/2015.
 */
public class HabitatDetailView implements ChangeableView {

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
    private Activity context;

    public HabitatDetailView(Activity context, View view, Habitat habitat) {
        this(context, view);

        Assert.assertNotNull("habitat cannot be null!", habitat);
        bind(habitat);
    }

    public HabitatDetailView(Activity context, View view) {
        this.context = context;
        type = new CodeListSpinnerView(R.id.habitatTypeValue, CodeList.Name.habitatTypes.name(), context, view, false);
        track = new CodeListSpinnerView(R.id.habitatTrackValue, CodeList.Name.habitatTrackTypes.name(), context, view);
        forestAge = new CodeListSpinnerView(R.id.habitatForestAgeValue, CodeList.Name.habitatForestAgeTypes.name(), context, view);
        treeType = new CodeListSpinnerView(R.id.habitatTreeTypeValue, CodeList.Name.habitatTreeTypes.name(), context, view);
        forestType = new CodeListSpinnerView(R.id.habitatForestTypeValue, CodeList.Name.habitatForestTypes.name(), context, view);

        forestTypeContainer = view.findViewById(R.id.habitatForestTypeContainer);
        forestAgeContainer = view.findViewById(R.id.forestAgeContainer);
        treeTypeContainer = view.findViewById(R.id.habitatTreeTypeContainer);

        type.addOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showHideTreeProperties(type.getSelectedCodeListValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showHideTreeProperties(type.getSelectedCodeListValue());
            }
        });
        initialHash = hashCode();
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
        initialHash = hashCode();
    }

    public boolean isChanged() {
        return initialHash != hashCode();
    }

}
