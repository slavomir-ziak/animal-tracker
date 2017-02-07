package com.wecode.animaltracker.fragment;

import com.wecode.animaltracker.model.Persistable;

/**
 * Created by SZIAK on 10/1/2016.
 */

public interface Fragment {

    int getNameResourceId();

    boolean isChangedByUser();

    String getName();
}
