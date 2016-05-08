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
import android.widget.TextView;
import android.widget.Toast;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.detail.TransectFindingDetailActivity;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.activity.util.TransectFindingListViewDataAdapter;
import com.wecode.animaltracker.data.TransectFindingDataService;
import com.wecode.animaltracker.model.TransectFinding;

import java.util.List;

public class TransectFindingsList extends AppCompatActivity {

    private TransectFindingDataService transectFindingDataService = TransectFindingDataService.getInstance();
    private Action action;
    private Long transectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transect_findings_list);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent =  getIntent();
        action = Action.fromString(intent.getAction());
        transectId = (Long) intent.getExtras().get("id");

        final List<TransectFinding> list = transectFindingDataService.list();

        TransectFindingListViewDataAdapter adapter = new TransectFindingListViewDataAdapter(this, list);
        ListView itemsListView = (ListView) findViewById(R.id.transectFindingsList);

        itemsListView.setAdapter(adapter);
        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toast.makeText(TransectFindingsList.this, ("You Clicked at " + view), Toast.LENGTH_SHORT).show();

                TextView transectFindingId = (TextView) view.findViewById(R.id.transectFindingListItemID);

                Intent intent = new Intent(TransectFindingsList.this, TransectFindingDetailActivity.class);
                intent.putExtra(Constants.PARENT_ACTIVITY, TransectFindingsList.class);
                intent.putExtra("id", Long.valueOf(transectFindingId.getText().toString()));
                intent.setAction(action.toString());
                startActivity(intent);

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
}
