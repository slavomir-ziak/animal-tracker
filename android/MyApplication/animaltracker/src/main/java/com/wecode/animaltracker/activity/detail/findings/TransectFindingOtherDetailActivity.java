package com.wecode.animaltracker.activity.detail.findings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wecode.animaltracker.activity.ViewPagerAdapter;
import com.wecode.animaltracker.fragment.detail.finding.TransectFindingOtherFindingFragment;
import com.wecode.animaltracker.model.EntityName;
import com.wecode.animaltracker.service.CodeListService;
import com.wecode.animaltracker.service.TransectFindingOtherDataService;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingOtherDetailActivity extends TransectFindingCommonActivity {

    private TransectFindingOtherFindingFragment transectFindingOtherFindingFragment;

    private CodeListService codeListService = CodeListService.getInstance();

    public TransectFindingOtherDetailActivity(){
        entityName = EntityName.TRANECT_FINDING_OTHER;
    }

    protected ViewPager setupViewPager(int viewPagerId, Intent intent) {
        ViewPager viewPager = (ViewPager) findViewById(viewPagerId);

        if (adapter == null) {
            adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        }

        setupOtherFragment(adapter, intent);

        if (id != null) {
            setupPhotosFragment(adapter);
            setupSampleListFragment(adapter);
        }

        viewPager.setAdapter(adapter);
        return viewPager;
    }

    private void setupOtherFragment(ViewPagerAdapter adapter, Intent intent) {
        if (transectFindingOtherFindingFragment != null) {
            return;
        }
        transectFindingOtherFindingFragment = new TransectFindingOtherFindingFragment();
        Bundle bundle = new Bundle();
        String evidence = intent.getExtras().getString("evidence");

        if (id != null) {
            bundle.putLong("id", id);
            evidence = TransectFindingOtherDataService.getInstance().find(id).getEvidence();
        }

        if (evidence != null) {
            String localized = codeListService.getLocalisedValueByNameAndValue("findingOtherEvidence", evidence);
            bundle.putString("evidence", evidence);
            transectFindingOtherFindingFragment.setFragmentTitle(localized);
            setTitle(localized);
        }

        bundle.putString("action", action.toString());
        bundle.putLong("transectFindingSiteId", transectFindingSiteId);
        transectFindingOtherFindingFragment.setArguments(bundle);
        adapter.addFragment(transectFindingOtherFindingFragment);
    }

    public void addPhoto(View view) {
        addPhoto();
    }

    protected void saveTransectFinding() {
        Long id = transectFindingOtherFindingFragment.saveTransectFinding();
        if (this.id == null) {
            this.id = id;
            initTabLayout(getIntent());
        }
    }

    @Override
    protected boolean isChangedByUser() {
        return transectFindingOtherFindingFragment.isChangedByUser();
    }

}
