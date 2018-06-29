package mehroz.sis.com.androiddevelopmenttest.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import mehroz.sis.com.androiddevelopmenttest.R;


public class ImageHelper {

    private static ImageHelper imageHelper;


    public static ImageHelper getInstance() {
        if (imageHelper == null) {
            synchronized (ImageHelper.class) {
                imageHelper = new ImageHelper();
            }
        }
        return imageHelper;
    }

    public static void setImageInGlide(Context context,String uri, ImageView imageView)
    {
        Glide.with(context).load(uri)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_sentiment_neutral_black_24dp)
                .into(imageView);
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


    public static Bitmap getRotatedImage(File uri, Bitmap bitmap) {
        Bitmap rotatedBitmap = null;
        try {
            ExifInterface ei = new ExifInterface(uri.getPath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = ImageHelper.rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = ImageHelper.rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = ImageHelper.rotateImage(bitmap, 270);
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotatedBitmap;
    }

    public static void saveImageInDevice(String mPath,Bitmap bitmap)
    {
        try {

            File imageFile = new File(mPath);

            if (!imageFile.isDirectory()) {
                imageFile.getParentFile().mkdir();
            }


            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 80;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
