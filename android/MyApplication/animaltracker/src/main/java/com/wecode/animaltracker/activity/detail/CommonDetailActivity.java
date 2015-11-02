package com.wecode.animaltracker.activity.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;

/**
 * Created by sziak on 11/2/2015.
 */
public class CommonDetailActivity extends AppCompatActivity {

    protected Action action;
    protected Class parentActivityClass;

    protected Long extractParams(Intent intent) {
        String intentAction = intent.getAction();
        Long id = null;
        if ("new".equals(intentAction)) {
            action = Action.NEW;

        } else if ("edit".equals(intentAction)) {
            id = (Long) intent.getExtras().get("id");
            action = Action.EDIT;

        } else if ("view".equals(intentAction)) {
            id = (Long) intent.getExtras().get("id");
            action = Action.VIEW;
        } else {
            throw new RuntimeException("no action defined!");
        }

        parentActivityClass = (Class) getIntent().getExtras().get(Constants.PARENT_ACTIVITY);

        if (parentActivityClass == null) {
            throw new NullPointerException("parentActivityClass is null");
        }

        return id;
    }

}
