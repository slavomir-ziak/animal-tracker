package com.wecode.animaltracker.activity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.SpinnersHelper;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.service.HabitatDataService;
import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.util.LocationUtil;
import com.wecode.animaltracker.view.HabitatDetailView;

import java.io.File;

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

        extractParams(getIntent());

        if (id != null) {
            habitatDetailView = new HabitatDetailView(this, findViewById(android.R.id.content), habitatService.find(id));
        } else {
            habitatDetailView = new HabitatDetailView(this, findViewById(android.R.id.content));
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
        getMenuInflater().inflate(R.menu.menu_habitat_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
