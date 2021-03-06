package com.wecode.animaltracker.activity.detail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.common.CommonDetailActivity;
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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        extractParams(getIntent());

        if (id != null) {
            habitatDetailView = new HabitatDetailView(this, findViewById(android.R.id.content), habitatService.find(id));
        } else {
            habitatDetailView = new HabitatDetailView(this, findViewById(android.R.id.content));
        }

    }

    @Override
    public void onBackPressed() {

        if (habitatDetailView.isChanged()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_save_changes_before_leave)
                    .setPositiveButton(R.string.save, new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int whichButton) {
                            save();
                            endActivity(RESULT_OK);
                        }
                    })
                    .setNegativeButton(R.string.dialog_discard_and_leave, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            endActivity(RESULT_CANCELED);
                        }
                    })
                    .show();
        } else {
            endActivity(habitatDetailView.getId() != null ? RESULT_OK : RESULT_CANCELED);
        }

    }

    private void save() {

        Habitat habitat = habitatDetailView.toHabitat();
        habitat = habitatService.save(habitat);

        habitatDetailView.setId(habitat.getId());
        id = habitat.getId();

        Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
    }

    private void endActivity(int resultCode) {
        Intent intent = new Intent();
        if (habitatDetailView.getId() != null && resultCode == RESULT_OK) {
            intent.putExtra("id", habitatDetailView.getId());
        }
        setResult(resultCode, intent);
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

        if (id == R.id.action_save) {
            save();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
