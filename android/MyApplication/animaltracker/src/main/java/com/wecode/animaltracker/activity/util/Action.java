package com.wecode.animaltracker.activity.util;

/**
 * Created by sziak on 10/3/2015.
 */
public enum Action {

    EDIT, VIEW, NEW;

    public static Action fromString(String value) {

        if (NEW.toString().equals(value)) {
            return NEW;
        } else if (EDIT.toString().equals(value)) {
            return EDIT;
        } else if (VIEW.toString().equals(value)) {
            return VIEW;
        }
        throw new EnumConstantNotPresentException(Action.class, value);
    }

    @Override
    public String toString() {
        return getClass().getName() + ":" + super.toString();
    }
}
