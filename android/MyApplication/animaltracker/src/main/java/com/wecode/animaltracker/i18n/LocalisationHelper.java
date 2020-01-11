package com.wecode.animaltracker.i18n;

import com.wecode.animaltracker.model.CodeList;

import java.util.Locale;

public class LocalisationHelper {

    public static String getLocalisedValue(CodeList codeList) {
        String language = Locale.getDefault().getLanguage();
        switch (language) {
            case "sk": return codeList.getValueSk();
            case "fr": return codeList.getValueFr();
            case "pt": return codeList.getValuePt();
            default: return codeList.getValue();
        }
    }

    public static void setAllValuesSame(String value, CodeList codeList) {
        codeList.setValueSk(value);
        codeList.setValueFr(value);
        codeList.setValuePt(value);
        codeList.setValue(value);
    }

    public static String getValueColumn() {
        String language = Locale.getDefault().getLanguage();
        switch (language) {
            case "sk":
            case "fr":
            case "pt": return "VALUE_" + language.toUpperCase();
            default: return CodeList.VALUE_COLUMN;
        }
    }
}
