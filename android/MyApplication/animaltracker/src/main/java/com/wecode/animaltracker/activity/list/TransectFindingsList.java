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
import com.wecode.animaltracker.activity.detail.findings.TransectFindingDetailActivity;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.adapter.TransectFindingListViewDataAdapter;
import com.wecode.animaltracker.model.findings.TransectFinding;
import com.wecode.animaltracker.service.TransectFindingDataService;

import java.util.List;

public class TransectFindingsList extends AppCompatActivity {

    private static final int DISPLAY_TRANSECT_FINDING_DETAIL = 0;

    private TransectFindingDataService transectFindingDataService = TransectFindingDataService.getInstance();
    private Action action;
    private Long transectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transect_finding_list);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent =  getIntent();
        action = Action.fromString(intent.getAction());
        transectId = (Long) intent.getExtras().get("transectId");

        refreshTransectFindings();

    }

    private void refreshTransectFindings() {
        final List<TransectFinding> list = transectFindingDataService.findByTransectId(transectId);

        TransectFindingListViewDataAdapter adapter = new TransectFindingListViewDataAdapter(this, list);
        ListView itemsListView = (ListView) findViewById(R.id.transectFindingsList);

        itemsListView.setAdapter(adapter);
        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TextView transectFindingId = (TextView) view.findViewById(R.id.transectFindingListItemID);

                Intent intent = new Intent(TransectFindingsList.this, TransectFindingDetailActivity.class);
                intent.putExtra(Constants.PARENT_ACTIVITY, TransectFindingsList.class);
                intent.putExtra("id", Long.valueOf(transectFindingId.getText().toString()));
                intent.putExtra("transectId", transectId);
                intent.setAction(action.toString());
                startActivityForResult(intent, DISPLAY_TRANSECT_FINDING_DETAIL);

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Operation canceled.", Toast.LENGTH_LONG).show();
            return;
        }

        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "Problem with displying tranect finding detail.", Toast.LENGTH_LONG).show();
            return;
        }

        switch(requestCode) {
            case DISPLAY_TRANSECT_FINDING_DETAIL:
                Long id = data.getExtras().getLong("id");
                Toast.makeText(this, "Transect finding saved, ID = " + id, Toast.LENGTH_LONG).show();
                refreshTransectFindings();
        }

    }

}
