package com.wecode.animaltracker.activity.detail;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.detail.findings.TransectFindingDetailActivity;
import com.wecode.animaltracker.activity.list.TransectFindingsList;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.service.TransectDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.LocationUtil;
import com.wecode.animaltracker.util.Permissions;
import com.wecode.animaltracker.view.TransectDetailView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jxl.CellType;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class TransectDetailActivity extends CommonDetailActivity implements LocationListener {


    private static final int SET_HABITAT_REQUEST = 0;
    private static final int SET_WEATHER_REQUEST = 1;
    private static final int ADD_FINDING_REQUEST = 2;

    private static final int ACCESS_FINE_LOCATION_REQUEST = 3;

    private static final int EDIT_START_LOCATION_REQUEST = 4;
    private static final int EDIT_END_LOCATION_REQUEST = 5;
    private static final int EXPORT_FILE_PERMISSION_REQUEST = 6;

    private static TransectDataService transectDataService = TransectDataService.getInstance();

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

        initGui();

        LocationUtil.initLocationManager(this, ACCESS_FINE_LOCATION_REQUEST);

    }

    private void initGui() {

        Transect transect = null;
        if (id != null) {
            transect = transectDataService.find(id);
        }

        transectDetailView = new TransectDetailView(this, transect);

        switch (action) {
            case EDIT:
                transectDetailView.initGuiForEdit();
                break;
            case VIEW:
                transectDetailView.initGuiForView();
                break;
            case NEW:
                transectDetailView.initGuiForNew();
                break;
        }

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

        if (requestCode == EXPORT_FILE_PERMISSION_REQUEST) {
            if (grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                exportToExcel();
            } else {
                Log.w(Globals.APP_NAME, "EXPORT_FILE_PERMISSION_REQUEST NOT granted");
            }
        }
    }

    public void startTransect(View view) {

        if (currentLocation == null) {
            Toast.makeText(this, "Location not acquired.", Toast.LENGTH_SHORT).show();
        } else {
            Log.i(Globals.APP_NAME, "Location: " + currentLocation.getLatitude() + ", " + currentLocation.getLongitude());
            transectDetailView.getStartLocation().setText(LocationUtil.formatLocation(currentLocation));
        }

        // TODO refactor this as part of TransectDetailView object
        String startDateTime = DateFormat.getDateTimeInstance().format(new Date());
        transectDetailView.getStartDateTime().setText(startDateTime);

        saveTransect();

    }

    public void endTransect(View view) {

        if (currentLocation == null) {
            Toast.makeText(this, "Location not acquired.", Toast.LENGTH_SHORT).show();
        } else {
            transectDetailView.getEndLocation().setText(LocationUtil.formatLocation(currentLocation));
        }

        String endDateTime = DateFormat.getDateTimeInstance().format(new Date());
        transectDetailView.getEndDateTime().setText(endDateTime);

        saveTransect();

    }

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

    public void addFinding(View view) {
        Intent intent = new Intent(this, TransectFindingDetailActivity.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(Action.NEW.toString());
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

            case EDIT_START_LOCATION_REQUEST:
                String location = data.getExtras().getString("location");
                transectDetailView.getStartLocation().setText(location);
                break;

            case EDIT_END_LOCATION_REQUEST:
                location = data.getExtras().getString("location");
                transectDetailView.getEndLocation().setText(location);
                break;
        }

        saveTransect();

    }

    private void saveTransect() {
        if (transectDetailView.isValid()) {
            Transect transect = transectDataService.save(transectDetailView.toTransect());
            transectDetailView.setIdValue(transect.getId());
            this.id = transect.getId();
            Toast.makeText(this, "Transect saved.", Toast.LENGTH_SHORT).show();
        } else {
            return;
        }

        action = Action.EDIT;
        transectDetailView.initGuiForEdit();
    }

    public void saveTransect(View view) {
        saveTransect();
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
    public void onBackPressed() {

        Transect transect = transectDataService.save(transectDetailView.toTransect());
        transectDetailView.setIdValue(transect.getId());

        Intent intent = new Intent();
        intent.putExtra("id", transect.getId());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void editStartLocation(View view) {
        Intent intent = new Intent(this, EditLocationActivity.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(Action.NEW.toString());
        intent.putExtra("location", transectDetailView.getStartLocation().getText().toString());
        startActivityForResult(intent, EDIT_START_LOCATION_REQUEST);
    }


    public void editEndLocation(View view) {
        Intent intent = new Intent(this, EditLocationActivity.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(Action.NEW.toString());
        intent.putExtra("location", transectDetailView.getEndLocation().getText().toString());
        startActivityForResult(intent, EDIT_END_LOCATION_REQUEST);
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


    public void export(View view) {

        if (!Permissions.grantedOrRequestPermissions(this, EXPORT_FILE_PERMISSION_REQUEST,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            return;
        }

        exportToExcel();

    }

    private void exportToExcel() {

        File excelFile = getExcelFile();
        InputStream reportStream = null;
        try {

            reportStream = getAssets().open("Transects-report.xls");

            Transect transect = transectDataService.find(id);

            createExcel(reportStream, excelFile, transect);

            Toast.makeText(this, "Exported to: " + excelFile.getAbsolutePath(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (reportStream != null) {
                try {
                    reportStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void createExcel(InputStream input, File output, Transect transect) throws IOException, WriteException, BiffException {

        Workbook wk = Workbook.getWorkbook(input);

        WritableWorkbook wkr = Workbook.createWorkbook(output, wk);

        WritableSheet sheet = wkr.getSheet(0);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                WritableCell cell = sheet.getWritableCell(0, 2);
                if (cell.getContents().equals("{transect.id}")) {
                    setCellText(transect.getId().toString(), cell);
                }
            }
        }


        wkr.write();
        wkr.close();
    }

    private void setCellText(String text, WritableCell cell) {
        if (cell.getType() == CellType.LABEL)
        {
            Label l = (Label) cell;
            l.setString(text);
        }
    }

    @SuppressLint("SimpleDateFormat")
    private File getTransectRootDirectory() {
        String date = new SimpleDateFormat("yyyy_MM_dd_").format(transectDetailView.getStartDateTimeParsed());
        String routeName = transectDetailView.getRouteName().getText().toString().replaceAll(" ", "_");
        String transectDirName = date + routeName + "_ID_" + transectDetailView.getId().getText();

        File transectRootDir = new File(Globals.getAppRootDir(), transectDirName);

        Globals.createDirectory(transectRootDir);
        return transectRootDir;
    }

    private File getExcelFile(){
        File transectDir = getTransectRootDirectory();
        File excelName = new File(transectDir, "transect_report.xls");
        int counter = 1;
        while(excelName.exists()) {
            excelName = new File(transectDir, "transect_report_" + counter + ".xls");
            counter++;
        }
        return excelName;
    }
}
