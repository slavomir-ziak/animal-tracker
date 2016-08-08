package com.wecode.animaltracker.activity.list;

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
import com.wecode.animaltracker.service.PhotosDataService;
import com.wecode.animaltracker.util.Assert;

import java.io.File;
import java.util.List;

public class PhotosList extends AppCompatActivity {

    private PhotosDataService photosDataService = PhotosDataService.getInstance();

    private File picturesDirectory = Globals.getPhotosStorageDir();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_tiles);

        Long transectFindingId = getIntent().getLongExtra("transectFindingId", 0);
        Assert.isTrue("transectFindingId missing ", transectFindingId > 0);

        List<Photo> photos = photosDataService.findByTransectFindingId(transectFindingId);

        for (int i = 0; i < photos.size(); i++) {
            Log.i("photo["+i+"]: ", photos.get(i).toString());
        }

        GridView gridView = (GridView) findViewById(R.id.activity_photo_tiles_gridview);
        Assert.assertNotNull("gridView missing ", gridView);

        gridView.setAdapter(new ImageAdapter(this, photos, picturesDirectory));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id)
            {
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_transect_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
