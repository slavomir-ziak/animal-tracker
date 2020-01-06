package com.wecode.animaltracker;

import android.app.Application;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.wecode.animaltracker.model.CodeList;
import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.model.Sample;
import com.wecode.animaltracker.model.Settings;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.model.TransectFindingSite;
import com.wecode.animaltracker.model.Weather;
import com.wecode.animaltracker.model.findings.TransectFindingFeces;
import com.wecode.animaltracker.model.findings.TransectFindingFootprints;
import com.wecode.animaltracker.model.findings.TransectFindingOther;
import com.wecode.animaltracker.ormlite.DatabaseHelper;
import com.wecode.animaltracker.service.CodeListService;
import com.wecode.animaltracker.service.HabitatDataService;
import com.wecode.animaltracker.service.PhotoDataService;
import com.wecode.animaltracker.service.SampleDataService;
import com.wecode.animaltracker.service.SettingsDataService;
import com.wecode.animaltracker.service.TransectDataService;
import com.wecode.animaltracker.service.TransectFindingFecesDataService;
import com.wecode.animaltracker.service.TransectFindingFootprintsDataService;
import com.wecode.animaltracker.service.TransectFindingOtherDataService;
import com.wecode.animaltracker.service.TransectFindingSiteDataService;
import com.wecode.animaltracker.service.WeatherDataService;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.android.ContextHolder;
import org.sqldroid.DroidDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by SZIAK on 9/4/2016.
 */
public class AnimalTrackerApp extends Application {

    private static String TAG = AnimalTrackerApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        flyaway();
    }

    private void flyaway() {

        DroidDataSource dataSource = new DroidDataSource(getPackageName(), DatabaseHelper.createDbName(this.getPackageName())) {

            @Override
            public Connection getConnection() throws SQLException {

                // BECAUSE ormlite uses sqlite db on this path:
                String url = "jdbc:sqldroid:" + "/data/data/" + packageName + "/databases/" + databaseName;
                return new org.sqldroid.SQLDroidDriver().connect(url , new Properties());
            }

        };

        ContextHolder.setContext(this);
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        try {

            HabitatDataService.initialize(
                    OpenHelperManager.getHelper(this, DatabaseHelper.class).createDao(Habitat.class)
            );
            CodeListService.initialize(
                    OpenHelperManager.getHelper(this, DatabaseHelper.class).createDao(CodeList.class)
            );
            PhotoDataService.initialize(
                    OpenHelperManager.getHelper(this, DatabaseHelper.class).createDao(Photo.class)
            );
            SampleDataService.initialize(
                    OpenHelperManager.getHelper(this, DatabaseHelper.class).createDao(Sample.class)
            );
            SettingsDataService.initialize(
                    OpenHelperManager.getHelper(this, DatabaseHelper.class).createDao(Settings.class)
            );
            TransectDataService.initialize(
                    OpenHelperManager.getHelper(this, DatabaseHelper.class).createDao(Transect.class)
            );
            TransectFindingFecesDataService.initialize(
                    OpenHelperManager.getHelper(this, DatabaseHelper.class).createDao(TransectFindingFeces.class)
            );
            TransectFindingFootprintsDataService.initialize(
                    OpenHelperManager.getHelper(this, DatabaseHelper.class).createDao(TransectFindingFootprints.class)
            );
            TransectFindingOtherDataService.initialize(
                    OpenHelperManager.getHelper(this, DatabaseHelper.class).createDao(TransectFindingOther.class)
            );
            TransectFindingSiteDataService.initialize(
                    OpenHelperManager.getHelper(this, DatabaseHelper.class).createDao(TransectFindingSite.class)
            );
            WeatherDataService.initialize(
                    OpenHelperManager.getHelper(this, DatabaseHelper.class).createDao(Weather.class)
            );

        } catch (SQLException e) {
            Log.e(TAG, "", e);
            throw new RuntimeException(e);
        }


    }



}
