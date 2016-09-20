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
import com.wecode.animaltracker.export.DataExporter;
import com.wecode.animaltracker.export.TransectReportRow;
import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.model.findings.TransectFinding;
import com.wecode.animaltracker.model.findings.TransectFindingFeces;
import com.wecode.animaltracker.model.findings.TransectFindingFootprints;
import com.wecode.animaltracker.model.findings.TransectFindingOther;
import com.wecode.animaltracker.service.HabitatDataService;
import com.wecode.animaltracker.service.TransectDataService;
import com.wecode.animaltracker.service.TransectFindingFecesDataService;
import com.wecode.animaltracker.service.TransectFindingFootprintsDataService;
import com.wecode.animaltracker.service.TransectFindingOtherDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.LocationUtil;
import com.wecode.animaltracker.util.Permissions;
import com.wecode.animaltracker.view.TransectDetailView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            transectDetailView.getStartLocation().setText(LocationUtil.formatLocationToMinutesAndSeconds(currentLocation));
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
            transectDetailView.getEndLocation().setText(LocationUtil.formatLocationToMinutesAndSeconds(currentLocation));
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

        WritableWorkbook wkr = null;

        try {
            Workbook wk = Workbook.getWorkbook(input);
            wkr = Workbook.createWorkbook(output, wk);
            WritableSheet sheet = wkr.getSheet(0);
            Transect transect1 = transectDetailView.toTransect();
            DataExporter dataExporter = new DataExporter(transect1);
            for (int column = 0; column < 50; column++) {
                for (int row = 0; row < 5; row++) {
                    WritableCell cell = sheet.getWritableCell(column, row);
                    String data = dataExporter.getData(cell.getContents());
                    if (data != null) {
                        setCellText(data, cell);
                    }
                }
            }

            List<TransectReportRow> allRows = new ArrayList<>();

            for (TransectFinding transectFinding : transect1.getFindings()) {
                List<TransectReportRow> rows = new ArrayList<>();
                List<TransectFindingFootprints> footprints = TransectFindingFootprintsDataService.getInstance().findByTransectFindingId(transectFinding.getId());

                for (TransectFindingFootprints footprintFinding : footprints) {
                    TransectReportRow row = new TransectReportRow();
                    row.setFootprintsBackSize(footprintFinding.getBackSizeFormatted());
                    row.setFootprintsFrontSize(footprintFinding.getFrontSizeFormatted());
                    row.setFootprintsDirection(footprintFinding.getDirection());
                    row.setFootprintsNumberOfAnimlas(footprintFinding.getNumberOfAnimals());
                    row.setFootprintsStride(footprintFinding.getStride());
                    //row.setFootprintsSubstract(footprintFinding.getsubstrrack);
                    rows.add(row);
                }

                List<TransectFindingFeces> fecesList = TransectFindingFecesDataService.getInstance().findByTransectFindingId(transectFinding.getId());
                for (int i = 0; i < fecesList.size(); i++) {

                    TransectFindingFeces fecesFinding = fecesList.get(i);
                    TransectReportRow row;
                    if (rows.size() >= i + 1) {
                        row = rows.get(i);
                    } else {
                        row = new TransectReportRow();
                        rows.add(row);
                    }
                    row.setFecesPrey(fecesFinding.getPrey());
                    row.setFecesState(fecesFinding.getState());
                    //row.setFecesSubstract(fecesFinding.getsubstract);

                }

                int rowCounter=0;
                List<TransectFindingOther> othersList = TransectFindingOtherDataService.getInstance().findByTransectFindingId(transectFinding.getId());
                for (int i = 0; i < othersList.size(); i++) {
                    TransectFindingOther otherFindings = othersList.get(i);

                    if (otherFindings.getEvidence().equalsIgnoreCase("urine")) {
                        TransectReportRow row;
                        if (rows.size() >= rowCounter + 1) {
                            row = rows.get(rowCounter);
                        } else {
                            row = new TransectReportRow();
                            rows.add(row);
                        }
                        row.setUrineLocation(otherFindings.getObservations());
                        rowCounter++;
                    }
                }

                rowCounter=0;
                for (int i = 0; i < othersList.size(); i++) {
                    TransectFindingOther otherFindings = othersList.get(i);

                    if (!otherFindings.getEvidence().equalsIgnoreCase("urine")) {

                        TransectReportRow row;
                        if (rows.size() >= rowCounter + 1) {
                            row = rows.get(rowCounter);
                        } else {
                            row = new TransectReportRow();
                            rows.add(row);
                        }

                        row.setOtherEvidence(otherFindings.getEvidence());
                        row.setOtherObservations(otherFindings.getObservations());
                        rowCounter++;
                    }
                }


                for (TransectReportRow row : rows) {
                    row.setSpecie(transectFinding.getSpecies());
                    //row.setElevation(transectFinding.getele);
                    row.setLatitude(transectFinding.getLocationLatitude());
                    row.setLongitude(transectFinding.getLocationLongitude());
                    if (transectFinding.getHabitatId() != null) {
                        Habitat habitat = HabitatDataService.getInstance().find(transectFinding.getHabitatId());
                        row.setHabitat(habitat);
                    }
                }
                allRows.addAll(rows);
            }

            int row = 7;
            for (TransectReportRow rowData : allRows) {

                for (int col = 0; col < 20; col++) {
                    Object data = "";

                    switch(col){
                        case 0: data = String.valueOf(row - 6); break;
                        case 1: data = rowData.getSpecie(); break;
                        case 2: data = rowData.getElevationValue(); break;
                        case 3: data = rowData.getLatitudeValue(); break;
                        case 4: data = rowData.getLongitudeValue(); break;
                        case 5: data = rowData.getHabitat(); break;
                        case 6: data = rowData.getFootprintsNumberOfAnimlas(); break;
                        case 7: data = rowData.getFootprintsSubstract(); break;
                        case 8: data = rowData.getFootprintsDirection(); break;
                        case 9: data = rowData.getFootprintsStride(); break;
                        case 10: data = rowData.getFootprintsFrontSize(); break;
                        case 11: data = rowData.getFootprintsBackSize(); break;
                        //case 12: data = rowData.getSpecie(); break;
                        case 13: data = rowData.getFecesState(); break;
                        case 14: data = rowData.getFecesPrey(); break;
                        case 15: data = rowData.getFecesSubstract(); break;
                        //case 16:
                        case 17: data = rowData.getUrineLocation(); break;
                        case 18: data = rowData.getOtherEvidence(); break;
                        case 19: data = rowData.getOtherObservations(); break;

                    }

                    if (data != null) {
                        //setCellText(data.toString(), cell);
                        sheet.addCell(new Label(col, row, data.toString()));
                    }

                }

                row++;
            }


        } finally {
            if (wkr != null) {
                wkr.write();
                wkr.close();
            }
        }
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

        String routeName = transectDetailView.getRouteName().getText().toString().replaceAll(" ", "_");
        String transectDirName = "ID_" + transectDetailView.getId().getText() + "_" + routeName;

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
