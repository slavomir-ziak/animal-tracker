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
import com.wecode.animaltracker.fragment.ITransect;
import com.wecode.animaltracker.fragment.TransectFragment;
import com.wecode.animaltracker.fragment.WeatherFragment;
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

    private TransectFragment transectFragment;

    private WeatherFragment weatherFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transect_detail2);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        extractParams(intent);

        LocationUtil.initLocationManager(this, ACCESS_FINE_LOCATION_REQUEST);

        entityName = Photo.EntityName.TRANSECT;

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        transectFragment = new TransectFragment();
        Bundle bundle = new Bundle();
        if (id != null) {
            bundle.putLong("transectId", id);
        }
        bundle.putString("action", action.toString());
        transectFragment.setArguments(bundle);
        adapter.addFragment(transectFragment);

        weatherFragment = new WeatherFragment();
        bundle = new Bundle();
        if (id != null) {
            Transect transect = TransectDataService.getInstance().find(id);
            if (transect.getWatherId() != null) {
                bundle.putLong("weatherId", transect.getWatherId());
            }
        }

        bundle.putString("action", action.toString());
        weatherFragment.setArguments(bundle);
        adapter.addFragment(weatherFragment);

        viewPager.setAdapter(adapter);
    }

    @Override
    public Location getCurrentLocation() {
        return currentLocation;
    }

    public TransectFragment getTransectFragment() {
        return transectFragment;
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<ITransect> mFragmentList = new ArrayList<>();

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

        void addFragment(ITransect fragment) {
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
/*
    public void setWeather(View view) {
        Intent intent = new Intent(this, WeatherDetailActivity.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(Action.NEW.toString());

        Long weatherId = transectDetailView.getWeatherId();
        if (weatherId != null) {
            intent.setAction(action.toString()); // edit or view
            intent.putExtra("id", weatherId);
        } else {
            intent.setAction(Action.NEW.toString());
        }

        intent.putExtra("transectId", transectDetailView.getIdValue());
        startActivityForResult(intent, SET_WEATHER_REQUEST);
    }

    public void setHabitat(View view) {
        Intent intent = new Intent(this, HabitatDetailActivity.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());

        Long habitatId = transectDetailView.getHabitatId();
        if (habitatId != null) {
            intent.setAction(action.toString()); // edit or view
            intent.putExtra("id", habitatId);
        } else {
            intent.setAction(Action.NEW.toString());
        }

        intent.putExtra("transectId", transectDetailView.getIdValue());
        startActivityForResult(intent, SET_HABITAT_REQUEST);
    }


    public void viewFindings(View view) {
        Intent intent = new Intent(this, TransectFindingsList.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(action.toString());
        intent.putExtra("transectId", transectDetailView.getIdValue());
        startActivity(intent);
    }*/

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.transect_action_add_finding) {
            addFinding(null);
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {

            Transect transect = getTransectFragment().saveTransect();

            if (transect != null) {
                Weather weather = getWeatherFragment().saveWeather();
                transect.setWeatherId(weather.getId());
                transect.save();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        Transect transect = (Transect) transectFragment.getData();

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
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        currentLocation = null;
    }

    @Override
    public void onProviderDisabled(String provider) {
        currentLocation = null;
    }

    public WeatherFragment getWeatherFragment() {
        return weatherFragment;
    }
}
