package com.wecode.animaltracker.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.async.BitmapWorkerTask;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.service.PhotosDataService;

import java.io.File;
import java.net.URI;
import java.util.List;

public class ImageAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    private final Long entityId;
    private final String entityName;
    private File photoDirectory;

    private List<Photo> photos;

    private Activity context;

    private PhotosDataService photosDataService = PhotosDataService.getInstance();

    public ImageAdapter(Activity context, Long entityId, String entityName, File photoDirectory) {
        this.context = context;
        this.entityId = entityId;
        this.entityName = entityName;
        this.photoDirectory = photoDirectory;

        refreshPhotos();
    }

    public void refreshPhotos() {
        photos = photosDataService.findByEntityIdAndName(entityId, entityName);

        for (int i = 0; i < photos.size(); i++) {
            Log.i(Globals.APP_NAME, "photo["+i+"]: " + photos.get(i).toString());
        }
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
        int size = 185;
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(size, size));
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setPadding(5, 5, 5, 5);
        } else {
            imageView = (ImageView) convertView;
        }

        File file = new File(photoDirectory, photos.get(position).getFileName());
        Log.i(Globals.APP_NAME, "ImageAdapter file: " + file.getAbsolutePath());

        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imageView);
        bitmapWorkerTask.execute(file);
        //Bitmap bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(file.getAbsolutePath()), size, size);
        //imageView.setRotation(90);
        //imageView.setImageBitmap(bitmap);

        return imageView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        File file = new File(photoDirectory, photos.get(position).getFileName());
        showPhoto(file);
    }

    private void showPhoto(File photoUri){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(photoUri), "image/*");
        context.startActivity(intent);
    }
}