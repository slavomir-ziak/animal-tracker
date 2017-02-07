package com.wecode.animaltracker.activity.detail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.common.PhotoEnabledCommonActivity;
import com.wecode.animaltracker.activity.detail.findings.TransectFindingFecesDetailActivity;
import com.wecode.animaltracker.activity.detail.findings.TransectFindingFootprintsDetailActivity;
import com.wecode.animaltracker.activity.detail.findings.TransectFindingOtherDetailActivity;
import com.wecode.animaltracker.activity.location.EditLocationDMSFormatActivity;
import com.wecode.animaltracker.activity.location.EditLocationDecimalFormatActivity;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.adapter.TransectFindingDetailsListAdapter;
import com.wecode.animaltracker.model.EntityName;
import com.wecode.animaltracker.model.Persistable;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.model.TransectFindingSite;
import com.wecode.animaltracker.model.findings.TransectFindingFeces;
import com.wecode.animaltracker.model.findings.TransectFindingFootprints;
import com.wecode.animaltracker.model.findings.TransectFindingOther;
import com.wecode.animaltracker.service.SettingsDataService;
import com.wecode.animaltracker.service.TransectDataService;
import com.wecode.animaltracker.service.TransectFindingSiteDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.LocationUtil;
import com.wecode.animaltracker.util.Permissions;
import com.wecode.animaltracker.view.TransectFindingSiteDetailView;

import java.io.File;
import java.util.List;

public class TransectFindingSiteDetailActivity extends PhotoEnabledCommonActivity implements LocationListener {

    private static final int SET_HABITAT_REQUEST = 10;
    private static final int ACCESS_FINE_LOCATION_REQUEST = 400;

    private static final int EDIT_LOCATION_REQUEST = 500;

    private TransectFindingSiteDataService transectFindingSiteDataService = TransectFindingSiteDataService.getInstance();

    private TransectFindingSiteDetailView transectFindingSiteDetailView;

    private Long transectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transect_finding_site);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        LocationUtil.initLocationManager(this, ACCESS_FINE_LOCATION_REQUEST);

        extractParams(getIntent());

        transectId = getIntent().getExtras().getLong("transectId");

        if (id != null) {
            TransectFindingSite transectFindingSite = transectFindingSiteDataService.find(id);
            transectFindingSiteDetailView = new TransectFindingSiteDetailView(this, transectFindingSite);
            initFindings();

        } else {
            transectFindingSiteDetailView = new TransectFindingSiteDetailView(this, transectId);
        }

        initGui();

        entityName = EntityName.TRANECT_FINDING_SITE;
    }

    private void initFindings() {
        if (id == null) {
            return;
        }

        final List<Persistable> findingDetails = transectFindingSiteDataService.findFindingDetails(id);
        ListView findingDetailsView = (ListView) findViewById(R.id.findingDetails);
        findingDetailsView.setAdapter(new TransectFindingDetailsListAdapter(this, findingDetails));
        findingDetailsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Persistable persistable = findingDetails.get(position);
                if (persistable instanceof TransectFindingFootprints) {
                    TransectFindingSiteDetailActivity.this.editFootprints(persistable.getId());
                } else if (persistable instanceof TransectFindingFeces) {
                    TransectFindingSiteDetailActivity.this.editFeces(persistable.getId());
                } else if (persistable instanceof TransectFindingOther) {
                    TransectFindingSiteDetailActivity.this.editOther(persistable.getId());
                } else {
                    throw new RuntimeException("cannot handle " + persistable);
                }
            }
        });
    }

    private void initGui() {
        switch (action) {
            case EDIT:
                transectFindingSiteDetailView.initGuiForEdit();
                break;
            case VIEW:
                transectFindingSiteDetailView.initGuiForView();
                break;
            case NEW:
                transectFindingSiteDetailView.initGuiForNew();
                break;
        }

        findViewById(R.id.findingLocationValue).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                editLocation(v);
            }
        });
    }


    @Override
    public void onBackPressed() {

        if (transectFindingSiteDetailView.isChanged()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_save_changes_before_leave)
                    .setPositiveButton(R.string.save, new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int whichButton) {
                            saveTransectFinding();
                            endActivity();
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

    private void endActivity() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_transect_finding_site, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.transect_finding_action_habitat) {
            setHabitat(null);
            return true;
        }

        if (id == R.id.action_save) {
            saveTransectFinding();
            return true;
        }

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setHabitat(View view) {
        Intent intent = new Intent(this, HabitatDetailActivity.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, TransectFindingSiteDetailActivity.class);

        Long habitatId = transectFindingSiteDetailView.getHabitatId();
        if (habitatId != null) {
            intent.setAction(action.toString()); // edit or view
            intent.putExtra("id", habitatId);
        } else {
            intent.setAction(Action.NEW.toString());
        }

        startActivityForResult(intent, SET_HABITAT_REQUEST);
    }

    public void editFootprints(Long footprintsId) {
        commonEdit(footprintsId, TransectFindingFootprintsDetailActivity.class);
    }

    public void editFeces(Long fecesId) {
        commonEdit(fecesId, TransectFindingFecesDetailActivity.class);
    }

    public void editOther(Long otherId) {
        commonEdit(otherId, TransectFindingOtherDetailActivity.class);
    }

    private void commonEdit(Long id, Class<? extends AppCompatActivity> activityClass){
        Intent intent = new Intent(this, activityClass);
        intent.putExtra("id", id);
        intent.putExtra("transectFindingSiteId", transectFindingSiteDetailView.getId());
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(Action.EDIT.toString());
        startActivityForResult(intent, 0);
    }

    public void addFeces(View view) {
        commonAdd(TransectFindingFecesDetailActivity.class);
    }

    public void addFootprints(View view) {
        commonAdd(TransectFindingFootprintsDetailActivity.class);
    }

    public void addOthers(View view) {
        commonAdd(TransectFindingOtherDetailActivity.class);
    }

    public void addUrine(View view) {
        commonOtherAdd("Urine");
    }

    public void addScratches(View view) {
        commonOtherAdd("Scratches");
    }

    public void addHairs(View view) {
        commonOtherAdd("Hairs");
    }

    private void commonAdd(Class<? extends AppCompatActivity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtra("transectFindingSiteId", transectFindingSiteDetailView.getId());
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(Action.NEW.toString());
        startActivityForResult(intent, 0);
    }

    private void commonOtherAdd(String evidence) {
        Intent intent = new Intent(this, TransectFindingOtherDetailActivity.class);
        intent.putExtra("transectFindingSiteId", transectFindingSiteDetailView.getId());
        intent.putExtra("evidence", evidence);
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(Action.NEW.toString());
        startActivityForResult(intent, 0);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == ACCESS_FINE_LOCATION_REQUEST) {
            if (Permissions.grantResults(grantResults)) {
                Log.i(Globals.APP_NAME, "ACCESS_FINE_LOCATION granted");
                LocationUtil.initLocationManager(this, ACCESS_FINE_LOCATION_REQUEST);
            } else {
                Log.w(Globals.APP_NAME, "ACCESS_FINE_LOCATION NOT granted");
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        initFindings();

        if (resultCode == RESULT_CANCELED) {
            //Toast.makeText(this, "Operation canceled.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (resultCode != RESULT_OK) {
            Toast.makeText(this, getString(R.string.problem_with_creating, getNameForRequestCode(requestCode)), Toast.LENGTH_SHORT).show();
            return;
        }

        switch (requestCode) {
            case SET_HABITAT_REQUEST:
                Long id = data.getExtras().getLong("id");
                Assert.assertNotNullNotZero("HabitatId", id);

                int code = transectFindingSiteDetailView.getHabitatId() == null ? R.string.habitat_created : R.string.habitat_modified;
                Toast.makeText(this, getString(code), Toast.LENGTH_SHORT).show();

                transectFindingSiteDetailView.setHabitatId(id);
                saveTransectFinding();
                break;

            case EDIT_LOCATION_REQUEST:
                String location = data.getExtras().getString("location");
                Assert.assertNotNull("location", location);
                transectFindingSiteDetailView.getLocation().setText(location);
                break;
        }

    }

    @Override
    protected void refreshPhotos() {

    }

    private void saveTransectFinding() {
        TransectFindingSite transectFindingSite = transectFindingSiteDataService.save(transectFindingSiteDetailView.toTransectFinding());
        id = transectFindingSite.getId();

        Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();

        transectFindingSiteDetailView.setId(transectFindingSite.getId());
        transectFindingSiteDetailView.initGuiForEdit();
    }


    @Override
    public void onLocationChanged(Location location) {
        if (transectFindingSiteDetailView.getTransectFindingSite() == null || !transectFindingSiteDetailView.getTransectFindingSite().hasLocation()) {
            transectFindingSiteDetailView.setLocation(location);
        }
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

    private String getNameForRequestCode(int requestCode) {
        switch (requestCode) {
            case SET_HABITAT_REQUEST:
                return "habitat";
            default:
                return "UNKNOWN";
        }
    }

    public void editLocation(View view) {
        Intent intent;
        if (SettingsDataService.getInstance().get().isLocationDMS()) {
            intent = new Intent(this, EditLocationDMSFormatActivity.class);
        } else {
            intent = new Intent(this, EditLocationDecimalFormatActivity.class);
        }

        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(Action.NEW.toString());
        intent.putExtra("location", transectFindingSiteDetailView.getLocation().getText().toString());
        startActivityForResult(intent, EDIT_LOCATION_REQUEST);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem transect_finding_action_add_photo = menu.findItem(R.id.transect_finding_action_add_photo);
        MenuItem transect_finding_action_habitat = menu.findItem(R.id.transect_finding_action_habitat);
        MenuItem transect_finding_action_photos = menu.findItem(R.id.transect_finding_action_photos);

        if (transectFindingSiteDetailView.getId() == null) {
            transect_finding_action_add_photo.setEnabled(false);
            transect_finding_action_habitat.setEnabled(false);
            transect_finding_action_photos.setEnabled(false);
        } else {
            transect_finding_action_add_photo.setEnabled(true);
            transect_finding_action_habitat.setEnabled(true);
            transect_finding_action_photos.setEnabled(true);
        }
        return true;
    }

    @Override
    protected Long getCurrentTransectId() {
        return transectId;
    }

    @Override
    protected File getPhotoDirectory() {
        Transect transect = TransectDataService.getInstance().find(transectId);
        return Globals.getTransectPhotosDirectory(transect);
    }


}
