package com.wecode.animaltracker.activity.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.ListViewDataAdapter;

public class TransectsList extends AppCompatActivity {

    String[] names = {
            "Google Plus",
            "Twitter"
    };

    Integer[] imageId = {
            R.drawable.beer,
            R.drawable.beer,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transects_list);
        ListViewDataAdapter adapter = new ListViewDataAdapter(this, names, imageId);
        ListView itemsListView = (ListView) findViewById(R.id.transectsList);

        itemsListView.setAdapter(adapter);
        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toast.makeText(TransectsList.this, ("You Clicked at " + names[+position]), Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(PhotosList.this, ItemDetailActivity.class);

                intent.putExtra(ItemDetailActivity.ITEM_ID, names[+position]);
                startActivity(intent);*/
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transect_detail, menu);
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
