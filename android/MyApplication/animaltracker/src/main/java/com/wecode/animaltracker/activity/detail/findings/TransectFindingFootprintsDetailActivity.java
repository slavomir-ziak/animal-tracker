package com.wecode.animaltracker.activity.detail.findings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wecode.animaltracker.activity.ViewPagerAdapter;
import com.wecode.animaltracker.fragment.detail.finding.TransectFindingFootpritsFindingFragment;
import com.wecode.animaltracker.model.EntityName;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingFootprintsDetailActivity extends TransectFindingCommonActivity {

    private TransectFindingFootpritsFindingFragment transectFindingFootpritsFindingFragment;

    public TransectFindingFootprintsDetailActivity(){
        entityName = EntityName.TRANECT_FINDING_FOOTPRINT;
    }

    protected ViewPager setupViewPager(int viewPagerId, Intent intent) {
        ViewPager viewPager = (ViewPager) findViewById(viewPagerId);

        if (adapter == null) {
            adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        }

        setupFootprintsFragment(adapter);

        if (id != null) {
            setupPhotosFragment(adapter);
        }

        viewPager.setAdapter(adapter);
        return viewPager;
    }

    private void setupFootprintsFragment(ViewPagerAdapter adapter) {
        if (transectFindingFootpritsFindingFragment != null) {
            return;
        }
        transectFindingFootpritsFindingFragment = new TransectFindingFootpritsFindingFragment();
        Bundle bundle = new Bundle();
        if (id != null) {
            bundle.putLong("id", id);
        }

        bundle.putString("action", action.toString());
        bundle.putLong("transectFindingSiteId", transectFindingSiteId);
        transectFindingFootpritsFindingFragment.setArguments(bundle);
        adapter.addFragment(transectFindingFootpritsFindingFragment);
    }

    public void addPhoto(View view) {
        addPhoto();
    }

    protected void saveTransectFinding() {
        Long id = transectFindingFootpritsFindingFragment.saveTransectFinding();
        if (this.id == null) {
            this.id = id;
            initTabLayout(getIntent());
        }
    }

    @Override
    protected boolean isChangedByUser() {
        return transectFindingFootpritsFindingFragment.isChangedByUser();
    }
}
