package com.example.suiterentals.Model;

import android.graphics.Bitmap;

public class productImages {
    Bitmap image;

    public productImages() {
    }

    public productImages(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
