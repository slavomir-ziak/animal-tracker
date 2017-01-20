package com.wecode.animaltracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;

import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.service.TransectDataService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sziak on 16-May-16.
 */
public class Globals {

    public static final String APP_NAME = "LCSI-WildlifeTracker";

    public static File getAppRootDir() {

        File file = new File(Environment.getExternalStorageDirectory(), APP_NAME);

        createDirectory(file);

        Log.i(Globals.APP_NAME, file.getAbsolutePath());
        return file;
    }

    public static void createDirectory(File file) {
        if (!file.exists() && !file.mkdirs()) {
            String message = "Directory " + file.getAbsolutePath() + " not created";
            Log.e(Globals.APP_NAME, message);
            throw new RuntimeException(message);
        }
    }

    public static String formatForUser(File file) {
        String absoluteFilePath = file.getAbsolutePath();
        String externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        return "<External storage>" + absoluteFilePath.replace(externalStoragePath, "");
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTransectRootDirectoryName(Transect transect) {

        if (transect.getRootDirectoryName() != null) {
            File file = new File(getAppRootDir(), transect.getRootDirectoryName());
            if (file.exists()) {
                return transect.getRootDirectoryName();
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");
        Date startDateTime = transect.getStartDateTime() != null ? transect.getStartDateTime() : new Date();
        String transectRootDirectory = String.format(Locale.getDefault(), "%03d_%s_%s", transect.getId(), transect.getRouteName(), simpleDateFormat.format(startDateTime));

        int counter = 0;
        boolean created = new File(getAppRootDir(), transectRootDirectory).mkdirs();

        while (!created) {
            counter++;
            transectRootDirectory  = transectRootDirectory + "_" + counter;
            created = new File(getAppRootDir(), transectRootDirectory ).mkdirs();
            if (counter > 100) {
                throw new RuntimeException("Could not create directory: " + created);
            }
        }

        return transectRootDirectory;
    }

    public static File getTransectPhotosDirectory(Transect transect) {
        if (transect.getRootDirectoryName() == null) {
            String transectRootDirectory = getTransectRootDirectoryName(transect);
            transect.setRootDirectoryName(transectRootDirectory);
            transect = TransectDataService.getInstance().save(transect);
        }
        File transectRootDir = new File(Globals.getAppRootDir(), transect.getRootDirectoryName());
        File photosDir = new File(transectRootDir, "Photos");
        Globals.createDirectory(photosDir);
        return photosDir;
    }

    public static void refreshFileSystem(Context context) {
        MediaScannerConnection.scanFile(context, new String[]{Globals.getAppRootDir().getAbsolutePath()}, null, null);
    }


}
