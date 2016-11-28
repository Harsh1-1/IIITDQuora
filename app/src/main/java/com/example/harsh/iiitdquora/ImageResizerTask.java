package com.example.harsh.iiitdquora;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * Created by Abhi on 18-11-2016.
 */

public class ImageResizerTask extends AsyncTask<String, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewReference;
    private final WeakReference<Updatable> fragmentReference;
    private String data = null;
    private int width = 0;
    private int height = 0;

    public ImageResizerTask(ImageView imageView, Updatable fragment) {
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.fragmentReference = new WeakReference<Updatable>(fragment);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        data = params[0];
        width = Integer.parseInt(params[1]);
        height = Integer.parseInt(params[2]);
        if(params.length == 4){
            String type = params[3];
            if(type.equals("url")){
                try {
                    URL url = new URL(data);
                    return CustomImageLoader.getImage(url, width, height);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                return null;
            }
        }
        return CustomImageLoader.getImage(data, width, height);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewReference != null && bitmap != null && fragmentReference != null) {
            final ImageView imageView = imageViewReference.get();
            final Updatable fragment = fragmentReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }else{
                imageView.setImageDrawable(null);
            }
            if(fragment != null){
                fragment.update(bitmap);
            }
        }
    }

}
