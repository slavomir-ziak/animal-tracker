package com.wecode.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class IntentTest2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_test2);
        // First intent to use ACTION_VIEW action with correct data
        Button startBrowser_a = (Button) findViewById(R.id.start_browser_a);
        startBrowser_a.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://www.example.com"));
                startActivity(i);
            }
        });
        // Second intent to use LAUNCH action with correct data
        Button startBrowser_b = (Button) findViewById(R.id.start_browser_b);
        startBrowser_b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent("com.example.intentdemo.LAUNCH",
                        Uri.parse("http://www.example.com"));
                startActivity(i);
            }
        });
        // Third intent to use LAUNCH action with incorrect data
        Button startBrowser_c = (Button) findViewById(R.id.start_browser_c);
        startBrowser_c.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent("com.example.intentdemo.LAUNCH",
                        Uri.parse("https://www.example.com"));
                try {
                    startActivity(i);
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(getBaseContext(), "no activity", Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intent_test2, menu);
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
