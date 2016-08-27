package com.wecode.animaltracker;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;
import com.wecode.animaltracker.activity.MainActivity;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sziak on 10/2/2015.
 */
public class StorageHelper {

    private Context context;
    private File xmlDataFile;

    public StorageHelper(Context context) {
        this.context = context;
        /*File externalStorageDirectory = context.getFilesDir();
        File animalTrackerDataDir = new File(externalStorageDirectory, "AnimalTracker");
        xmlDataFile = new File(animalTrackerDataDir, "data.xml");

        if (!xmlDataFile.mkdirs()) {
            Toast.makeText(context, "could not create " + xmlDataFile, Toast.LENGTH_LONG).show();
            Log.d(Globals.APP_NAME, "could not create " + xmlDataFile);
            //throw new RuntimeException("could not create " + xmlDataFile);
        }*/
    }

    public void store(Uri pictureFile, Location location, String note) {
        FileOutputStream fos = null;

        try {

            boolean mExternalStorageAvailable = false;
            boolean mExternalStorageWriteable = false;
            String state = Environment.getExternalStorageState();

            if (Environment.MEDIA_MOUNTED.equals(state)) {
                // We can read and write the media
                mExternalStorageAvailable = mExternalStorageWriteable = true;
            } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                // We can only read the media
                mExternalStorageAvailable = true;
                mExternalStorageWriteable = false;
            } else {
                // Something else is wrong. It may be one of many other states, but all we need
                //  to know is we can neither read nor write
                mExternalStorageAvailable = mExternalStorageWriteable = false;
            }

            if (!mExternalStorageWriteable) {

                Toast.makeText(context, "!mExternalStorageWriteable", Toast.LENGTH_LONG).show();
                Log.d(Globals.APP_NAME, "!mExternalStorageWriteable");

                return ;
            }

            File sdCard = Environment.getExternalStorageDirectory();
            File animalTrackerdir = new File (sdCard, "AnimalTracker");
            if (!animalTrackerdir.mkdirs()) {
                Toast.makeText(context, "could not create " + animalTrackerdir, Toast.LENGTH_LONG).show();
                Log.d(Globals.APP_NAME, "could not create " + animalTrackerdir);
                //throw new RuntimeException("could not create " + xmlDataFile);
            }

            File dataXml = new File(animalTrackerdir, "data.xml");

            fos = new FileOutputStream(dataXml);

            //fos = context.openFileOutput("data.xml", Context.MODE_PRIVATE);

            XmlSerializer serializer = Xml.newSerializer();
            serializer.setOutput(fos, "UTF-8");

            serializer.startDocument(null, Boolean.valueOf(true));
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            serializer.startTag(null, "root");


                serializer.startTag(null, "record");


                    addTag(pictureFile.toString(), "file", serializer);
                    addTag(location.toString(), "location", serializer);
                    addTag(note, "note", serializer);

                serializer.endTag(null, "record");

            serializer.endDocument();

            serializer.flush();
            if (fos != null) {
                fos.close();
            }

            Log.d(Globals.APP_NAME, "Saved to " +context.getFilesDir());
            Toast.makeText(context, "Successfully added animal track.", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            Log.e(Globals.APP_NAME, "IOException while saving " + xmlDataFile, e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addTag(String data, String tagname, XmlSerializer serializer) throws IOException {
        serializer.startTag(null, tagname);
        serializer.text(data);
        serializer.endTag(null, tagname);
    }

}
