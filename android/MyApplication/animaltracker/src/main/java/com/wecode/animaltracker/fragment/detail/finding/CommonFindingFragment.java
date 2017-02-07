package com.wecode.animaltracker.fragment.detail.finding;

import android.os.Bundle;

import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.util.Assert;

/**
 * Created by slavo on 1/22/2017.
 */

public class CommonFindingFragment extends android.support.v4.app.Fragment {

    protected Action action;

    protected Long transectFindingSiteId;

    protected Long id;

    protected void extractParams(Bundle bundle) {

        if (transectFindingSiteId == null) {
            transectFindingSiteId = getArguments().getLong("transectFindingSiteId", 0);
            transectFindingSiteId = transectFindingSiteId == 0 ? null : transectFindingSiteId;

            action = Action.fromString(getArguments().getString("action"));
            if (transectFindingSiteId == null && action != Action.NEW ) {
                Assert.assertNotNullNotZero("transectFindingSiteId musi byt zadane", transectFindingSiteId);
            }
        }

        if (id == null) {
            id = (Long) bundle.get("id");
        }

        if (action != Action.NEW) {
            Assert.assertNotNullNotZero("id musi byt zadane", id);
        }

    }

}
