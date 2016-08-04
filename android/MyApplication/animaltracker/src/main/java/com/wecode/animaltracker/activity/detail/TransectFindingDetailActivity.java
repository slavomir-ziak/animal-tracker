package com.wecode.animaltracker.activity.detail;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import com.wecode.animaltracker.*;
import com.wecode.animaltracker.activity.MainActivity;
import com.wecode.animaltracker.activity.TransectFindingAddSampleActivity;
import com.wecode.animaltracker.activity.list.PhotosList;
import com.wecode.animaltracker.activity.list.TransectFindingSamplesList;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.model.Sample;
import com.wecode.animaltracker.model.TransectFinding;
import com.wecode.animaltracker.service.PhotosDataService;
import com.wecode.animaltracker.service.SampleDataService;
import com.wecode.animaltracker.service.TransectFindingDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.view.TransectFindingDetailView;

import java.io.File;
import java.util.UUID;

public class TransectFindingDetailActivity extends CommonDetailActivity implements OnFragmentInteractionListener, LocationListener {

    private static final int SET_HABITAT_REQUEST = 0;
    private static final int ADD_SAMPLE_REQUEST = 1;
    private static final int ADD_PHOTO_REQUEST = 2;

    private TransectFindingDataService transectFindingDataService = TransectFindingDataService.getInstance();

    private PhotosDataService photosDataService = PhotosDataService.getInstance();

    private SampleDataService sampleDataService = SampleDataService.getInstance();

    private TransectFindingDetailView transectFindingView;

    private FootprintsFragment footprintsFragment;

    private Fragment activeFragment;

    private FecesFragment fecesFragment;

    private Long transectId;

    private File outputPhotoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_detail);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initLocationManager();

        extractParams(getIntent());

        transectId = getIntent().getExtras().getLong("transectId");

        TransectFinding transectFinding = transectFindingDataService.find(id);

        initialiseFragmentLogic();

        if (id != null) {
            transectFindingView = new TransectFindingDetailView(this, transectFinding);
        } else {
            transectFindingView = new TransectFindingDetailView(this);
        }

        fecesFragment = new FecesFragment(transectFindingView);
        footprintsFragment = new FootprintsFragment(transectFindingView);
    }

    @Override
    public void onBackPressed() {
        TransectFinding transectFinding = transectFindingView.toTransectFinding();
        transectFinding.setTransectId(transectId);

        saveTransectFinding();

        Intent intent = new Intent();
        intent.putExtra("id", transectFinding.getId());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initialiseFragmentLogic() {

        Spinner findingTypeValue = (Spinner) this.findViewById(R.id.findingTypeValue);
        findingTypeValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                final FragmentTransaction ft = getFragmentManager().beginTransaction();

                if (position == 0) {

                    ft.replace(R.id.specificDataContainer, footprintsFragment);
                    activeFragment = footprintsFragment;

                } else if (position == 1) {

                    ft.replace(R.id.specificDataContainer, fecesFragment);
                    activeFragment = fecesFragment;

                } else {
                    if (activeFragment != null) {
                        ft.remove(activeFragment);
                        activeFragment = null;
                    }
                }

                ft.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });


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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initLocationManager() {
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(criteria, true);

        if (provider != null) {
            locationManager.requestLocationUpdates(provider, 0, 0, this);
        }

    }

    public void setHabitat(View view) {
        Intent intent = new Intent(this, HabitatDetailActivity.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, TransectFindingDetailActivity.class);
        intent.setAction(action.toString());
        intent.putExtra("id", transectFindingView.getHabitatId());
        startActivityForResult(intent, SET_HABITAT_REQUEST);
    }

    public void showPhotos(View view) {
        Intent intent = new Intent(this, PhotosList.class);
        intent.putExtra("transectDetailId", id);
        startActivity(intent);
    }

    public void showSamples(View view) {
        Intent intent = new Intent(this, TransectFindingSamplesList.class);
        intent.putExtra("transectDetailId", id);
        startActivity(intent);
    }

    public void addSample(View view) {
        Intent intent = new Intent(this, TransectFindingAddSampleActivity.class);
        startActivityForResult(intent, ADD_SAMPLE_REQUEST);
    }

    public void addPhoto(View view) {

        outputPhotoFile = new File(Globals.getPhotosStorageDir(), "T" + transectId + "F" + id + "-photo-" + UUID.randomUUID() + ".jpg");

        Log.d(MainActivity.LOG_TAG, "Picture will be saved:" + outputPhotoFile);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputPhotoFile));
        startActivityForResult(cameraIntent, ADD_PHOTO_REQUEST);
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
                Log.d(MainActivity.LOG_TAG, "Pic saved, intent: " + data);
                Photo photo = new Photo(this.id, outputPhotoFile.getName());
                photosDataService.save(photo);

                break;

            case ADD_SAMPLE_REQUEST:
                String sampleNumber = data.getExtras().getString("sampleNumber");
                Log.d(MainActivity.LOG_TAG, "sampleNumber: " + sampleNumber);
                sampleDataService.save(new Sample(null, sampleNumber, transectFindingView.getId()));
                break;

        }

        saveTransectFinding();
    }

    private void saveTransectFinding() {
        TransectFinding transectFinding = transectFindingDataService.save(transectFindingView.toTransectFinding());
        transectFindingView.setId(transectFinding.getId());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        System.out.println(uri);
    }

    @Override
    public void onLocationChanged(Location location) {
        //this.currentLocation = location;
        transectFindingView.setLocation(location);
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

    public FootprintsFragment getFootprintsFragment() {
        return footprintsFragment;
    }

    public FecesFragment getFecesFragment() {
        return fecesFragment;
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
}
