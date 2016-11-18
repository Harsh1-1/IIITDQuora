package com.example.harsh.iiitdquora;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by Abhi on 18-11-2016.
 */

public class ImageResizerTask extends AsyncTask<String, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewReference;
    private String data = null;
    private int width = 0;
    private int height = 0;

    public ImageResizerTask(ImageView imageView) {
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        data = params[0];
        width = Integer.parseInt(params[1]);
        height = Integer.parseInt(params[2]);
        return CustomImageLoader.getImage(data, width, height);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

}
