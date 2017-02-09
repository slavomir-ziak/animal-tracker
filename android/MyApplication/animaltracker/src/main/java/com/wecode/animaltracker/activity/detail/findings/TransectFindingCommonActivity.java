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
import com.wecode.animaltracker.fragment.list.PhotosListFragment;
import com.wecode.animaltracker.fragment.list.TransectFindingSamplesListFragment;
import com.wecode.animaltracker.model.Sample;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.model.TransectFindingSite;
import com.wecode.animaltracker.service.SampleDataService;
import com.wecode.animaltracker.service.TransectDataService;
import com.wecode.animaltracker.service.TransectFindingSiteDataService;
import com.wecode.animaltracker.util.Assert;

import java.io.File;

/**
 * Created by slavo on 2/6/2017.
 */

public abstract class TransectFindingCommonActivity extends PhotoEnabledCommonActivity {

    private static final int ADD_SAMPLE_REQUEST = 100;

    protected long transectFindingSiteId;

    private SampleDataService sampleDataService = SampleDataService.getInstance();

    protected TransectFindingSamplesListFragment transectFindingSamplesListFragment;

    protected PhotosListFragment photosListFragment;

    protected ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabbed_activity);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        extractParams(getIntent());

        transectFindingSiteId = getIntent().getExtras().getLong("transectFindingSiteId");
        Assert.assertNotNullNotZero("transectFindingSiteId not present", transectFindingSiteId);
        initTabLayout(getIntent());

    }

    protected void initTabLayout(Intent intent) {
        ViewPager viewPager = setupViewPager(R.id.viewpager, intent);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    protected abstract ViewPager setupViewPager(int viewpager, Intent intent);

    @Override
    protected void refreshPhotos() {
        photosListFragment.refreshPhotos();
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

    protected void setupSampleListFragment(ViewPagerAdapter adapter) {
        Bundle bundle = new Bundle();
        bundle.putString("entityName", entityName.toString());
        bundle.putLong("transectFindingId", id);

        transectFindingSamplesListFragment = new TransectFindingSamplesListFragment();
        transectFindingSamplesListFragment.setArguments(bundle);
        adapter.addFragment(transectFindingSamplesListFragment);
    }

    protected void setupPhotosFragment(ViewPagerAdapter adapter) {
        Bundle bundle = new Bundle();
        bundle.putLong("entityId", id);
        bundle.putLong("transectId", getCurrentTransectId());
        bundle.putString("entityName", entityName.toString());

        photosListFragment = new PhotosListFragment();
        photosListFragment.setArguments(bundle);
        adapter.addFragment(photosListFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (isChangedByUser()) {
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

    protected abstract boolean isChangedByUser();

    protected abstract void saveTransectFinding();

    private void endActivity() {
        Intent intent = new Intent();
        intent.putExtra("id", id);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch(requestCode){

            case ADD_SAMPLE_REQUEST:
                String sampleNumber = data.getExtras().getString("sampleNumber");
                int sampleSequenceNumber = data.getExtras().getInt("sampleSequenceNumber", 0);
                Assert.assertNotNull("sampleNumber", sampleNumber);
                Assert.assertNotNullNotZero("sampleSequenceNumber", (long) sampleSequenceNumber);

                sampleDataService.save(new Sample(sampleNumber, id, sampleSequenceNumber, entityName));
                transectFindingSamplesListFragment.refreshSamples();
                break;
        }
    }

    public void addSample(View view) {
        Intent intent = new Intent(this, TransectFindingAddSampleActivity.class);
        startActivityForResult(intent, ADD_SAMPLE_REQUEST);
    }
}
