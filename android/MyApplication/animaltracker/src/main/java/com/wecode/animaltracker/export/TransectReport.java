package com.wecode.animaltracker.export;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.model.findings.TransectFinding;
import com.wecode.animaltracker.model.findings.TransectFindingFeces;
import com.wecode.animaltracker.model.findings.TransectFindingFootprints;
import com.wecode.animaltracker.model.findings.TransectFindingOther;
import com.wecode.animaltracker.service.HabitatDataService;
import com.wecode.animaltracker.service.SettingsDataService;
import com.wecode.animaltracker.service.TransectFindingFecesDataService;
import com.wecode.animaltracker.service.TransectFindingFootprintsDataService;
import com.wecode.animaltracker.service.TransectFindingOtherDataService;
import com.wecode.animaltracker.util.LocationUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import jxl.CellType;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * Created by SZIAK on 9/19/2016.
 */
public class TransectReport {

    private Transect transect;
    private Activity context;

    private TransectReport(){}

    public TransectReport(Transect transect, Activity context) {
        this.transect = transect;
        this.context = context;
    }

    public void exportToExcel() {

        File excelFile = getExcelFile();
        InputStream reportStream = null;
        try {

            reportStream = context.getAssets().open("Transects-report.xls");

            createExcel(reportStream, excelFile, transect);

            Toast.makeText(context, "Exported to: " + excelFile.getAbsolutePath(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Log.e(Globals.APP_NAME, "Problem with excel export", e);
            Toast.makeText(context, "Problem with excel export. Exception: " + e.getClass() + ", message: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (reportStream != null) {
                try {
                    reportStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Globals.refreshFileSystem(context);
            }
        }

    }

    private void createExcel(InputStream input, File output, Transect transect) throws IOException, WriteException, BiffException {

        WritableWorkbook wkr = null;

        try {
            Workbook wk = Workbook.getWorkbook(input);
            wkr = Workbook.createWorkbook(output, wk);
            WritableSheet sheet = wkr.getSheet(0);
            for (int column = 0; column < 50; column++) {
                for (int row = 0; row < 5; row++) {
                    WritableCell cell = sheet.getWritableCell(column, row);
                    String data = getData(cell.getContents());
                    if (data != null) {
                        setCellText(data, cell);
                    }
                }
            }

            List<TransectReportRow> allRows = new ArrayList<>();

            for (TransectFinding transectFinding : transect.getFindings()) {
                List<TransectReportRow> rows = new ArrayList<>();
                List<TransectFindingFootprints> footprints = TransectFindingFootprintsDataService.getInstance().findByTransectFindingId(transectFinding.getId());

                for (TransectFindingFootprints footprintFinding : footprints) {
                    TransectReportRow row = new TransectReportRow();
                    row.setFootprintsBackSize(footprintFinding.getBackSizeFormatted());
                    row.setFootprintsFrontSize(footprintFinding.getFrontSizeFormatted());
                    row.setFootprintsDirection(footprintFinding.getDirection());
                    row.setFootprintsNumberOfAnimlas(footprintFinding.getNumberOfAnimals());
                    row.setFootprintsStride(footprintFinding.getStride());
                    row.setFootprintsSubstract(footprintFinding.getSubstract());
                    rows.add(row);
                }

                List<TransectFindingFeces> fecesList = TransectFindingFecesDataService.getInstance().findByTransectFindingId(transectFinding.getId());
                for (int i = 0; i < fecesList.size(); i++) {

                    TransectFindingFeces fecesFinding = fecesList.get(i);
                    TransectReportRow row;
                    if (rows.size() >= i + 1) {
                        row = rows.get(i);
                    } else {
                        row = new TransectReportRow();
                        rows.add(row);
                    }
                    row.setFecesPrey(fecesFinding.getPrey());
                    row.setFecesState(fecesFinding.getState());
                    row.setFecesSubstract(fecesFinding.getSubstract());

                }

                int rowCounter=0;
                List<TransectFindingOther> othersList = TransectFindingOtherDataService.getInstance().findByTransectFindingId(transectFinding.getId());
                for (int i = 0; i < othersList.size(); i++) {
                    TransectFindingOther otherFindings = othersList.get(i);

                    if (otherFindings.getEvidence().equalsIgnoreCase("urine")) {
                        TransectReportRow row;
                        if (rows.size() >= rowCounter + 1) {
                            row = rows.get(rowCounter);
                        } else {
                            row = new TransectReportRow();
                            rows.add(row);
                        }
                        row.setUrineLocation(otherFindings.getObservations());
                        rowCounter++;
                    }
                }

                rowCounter=0;
                for (int i = 0; i < othersList.size(); i++) {
                    TransectFindingOther otherFindings = othersList.get(i);

                    if (!otherFindings.getEvidence().equalsIgnoreCase("urine")) {

                        TransectReportRow row;
                        if (rows.size() >= rowCounter + 1) {
                            row = rows.get(rowCounter);
                        } else {
                            row = new TransectReportRow();
                            rows.add(row);
                        }

                        row.setOtherEvidence(otherFindings.getEvidence());
                        row.setOtherObservations(otherFindings.getObservations());
                        rowCounter++;
                    }
                }


                for (TransectReportRow row : rows) {
                    row.setSpecie(transectFinding.getSpecies());
                    row.setElevation(transectFinding.getLocationElevation());
                    row.setLatitude(transectFinding.getLocationLatitude());
                    row.setLongitude(transectFinding.getLocationLongitude());
                    if (transectFinding.getHabitatId() != null) {
                        Habitat habitat = HabitatDataService.getInstance().find(transectFinding.getHabitatId());
                        row.setHabitat(habitat);
                    }
                }
                allRows.addAll(rows);
            }

            int row = 7;
            for (TransectReportRow rowData : allRows) {

                for (int col = 0; col < 20; col++) {
                    Object data = "";

                    switch(col){
                        case 0: data = String.valueOf(row - 6); break;
                        case 1: data = rowData.getSpecie(); break;
                        case 2: data = rowData.getElevationValue(); break;
                        case 3: data = rowData.getLatitudeValue(); break;
                        case 4: data = rowData.getLongitudeValue(); break;
                        case 5: data = rowData.getHabitat(); break;
                        case 6: data = rowData.getFootprintsNumberOfAnimlas(); break;
                        case 7: data = rowData.getFootprintsSubstract(); break;
                        case 8: data = rowData.getFootprintsDirection(); break;
                        case 9: data = rowData.getFootprintsStride(); break;
                        case 10: data = rowData.getFootprintsFrontSize(); break;
                        case 11: data = rowData.getFootprintsBackSize(); break;
                        //case 12:
                        case 13: data = rowData.getFecesState(); break;
                        case 14: data = rowData.getFecesPrey(); break;
                        case 15: data = rowData.getFecesSubstract(); break;
                        //case 16:
                        case 17: data = rowData.getUrineLocation(); break;
                        case 18: data = rowData.getOtherEvidence(); break;
                        case 19: data = rowData.getOtherObservations(); break;

                    }

                    if (data != null) {
                        CellFormat cellFormat = sheet.getCell(col, row).getCellFormat();
                        Label cell = new Label(col, row, data.toString());
                        cell.setCellFormat(cellFormat);
                        sheet.addCell(cell);
                    }

                }

                row++;
            }


        } finally {
            if (wkr != null) {
                wkr.write();
                wkr.close();
            }
        }
    }

    private void setCellText(String text, WritableCell cell) {
        if (cell.getType() == CellType.LABEL)
        {
            Label l = (Label) cell;
            l.setString(text);
        }
    }

    private File getExcelFile(){
        String transectRootDirectory = Globals.getTransectRootDirectoryName(transect);
        File transectDir = new File(Globals.getAppRootDir(), transectRootDirectory);
        File excelName = new File(transectDir, "transect_report.xls");
        int counter = 1;
        while(excelName.exists()) {
            excelName = new File(transectDir, "transect_report_" + counter + ".xls");
            counter++;
        }
        return excelName;
    }

    private String getData(String key) {

        switch (key) {
            case "ID": return transect.getId().toString();
            case "REGION": return transect.getRouteName();
            case "LOCALISATION": return transect.getLocalisation();
            case "TRACKER": return SettingsDataService.getInstance().get().getTrackerName();
            case "DATE": {
                if (transect.getStartDateTime() != null) {
                    return DateFormat.getInstance().format(transect.getStartDateTime());
                } else {
                    return "n/a";
                }

            }
            case "GRID_NUMBER": return transect.getColumn("n/a");
            case "START_LOCATION": return transect.getStartLatitude() == null ? "n/a" :
                    LocationUtil.formatLocation(
                            transect.getStartLatitude(), transect.getStartLongitude(), transect.getStartElevation()
                    );
            case "END_LOCATION": return transect.getEndLatitude() == null ? "n/a" :
                    LocationUtil.formatLocation(transect.getEndLatitude(), transect.getEndLongitude(), transect.getEndElevation()
                    );
            //case "TOTAL_LENGTH": return transect.toString();
            case "WEATHER": return formatWeather(transect.getWeatherId());
        }

        return null;
    }

    private String formatWeather(Long weatherId) {
        ///weather.get
        return null;
    }
}
