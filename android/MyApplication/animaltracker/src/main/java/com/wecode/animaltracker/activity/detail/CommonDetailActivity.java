package com.wecode.animaltracker.activity.detail;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.list.PhotosList;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.model.Sample;
import com.wecode.animaltracker.service.PhotosDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.Permissions;

import java.io.File;
import java.util.UUID;

/**
 * Created by sziak on 11/2/2015.
 */
public class CommonDetailActivity extends android.support.v7.app.AppCompatActivity {

    private static final int CODE_START = 10;

    private static final int ADD_PHOTO_REQUEST = CODE_START + 2;

    private static final int ADD_PHOTO_PERMISSION_REQUEST = CODE_START + 3;

    public static String TAG = Globals.APP_NAME;

    protected Action action;
    protected Class parentActivityClass;
    protected Long id;
    protected Photo.EntityName entityName;

    private File outputPhotoFile;

    protected void extractParams(Intent intent) {

        action = Action.fromString(intent.getAction());
        id = (Long) intent.getExtras().get("id");

        if (action != Action.NEW ) {
            Assert.assertNotNull("id musi byt zadane", id);
        }

        parentActivityClass = (Class) getIntent().getExtras().get(Constants.PARENT_ACTIVITY);
        Assert.assertNotNull("parentActivityClass musi byt zadane", parentActivityClass);

    }


    @Override
    public Intent getSupportParentActivityIntent() {
        if (parentActivityClass == null) {
            return super.getSupportParentActivityIntent();
        } else {
            return getParentActivityIntentImpl();
        }
    }

    @Override
    public Intent getParentActivityIntent() {
        if (parentActivityClass == null) {
            return super.getParentActivityIntent();
        } else {
            return getParentActivityIntentImpl();
        }
    }

    private Intent getParentActivityIntentImpl() {
        Intent i = new Intent(this, parentActivityClass);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return i;
    }

    public void showPhotos() {
        Intent intent = new Intent(this, PhotosList.class);
        intent.putExtra("entityId", id);
        intent.putExtra("entityName", entityName.toString());
        startActivity(intent);
    }

    public void addPhoto() {

        if (!Permissions.grantedOrRequestPermissions(this, ADD_PHOTO_PERMISSION_REQUEST,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)) {
            return;
        }

        outputPhotoFile = new File(Globals.getPhotosStorageDir(), "F" + id + "-photo-" + UUID.randomUUID() + ".jpg");

        Log.d(Globals.APP_NAME, "Picture will be saved:" + outputPhotoFile);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputPhotoFile));

        startActivityForResult(cameraIntent, ADD_PHOTO_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == ADD_PHOTO_PERMISSION_REQUEST) {
            if (Permissions.grantResults(grantResults)) {
                addPhoto();
            } else {
                Log.w(Globals.APP_NAME, "ADD_PHOTO_PERMISSION_REQUEST NOT granted");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Operation canceled.", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {

            case ADD_PHOTO_REQUEST:
                Log.d(Globals.APP_NAME, "Pic saved, intent: " + data);
                Photo photo = new Photo(entityName, id, outputPhotoFile.getName());
                PhotosDataService.getInstance().save(photo);
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.transect_finding_action_add_photo) {
            addPhoto();
            return true;
        }
        if (id == R.id.transect_finding_action_photos) {
            showPhotos();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem addPhotoMenuItem = menu.findItem(R.id.transect_finding_action_add_photo);
        MenuItem photosMenuItem = menu.findItem(R.id.transect_finding_action_photos);

        if (id == null) {
            if (addPhotoMenuItem != null) addPhotoMenuItem.setEnabled(false);
            if (photosMenuItem != null) photosMenuItem.setEnabled(false);
        } else {
            if (addPhotoMenuItem != null) addPhotoMenuItem.setEnabled(true);
            if (photosMenuItem != null) photosMenuItem.setEnabled(true);
        }
        return true;
    }
}
