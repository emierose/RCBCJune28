/* 
 *	Created by Ronald Phillip C. Cui Feb 4, 2014
 */
package UnlitechDevFramework.src.ud.framework.utilities;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;

public class ImageUtil {

    /**
     * used to scale an image to a given width and height and rotate it to a given degree
     *
     * @param bitmap      - bitmap to be scaled
     * @param width       - new width of the bitmap
     * @param height      - new height of the bitmap
     * @param rotateAngle - rotation angle of the bitmap
     * @return a scaled bitmap version
     */
    public static Bitmap scaleImage(Bitmap bitmap, int width, int height, int rotateAngle) {

        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateAngle);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        return bitmap;

    }

    /**
     * used to scale an image to a given width and height
     *
     * @param bitmap - bitmap to be scaled
     * @param width  - new width of the bitmap
     * @param height - new height of the bitmap
     * @return a scaled bitmap version
     */
    public static Bitmap scaleImage(Bitmap bitmap, int width, int height) {

        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        return bitmap;

    }

    /**
     * Rotate a image to a specified degree
     *
     * @param bitmap      - bitmap to rotate
     * @param rotateAngle - angle of rotation
     * @return rotated bitmap
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int rotateAngle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateAngle);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        return bitmap;
    }

    /**
     * convert bitmap to byte array
     *
     * @param src     - bitmap to convert
     * @param format  - compression format
     * @param quality - quality of image
     * @return byte array of the bitmap
     */
    public static byte[] getByteArray(Bitmap src, Bitmap.CompressFormat format, int quality) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        src.compress(format, quality, os);

        return os.toByteArray();

    }

}
