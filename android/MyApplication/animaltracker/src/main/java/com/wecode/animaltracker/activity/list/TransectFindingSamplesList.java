package com.wecode.animaltracker.activity.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.adapter.TransectFindingSamplesListAdapter;
import com.wecode.animaltracker.model.Sample;
import com.wecode.animaltracker.service.SampleDataService;
import com.wecode.animaltracker.util.Assert;

import java.util.List;

/**
 * Created by SZIAK on 7/31/2016.
 */
public class TransectFindingSamplesList extends AppCompatActivity {

    private SampleDataService sampleDataService = SampleDataService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transect_finding_sample_list);

        Long transectFindingId = getIntent().getLongExtra("transectFindingId", 0);
        Assert.isTrue("transectFindingId missing ", transectFindingId > 0);

        List<Sample> samples = sampleDataService.findByTransectFindingId(transectFindingId);

        ListView listView = (ListView) findViewById(R.id.transectFindingDetailSamplesListView);


        ListAdapter adapter = new TransectFindingSamplesListAdapter(this, samples);
        listView.setAdapter(adapter);
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
}
