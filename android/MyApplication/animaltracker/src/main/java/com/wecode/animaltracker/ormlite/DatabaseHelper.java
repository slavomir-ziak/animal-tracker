package com.wecode.animaltracker.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.wecode.animaltracker.model.Persistable;

import java.sql.SQLException;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, createDbName(context.getPackageName()), null, DATABASE_VERSION);
	}

	@NonNull
	public static String createDbName(String packageName) {
		return packageName + "_" + (isTesting() ? "testing_" : "") + "ormlite_1.db";
	}

	private static boolean isTesting() {
		try {
			Class.forName("org.junit.Assert");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	/**
	 * This is called when the database is first created. Usually you should call createTable statements here to create
	 * the tables that will store your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, final ConnectionSource connectionSource) {

	}

	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {

	}

	/**
	 * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
	 * value.
	 * @param clazz
	 */
	public <T extends Persistable> Dao<T, Long> createDao(Class<T> clazz) throws SQLException {
		return getDao(clazz);
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
	}

}