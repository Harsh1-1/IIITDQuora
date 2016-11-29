package com.example.harsh.iiitdquora;

import android.graphics.Bitmap;

/**
 * Created by Abhi on 27-11-2016.
 */

//Interface for receiving updates from Async Task
public interface Updatable {
    public void update(Bitmap bm);
}
