package com.wecode.animaltracker.activity.detail;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.list.FindingsList;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.data.TransectDataService;
import com.wecode.animaltracker.model.Transect;

public class TransectDetail extends AppCompatActivity implements LocationListener {

    private static TransectDataService service = new TransectDataService();
    private long id;
    private Action action;
    private Transect transect;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transect_detail, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transect_detail);

        String intentAction = getIntent().getAction();

        if ("new".equals(intentAction)) {
            action = Action.NEW;

        } else if ("edit".equals(intentAction)) {
            id = getIntent().getLongExtra("id", -1);
            action = Action.EDIT;

        } else if ("view".equals(intentAction)) {
            id = getIntent().getLongExtra("id", -1);
            action = Action.VIEW;
        }

        initGui(action, id);

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    private void initGui(Action action, long id) {

        if (id != -1) {
            transect = service.find(id);
        }

        if (action != Action.NEW) {


        }

        switch (action){

            case EDIT:
                break;
            case VIEW:
                break;
            case NEW:
                break;
        }

    }

    public void setWeather(View view) {
        Intent intent = new Intent(this, WeatherDetail.class);
        startActivity(intent);
    }

    public void setHabitat(View view) {
        Intent intent = new Intent(this, HabitatDetail.class);
        intent.putExtra(HabitatDetail.PARENT_TRANSCET, true);
        startActivity(intent);
    }

    public void addFinding(View view) {
        Intent intent = new Intent(this, FindingDetail.class);
        startActivity(intent);
    }

    public void viewFindings(View view) {
        Intent intent = new Intent(this, FindingsList.class);
        startActivity(intent);
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

    @Override
    public void onLocationChanged(Location location) {

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
}
