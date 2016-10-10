package com.wecode.animaltracker.activity.list;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.adapter.ImageAdapter;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.service.PhotosDataService;
import com.wecode.animaltracker.service.TransectDataService;
import com.wecode.animaltracker.util.Assert;

import java.io.File;
import java.util.List;

public class PhotosList extends AppCompatActivity {

    private PhotosDataService photosDataService = PhotosDataService.getInstance();

    private File picturesDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_tiles);

        Long entityId = getIntent().getLongExtra("entityId", 0);
        String entityName = getIntent().getStringExtra("entityName");
        Long transectId = getIntent().getLongExtra("transectId", 0);

        Assert.isTrue("transectId missing", transectId > 0);
        Assert.isTrue("entityId missing", entityId > 0);
        Assert.isTrue("entityName missing", entityName != null);

        List<Photo> photos = photosDataService.findByEntityIdAndName(entityId, entityName);

        for (int i = 0; i < photos.size(); i++) {
            Log.i(Globals.APP_NAME, "photo["+i+"]: " + photos.get(i).toString());
        }

        GridView gridView = (GridView) findViewById(R.id.activity_photo_tiles_gridview);
        Assert.assertNotNull("gridView missing ", gridView);

        Transect transect = TransectDataService.getInstance().find(transectId);
        picturesDirectory = Globals.getTransectPhotosDirectory(transect);

        ImageAdapter adapter = new ImageAdapter(this, entityId, entityName, picturesDirectory);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(adapter);

    }

}
