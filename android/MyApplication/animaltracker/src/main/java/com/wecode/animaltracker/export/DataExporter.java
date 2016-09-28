package com.wecode.animaltracker.export;

import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.util.LocationUtil;

import java.text.DateFormat;

/**
 * Created by SZIAK on 9/19/2016.
 */
public class DataExporter {

    private Transect transect;

    public DataExporter(Transect transect) {
        this.transect = transect;
    }

    public String getData(String key) {

        switch (key) {
            case "ID": return transect.getId().toString();
            case "REGION": return transect.getRouteName();
            //case "LOCALISATION": return "TODO";
            case "TRACKER": return "Nuno Guimaraes";
            case "DATE": {
                if (transect.getStartDateTime() != null) {
                    return DateFormat.getInstance().format(transect.getStartDateTime());
                } else {
                    return "n/a";
                }

            }
            case "GRID_NUMBER": return transect.getColumn().toString();
            case "START_LOCATION": return LocationUtil.formatLocation(transect.getStartLatitude(), transect.getStartLongitude());
            case "END_LOCATION": return LocationUtil.formatLocation(transect.getEndLatitude(), transect.getEndLongitude());
            //case "TOTAL_LENGTH": return transect.toString();
            //case "WEATHER": return transect.toString();
        }

        return null;
    }
}
