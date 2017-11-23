package com.wecode.animaltracker.async;

import android.os.AsyncTask;

import com.wecode.animaltracker.util.EmailService;

/**
 * Created by slavo on 2/16/2017.
 */

public class EmailSendingWorker extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] params) {
        new EmailService().sendMail(
                "test",
                "test body",
                "slavomir.ziak@gmail.com",
                "slavomir.ziak@gmail.com"
        );
        return null;
    }

}
