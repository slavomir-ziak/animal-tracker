package com.wecode.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by sziak on 9/26/2015.
 */
public class MyService extends Service {

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        broadcastIntent("Service started");
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

        /* for (int i = 0; i < 10; i++) {
            Toast.makeText(this, "Service Started " + i, Toast.LENGTH_LONG).show();
        }*/

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        broadcastIntent("Service destroyed.");
        //Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    // broadcast a custom intent.
    public void broadcastIntent(String data)
    {
        Intent intent = new Intent();
        intent.setAction(Constants.CUSTOM_INTENT);
        intent.putExtra(Constants.MYSERVICE_STATUS, data);
        sendBroadcast(intent);
    }


}