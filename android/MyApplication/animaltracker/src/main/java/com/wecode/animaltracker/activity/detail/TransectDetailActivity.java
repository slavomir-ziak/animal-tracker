package com.wecode.animaltracker.activity.detail;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
import com.wecode.animaltracker.activity.detail.findings.TransectFindingDetailActivity;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.fragment.HabitatFragment;
import com.wecode.animaltracker.fragment.IFragment;
import com.wecode.animaltracker.fragment.PhotosFragment;
import com.wecode.animaltracker.fragment.TransectDetailFragment;
import com.wecode.animaltracker.fragment.TransectFindingListFragment;
import com.wecode.animaltracker.fragment.WeatherFragment;
import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.model.Weather;
import com.wecode.animaltracker.service.TransectDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.LocationUtil;

import java.util.ArrayList;
import java.util.List;

public class TransectDetailActivity extends CommonDetailActivity implements LocationListener, LocationProvidingActivity {

    private static final int ADD_FINDING_REQUEST = 2;

    private static final int ACCESS_FINE_LOCATION_REQUEST = 3;

    private volatile Location currentLocation;

    private TransectDetailFragment transectFragment;

    private WeatherFragment weatherFragment;

    private HabitatFragment habitatFragment;

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

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        setupTransectFragment(adapter);
        setupTransectFindingListFragment(adapter);
        setupWeatherFragment(adapter);
        setupHabitatFragment(adapter);
        setupPhotosFragment(adapter);

        viewPager.setAdapter(adapter);
    }

    private void setupPhotosFragment(ViewPagerAdapter adapter) {
        Bundle bundle = new Bundle();
        bundle.putLong("entityId", id);
        bundle.putString("entityName", Photo.EntityName.TRANSECT.toString());

        PhotosFragment photosFragment = new PhotosFragment();
        photosFragment.setArguments(bundle);
        adapter.addFragment(photosFragment);
    }

    private void setupTransectFindingListFragment(ViewPagerAdapter adapter) {
        Bundle bundle = new Bundle();
        bundle.putString("action", Action.NEW.toString());

        if (id != null) {
            bundle.putLong("transectId", id);
            bundle.putString("action", action == Action.VIEW ? Action.VIEW.toString() : Action.EDIT.toString());
        }

        TransectFindingListFragment transectFindingListFragment = new TransectFindingListFragment();
        transectFindingListFragment.setArguments(bundle);
        adapter.addFragment(transectFindingListFragment);
    }

    private void setupHabitatFragment(ViewPagerAdapter adapter) {
        Bundle bundle = new Bundle();
        bundle.putString("action", Action.NEW.toString());

        if (id != null) {
            Transect transect = TransectDataService.getInstance().find(id);
            if (transect.getHabitatId() != null) {
                bundle.putLong("habitatId", transect.getHabitatId());
                bundle.putString("action", action == Action.VIEW ? Action.VIEW.toString() : Action.EDIT.toString());
            }
        }

        habitatFragment = new HabitatFragment();
        habitatFragment.setArguments(bundle);
        adapter.addFragment(habitatFragment);
    }

    private void setupWeatherFragment(ViewPagerAdapter adapter) {
        Bundle bundle = new Bundle();
        bundle.putString("action", Action.NEW.toString());

        if (id != null) {
            Transect transect = TransectDataService.getInstance().find(id);
            if (transect.getWatherId() != null) {
                bundle.putLong("weatherId", transect.getWatherId());
                bundle.putString("action", action == Action.VIEW ? Action.VIEW.toString() : Action.EDIT.toString());
            }
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

        private final List<IFragment> mFragmentList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return (Fragment) mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(IFragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentList.get(position).getName();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_transect_detail, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == ACCESS_FINE_LOCATION_REQUEST) {
            if (grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                Log.i(Globals.APP_NAME, "ACCESS_FINE_LOCATION granted");
                LocationUtil.initLocationManager(this, ACCESS_FINE_LOCATION_REQUEST);
            } else {
                Log.w(Globals.APP_NAME, "ACCESS_FINE_LOCATION NOT granted");
            }
        }

    }

    public void addFinding(View view) {
        Intent intent = new Intent(this, TransectFindingDetailActivity.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(Action.NEW.toString());
        intent.putExtra("transectId", id);
        startActivityForResult(intent, ADD_FINDING_REQUEST);
    }

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

            case ADD_FINDING_REQUEST:
                id = data.getExtras().getLong("id");
                Assert.assertNotNull("Finding", id);
                Toast.makeText(this, "Finding created, ID = " + id, Toast.LENGTH_LONG).show();
                break;
        }

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
            addFinding(null);
            return true;
        }

        if (id == R.id.action_save) {

            Transect transect = transectFragment.saveTransect();

            if (transect != null) {
                Weather weather = weatherFragment.saveWeather();
                transect.setWeatherId(weather.getId());

                Habitat habitat = habitatFragment.saveHabitat();
                transect.setHabitatId(habitat.getId());

                transect.save();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        Transect transect = transectFragment.saveTransect();

        Intent intent = new Intent();
        intent.putExtra("id", transect.getId());
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

}
