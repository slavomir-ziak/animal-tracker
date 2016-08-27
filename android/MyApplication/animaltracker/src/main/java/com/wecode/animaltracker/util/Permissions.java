package com.wecode.animaltracker.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.wecode.animaltracker.Globals;

import java.util.Arrays;

/**
 * Created by SZIAK on 8/28/2016.
 */
public class Permissions {

    public static boolean grantedOrRequestPermissions(Activity context, int requestCode, String ... permissions) {

        boolean granted = true;

        for (String permission : permissions) {
            granted = granted && ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        }

        if (!granted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.requestPermissions(permissions, requestCode);
                Log.i(Globals.APP_NAME, Arrays.toString(permissions) + " not granted, but requested");
                return false;
            }
            Log.i(Globals.APP_NAME, Arrays.toString(permissions) + " not granted");
            return false;
        }

        Log.i(Globals.APP_NAME, Arrays.toString(permissions) + " granted");
        return true;
    }

    public static boolean grantResults(int[] grantResults) {
        boolean granted = true;
        for (int grantResult : grantResults) {
            granted = granted && grantResult == android.content.pm.PackageManager.PERMISSION_GRANTED;
        }
        return granted;
    }
}
