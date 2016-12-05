package com.wecode.animaltracker;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.orm.SugarApp;
import com.orm.SugarContext;
import com.orm.SugarDb;
import com.orm.util.MigrationFileParser;
import com.wecode.animaltracker.model.CodeList;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.service.CodeListService;
import com.wecode.animaltracker.service.SettingsDataService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

/**
 * Created by SZIAK on 9/4/2016.
 */
public class AnimalTrackerApp extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();

        System.out.println(CodeListService.getInstance().list());

        if (CodeListService.getInstance().list().size() == 0) {
            executeScript("init_db_codelists.sql");
        }

        if (SettingsDataService.getInstance().list().size() == 0) {
            executeScript("init_db_settings.sql");
        }

    }

    private void executeScript(String file) {
        try {
            InputStream is = this.getAssets().open("sugar_upgrades/" + file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            MigrationFileParser migrationFileParser = new MigrationFileParser(sb.toString());
            for(String statement: migrationFileParser.getStatements()){
                Log.i("Sugar script", statement);
                if (!statement.isEmpty()) {
                    getDb().execSQL(statement);
                }
            }

        } catch (IOException e) {
            Log.e(Globals.APP_NAME, e.getMessage());
        }

        Log.i(Globals.APP_NAME, "Script "+file+" executed");
    }

    private SQLiteDatabase getDb() {

        try {
            Field f = SugarContext.getSugarContext().getClass().getDeclaredField("sugarDb");
            f.setAccessible(true);
            SugarDb db = (SugarDb) f.get(SugarContext.getSugarContext());
            return db.getDB();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
