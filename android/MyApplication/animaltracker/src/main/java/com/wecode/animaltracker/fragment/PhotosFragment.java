package com.wecode.animaltracker.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.adapter.ImageAdapter;
import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.service.HabitatDataService;
import com.wecode.animaltracker.service.PhotosDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.view.HabitatDetailView;

import java.io.File;
import java.util.List;

/**
 * Created by SZIAK on 10/2/2016.
 */

public class PhotosFragment extends Fragment implements IFragment {

    private PhotosDataService photosDataService = PhotosDataService.getInstance();

    private File picturesDirectory = Globals.getPhotosStorageDir();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_photo_tiles, container, false);

        Long entityId = getArguments().getLong("entityId");
        entityId = entityId == 0 ? null : entityId;

        if (entityId == null) {
            return view;
        }

        String entityName = getArguments().getString("entityName");

        List<Photo> photos = photosDataService.findByEntityIdAndName(entityId, entityName);

        for (int i = 0; i < photos.size(); i++) {
            Log.i(Globals.APP_NAME, "photo["+i+"]: " + photos.get(i).toString());
        }

        GridView gridView = (GridView) view.findViewById(R.id.activity_photo_tiles_gridview);
        Assert.assertNotNull("gridView missing ", gridView);

        gridView.setAdapter(new ImageAdapter(getActivity(), photos, picturesDirectory));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id)
            {
                //showPhoto(Uri);
            }
        });

        return view;
    }

    @Override
    public String getName() {
        return "Photos";
    }
}
