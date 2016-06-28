package UnlitechDevFramework.src.ud.framework.utilities;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtil {

    public static final String NO_FILE = "There is no file to be uploaded";
    public static final String NOT_A_FILE = "Failed to uploaded. <file> is not a file";
    public static final String FILE_NOT_FOUND = "Cannot find file";
    public static String TWO_HYPEN = "--";
    public static String BOUNDARY = "*****";
    public static String LINE_END = "\r\n";

    public static String getSDState() {
        return Environment.getExternalStorageState();
    }

    /**
     * Used to append to a file create a file
     *
     * @param dirName  - the name of the directory
     * @param fileName - the name of the filename
     * @param date     � byte array of the image to be saved as a file
     * @return FileStatus - the status of the file whether it be successfully created or there is a error that occurred
     * @throws IOException
     */

    public static FileStatus saveImage(String dirName, String fileName, byte[] data) throws IOException {


        String sdState = Environment.getExternalStorageState();

        if (sdState.equals(Environment.MEDIA_MOUNTED)) {

            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

            File dir = new File(dirName);

            if (!dir.exists())
                dir.mkdirs();

            File file = new File(dir, fileName);

            if (file.exists())
                file.delete();

            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

            return FileStatus.WRITE_SUCCESSFUL;

        } else
            return FileStatus.SD_UNMOUNTED;
    }

    /**
     * Used to append to a file create a file
     *
     * @param dirName  - the name of the directory
     * @param fileName - the name of the filename
     * @param bitmap   � bitmap to be saved as a file
     * @return FileStatus - the status of the file whether it be successfully created or there is a error that occurred
     * @throws IOException
     */

    public static FileStatus saveImage(String dirName, String fileName, Bitmap bitmap) throws IOException {


        String sdState = Environment.getExternalStorageState();

        if (sdState.equals(Environment.MEDIA_MOUNTED)) {

            File dir = new File(dirName);

            if (!dir.exists())
                dir.mkdirs();

            File file = new File(dir, fileName);

            if (file.exists())
                file.delete();

            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

            return FileStatus.WRITE_SUCCESSFUL;

        } else
            return FileStatus.SD_UNMOUNTED;
    }

    /**
     * Used to create a file
     *
     * @param dirName  - the name of the directory
     * @param fileName - the name of the filename
     * @param data     - data to save in the file (note: should be in bytes)
     * @return FileStatus - the status of the file whether it be successfully created or there is a error that occurred
     * @throws IOException
     */
    public static FileStatus saveFile(String dirName, String fileName, byte[] data) throws IOException {

        String sdState = Environment.getExternalStorageState();

        if (sdState.equals(Environment.MEDIA_MOUNTED)) {

            File dir = new File(dirName);

            if (!dir.exists())
                dir.mkdirs();

            File file = new File(dir, fileName);

            if (file.exists())
                file.delete();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.flush();
            fos.close();

            return FileStatus.WRITE_SUCCESSFUL;

        } else
            return FileStatus.SD_UNMOUNTED;
    }

    /**
     * Used to append to a file create a file
     *
     * @param dirName  - the name of the directory
     * @param fileName - the name of the filename
     * @param data     - data to save in the file (note: should be in bytes)
     * @return FileStatus - the status of the file whether it be successfully created or there is a error that occurred
     * @throws IOException
     */
    public static FileStatus appendToFile(String dirName, String fileName, byte[] data) throws IOException {

        String sdState = Environment.getExternalStorageState();

        if (sdState.equals(Environment.MEDIA_MOUNTED)) {

            File dir = new File(dirName);

            if (!dir.exists())
                dir.mkdirs();

            File file = new File(dir, fileName);

            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(data);
            fos.flush();
            fos.close();
            return FileStatus.WRITE_SUCCESSFUL;

        } else
            return FileStatus.SD_UNMOUNTED;
    }

    public static FileStatus zipFile(String zipPath, String zipFileName, String folderName, String... files) throws IOException {

        int BUFFER = 2048;

        File dir = new File(zipPath);

        if (!dir.exists())
            dir.mkdirs();

        File file = new File(dir, zipFileName);

        BufferedInputStream origin = null;
        FileOutputStream dest = new FileOutputStream(file);
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
        byte data[] = new byte[BUFFER];

        for (int i = 0; i < files.length; i++) {

            System.out.println("Compress: " + "Adding: " + files[i]);
            FileInputStream fi = new FileInputStream(files[i]);
            origin = new BufferedInputStream(fi, BUFFER);

            ZipEntry entry = new ZipEntry((folderName != null ? folderName + "/" : "") + files[i].substring(files[i].lastIndexOf("/") + 1));
            out.putNextEntry(entry);
            int count;

            while ((count = origin.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }

            origin.close();

        }

        out.close();

        return FileStatus.WRITE_SUCCESSFUL;

    }

    public static void writeURLConnectionParam(DataOutputStream dos, String paramName, String value) throws IOException {
        // TODO Auto-generated method stub

        dos.writeBytes(TWO_HYPEN + BOUNDARY + LINE_END);
        dos.writeBytes("Content-Disposition: form-data; name=\"" + paramName + "\"" + LINE_END);
        dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + LINE_END);
        dos.writeBytes(LINE_END);
        dos.writeBytes(value + LINE_END);
        dos.flush();

    }

    public static void addUploadFileURLConnection(DataOutputStream dos, String paramName, String fileName) throws IOException {

        dos.writeBytes(TWO_HYPEN + BOUNDARY + LINE_END);
        dos.writeBytes("Content-Disposition: form-data; name=\"" + paramName + "\";filename=\""
                + fileName + "\"" + LINE_END);
        dos.writeBytes("Content-Type: " + URLConnection.guessContentTypeFromName(fileName) + LINE_END);
        dos.writeBytes("Content-Transfer-Encoding: binary" + LINE_END);

        dos.writeBytes(LINE_END);

        dos.flush();
    }

    public static void deleteDir(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteDir(child);

        fileOrDirectory.delete();

    }

    public static int getFileCount(String path, final String regEx) {

        return new File(path).list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String filename) {
                // TODO Auto-generated method stub
                return filename.matches(regEx);
            }
        }).length;

    }

    public static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();

    }

    public void unzip(String zipFile, String targetLocation) throws IOException {

        //create target location folder if not exist
        dirChecker(targetLocation);

        FileInputStream fin = new FileInputStream(zipFile);
        ZipInputStream zin = new ZipInputStream(fin);
        ZipEntry ze = null;

        while ((ze = zin.getNextEntry()) != null) {

            //create dir if required while unzipping
            if (ze.isDirectory()) {
                dirChecker(ze.getName());
            } else {
                FileOutputStream fout = new FileOutputStream(targetLocation + ze.getName());

                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }

                zin.closeEntry();
                fout.close();
            }

        }
        zin.close();

    }

    private void dirChecker(String dir) {
        // TODO Auto-generated method stub
        File file = new File(dir);
        if (file.exists()) file.mkdirs();
    }

    public enum FileStatus {

        WRITE_SUCCESSFUL("Successfully saved file"),
        READ_SUCCESSFUL("Sucessfully read file"),
        SD_UNMOUNTED("SD card not mounted"),
        WRITE_FAILED("Failed to save file"),
        READ_FAILED("Failed to read file"),
        UNKNOWN_ERROR("Unexpected Error Occurred"),
        NO_FILES("No files");

        String status = "";

        FileStatus(String status) {
            this.status = status;
        }

        public String toString() {
            return status;
        }

    }
}
