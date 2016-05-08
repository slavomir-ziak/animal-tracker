package com.wecode.animaltracker.activity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.SpinnersHelper;
import com.wecode.animaltracker.service.HabitatDataService;
import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.view.HabitatDetailView;

public class HabitatDetailActivity extends CommonDetailActivity {

    private HabitatDetailView habitatDetailView;

    private HabitatDataService habitatService = HabitatDataService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitat_detail);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SpinnersHelper.setSpinnerData(this, R.id.habitatTypeValue, R.array.habitatTypes);
        SpinnersHelper.setSpinnerData(this, R.id.habitatTrackValue, R.array.habitatTrackTypes);
        SpinnersHelper.setSpinnerData(this, R.id.habitatForestAgeValue, R.array.habitatForestAgeTypes);
        SpinnersHelper.setSpinnerData(this, R.id.habitatTreeTypeValue, R.array.habitatTreeTypes);
        SpinnersHelper.setSpinnerData(this, R.id.habitatForestTypeValue, R.array.habitatForestTypes);

        extractParams(getIntent());

        if (id != null) {
            habitatDetailView = new HabitatDetailView(this, habitatService.find(id));
        } else {
            habitatDetailView = new HabitatDetailView(this);
        }

    }

    @Override
    public void onBackPressed() {
        Habitat t = habitatDetailView.toHabitat();
        habitatService.save(t);

        Intent intent = new Intent();
        intent.putExtra("id", t.getId());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_habitat_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
