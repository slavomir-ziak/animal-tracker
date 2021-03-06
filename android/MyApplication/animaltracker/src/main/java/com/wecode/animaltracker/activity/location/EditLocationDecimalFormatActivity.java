package com.wecode.animaltracker.activity.location;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.common.CommonDetailActivity;
import com.wecode.animaltracker.util.LocationUtil;
import com.wecode.animaltracker.view.location.EditLocationDecimalView;

public class EditLocationDecimalFormatActivity extends CommonDetailActivity implements LocationListener {

    private static final int ACCESS_FINE_LOCATION_REQUEST = 1;
    private EditLocationDecimalView editLocatioView;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location_decimal);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String location = getIntent().getStringExtra("location");

        editLocatioView = new EditLocationDecimalView(location, this);

        extractParams(getIntent());

        LocationUtil.initLocationManager(this, ACCESS_FINE_LOCATION_REQUEST);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            if (editLocatioView.validate(this)) {
                endActivity(RESULT_OK);
                return true;
            }
        }

        if (id == R.id.action_location_acquire) {
            if (currentLocation == null) {
                Toast.makeText(EditLocationDecimalFormatActivity.this, R.string.location_not_acquired, Toast.LENGTH_SHORT).show();
                return true;
            }
            editLocatioView.initFromLocation(currentLocation);
            return true;
        }

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (editLocatioView.isChanged()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_save_changes_before_leave)
                    .setPositiveButton(R.string.save, new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (editLocatioView.validate(EditLocationDecimalFormatActivity.this)) {
                                endActivity(RESULT_OK);
                            }
                        }
                    })
                    .setNegativeButton(R.string.dialog_discard_and_leave, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            endActivity(RESULT_CANCELED);
                        }
                    })
                    .show();
        } else {
            endActivity(RESULT_CANCELED);
        }

    }

    private void endActivity(int resultCode) {
        Intent intent = new Intent();
        if (resultCode == RESULT_OK) {
            intent.putExtra("location", editLocatioView.getLocation());
        }
        setResult(resultCode, intent);
        finish();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.currentLocation = location;
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

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }
}
