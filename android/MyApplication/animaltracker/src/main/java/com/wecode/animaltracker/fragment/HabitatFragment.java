package com.wecode.animaltracker.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.model.Persistable;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.service.HabitatDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.view.HabitatDetailView;

/**
 * Created by SZIAK on 10/2/2016.
 */

public class HabitatFragment extends Fragment implements IFragment {

    private HabitatDetailView habitatDetailView;

    private HabitatDataService habitatService = HabitatDataService.getInstance();
    private Long habitatId;
    private Action action;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_habitat_detail, container, false);

        view.findViewById(R.id.toolbar).setVisibility(View.GONE);

        habitatId = getArguments().getLong("habitatId", 0);
        habitatId = habitatId == 0 ? null : habitatId;

        action = Action.fromString(getArguments().getString("action"));
        if (action != Action.NEW ) {
            Assert.assertNotNull("habitatId musi byt zadane", habitatId);
        }

        if (habitatId != null) {
            habitatDetailView = new HabitatDetailView(getActivity(), view, habitatService.find(habitatId));
        } else {
            habitatDetailView = new HabitatDetailView(getActivity(), view);
        }

        // entityName = Photo.EntityName.HABITAT;
        return view;
    }

    public Habitat saveHabitat(){
        Habitat t = habitatDetailView.toHabitat();
        return habitatService.save(t);
    }


    @Override
    public String getName() {
        return "Habitat";
    }
}
