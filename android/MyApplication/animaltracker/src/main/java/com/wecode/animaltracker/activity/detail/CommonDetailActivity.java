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
        if ("new".equalsIgnoreCase(intentAction)) {
            action = Action.NEW;

        } else if ("edit".equalsIgnoreCase(intentAction)) {
            id = (Long) intent.getExtras().get("id");
            action = Action.EDIT;

        } else if ("view".equalsIgnoreCase(intentAction)) {
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


    @Override
    public Intent getSupportParentActivityIntent() {
        if (parentActivityClass == null) {
            return super.getSupportParentActivityIntent();
        } else {
            return getParentActivityIntentImpl();
        }
    }

    @Override
    public Intent getParentActivityIntent() {
        if (parentActivityClass == null) {
            return super.getParentActivityIntent();
        } else {
            return getParentActivityIntentImpl();
        }
    }

    private Intent getParentActivityIntentImpl() {

        Intent i = new Intent(this, parentActivityClass);

        // If you are reusing the previous Activity (i.e. bringing it to the top
        // without re-creating a new instance) set these flags:
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        return i;
    }

}
