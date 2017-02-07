package com.wecode.animaltracker.activity.detail;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.LocationProvidingActivity;
import com.wecode.animaltracker.activity.ViewPagerAdapter;
import com.wecode.animaltracker.activity.common.PhotoEnabledCommonActivity;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.export.TransectReport;
import com.wecode.animaltracker.fragment.list.PhotosListFragment;
import com.wecode.animaltracker.fragment.detail.TransectDetailFragment;
import com.wecode.animaltracker.fragment.list.TransectFindingSiteListFragment;
import com.wecode.animaltracker.model.EntityName;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.service.TransectDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.LocationUtil;
import com.wecode.animaltracker.util.Permissions;

import java.io.File;

public class TransectDetailActivity extends PhotoEnabledCommonActivity implements LocationListener, LocationProvidingActivity {

    private static final int ADD_FINDING_REQUEST = 2;

    private static final int ACCESS_FINE_LOCATION_REQUEST = 3;

    private static final int EXPORT_FILE_PERMISSION_REQUEST = 6;

    private volatile Location currentLocation;

    private TransectDetailFragment transectFragment;

    private TransectFindingSiteListFragment transectFindingSiteListFragment;

    private PhotosListFragment photosListFragment;

    private ViewPagerAdapter adapter;

    {
        entityName = EntityName.TRANSECT;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabbed_activity);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        extractParams(intent);

        LocationUtil.initLocationManager(this, ACCESS_FINE_LOCATION_REQUEST);

        initTabLayout();

    }

    private void initTabLayout() {
        ViewPager viewPager = setupViewPager(R.id.viewpager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private ViewPager setupViewPager(int viewPagerId) {

        ViewPager viewPager = (ViewPager) findViewById(viewPagerId);

        if (adapter == null) {
            adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
            setupTransectFragment(adapter);
        }

        if (id != null) {
            setupTransectFindingListFragment(adapter);
            //setupWeatherFragment(adapter);
            setupPhotosFragment(adapter);
        }

        viewPager.setAdapter(adapter);
        return viewPager;
    }

    private void setupPhotosFragment(ViewPagerAdapter adapter) {
        Bundle bundle = new Bundle();
        bundle.putLong("entityId", id);
        bundle.putLong("transectId", id);
        bundle.putString("entityName", EntityName.TRANSECT.toString());
        photosListFragment = new PhotosListFragment();
        photosListFragment.setArguments(bundle);
        adapter.addFragment(photosListFragment);
    }

    private void setupTransectFindingListFragment(ViewPagerAdapter adapter) {
        Bundle bundle = new Bundle();
        bundle.putString("action", Action.NEW.toString());
        bundle.putLong("transectId", id);
        bundle.putString("action", action == Action.VIEW ? Action.VIEW.toString() : Action.EDIT.toString());
        transectFindingSiteListFragment = new TransectFindingSiteListFragment();
        transectFindingSiteListFragment.setArguments(bundle);
        adapter.addFragment(transectFindingSiteListFragment);
    }

/*    private void setupWeatherFragment(ViewPagerAdapter adapter) {

        Bundle bundle = new Bundle();
        bundle.putString("action", Action.NEW.toString());

        Transect transect = TransectDataService.getInstance().find(id);
        if (transect.getWatherId() != null) {
            bundle.putLong("weatherId", transect.getWatherId());
            bundle.putString("action", action == Action.VIEW ? Action.VIEW.toString() : Action.EDIT.toString());
        }

        weatherFragment = new WeatherFragment();
        weatherFragment.setArguments(bundle);
        adapter.addFragment(weatherFragment);
    }
*/

    private void setupTransectFragment(ViewPagerAdapter adapter) {
        transectFragment = new TransectDetailFragment();
        Bundle bundle = new Bundle();
        if (id != null) {
            bundle.putLong("transectId", id);
        }
        bundle.putString("action", action.toString());
        transectFragment.setArguments(bundle);
        adapter.addFragment(transectFragment);
    }

    @Override
    public Location getCurrentLocation() {
        return currentLocation;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_transect_detail, menu);
        return true;
    }

    @Override
    protected Long getCurrentTransectId() {
        return id;
    }

    @Override
    protected File getPhotoDirectory() {
        Transect transect = TransectDataService.getInstance().find(id);
        return Globals.getTransectPhotosDirectory(transect);
    }

    public void addFinding() {
        Intent intent = new Intent(this, TransectFindingSiteDetailActivity.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(Action.NEW.toString());
        intent.putExtra("transectId", id);
        startActivityForResult(intent, ADD_FINDING_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_FINDING_REQUEST) {
            transectFindingSiteListFragment.refreshFindings();
        }

        if (resultCode == RESULT_CANCELED) {
            //Toast.makeText(this, R.string.operation_canceled, Toast.LENGTH_SHORT).show();
            return;
        }

        if (resultCode != RESULT_OK) {
            Toast.makeText(this, getString(R.string.problem_with_creating) + getNameForRequestCode(requestCode), Toast.LENGTH_SHORT).show();
            return;
        }

        switch (requestCode) {

            case ADD_FINDING_REQUEST:
                id = data.getExtras().getLong("id");
                Assert.assertNotNullNotZero("Finding", id);
                Toast.makeText(this, getString(R.string.transect_finding_saved), Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    protected void refreshPhotos() {
        photosListFragment.refreshPhotos();
    }

    private String getNameForRequestCode(int requestCode) {
        switch (requestCode) {
            case ADD_FINDING_REQUEST:
                return "finding";
            default:
                return "UNKNOWN";
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.transect_action_add_finding) {
            addFinding();
            return true;
        }

        if (id == R.id.action_save) {
            saveAll();
            return true;
        }

        if (id == R.id.transect_finding_action_export) {
            exportToExcel();
            return true;
        }

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addFindingSite(View view) {
        addFinding();
    }

    public void addPhoto(View view) {
        addPhoto();
    }

    public boolean saveAll() {
        Transect transect = transectFragment.validateAndSaveTransect();
        if (transect == null) {
            return false;
        }

/*        if (weatherFragment != null) {
            Weather weather = weatherFragment.saveWeather();
            transect.setWeatherId(weather != null ? weather.getId() : null);
        }*/

        TransectDataService.getInstance().save(transect);

        if (this.id == null) {
            this.id = transect.getId();
            initTabLayout();
        }

        Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();

        return true;
    }


    @Override
    public void onBackPressed() {

        if (transectFragment.isChangedByUser()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_save_changes_before_leave)
                    .setPositiveButton(R.string.save, new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (saveAll()){
                                endActivity();
                            }
                        }
                    })
                    .setNegativeButton(R.string.dialog_discard_and_leave, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            endActivity();
                        }
                    })
                    .show();
        } else {
            endActivity();
        }

    }

    private void endActivity(){
        Intent intent = new Intent();
        intent.putExtra("id", id);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {    }

    @Override
    public void onProviderEnabled(String provider) {
        currentLocation = null;
    }

    @Override
    public void onProviderDisabled(String provider) {
        currentLocation = null;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem addFinding = menu.findItem(R.id.transect_action_add_finding);
        MenuItem export = menu.findItem(R.id.transect_finding_action_export);

        if (id == null) {
            if (addFinding != null) addFinding.setEnabled(false);
            if (export != null) export.setEnabled(false);
        } else {
            if (addFinding != null) addFinding.setEnabled(true);
            if (export != null) export.setEnabled(true);
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == EXPORT_FILE_PERMISSION_REQUEST) {
            if (grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                exportToExcel();
            } else {
                Log.w(Globals.APP_NAME, "EXPORT_FILE_PERMISSION_REQUEST NOT granted");
            }
        }

        if (requestCode == ACCESS_FINE_LOCATION_REQUEST) {
            if (grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                Log.i(Globals.APP_NAME, "ACCESS_FINE_LOCATION granted");
                LocationUtil.initLocationManager(this, ACCESS_FINE_LOCATION_REQUEST);
            } else {
                Log.w(Globals.APP_NAME, "ACCESS_FINE_LOCATION NOT granted");
            }
        }
    }

    public void exportToExcel() {

        if (!Permissions.grantedOrRequestPermissions(this, EXPORT_FILE_PERMISSION_REQUEST,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            return;
        }

        new TransectReport(TransectDataService.getInstance().find(id), this).exportToExcel();

    }

}
