package com.wecode.animaltracker.activity.common;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.list.PhotosList;
import com.wecode.animaltracker.model.EntityName;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.service.PhotoDataService;
import com.wecode.animaltracker.util.Permissions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SZIAK on 10/10/2016.
 */

public abstract class PhotoEnabledCommonActivity extends CommonDetailActivity {

    private static final int CODE_START = 10;

    private static final int ADD_PHOTO_REQUEST = CODE_START + 2;

    private static final int ADD_PHOTO_PERMISSION_REQUEST = CODE_START + 3;

    protected EntityName entityName;

    private File outputPhotoFile;

    public void showPhotos() {
        Intent intent = new Intent(this, PhotosList.class);
        intent.putExtra("entityId", id);
        intent.putExtra("entityName", entityName.toString());
        intent.putExtra("transectId", getCurrentTransectId());
        startActivity(intent);
    }

    protected abstract Long getCurrentTransectId();

    @SuppressLint("SimpleDateFormat")
    public void addPhoto() {

        if (!Permissions.grantedOrRequestPermissions(this, ADD_PHOTO_PERMISSION_REQUEST,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)) {
            return;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        outputPhotoFile = new File(getPhotoDirectory(), simpleDateFormat.format(new Date()) + ".jpeg");

        Log.d(Globals.APP_NAME, "Picture will be saved:" + outputPhotoFile);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Uri uri = FileProvider.getUriForFile(
                this,
                "com.wecode.animaltracker.fileprovider", outputPhotoFile);

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        startActivityForResult(cameraIntent, ADD_PHOTO_REQUEST);
    }

    protected abstract File getPhotoDirectory();

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
            //Toast.makeText(this, R.string.operation_canceled, Toast.LENGTH_SHORT).show();
            return;
        }

        switch (requestCode) {

            case ADD_PHOTO_REQUEST:
                Log.d(Globals.APP_NAME, "Pic saved, intent: " + data);
                Photo photo = new Photo(entityName, id, outputPhotoFile.getName());
                PhotoDataService.getInstance().save(photo);
                refreshPhotos();
                break;

        }
    }

    protected abstract void refreshPhotos();

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
