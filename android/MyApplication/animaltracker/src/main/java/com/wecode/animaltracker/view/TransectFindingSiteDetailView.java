package com.wecode.animaltracker.view;

import android.app.Activity;
import android.location.Location;
import android.widget.Button;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.model.CodeList;
import com.wecode.animaltracker.model.TransectFindingSite;
import com.wecode.animaltracker.service.CodeListService;
import com.wecode.animaltracker.service.TransectFindingSiteDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.LocationUtil;

import java.util.Locale;

/**
 * Created by sziak on 10-Apr-16.
 */
public class TransectFindingSiteDetailView  implements ChangeableView {

    private TransectFindingSiteDataService service = TransectFindingSiteDataService.getInstance();

    private Long id;
    private Long habitatId;
    private Long transectId;
    private CodeListSpinnerView species;

    private TextView location;
    private TextView idLabel;

    private final Button addFececButton;
    private final Button addFootprintsButton;
    private final Button addUrineButton;
    private final Button addHairsButton;
    private final Button addScratchesButton;
    private final Button addOtherButton;

    private TransectFindingSite transectFindingSite;

    private int initialHash;

    public TransectFindingSiteDetailView(Activity context, TransectFindingSite transectFindingSite) {
        this(context, transectFindingSite.getId());
        this.transectFindingSite = transectFindingSite;

        Assert.assertNotNull("transectFindingSite cannot be null!", transectFindingSite);
        bind(transectFindingSite);
    }

    public TransectFindingSiteDetailView(Activity context, Long transectId) {

        this.transectId = transectId;

        species = new CodeListSpinnerView(R.id.findingSpeciesValue, CodeList.Name.findingSpeciesTypes.name(), context, false, getDefaultAnimal());
        location = (TextView) context.findViewById(R.id.findingLocationValue);
        addFececButton = (Button) context.findViewById(R.id.transectFindingAddFecesButton);
        addFootprintsButton = (Button) context.findViewById(R.id.transectFindingAddFootprintsButton);
        addOtherButton = (Button) context.findViewById(R.id.transectFindingAddOtherButton);
        addUrineButton = (Button) context.findViewById(R.id.transectFindingAddUrineButton);
        addHairsButton = (Button) context.findViewById(R.id.transectFindingAddHairsButton);
        addScratchesButton = (Button) context.findViewById(R.id.transectFindingAddScratchesButton);
        idLabel = (TextView) context.findViewById(R.id.idLabel);
        initialHash = hashCode();
    }

    private String getDefaultAnimal() {
        return CodeListService.getInstance().getLocalisedValueByNameAndValue(CodeList.Name.findingSpeciesTypes, "Wolf");
    }

    private void bind(TransectFindingSite transectFindingSite) {

        species.select(transectFindingSite.getSpecies());

        if (transectFindingSite.hasLocation()) {
            setLocation(transectFindingSite.getLocationLatitude(), transectFindingSite.getLocationLongitude(), transectFindingSite.getLocationElevation());
        }

        habitatId = transectFindingSite.getHabitatId();
        transectId = transectFindingSite.getTransectId();
        id = transectFindingSite.getId();
        if (id != null) {
            idLabel.setText(String.format(Locale.getDefault(), "ID: %d", id));
        }
        initialHash = hashCode();
    }

    private void setLocation(Double latitude, Double longiture, Double altitude) {
        location.setText(LocationUtil.formatLocation(latitude, longiture, altitude));
    }

    private double[] getLocationParsed() {
        return LocationUtil.parseLocation(location.getText().toString());
    }

    public void setLocation(Location location) {
        setLocation(location.getLatitude(), location.getLongitude(), location.getAltitude());
    }

    public TransectFindingSite toTransectFinding() {

        if (id != null) {
            transectFindingSite = service.find(id);
        } else {
            transectFindingSite = new TransectFindingSite();
        }
        transectFindingSite.setSpecies(species.getSelectedCodeListValue());

        if (location.getText().toString().length() > 0) {
            double[] parsed = getLocationParsed();

            transectFindingSite.setLocationLatitude(parsed[0]);
            transectFindingSite.setLocationLongitude(parsed[1]);
            transectFindingSite.setLocationElevation(parsed[2]);
        }

        transectFindingSite.setHabitatId(habitatId);
        transectFindingSite.setTransectId(transectId);
        transectFindingSite.setId(id);
        initialHash = hashCode();

        return transectFindingSite;
    }

    public Long getHabitatId() {
        return habitatId;
    }

    public void setHabitatId(Long habitatId) {
        this.habitatId = habitatId;
    }

    public Long getTransectId() {
        return transectId;
    }

    public void setTransectId(Long transectId) {
        this.transectId = transectId;
    }

    public void setId(Long id) {
        this.id = id;
        idLabel.setText(String.format(Locale.getDefault(), "ID: %d", id));
        initialHash = hashCode();
    }

    public Long getId() {
        return id;
    }

    public void initGuiForEdit() {
        enableButtons(true);
    }

    public void initGuiForView() {
        enableButtons(false);
    }

    public void initGuiForNew() {
        enableButtons(false);
    }

    private void enableButtons(boolean enable) {
        addFececButton.setEnabled(enable);
        addFootprintsButton.setEnabled(enable);
        addOtherButton.setEnabled(enable);
        addUrineButton.setEnabled(enable);
        addHairsButton.setEnabled(enable);
        addScratchesButton.setEnabled(enable);
    }

    public TextView getLocation() {
        return location;
    }

    public TransectFindingSite getTransectFindingSite() {
        return transectFindingSite;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (habitatId != null ? habitatId.hashCode() : 0);
        result = 31 * result + (transectId != null ? transectId.hashCode() : 0);
        result = 31 * result + (species.getSelectedCodeListValue() != null ? species.getSelectedCodeListValue().hashCode() : 0);
        result = 31 * result + (location.getText() != null ? location.getText().toString().hashCode() : 0);
        return result;
    }

    public boolean isChanged() {
        return initialHash != hashCode();
    }
}
