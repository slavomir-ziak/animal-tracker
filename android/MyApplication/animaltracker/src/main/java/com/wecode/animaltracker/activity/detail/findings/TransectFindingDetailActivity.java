package com.wecode.animaltracker.activity.detail.findings;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.wecode.animaltracker.*;
import com.wecode.animaltracker.activity.TransectFindingAddSampleActivity;
import com.wecode.animaltracker.activity.detail.CommonDetailActivity;
import com.wecode.animaltracker.activity.detail.EditLocationActivity;
import com.wecode.animaltracker.activity.detail.HabitatDetailActivity;
import com.wecode.animaltracker.activity.list.PhotosList;
import com.wecode.animaltracker.activity.list.TransectFindingSamplesList;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.adapter.TransectFindingDetailsListAdapter;
import com.wecode.animaltracker.model.Persistable;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.model.Sample;
import com.wecode.animaltracker.model.findings.TransectFinding;
import com.wecode.animaltracker.service.PhotosDataService;
import com.wecode.animaltracker.service.SampleDataService;
import com.wecode.animaltracker.service.TransectFindingDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.LocationUtil;
import com.wecode.animaltracker.util.Permissions;
import com.wecode.animaltracker.view.TransectFindingDetailView;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class TransectFindingDetailActivity extends CommonDetailActivity implements LocationListener {

    private static final int SET_HABITAT_REQUEST = 0;
    private static final int ADD_SAMPLE_REQUEST = 1;
    private static final int ADD_PHOTO_REQUEST = 2;

    private static final int ADD_PHOTO_PERMISSION_REQUEST = 3;
    private static final int ACCESS_FINE_LOCATION_REQUEST = 4;

    private static final int EDIT_LOCATION_REQUEST = 5;

    private TransectFindingDataService transectFindingDataService = TransectFindingDataService.getInstance();

    private PhotosDataService photosDataService = PhotosDataService.getInstance();

    private SampleDataService sampleDataService = SampleDataService.getInstance();

    private TransectFindingDetailView transectFindingView;

    private Long transectId;

    private File outputPhotoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transect_finding_detail);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LocationUtil.initLocationManager(this, ACCESS_FINE_LOCATION_REQUEST);

        extractParams(getIntent());

        transectId = getIntent().getExtras().getLong("transectId");

        if (id != null) {
            TransectFinding transectFinding = transectFindingDataService.find(id);
            transectFindingView = new TransectFindingDetailView(this, transectFinding);
            List<Persistable> findingDetails = transectFindingDataService.findFindingDetails(id);
            ListView findingDetailsView = (ListView) findViewById(R.id.findingDetails);
            findingDetailsView.setAdapter(new TransectFindingDetailsListAdapter(this, findingDetails));

        } else {
            transectFindingView = new TransectFindingDetailView(this, transectId);
        }

        //initGui(transectFindingView);


    }

    private void initGui(TransectFindingDetailView transectFindingView) {

        switch (action) {

            case EDIT:
                transectFindingView.initGuiForEdit();
                break;
            case VIEW:
                transectFindingView.initGuiForView();
                break;
            case NEW:
                transectFindingView.initGuiForNew();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transect_finding_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.transect_finding_action_add_photo) {
            addPhoto(null);
            return true;
        }
        if (id == R.id.transect_finding_action_photos) {
            showPhotos(null);
            return true;
        }
        if (id == R.id.transect_finding_action_add_sample) {
            addSample(null);
            return true;
        }
        if (id == R.id.transect_finding_action_samples) {
            showSamples(null);
            return true;
        }
        if (id == R.id.transect_finding_action_habitat) {
            setHabitat(null);
            return true;
        }
        if (id == R.id.action_save) {
            saveTransectFinding();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setHabitat(View view) {
        Intent intent = new Intent(this, HabitatDetailActivity.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, TransectFindingDetailActivity.class);

        Long habitatId = transectFindingView.getHabitatId();
        if (habitatId != null) {
            intent.setAction(action.toString()); // edit or view
            intent.putExtra("id", habitatId);
        } else {
            intent.setAction(Action.NEW.toString());
        }

        startActivityForResult(intent, SET_HABITAT_REQUEST);
    }

    public void addFootprints(View view) {
        Intent intent = new Intent(this, TransectFindingFootprintsDetailActivity.class);
        intent.putExtra("transectFindingId", transectFindingView.getId());
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(Action.NEW.toString());
        startActivity(intent);
    }

    public void addFeces(View view) {
        Intent intent = new Intent(this, TransectFindingFecesDetailActivity.class);
        intent.putExtra("transectFindingId", transectFindingView.getId());
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(Action.NEW.toString());
        startActivity(intent);
    }

    public void addOthers(View view) {
        Intent intent = new Intent(this, TransectFindingOtherDetailActivity.class);
        intent.putExtra("transectFindingId", transectFindingView.getId());
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(Action.NEW.toString());
        startActivity(intent);
    }




    public void showPhotos(View view) {
        Intent intent = new Intent(this, PhotosList.class);
        intent.putExtra("transectFindingId", transectFindingView.getId());
        startActivity(intent);
    }

    public void showSamples(View view) {
        Intent intent = new Intent(this, TransectFindingSamplesList.class);
        intent.putExtra("transectFindingId", transectFindingView.getId());
        startActivity(intent);
    }

    public void addSample(View view) {
        Intent intent = new Intent(this, TransectFindingAddSampleActivity.class);
        startActivityForResult(intent, ADD_SAMPLE_REQUEST);
    }

    public void addPhoto(View view) {

        if (!Permissions.grantedOrRequestPermissions(this, ADD_PHOTO_PERMISSION_REQUEST,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)) {
            return;
        }

        outputPhotoFile = new File(Globals.getPhotosStorageDir(), "T" + transectId + "F" + id + "-photo-" + UUID.randomUUID() + ".jpg");

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
                addPhoto(null);
            } else {
                Log.w(Globals.APP_NAME, "ADD_PHOTO_PERMISSION_REQUEST NOT granted");
            }
        }

        if (requestCode == ACCESS_FINE_LOCATION_REQUEST) {
            if (Permissions.grantResults(grantResults)) {
                Log.i(Globals.APP_NAME, "ACCESS_FINE_LOCATION granted");
                LocationUtil.initLocationManager(this, ACCESS_FINE_LOCATION_REQUEST);
            } else {
                Log.w(Globals.APP_NAME, "ACCESS_FINE_LOCATION NOT granted");
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

        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "Problem with creating: " + getNameForRequestCode(requestCode), Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case SET_HABITAT_REQUEST:
                Long id = data.getExtras().getLong("id");
                Assert.assertNotNull("HabitatId", id);
                String text = transectFindingView.getHabitatId() == null ? "created" : "modified";
                Toast.makeText(this, "Habitat " + text + ", ID = " + id, Toast.LENGTH_LONG).show();

                transectFindingView.setHabitatId(id);

                break;

            case ADD_PHOTO_REQUEST:
                Log.d(Globals.APP_NAME, "Pic saved, intent: " + data);
                Photo photo = new Photo(transectFindingView.getId(), outputPhotoFile.getName());
                photosDataService.save(photo);

                break;

            case ADD_SAMPLE_REQUEST:
                String sampleNumber = data.getExtras().getString("sampleNumber");
                Log.d(Globals.APP_NAME, "sampleNumber: " + sampleNumber);
                sampleDataService.save(new Sample(null, sampleNumber, transectFindingView.getId()));
                break;

            case EDIT_LOCATION_REQUEST:
                String location = data.getExtras().getString("location");
                transectFindingView.getLocation().setText(location);
                break;
        }

        saveTransectFinding();
    }

    private void saveTransectFinding() {
        TransectFinding transectFinding = transectFindingDataService.save(transectFindingView.toTransectFinding());
        transectFindingView.setId(transectFinding.getId());
        transectFindingView.initGuiForEdit();
        this.id = transectFinding.getId();
        Toast.makeText(TransectFindingDetailActivity.this, "Finding saved.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onLocationChanged(Location location) {
        if (transectFindingView.getTransectFinding() == null || !transectFindingView.getTransectFinding().hasLocation()) {
            transectFindingView.setLocation(location);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private String getNameForRequestCode(int requestCode) {
        switch (requestCode) {
            case SET_HABITAT_REQUEST:
                return "habitat";
            case ADD_PHOTO_REQUEST:
                return "photo";
            case ADD_SAMPLE_REQUEST:
                return "sample";
            default:
                return "UNKNOWN";
        }
    }

    public void editLocation(View view) {
        Intent intent = new Intent(this, EditLocationActivity.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(Action.NEW.toString());
        intent.putExtra("location", transectFindingView.getLocation().getText().toString());
        startActivityForResult(intent, EDIT_LOCATION_REQUEST);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem transect_finding_action_add_sample = menu.findItem(R.id.transect_finding_action_add_sample);
        MenuItem transect_finding_action_add_photo = menu.findItem(R.id.transect_finding_action_add_photo);
        MenuItem transect_finding_action_habitat = menu.findItem(R.id.transect_finding_action_habitat);
        MenuItem transect_finding_action_samples = menu.findItem(R.id.transect_finding_action_samples);
        MenuItem transect_finding_action_photos = menu.findItem(R.id.transect_finding_action_photos);

        if (transectFindingView.getId() == null) {
            transect_finding_action_add_sample.setEnabled(false);
            transect_finding_action_add_photo.setEnabled(false);
            transect_finding_action_habitat.setEnabled(false);
            transect_finding_action_samples.setEnabled(false);
            transect_finding_action_photos.setEnabled(false);
        } else {
            transect_finding_action_add_sample.setEnabled(true);
            transect_finding_action_add_photo.setEnabled(true);
            transect_finding_action_habitat.setEnabled(true);
            transect_finding_action_samples.setEnabled(true);
            transect_finding_action_photos.setEnabled(true);
        }
        return true;
    }
}
