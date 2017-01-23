package com.wecode.animaltracker.activity.common;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.list.PhotosList;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.model.Sample;
import com.wecode.animaltracker.service.PhotosDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.Permissions;

import java.io.File;
import java.util.UUID;

/**
 * Created by sziak on 11/2/2015.
 */
public abstract class CommonDetailActivity extends AppCompatActivity {

    protected Action action;
    protected Class parentActivityClass;
    protected Long id;

    protected void extractParams(Intent intent) {

        action = Action.fromString(intent.getAction());
        id = (Long) intent.getExtras().get("id");

        if (action != Action.NEW ) {
            Assert.assertNotNullNotZero("id musi byt zadane", id);
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
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return i;
    }

}
