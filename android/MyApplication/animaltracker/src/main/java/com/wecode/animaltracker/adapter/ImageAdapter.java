package com.wecode.animaltracker.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.model.Photo;

import java.io.File;
import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private File photoDirectory;

    private List<Photo> photos;

    private Context context;

    public ImageAdapter(Context context, List<Photo> photos, File photoDirectory) {
        this.context = context;
        this.photoDirectory = photoDirectory;
        this.photos = photos;
    }

    //---returns the number of images---
    public int getCount() {
        return photos.size();
    }

    //---returns the ID of an item---
    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return photos.get(position).getId();
    }

    //---returns an ImageView view---
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(185, 185));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5, 5, 5, 5);
        } else {
            imageView = (ImageView) convertView;
        }

        File file = new File(photoDirectory, photos.get(position).getFileName());
        Log.i(Globals.APP_NAME, "ImageAdapter file: " + file.getAbsolutePath());
        imageView.setImageURI(Uri.fromFile(file));
        return imageView;
    }
}