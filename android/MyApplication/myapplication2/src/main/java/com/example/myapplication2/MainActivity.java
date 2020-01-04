package com.example.myapplication2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication2.test.DatabaseHelper;
import com.example.myapplication2.test.SimpleData;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.android.ContextHolder;
import org.sqldroid.DroidDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    private String tag = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DroidDataSource dataSource = new DroidDataSource(getPackageName(), DatabaseHelper.DATABASE_NAME){

            @Override
            public Connection getConnection() throws SQLException {
                String url = "jdbc:sqldroid:" + "/data/data/" + packageName + "/databases/" + databaseName;
                return new org.sqldroid.SQLDroidDriver().connect(url , new Properties());
            }

        };

        ContextHolder.setContext(this);
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();


        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select * from simpledata")) {

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("done");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Dao<SimpleData, Integer> simpleDao = OpenHelperManager.getHelper(this, DatabaseHelper.class).getSimpleDataDao();

            SimpleData simpleData = new SimpleData(1L);
            int i = simpleDao.create(simpleData);

            Log.i(tag, String.valueOf(simpleData));

            List<SimpleData> list = simpleDao.queryForAll();


            Log.i(tag, String.valueOf(list));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
