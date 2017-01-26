package com.wecode.animaltracker.activity.detail.findings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.ViewPagerAdapter;
import com.wecode.animaltracker.activity.common.PhotoEnabledCommonActivity;
import com.wecode.animaltracker.activity.detail.TransectFindingAddSampleActivity;
import com.wecode.animaltracker.fragment.PhotosFragment;
import com.wecode.animaltracker.fragment.TransectFindingFootpritsFindingFragment;
import com.wecode.animaltracker.fragment.TransectFindingSamplesListFragment;
import com.wecode.animaltracker.model.EntityName;
import com.wecode.animaltracker.model.Sample;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.model.TransectFindingSite;
import com.wecode.animaltracker.service.SampleDataService;
import com.wecode.animaltracker.service.TransectDataService;
import com.wecode.animaltracker.service.TransectFindingSiteDataService;
import com.wecode.animaltracker.service.TransectFindingFootprintsDataService;
import com.wecode.animaltracker.util.Assert;

import java.io.File;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingFootprintsDetailActivity extends PhotoEnabledCommonActivity {

    private static final int ADD_SAMPLE_REQUEST = 100;

    private long transectFindingSiteId;

    private TransectFindingFootprintsDataService transectFindingFootprintsDataService = TransectFindingFootprintsDataService.getInstance();

    private SampleDataService sampleDataService = SampleDataService.getInstance();

    private TransectFindingFootpritsFindingFragment transectFindingFootpritsFindingFragment;

    private TransectFindingSamplesListFragment transectFindingSamplesListFragment;

    private PhotosFragment photosFragment;

    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transect_detail);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        extractParams(getIntent());

        transectFindingSiteId = getIntent().getExtras().getLong("transectFindingSiteId");
        entityName = EntityName.TRANECT_FINDING_FOOTPRINT;

        initTabLayout();

    }

    private void initTabLayout() {
        ViewPager viewPager = setupViewPager(R.id.viewpager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private ViewPager setupViewPager(int viewPagerId) {
        ViewPager viewPager = (ViewPager) findViewById(viewPagerId);

        if (adapter == null) {
            adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
            setupFootprintsFragment(adapter);
        }

        if (id != null) {
            setupPhotosFragment(adapter);
            setupSampleListFragment(adapter);
        }

        viewPager.setAdapter(adapter);
        return viewPager;
    }

    private void setupSampleListFragment(ViewPagerAdapter adapter) {
        Bundle bundle = new Bundle();
        bundle.putString("entityName", EntityName.TRANECT_FINDING_FOOTPRINT.toString());
        bundle.putLong("transectFindingId", id);

        transectFindingSamplesListFragment = new TransectFindingSamplesListFragment();
        transectFindingSamplesListFragment.setArguments(bundle);
        adapter.addFragment(transectFindingSamplesListFragment);
    }

    private void setupPhotosFragment(ViewPagerAdapter adapter) {
        Bundle bundle = new Bundle();
        bundle.putLong("entityId", id);
        bundle.putLong("transectId", getCurrentTransectId());
        bundle.putString("entityName", EntityName.TRANECT_FINDING_FOOTPRINT.toString());

        photosFragment = new PhotosFragment();
        photosFragment.setArguments(bundle);
        adapter.addFragment(photosFragment);
    }

    private void setupFootprintsFragment(ViewPagerAdapter adapter) {
        transectFindingFootpritsFindingFragment = new TransectFindingFootpritsFindingFragment();
        Bundle bundle = new Bundle();
        if (id != null) {
            bundle.putLong("id", id);
        }

        bundle.putString("action", action.toString());
        bundle.putLong("transectFindingSiteId", new Long(transectFindingSiteId));
        transectFindingFootpritsFindingFragment.setArguments(bundle);
        adapter.addFragment(transectFindingFootpritsFindingFragment);
    }

    public void addPhoto(View view) {
        addPhoto();
    }

    public void addSample(View view) {
        Intent intent = new Intent(this, TransectFindingAddSampleActivity.class);
        startActivityForResult(intent, ADD_SAMPLE_REQUEST);
    }

    @Override
    protected void refreshPhotos() {
        photosFragment.refreshPhotos();
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

        if (transectFindingFootpritsFindingFragment.isChangedByUser()) {
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
        transectFindingFootpritsFindingFragment.saveTransectFinding();
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
        TransectFindingSite transectFindingSite = TransectFindingSiteDataService.getInstance().find(transectFindingSiteId);
        return TransectDataService.getInstance().find(transectFindingSite.getTransectId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){

            case ADD_SAMPLE_REQUEST:
                String sampleNumber = data.getExtras().getString("sampleNumber");
                int sampleSequenceNumber = data.getExtras().getInt("sampleSequenceNumber", 0);
                Assert.assertNotNull("sampleNumber", sampleNumber);
                Assert.assertNotNullNotZero("sampleSequenceNumber", (long) sampleSequenceNumber);

                sampleDataService.save(new Sample(sampleNumber, id, sampleSequenceNumber, EntityName.TRANECT_FINDING_FOOTPRINT));
                transectFindingSamplesListFragment.refreshSamples();
                break;
        }
    }
}
