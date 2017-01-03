package com.wecode.animaltracker.activity.detail;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
import com.wecode.animaltracker.activity.common.PhotoEnabledCommonActivity;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.export.TransectReport;
import com.wecode.animaltracker.fragment.Fragment;
import com.wecode.animaltracker.fragment.PhotosFragment;
import com.wecode.animaltracker.fragment.TransectDetailFragment;
import com.wecode.animaltracker.fragment.TransectFindingSiteListFragment;
import com.wecode.animaltracker.fragment.WeatherFragment;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.model.Weather;
import com.wecode.animaltracker.service.TransectDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.LocationUtil;
import com.wecode.animaltracker.util.Permissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TransectDetailActivity extends PhotoEnabledCommonActivity implements LocationListener, LocationProvidingActivity {

    private static final int ADD_FINDING_REQUEST = 2;

    private static final int ACCESS_FINE_LOCATION_REQUEST = 3;

    private static final int EXPORT_FILE_PERMISSION_REQUEST = 6;

    private volatile Location currentLocation;

    private TransectDetailFragment transectFragment;

    private WeatherFragment weatherFragment;

    private TransectFindingSiteListFragment transectFindingSiteListFragment;

    private PhotosFragment photosFragment;

    private ViewPagerAdapter adapter;

    {
        entityName = Photo.EntityName.TRANSECT;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transect_detail);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            setupWeatherFragment(adapter);
            setupPhotosFragment(adapter);
        }

        viewPager.setAdapter(adapter);
        return viewPager;
    }

    private void setupPhotosFragment(ViewPagerAdapter adapter) {
        Bundle bundle = new Bundle();
        bundle.putLong("entityId", id);
        bundle.putLong("transectId", id);
        bundle.putString("entityName", Photo.EntityName.TRANSECT.toString());
        photosFragment = new PhotosFragment();
        photosFragment.setArguments(bundle);
        adapter.addFragment(photosFragment);
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

    private void setupWeatherFragment(ViewPagerAdapter adapter) {
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

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();

        private final Activity activity;

        ViewPagerAdapter(FragmentManager manager, Activity activity) {
            super(manager);
            this.activity = activity;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return (android.support.v4.app.Fragment) mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return activity.getResources().getString(mFragmentList.get(position).getNameResourceId());
        }
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
            Toast.makeText(this, "Operation canceled.", Toast.LENGTH_LONG).show();
            return;
        }

        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "Problem with creating: " + getNameForRequestCode(requestCode), Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {

            case ADD_FINDING_REQUEST:
                id = data.getExtras().getLong("id");
                Assert.assertNotNull("Finding", id);
                Toast.makeText(this, "Finding created, ID = " + id, Toast.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    protected void refreshPhotos() {
        photosFragment.refreshPhotos();
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

        return super.onOptionsItemSelected(item);
    }

    public void addFindingSite(View view) {
        addFinding();
    }

    public void addPhoto(View view) {
        addPhoto();
    }

    public void saveAll() {
        Transect transect = transectFragment.saveTransect();
        if (transect == null) {
            return;
        }

        if (weatherFragment != null) {
            Weather weather = weatherFragment.saveWeather();
            transect.setWeatherId(weather != null ? weather.getId() : null);
        }

        TransectDataService.getInstance().save(transect);

        if (this.id == null) {
            this.id = transect.getId();
            initTabLayout();
        }

        Toast.makeText(this, "Transect saved.", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onBackPressed() {
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
