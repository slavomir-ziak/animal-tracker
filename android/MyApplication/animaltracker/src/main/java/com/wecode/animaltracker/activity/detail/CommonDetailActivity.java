package com.wecode.animaltracker.activity.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.util.Assert;

/**
 * Created by sziak on 11/2/2015.
 */
public class CommonDetailActivity extends AppCompatActivity {

    protected Action action;
    protected Class parentActivityClass;
    protected Long id;

    private String getActionForChildActivity(Action action, Long childId) {
        if (action == Action.VIEW && childId == null) {

        }
        return null;
    }

    protected void extractParams(Intent intent) {

        action = Action.fromString(intent.getAction());
        id = (Long) intent.getExtras().get("id");

        if (action == Action.VIEW) {
            Assert.assertNotNull("id musi byt zadane", id);
        }

        parentActivityClass = (Class) getIntent().getExtras().get(Constants.PARENT_ACTIVITY);
        Assert.assertNotNull("parentActivityClass musi byt zadane", parentActivityClass);

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
