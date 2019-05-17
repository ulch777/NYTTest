package ua.ulch.nyttest.util;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;

import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class ReadWriteUtil {
    private static final String DIRECTORY_NAME = "nyt_test";

    public static boolean saveImage(Bitmap bitmap, String name, Context context) {
        boolean success = false;
        if (bitmap != null) {
            String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            createDirIfNotExists(DIRECTORY_NAME);
            String filePath = baseDir + "/" + DIRECTORY_NAME + File.separator + name;
            File imageFile = new File(filePath);
            if (!imageFile.exists()) {
                FileOutputStream outputStream;
                try {
                    outputStream = new FileOutputStream(imageFile);
                    int quality = 100;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    success = true;
//                    addImageToGallery(filePath, context);
                } catch (IOException e) {
                    e.printStackTrace();
                    success = false;
                }
            } else success = true;
        }
        return success;
    }

    public static boolean saveDocument(Document document, String name) {
        boolean success = false;
        if (document != null) {
            String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            createDirIfNotExists(DIRECTORY_NAME);
            String filePath = baseDir + "/" + DIRECTORY_NAME + File.separator + name;
            File file = new File(filePath);
            if (!file.exists()) {
                FileOutputStream outputStream;
                try {
                    outputStream = new FileOutputStream(file);

                    outputStream.flush();
                    outputStream.close();
                    success = true;
//                    addImageToGallery(filePath, context);
                } catch (IOException e) {
                    e.printStackTrace();
                    success = false;
                }
            } else success = true;
        }
        return success;
    }


    public static Bitmap getImage(String name) {
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filePath = baseDir + "/" + DIRECTORY_NAME + File.separator + name;
        File imageFile = new File(filePath);
        if (imageFile.exists()) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            return BitmapFactory.decodeFile(imageFile.getAbsolutePath(), bmOptions);
//            }
        }
        return null;
    }

    public static String getUri(String name) {
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        return baseDir + "/" + DIRECTORY_NAME + File.separator + name;

    }

    public static Bitmap getImageByFilePath(String filePath) {
        Bitmap bitmap = null;
        File imageFile = new File(filePath);
        if (imageFile.exists()) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), bmOptions);
//                return bitmap;
//            }
        }
        return bitmap;
    }

    private static boolean createDirIfNotExists(String path) {
        boolean ret = true;

        File file = new File(Environment.getExternalStorageDirectory(), path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                ret = false;
            }
        }
        return ret;
    }
    public static void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }


}