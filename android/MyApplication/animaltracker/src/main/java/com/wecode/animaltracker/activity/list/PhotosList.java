package com.wecode.animaltracker.activity.list;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.common.PhotoEnabledCommonActivity;
import com.wecode.animaltracker.adapter.ImageAdapter;
import com.wecode.animaltracker.model.EntityName;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.service.TransectDataService;
import com.wecode.animaltracker.util.Assert;

import java.io.File;

public class PhotosList extends PhotoEnabledCommonActivity {

    private Long transectId;

    private ImageAdapter imageAdapter;

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_tiles);

        Long entityId = getIntent().getLongExtra("entityId", 0);
        String entityName = getIntent().getStringExtra("entityName");
        transectId = getIntent().getLongExtra("transectId", 0);

        Assert.isTrue("transectId missing", transectId > 0);
        Assert.isTrue("entityId missing", entityId > 0);
        Assert.isTrue("entityName missing", entityName != null);


        gridView = (GridView) findViewById(R.id.activity_photo_tiles_gridview);
        Assert.assertNotNull("gridView missing ", gridView);

        Transect transect = TransectDataService.getInstance().find(transectId);
        File picturesDirectory = Globals.getTransectPhotosDirectory(transect);

        imageAdapter = new ImageAdapter(this, entityId, entityName, picturesDirectory);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(imageAdapter);

        this.entityName = EntityName.valueOf(entityName);
        this.id = entityId;
    }

    @Override
    protected Long getCurrentTransectId() {
        return transectId;
    }

    @Override
    protected File getPhotoDirectory() {
        Long currentTransectId = getCurrentTransectId();
        Transect transect = TransectDataService.getInstance().find(currentTransectId);
        return Globals.getTransectPhotosDirectory(transect);
    }

    @Override
    protected void refreshPhotos() {
        imageAdapter.refreshPhotos();
        gridView.setAdapter(imageAdapter);
    }

    public void addPhoto(View view) {
        addPhoto();
    }
}
