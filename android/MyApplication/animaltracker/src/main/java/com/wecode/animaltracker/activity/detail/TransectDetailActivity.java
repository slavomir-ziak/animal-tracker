package com.wecode.animaltracker.activity.detail;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.list.FindingsList;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.data.TransectDataService;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.view.TransectDetailView;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;

public class TransectDetailActivity extends CommonDetailActivity implements LocationListener {

    private static TransectDataService service = TransectDataService.INSTANCE;

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

        Intent intent = getIntent();

        Long id = extractParams(intent);

        Transect transect = null;
        if (id != null) {
            transect = service.find(id);
        }

        initGui(action, transect);

        initLocationManager();

    }

    private void initLocationManager() {
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

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

    public void startTransect(View view) {

        if (currentLocation == null) {
            Toast.makeText(this, "Location not acquired, is your GPS on?", Toast.LENGTH_SHORT).show();
            return;
        }

        String startDateTime = DateFormat.getDateTimeInstance().format(new Date());
        transectDetailView.getStartDateTime().setText(startDateTime);

        BigDecimal startLocationLatitude = new BigDecimal(currentLocation.getLatitude());
        startLocationLatitude = startLocationLatitude.setScale(6, BigDecimal.ROUND_DOWN);
        BigDecimal startLocationLongitude = new BigDecimal(currentLocation.getLongitude());
        startLocationLongitude = startLocationLongitude.setScale(6, BigDecimal.ROUND_DOWN);

        transectDetailView.getStartLocation().setText(startLocationLatitude.toString() +
                " " + startLocationLongitude.toString());

        service.save(transectDetailView.toTransect());
    }

    public void endTransect(View view) {

        if (currentLocation == null) {
            Toast.makeText(this, "Location not acquired, is your GPS on?", Toast.LENGTH_SHORT).show();
            return;
        }

        String endDateTime = DateFormat.getDateTimeInstance().format(new Date());
        transectDetailView.getEndDateTime().setText(endDateTime);

        BigDecimal endLocationLatitude = new BigDecimal(currentLocation.getLatitude());
        endLocationLatitude = endLocationLatitude.setScale(6, BigDecimal.ROUND_DOWN);
        BigDecimal endLocationLongitude = new BigDecimal(currentLocation.getLongitude());
        endLocationLongitude = endLocationLongitude.setScale(6, BigDecimal.ROUND_DOWN);

        transectDetailView.getEndLocation().setText(endLocationLatitude.toString() +
                " " + endLocationLongitude.toString());

        service.save(transectDetailView.toTransect());
    }

    public void setWeather(View view) {
        Intent intent = new Intent(this, WeatherDetailActivity.class);
        startActivity(intent);
    }

    public void setHabitat(View view) {
        Intent intent = new Intent(this, HabitatDetailActivity.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, FindingDetailActivity.class);
        startActivity(intent);
    }

    public void addFinding(View view) {
        Intent intent = new Intent(this, FindingDetailActivity.class);
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


    @Override
    public Intent getSupportParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    @Override
    public Intent getParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    private Intent getParentActivityIntentImpl() {
        Intent i = new Intent(this, parentActivityClass);

        // If you are reusing the previous Activity (i.e. bringing it to the top
        // without re-creating a new instance) set these flags:
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        return i;
    }
}
