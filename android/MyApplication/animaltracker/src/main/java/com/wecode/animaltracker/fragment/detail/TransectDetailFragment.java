package com.wecode.animaltracker.fragment.detail;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.LocationProvidingActivity;
import com.wecode.animaltracker.activity.detail.TransectDetailActivity;
import com.wecode.animaltracker.activity.location.EditLocationDMSFormatActivity;
import com.wecode.animaltracker.activity.location.EditLocationDecimalFormatActivity;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.activity.util.Constants;
import com.wecode.animaltracker.fragment.Fragment;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.service.SettingsDataService;
import com.wecode.animaltracker.service.TransectDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.util.LocationUtil;
import com.wecode.animaltracker.view.TransectDetailView;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by SZIAK on 10/1/2016.
 */

public class TransectDetailFragment extends android.support.v4.app.Fragment implements Fragment {

    private static final int EDIT_START_LOCATION_REQUEST = 4;
    private static final int EDIT_END_LOCATION_REQUEST = 5;

    private static TransectDataService transectDataService = TransectDataService.getInstance();

    private TransectDetailView transectDetailView;

    private Long transectId;

    private Action action;

    public TransectDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_transect_detail_fragment, container, false);

        if (transectId == null) {
            transectId = getArguments().getLong("transectId", 0);
            transectId = transectId == 0 ? null : transectId;

            action = Action.fromString(getArguments().getString("action"));
            if (transectId == null && action != Action.NEW ) {
                Assert.assertNotNullNotZero("transectId musi byt zadane", transectId);
            }
        }

        initGui(view);
        return view;
    }

    private void initGui(View view) {

        Transect transect = null;
        if (transectId != null) {
            transect = transectDataService.find(transectId);
        }

        transectDetailView = new TransectDetailView(view, getActivity(), transect);

        switch (action) {
            case EDIT:
                transectDetailView.initGuiForEdit();
                break;
            case VIEW:
                transectDetailView.initGuiForView();
                break;
            case NEW:
                transectDetailView.initGuiForNew();
                break;
        }

        View.OnClickListener editEndLocationListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEndLocation();
            }
        };

        View.OnClickListener editStartLocationListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editStartLocation();
            }
        };

        view.findViewById(R.id.transectFindingEditStartLocation).setOnClickListener(editStartLocationListener);
        view.findViewById(R.id.transectStartLocationValue).setOnClickListener(editStartLocationListener);

        view.findViewById(R.id.transectFindingEditEndtLocation).setOnClickListener(editEndLocationListener);
        view.findViewById(R.id.transectEndLocationValue).setOnClickListener(editEndLocationListener);

        view.findViewById(R.id.startTransectButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTransect();
            }
        });
        view.findViewById(R.id.endTransectButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTransect();
            }
        });

        view.findViewById(R.id.transectDetailAddFindingButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TransectDetailActivity) getActivity()).addFinding();
            }
        });

    }

    public void startTransect() {

        if (getCurrentLocation() == null) {
            Toast.makeText(getActivity(), R.string.location_not_acquired, Toast.LENGTH_SHORT).show();
        } else {
            Log.i(Globals.APP_NAME, "Location: " + getCurrentLocation().getLatitude() + ", " + getCurrentLocation().getLongitude());
            transectDetailView.getStartLocation().setText(LocationUtil.formatLocation(getCurrentLocation()));
        }

        // TODO refactor this as part of TransectDetailView object
        String startDateTime = DateFormat.getDateTimeInstance().format(new Date());
        transectDetailView.getStartDateTime().setText(startDateTime);

        ((TransectDetailActivity) getActivity()).saveAll();
    }

    private void endTransect() {

        new AlertDialog.Builder(getActivity())
            .setTitle(R.string.dialog_end_transect_title)
            .setMessage(R.string.dialog_end_transect)
            .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int whichButton) {

                if (getCurrentLocation() == null) {
                    Toast.makeText(getActivity(), R.string.location_not_acquired, Toast.LENGTH_SHORT).show();
                } else {
                    transectDetailView.getEndLocation().setText(LocationUtil.formatLocation(getCurrentLocation()));
                }

                String endDateTime = DateFormat.getDateTimeInstance().format(new Date());
                transectDetailView.getEndDateTime().setText(endDateTime);

                validateAndSaveTransect();

                }
            })
            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            })
        .show();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_CANCELED) {
            //Toast.makeText(getActivity(), R.string.operation_canceled, Toast.LENGTH_SHORT).show();
            return;
        }

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(getActivity(), getString(R.string.problem_with_creating, getNameForRequestCode(requestCode)), Toast.LENGTH_SHORT).show();
            return;
        }

        switch (requestCode) {

            case EDIT_START_LOCATION_REQUEST:
                String location = data.getExtras().getString("location");
                transectDetailView.getStartLocation().setText(location);
                break;

            case EDIT_END_LOCATION_REQUEST:
                location = data.getExtras().getString("location");
                transectDetailView.getEndLocation().setText(location);
                break;
        }


    }

    public Transect validateAndSaveTransect() {
        if (transectDetailView == null) {
            return null;
        }

        if (!transectDetailView.isValid(getActivity())) {
            Toast.makeText(getActivity(), R.string.transect_not_valid, Toast.LENGTH_SHORT).show();
            return null;
        }
        Transect transect = transectDataService.save(transectDetailView.toTransect());

        String rootDirectoryName = Globals.getTransectRootDirectoryName(transect);
        Globals.refreshFileSystem(getActivity());
        transect.setRootDirectoryName(rootDirectoryName);

        transectDataService.save(transect);

        transectDetailView.setIdValue(transect.getId());
        transectId = transect.getId();
        action = Action.EDIT;
        transectDetailView.initGuiForEdit();
        return transect;
    }

    private String getNameForRequestCode(int requestCode) {
        switch(requestCode) {
            case EDIT_START_LOCATION_REQUEST: return "EDIT_START_LOCATION_REQUEST";
            case EDIT_END_LOCATION_REQUEST: return "EDIT_END_LOCATION_REQUEST";
            default: return "UNKNOWN";
        }
    }

    public void editStartLocation() {
        editLocation(transectDetailView.getStartLocation().getText().toString(), EDIT_START_LOCATION_REQUEST);
    }


    public void editEndLocation() {
        editLocation(transectDetailView.getEndLocation().getText().toString(), EDIT_END_LOCATION_REQUEST);
    }

    private void editLocation(String location, int returnCode) {
        Intent intent;
        if (SettingsDataService.getInstance().get().isLocationDMS()) {
            intent = new Intent(getActivity(), EditLocationDMSFormatActivity.class);
        } else {
            intent = new Intent(getActivity(), EditLocationDecimalFormatActivity.class);
        }
        intent.putExtra(Constants.PARENT_ACTIVITY, getClass());
        intent.setAction(Action.NEW.toString());
        intent.putExtra("location", location);
        startActivityForResult(intent, returnCode);
    }

    @Override
    public int getNameResourceId() {
        return R.string.transect_fragment_name;
    }


    private Location getCurrentLocation() {
        return ((LocationProvidingActivity) getActivity()).getCurrentLocation();
    }

    public Long getTransectId() {
        if (transectDetailView == null) {
            return null;
        }
        return transectDetailView.getIdValue();
    }

    @Override
    public boolean isChangedByUser() {
        return transectDetailView.isChanged();
    }

    @Override
    public String getName() {
        return null;
    }
}
