package com.wecode.animaltracker.sqldroid;

import android.util.Log;

import org.sqldroid.DroidDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class MyDroidDataSource extends DroidDataSource {

    private static final String TAG = MyDroidDataSource.class.getSimpleName();

    public MyDroidDataSource(String packageName, String dbName) {
        super(packageName, dbName);
    }

    @Override
    public Connection getConnection() throws SQLException {

        // BECAUSE ormlite uses sqlite db on this path:
        String path = "/data/data/" + packageName + "/databases/" ;
        String url = "jdbc:sqldroid:" + path + databaseName;
        boolean mkdir = new File(path).mkdir();
        Log.i(TAG, "database directory " + path + " created; " + mkdir);
        return new org.sqldroid.SQLDroidDriver().connect(url , new Properties());
    }

}
