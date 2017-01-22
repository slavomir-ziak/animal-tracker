package com.wecode.animaltracker.activity.detail.findings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.common.PhotoEnabledCommonActivity;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.model.TransectFindingSite;
import com.wecode.animaltracker.model.findings.TransectFindingFootprints;
import com.wecode.animaltracker.service.TransectDataService;
import com.wecode.animaltracker.service.TransectFindingSiteDataService;
import com.wecode.animaltracker.service.TransectFindingFootprintsDataService;
import com.wecode.animaltracker.view.findings.TransectFindingFootprintsView;

import java.io.File;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingFootprintsDetailActivity extends PhotoEnabledCommonActivity {

    private long transectFindingId;

    private TransectFindingFootprintsDataService transectFindingFootprintsDataService = TransectFindingFootprintsDataService.getInstance();

    private TransectFindingFootprintsView transectFindingFootprintsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transect_finding_footprints);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        extractParams(getIntent());

        transectFindingId = getIntent().getExtras().getLong("transectFindingId");

        if (id != null) {
            TransectFindingFootprints transectFinding = transectFindingFootprintsDataService.find(id);
            transectFindingFootprintsView = new TransectFindingFootprintsView(this, transectFinding);
        } else {
            transectFindingFootprintsView = new TransectFindingFootprintsView(this, transectFindingId);
        }

        //initGui(transectFindingView);

        entityName = Photo.EntityName.TRANECT_FINDING_FOOTPRINT;
    }

    @Override
    protected void refreshPhotos() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transect_finding_footprints, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

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

    @Override
    public void onBackPressed() {

        if (transectFindingFootprintsView.isChanged()) {
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
        intent.putExtra("id", id);
        setResult(RESULT_OK, intent);
        finish();
    }
    private void saveTransectFinding() {
        TransectFindingFootprints transectFindingFootprints = transectFindingFootprintsDataService.save(transectFindingFootprintsView.toFootprintsFinding());
        transectFindingFootprintsView.setId(transectFindingFootprints.getId());

        this.id = transectFindingFootprints.getId();
        Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected File getPhotoDirectory() {
        return Globals.getTransectPhotosDirectory(getTransect());
    }

    @Override
    protected Long getCurrentTransectId() {
        return getTransect().getId();
    }

    private Transect getTransect() {
        TransectFindingSite transectFindingSite = TransectFindingSiteDataService.getInstance().find(transectFindingId);
        return TransectDataService.getInstance().find(transectFindingSite.getTransectId());
    }
}
