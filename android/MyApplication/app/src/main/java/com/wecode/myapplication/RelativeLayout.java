package com.wecode.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RelativeLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_layout);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String nowDate = dateFormat.format(date);
        TextView dateView = (TextView)findViewById(R.id.dates);
        dateView.setText(nowDate);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String nowTime = timeFormat.format(date);
        TextView timeView = (TextView)findViewById(R.id.times);
        timeView.setText(nowTime);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
