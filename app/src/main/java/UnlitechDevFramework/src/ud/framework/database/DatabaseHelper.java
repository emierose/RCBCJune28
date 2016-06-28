/**
 * Author: Ronald Phillip C. Cui
 * Date: Oct 12, 2013
 */
package UnlitechDevFramework.src.ud.framework.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static DatabaseHelper dbManager;
    private EngineDatabase mDatabase;

    /**
     * @param context  - application context
     * @param database - database
     */
    public DatabaseHelper(Context context, EngineDatabase database) {
        super(context, database.getName(), null, database.getVersion());
        mDatabase = database;
    }

    /**
     * creates the database
     * tip: call this app in the activity that is launch initially
     *
     * @param context  - the application's context
     * @param database - the database information of the app
     */
    public static void createDatabase(Context context, EngineDatabase database) {

        if (dbManager == null)
            dbManager = new DatabaseHelper(context, database);

    }

    /**
     * @return the instance of the helper class
     * - returns null when the database is not yet created
     */
    public static DatabaseHelper getInstance() {
        return dbManager;
    }

    /**
     * @return the instance of the database information set when creating the database
     * - returns null when the database is not yet created
     */
    public EngineDatabase getDatabaseInfo() {
        return mDatabase;
    }


    public void close() {
        dbManager = null;
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        for (Table table : mDatabase.getTables()) {
            db.execSQL(table.getTableStructure());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        for (Table table : mDatabase.getTables()) {
            db.execSQL("DROP TABLE IF EXISTS " + table.getName());
        }

        onCreate(db);

    }

    /**
     * @param sourceDB   - InputStream object of the database to be copied
     * @param outputPath - path to the database of the app
     */
    public boolean copyDatabase(InputStream sourceDB, String outputPath) {

        try {

            InputStream myInput = sourceDB;
            String outFileName = outputPath;
            OutputStream myOutput = new FileOutputStream(outFileName);

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void exportDB(String dbPath, String dir) {

        File sdFile = new File(dir);
        if (!sdFile.exists())
            sdFile.mkdirs();

        FileChannel source = null;
        FileChannel destination = null;
        String backupDBPath = mDatabase.getName();
        File currentDB = new File(dbPath);
        File backupDB = new File(dir, backupDBPath);

        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            System.out.println("DB Exported");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean dbExists(String dbPath) {
        SQLiteDatabase checkDB = null;

        try {
            checkDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
        }

        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }
}
