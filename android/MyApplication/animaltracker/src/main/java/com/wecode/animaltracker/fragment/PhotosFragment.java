package com.wecode.animaltracker.fragment;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.adapter.ImageAdapter;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.service.TransectDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.Permissions;

/**
 * Created by SZIAK on 10/2/2016.
 */

public class PhotosFragment extends android.support.v4.app.Fragment implements Fragment {

    private static final int CODE_START = 20;

    private static final int ADD_PHOTO_PERMISSION_REQUEST = CODE_START + 3;

    private ImageAdapter imageAdapter;

    private GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_photo_tiles, container, false);

        Long entityId = getArguments().getLong("entityId", 0);
        entityId = entityId == 0 ? null : entityId;

        if (entityId == null) {
            return view;
        }

        String entityName = getArguments().getString("entityName");
        Assert.isTrue("entityName missing", entityName != null);

        Long transectId = getArguments().getLong("transectId", 0);
        Assert.isTrue("transectId missing", transectId > 0);

        Transect transect = TransectDataService.getInstance().find(transectId);

        imageAdapter = new ImageAdapter(getActivity(), entityId, entityName, Globals.getTransectPhotosDirectory(transect));

        gridView = (GridView) view.findViewById(R.id.activity_photo_tiles_gridview);
        Assert.assertNotNull("gridView missing ", gridView);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(imageAdapter);

        if (!Permissions.grantedOrRequestPermissions(getActivity(), ADD_PHOTO_PERMISSION_REQUEST,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)) {
            return view;
        }

        return view;
    }

    public void refreshPhotos() {
        if (imageAdapter == null) {
            Log.w(Globals.APP_NAME, "imageAdapter is null");
            return;
        }

        imageAdapter.refreshPhotos();
        gridView.setAdapter(imageAdapter);
    }

    @Override
    public int getNameResourceId() {
        return R.string.photos_fragment_name;
    }

    @Override
    public boolean isChangedByUser() {
        return false;
    }

}
