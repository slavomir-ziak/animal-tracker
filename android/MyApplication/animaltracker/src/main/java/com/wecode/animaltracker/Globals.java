package com.wecode.animaltracker;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by sziak on 16-May-16.
 */
public class Globals {

    public static final String APP_NAME = "AnimalTracker";

    public static File getPhotosStorageDir() {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), APP_NAME);

        if (!file.exists() && !file.mkdirs()) {
            Log.e("", "Directory not created");
        }
        return file;
    }

}
