package com.wecode.animaltracker.activity.detail;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.list.TransectFindingsList;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.activity.util.LocationFormatter;
import com.wecode.animaltracker.data.TransectDataService;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.view.TransectDetailView;

import java.text.DateFormat;
import java.util.Date;

public class TransectDetailActivity extends CommonDetailActivity implements LocationListener {

    private static final int SET_HABITAT_REQUEST = 0;
    private static final int SET_WEATHER_REQUEST = 1;
    private static final int ADD_FINDING_REQUEST = 2;

    private static TransectDataService service = TransectDataService.getInstance();

    private TransectDetailView transectDetailView;

    private volatile Location currentLocation;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transect_detail, menu);
        //menu.add

        return true;
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

        Transect transect = null;
        if (id != null) {
            transect = service.find(id);
        }

        initGui(action, transect);

        initLocationManager();

    }

    private void initGui(Action action, Transect transect) {

        transectDetailView = new TransectDetailView(this, transect);

        switch (action){
            case EDIT:
                transectDetailView.disableAllForEdit();
                break;
            case VIEW:
                transectDetailView.disableAllForView();
                break;
            case NEW:
                transectDetailView.getId().setText(service.getNextId().toString());
                break;
        }

    }

    private void initLocationManager() {
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        String provider = locationManager.getBestProvider(criteria, true);

        if (provider != null) {
            locationManager.requestLocationUpdates(provider, 0, 0, this);
        }

    }

    public void startTransect(View view) {

        if (currentLocation == null) {
            Toast.makeText(this, "Location not acquired, is your GPS on?", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO refactor this as part of TransectDetailView object
        String startDateTime = DateFormat.getDateTimeInstance().format(new Date());
        transectDetailView.getStartDateTime().setText(startDateTime);

        transectDetailView.getStartLocation().setText(LocationFormatter.formatLocation(currentLocation));

        service.save(transectDetailView.toTransect());

        action = Action.EDIT;
    }

    public void endTransect(View view) {

        if (currentLocation == null) {
            Toast.makeText(this, "Location not acquired, is your GPS on?", Toast.LENGTH_SHORT).show();
            return;
        }

        String endDateTime = DateFormat.getDateTimeInstance().format(new Date());
        transectDetailView.getEndDateTime().setText(endDateTime);
        transectDetailView.getEndLocation().setText(LocationFormatter.formatLocation(currentLocation));

        service.save(transectDetailView.toTransect());
    }

    public void setWeather(View view) {
        Intent intent = new Intent(this, WeatherDetailActivity.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(action.toString());
        intent.putExtra("id", transectDetailView.getWeatherId());
        intent.putExtra("transectId", transectDetailView.getIdValue());
        startActivityForResult(intent, SET_WEATHER_REQUEST);
    }

    public void setHabitat(View view) {
        Intent intent = new Intent(this, HabitatDetailActivity.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(action.toString());
        intent.putExtra("id", transectDetailView.getHabitatId());
        intent.putExtra("transectId", transectDetailView.getIdValue());
        startActivityForResult(intent, SET_HABITAT_REQUEST);
    }

    public void addFinding(View view) {
        Intent intent = new Intent(this, TransectFindingDetailActivity.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(action.toString());
        intent.putExtra("transectId", transectDetailView.getIdValue());
        startActivityForResult(intent, ADD_FINDING_REQUEST);
    }

    public void viewFindings(View view) {
        Intent intent = new Intent(this, TransectFindingsList.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(action.toString());
        intent.putExtra("transectId", transectDetailView.getIdValue());
        startActivity(intent);
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
            case SET_HABITAT_REQUEST:
                Long id = data.getExtras().getLong("id");
                Assert.assertNotNull("Habitat", id);
                String text = transectDetailView.getHabitatId() == null ? "created" : "modified";
                Toast.makeText(this, "Habitat " + text + ", ID = " + id, Toast.LENGTH_LONG).show();

                transectDetailView.setHabitatId(id);
                break;

            case ADD_FINDING_REQUEST:
                id = data.getExtras().getLong("id");
                Assert.assertNotNull("Finding", id);
                Toast.makeText(this, "Finding created, ID = " + id, Toast.LENGTH_LONG).show();
                break;

            case SET_WEATHER_REQUEST:
                id = data.getExtras().getLong("id");
                Assert.assertNotNull("Weather", id);
                text = transectDetailView.getWeatherId() == null ? "created" : "modified";
                Toast.makeText(this, "Weather " + text + ", ID = " + id, Toast.LENGTH_LONG).show();

                transectDetailView.setWeatherId(id);
                break;
        }

        service.save(transectDetailView.toTransect());

    }

    private String getNameForRequestCode(int requestCode) {
        switch(requestCode) {
            case SET_HABITAT_REQUEST: return "habitat";
            case ADD_FINDING_REQUEST: return "finding";
            case SET_WEATHER_REQUEST: return "weather";
            default: return "UNKNOWN";
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
        if (id == R.id.transect_action_findings) {
            viewFindings(null);
            return true;
        }
        if (id == R.id.transect_action_habitat) {
            setHabitat(null);
            return true;
        }
        if (id == R.id.transect_action_weather) {
            setWeather(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
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


}
