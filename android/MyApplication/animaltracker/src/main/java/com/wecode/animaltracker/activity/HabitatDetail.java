package com.wecode.animaltracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.SpinnersHelper;

public class HabitatDetail extends AppCompatActivity {

    public static final String PARENT_TRANSCET = "PARENT_TRANSCET";

    private boolean parentIsActivityTransect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitat_detail);

        SpinnersHelper.setSpinnerData(this, R.id.habitatTypeValue, R.array.habitatTypes);
        SpinnersHelper.setSpinnerData(this, R.id.habitatTrackValue, R.array.habitatTrackTypes);
        SpinnersHelper.setSpinnerData(this, R.id.habitatForestAgeValue, R.array.habitatForestAgeTypes);
        SpinnersHelper.setSpinnerData(this, R.id.habitatTreeTypeValue, R.array.habitatTreeTypes);
        SpinnersHelper.setSpinnerData(this, R.id.habitatForestTypeValue, R.array.habitatForestTypes);

        parentIsActivityTransect = getIntent().getBooleanExtra(HabitatDetail.PARENT_TRANSCET, false);
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
        Intent i = null;

        // Here you need to do some logic to determine from which Activity you came.
        // example: you could pass a variable through your Intent extras and check that.
        if (parentIsActivityTransect) {
            i = new Intent(this, TransectDetail.class);
            // set any flags or extras that you need.
            // If you are reusing the previous Activity (i.e. bringing it to the top
            // without re-creating a new instance) set these flags:
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            // if you are re-using the parent Activity you may not need to set any extras
            //i.putExtra("someExtra", "whateverYouNeed");
        } else {
            i = new Intent(this, FindingDetail.class);
            // same comments as above
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            //i.putExtra("someExtra", "whateverYouNeed");
        }

        return i;
    }
}
