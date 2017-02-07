package com.wecode.animaltracker.activity.detail.findings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wecode.animaltracker.activity.ViewPagerAdapter;
import com.wecode.animaltracker.fragment.detail.finding.TransectFindingFecesFindingFragment;
import com.wecode.animaltracker.model.EntityName;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingFecesDetailActivity extends TransectFindingCommonActivity {

    private TransectFindingFecesFindingFragment transectFindingFecesFindingFragment;

    public TransectFindingFecesDetailActivity() {
        entityName = EntityName.TRANECT_FINDING_FECES;
    }

    protected ViewPager setupViewPager(int viewPagerId, Intent intent) {
        ViewPager viewPager = (ViewPager) findViewById(viewPagerId);

        if (adapter == null) {
            adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        }

        setupFecesFragment(adapter);

        if (id != null) {
            setupPhotosFragment(adapter);
            setupSampleListFragment(adapter);
        }

        viewPager.setAdapter(adapter);
        return viewPager;
    }

    @Override
    protected boolean isChangedByUser() {
        return transectFindingFecesFindingFragment.isChangedByUser();
    }

    private void setupFecesFragment(ViewPagerAdapter adapter) {
        if (transectFindingFecesFindingFragment != null){
            return;
        }

        transectFindingFecesFindingFragment = new TransectFindingFecesFindingFragment();
        Bundle bundle = new Bundle();
        if (id != null) {
            bundle.putLong("id", id);
        }
        bundle.putString("action", action.toString());
        bundle.putLong("transectFindingSiteId", transectFindingSiteId);
        transectFindingFecesFindingFragment.setArguments(bundle);
        adapter.addFragment(transectFindingFecesFindingFragment);
    }

    public void addPhoto(View view) {
        addPhoto();
    }

    @Override
    protected void saveTransectFinding() {
        Long id = transectFindingFecesFindingFragment.saveTransectFinding();
        if (this.id == null) {
            this.id = id;
            initTabLayout(getIntent());
        }
    }
}
