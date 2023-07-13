package processing.dw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.RawRes;

import java.io.IOException;
import java.io.InputStream;

import processing.core.PApplet;
import processing.core.PImage;

public class MyPApplet extends PApplet {


    public PImage loadImage(Context activity,@RawRes int id) {
        InputStream stream = null;
        if(id == 0) return null;
        if(activity != null){
            stream = activity.getResources().openRawResource(id);
        }
        if (stream == null) {
            System.err.println("Could not find the image " + id + ".");
            return null;
        } else {
            Bitmap bitmap = null;

            try {
                bitmap = BitmapFactory.decodeStream(stream);
            } finally {
                try {
                    stream.close();
                    stream = null;
                } catch (IOException var10) {
                }

            }

            if (bitmap == null) {
                System.err.println("Could not load the image because the bitmap was empty.");
                return null;
            } else {
                PImage image = new PImage(bitmap);
                image.parent = this;
                return image;
            }
        }
    }
}
