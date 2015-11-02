package com.wecode.animaltracker.activity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.SpinnersHelper;
import com.wecode.animaltracker.data.HabitatDataService;
import com.wecode.animaltracker.view.HabitatDetailView;

public class HabitatDetailActivity extends CommonDetailActivity {

    private HabitatDetailView habitatDetailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitat_detail);

        SpinnersHelper.setSpinnerData(this, R.id.habitatTypeValue, R.array.habitatTypes);
        SpinnersHelper.setSpinnerData(this, R.id.habitatTrackValue, R.array.habitatTrackTypes);
        SpinnersHelper.setSpinnerData(this, R.id.habitatForestAgeValue, R.array.habitatForestAgeTypes);
        SpinnersHelper.setSpinnerData(this, R.id.habitatTreeTypeValue, R.array.habitatTreeTypes);
        SpinnersHelper.setSpinnerData(this, R.id.habitatForestTypeValue, R.array.habitatForestTypes);

        Long habitatId = extractParams(getIntent());

        habitatDetailView = new HabitatDetailView(this, HabitatDataService.INSTANCE.find(habitatId));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_habitat_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public Intent getSupportParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    @Override
    public Intent getParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    private Intent getParentActivityIntentImpl() {
        Intent i = new Intent(this, parentActivityClass);

        // If you are reusing the previous Activity (i.e. bringing it to the top
        // without re-creating a new instance) set these flags:
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        return i;
    }
}
