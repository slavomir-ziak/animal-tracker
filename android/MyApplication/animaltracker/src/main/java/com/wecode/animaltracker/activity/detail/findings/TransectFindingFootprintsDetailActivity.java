package com.wecode.animaltracker.activity.detail.findings;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.detail.CommonDetailActivity;
import com.wecode.animaltracker.model.findings.TransectFindingFootprints;
import com.wecode.animaltracker.service.FootprintsFindingDataService;
import com.wecode.animaltracker.view.findings.TransectFindingFootprintsView;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingFootprintsDetailActivity extends CommonDetailActivity {

    private long transectFindingId;

    private FootprintsFindingDataService footprintsFindingDataService = FootprintsFindingDataService.getInstance();

    private TransectFindingFootprintsView transectFindingFootprintsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transect_finding_footprints);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        extractParams(getIntent());

        transectFindingId = getIntent().getExtras().getLong("transectFindingId");

        if (id != null) {
            TransectFindingFootprints transectFinding = footprintsFindingDataService.find(id);
            transectFindingFootprintsView = new TransectFindingFootprintsView(this, transectFinding);
        } else {
            transectFindingFootprintsView = new TransectFindingFootprintsView(this, transectFindingId);
        }

        //initGui(transectFindingView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);
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

        return super.onOptionsItemSelected(item);
    }

    private void saveTransectFinding() {
        TransectFindingFootprints transectFindingFootprints = footprintsFindingDataService.save(transectFindingFootprintsView.toFootprintsFinding());
        transectFindingFootprints.setId(transectFindingFootprints.getId());
        this.id = transectFindingFootprints.getId();
        Toast.makeText(this, "Footprints saved.", Toast.LENGTH_SHORT).show();
    }

}
