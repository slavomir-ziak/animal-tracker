package com.wecode.animaltracker;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by sziak on 16-May-16.
 */
public class Globals {

    public static final String APP_NAME = "AnimalTracker";

    public static final int SPINNER_ITEM_MIN_HEIGHT = 190;

    public static File getPhotosStorageDir() {
       return getStorageDir(APP_NAME);
    }

    public static File getStorageDir(String name) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), name);

        createDirectory(file);

        Log.i(Globals.APP_NAME, file.getAbsolutePath());
        return file;
    }

    private static void createDirectory(File file) {
        if (!file.exists() && !file.mkdirs()) {
            String message = "Directory " + file.getAbsolutePath() + " not created";
            Log.e(Globals.APP_NAME, message);
            throw new RuntimeException(message);
        }
    }

}
