package com.wecode.animaltracker.activity.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.detail.TransectDetailActivity;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.adapter.TransectListViewDataAdapter;
import com.wecode.animaltracker.service.TransectDataService;

public class TransectsList extends AppCompatActivity {

    private static final int DISPLAY_TRANSECT_DETAIL = 0;
    private static TransectDataService transectDataService = TransectDataService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transects_list);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        refreshTransects();

    }

    private void refreshTransects() {
        TransectListViewDataAdapter adapter = new TransectListViewDataAdapter(this, transectDataService.listAll());

        ListView itemsListView = (ListView) findViewById(R.id.transectsList);

        itemsListView.setAdapter(adapter);
        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(TransectsList.this, TransectDetailActivity.class);
                intent.putExtra(Constants.PARENT_ACTIVITY, TransectsList.class);
                intent.putExtra("id", id);
                intent.setAction(Action.EDIT.toString());
                startActivityForResult(intent, DISPLAY_TRANSECT_DETAIL);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_transect_detail, menu);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refreshTransects();
        if (resultCode == RESULT_CANCELED) {
            //Toast.makeText(this, R.string.operation_canceled, Toast.LENGTH_SHORT).show();
            return;
        }

        if (resultCode != RESULT_OK) {
            Toast.makeText(this, R.string.detail_display_problem, Toast.LENGTH_SHORT).show();
        }
    }
}
