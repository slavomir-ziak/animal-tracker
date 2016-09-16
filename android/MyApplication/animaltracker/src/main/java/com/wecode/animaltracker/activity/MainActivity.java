package com.wecode.animaltracker.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.detail.TransectDetailActivity;
import com.wecode.animaltracker.activity.detail.findings.TransectFindingDetailActivity;
import com.wecode.animaltracker.activity.list.TransectsList;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.model.CodeList;
import com.wecode.animaltracker.service.CodeListService;
import com.wecode.animaltracker.util.Permissions;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    public void fd(View view){
        Intent intent = new Intent(this, TransectFindingDetailActivity.class);
        intent.putExtra(Constants.PARENT_ACTIVITY, MainActivity.class);
        intent.setAction(Action.EDIT.toString());
        intent.putExtra("id", 1L);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FragmentDrawer drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        for (CodeList codeList : CodeListService.getInstance().list()) {
            System.out.println(codeList);
        }

        if (!Permissions.grantedOrRequestPermissions(this, 0, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            return;
        }

        testExcel();
    }

    private void testExcel() {
        //
        File excelTestDit = new File(Globals.getAppRootDir(), "excel_test");

        File input = new File(excelTestDit, "Transects-report.xls");
        File output = new File(excelTestDit, "Transects-report_2.xls");

        try {

            write(input, output);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                testExcel();
            }
    }

    public void write(File input, File output) throws IOException, WriteException, BiffException {

        Workbook wk = Workbook.getWorkbook(input);

        WritableWorkbook wkr = Workbook.createWorkbook(output, wk);

        WritableSheet sheet = wkr.getSheet(0);

        sheet.addCell(new Label(0, 0, "hi there"));

        wkr.write();
        wkr.close();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            System.out.println("Settings menu pressed");
            return true;
        }

        if (id == R.id.action_search) {
            System.out.println("Search menu pressed");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
