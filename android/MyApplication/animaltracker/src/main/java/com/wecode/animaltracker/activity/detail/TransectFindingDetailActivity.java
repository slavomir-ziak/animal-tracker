package com.wecode.animaltracker.activity.detail;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import com.wecode.animaltracker.FecesFragment;
import com.wecode.animaltracker.FootprintsFragment;
import com.wecode.animaltracker.OnFragmentInteractionListener;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.MainActivity;
import com.wecode.animaltracker.activity.list.PhotosList;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.data.TransectFindingDataService;
import com.wecode.animaltracker.model.TransectFinding;
import com.wecode.animaltracker.view.TransectFindingView;

import java.io.File;
import java.io.IOException;

public class TransectFindingDetailActivity extends CommonDetailActivity implements OnFragmentInteractionListener, LocationListener {

    private TransectFindingView transectFindingView;

    private TransectFindingDataService transectFindingDataService = TransectFindingDataService.getInstance();

    private FootprintsFragment footprintsFragment;

    private Fragment activeFragment;

    private FecesFragment fecesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_detail);
        initLocationManager();

        extractParams(getIntent());

        TransectFinding transectFinding = transectFindingDataService.find(id);

        initialiseFragmentLogic(transectFinding);

        if (id != null) {
            transectFindingView = new TransectFindingView(this, transectFinding);
        } else {
            transectFindingView = new TransectFindingView(this);
        }

        fecesFragment = new FecesFragment(transectFindingView);
        footprintsFragment = new FootprintsFragment(transectFindingView);
    }

    private void initialiseFragmentLogic(final TransectFinding transectFinding) {

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
        getMenuInflater().inflate(R.menu.menu_finding_detail, menu);
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

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }
    public void setHabitat(View view) {
        Intent intent = new Intent(this, HabitatDetailActivity.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, TransectFindingDetailActivity.class);
        startActivity(intent);
    }

    public void showPhotos(View view) {
        Intent intent = new Intent(this, PhotosList.class);
        startActivity(intent);
    }

    private File pictureDirectory;
    private static int TAKE_PHOTO_CODE = 0;
    private int count;
    private Uri outputFileUri;

    public void addPhoto(View view) {
        File newfile = new File(pictureDirectory, count + ".jpg");
        try {
            newfile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        outputFileUri = Uri.fromFile(newfile);
        System.out.println(outputFileUri);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            Log.d(MainActivity.LOG_TAG, "Pic saved");

            //ImageView photoView = (ImageView) findViewById(R.id.photoView);
            //photoView.setImageURI(outputFileUri);
        }
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

}
