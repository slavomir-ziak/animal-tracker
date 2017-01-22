package com.wecode.animaltracker.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.detail.TransectDetailActivity;
import com.wecode.animaltracker.activity.detail.TransectFindingSiteDetailActivity;
import com.wecode.animaltracker.activity.list.TransectsList;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.fragment.FragmentDrawer;
import com.wecode.animaltracker.service.SettingsDataService;
import com.wecode.animaltracker.util.Permissions;
import com.wecode.animaltracker.util.StringUtils;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

       /* FragmentDrawer drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);*/

        if (!Permissions.grantedOrRequestPermissions(this, 0,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            return;
        }

        if (StringUtils.isEmpty(SettingsDataService.getInstance().get().getTrackerName())){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_open_settings)
                    .setMessage(R.string.dialog_open_settings_message)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int whichButton) {
                            openSettings();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    })
                    /*.setNeutralButton(R.string.dialog_close_and_dont_show_again, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    })*/
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        Log.d(Globals.APP_NAME, "onDrawerItemSelected() called with: " + "view = [" + view + "], position = [" + position + "]");
    }

    public void newTransect(View view){
        Intent intent = new Intent(this, TransectDetailActivity.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, MainActivity.class);
        intent.setAction(Action.NEW.toString());
        startActivity(intent);
    }
    public void viewTransectsList(View view){
        Intent intent = new Intent(this, TransectsList.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            openSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


}
