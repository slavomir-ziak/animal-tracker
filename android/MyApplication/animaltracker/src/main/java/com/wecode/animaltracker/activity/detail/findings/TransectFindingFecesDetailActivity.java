package com.wecode.animaltracker.activity.detail.findings;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.detail.CommonDetailActivity;
import com.wecode.animaltracker.model.findings.TransectFindingFeces;
import com.wecode.animaltracker.service.FecesFindingDataService;
import com.wecode.animaltracker.view.findings.TransectFindingFecesView;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingFecesDetailActivity extends CommonDetailActivity {

    private long transectFindingId;

    private FecesFindingDataService fecesFindingDataService = FecesFindingDataService.getInstance();

    private TransectFindingFecesView transectFindingFecesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transect_finding_feces);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        extractParams(getIntent());

        transectFindingId = getIntent().getExtras().getLong("transectFindingId");

        if (id != null) {
            TransectFindingFeces transectFinding = fecesFindingDataService.find(id);
            transectFindingFecesView = new TransectFindingFecesView(this, transectFinding);
        } else {
            transectFindingFecesView = new TransectFindingFecesView(this, transectFindingId);
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
        TransectFindingFeces transectFindingFeces = fecesFindingDataService.save(transectFindingFecesView.toFecesFinding());
        transectFindingFeces.setId(transectFindingFeces.getId());
        this.id = transectFindingFeces.getId();
        Toast.makeText(this, "Feces saved.", Toast.LENGTH_SHORT).show();
    }

}
