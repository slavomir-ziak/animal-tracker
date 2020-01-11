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
import com.wecode.animaltracker.sqldroid.MyDroidDataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.android.ContextHolder;

import java.sql.SQLException;

/**
 * Created by SZIAK on 9/4/2016.
 */
public class LcsiWildLifeTrackerApp extends Application {

    private static final String TAG = LcsiWildLifeTrackerApp.class.getSimpleName();

    public static String databaseVersion = "n/a";

    @Override
    public void onCreate() {
        super.onCreate();
        flyaway();
        initDaos();
    }

    private void flyaway() {

        ContextHolder.setContext(this);
        MyDroidDataSource dataSource = new MyDroidDataSource(getPackageName(), DatabaseHelper.createDbName(this.getPackageName()));
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();

        try {
            flyway.migrate();

            MigrationInfo[] all = flyway.info().all();
            if (all.length > 0) {
                databaseVersion = all[all.length - 1].getVersion().getVersion();
            }
        } catch (Exception e) {
            Log.e(TAG, "during flyway migration", e);
            throw new RuntimeException(e);
        }
    }

    private void initDaos() {
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
            Log.e(TAG, "during init daos", e);
            throw new RuntimeException(e);
        }
    }
}
