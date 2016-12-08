package com.example.chaos.mobiledevelopmentcw_4a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by chaos on 03/12/2016.
 */

public class InitiativeDBMgr extends SQLiteOpenHelper {

    MainMenu godCode;

    private static final int DB_VER = 1;
    private static final String DB_PATH = "/data/data/com.example.chaos.mobiledevelopmentcw_4a/databases/";
    private static final String DB_NAME = "initiative.s3db";
    private static final String TBL_INITIATIVE = "TBL_INITIATIVE";

    public static final String COL_COMBATANTID = "COL_COMBATANTID";
    public static final String COL_NAME = "COL_NAME";
    public static final String COL_INITIATIVE= "COL_INITIATIVE";

    private final Context appContext;


    public InitiativeDBMgr(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.appContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_INITIATIVE_TABLE = "CREATE TABLE IF NOT EXISTS " + TBL_INITIATIVE + "(" + COL_COMBATANTID+ " INTEGER PRIMARY KEY," + COL_NAME + " TEXT," + COL_INITIATIVE + ")";
        db.execSQL(CREATE_INITIATIVE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TBL_INITIATIVE);
            onCreate(db);
        }
    }

    // ================================================================================
    // Creates a empty database on the system and rewrites it with your own database.
    // ================================================================================
    public void dbCreate() throws IOException {

        boolean dbExist = dbCheck();
        Log.d("dbExist", "dbExist = " + dbExist);
        if(!dbExist){
            //By calling this method an empty database will be created into the default system path
            //of your application so we can overwrite that database with our database.
            this.getReadableDatabase();

            try {
                Log.d("dbCopy", "Trying to copy DB");
                copyDBFromAssets();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }

    }

    // ============================================================================================
    // Check if the database already exist to avoid re-copying the file each time you open the application.
    // @return true if it exists, false if it doesn't
    // ============================================================================================
    private boolean dbCheck(){

        SQLiteDatabase db = null;

        try{
            String dbPath = DB_PATH + DB_NAME;
            Log.d("dbCheck", "Trying to find: " + dbPath);
            db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
            Log.d("dbCheck", "Doing a locale thing");
            db.setLocale(Locale.getDefault());
            Log.d("dbCheck", "setVersion");
            db.setVersion(1);
            Log.d("dbCheck", "Completed");
        }catch(SQLiteException e){
            Log.e("SQLHelper","Database not Found! HIYA");
        }

        if(db != null){
            Log.d("dbCheck", "Closing " + DB_NAME);
            db.close();
        }

        return db != null ? true : false;
    }

    // ============================================================================================
    // Copies your database from your local assets-folder to the just created empty database in the
    // system folder, from where it can be accessed and handled.
    // This is done by transfering bytestream.
    // ============================================================================================
    private void copyDBFromAssets() throws IOException {

        InputStream dbInput = null;
        OutputStream dbOutput = null;
        String dbFileName = DB_PATH + DB_NAME;

        try {

            Log.d("dbCopy", "Beginning to copy " + dbFileName);
            dbInput = appContext.getAssets().open(DB_NAME);
            dbOutput = new FileOutputStream(dbFileName);
            //transfer bytes from the dbInput to the dbOutput
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dbInput.read(buffer)) > 0) {
                dbOutput.write(buffer, 0, length);
            }
            //Close the streams
            dbOutput.flush();
            dbOutput.close();
            dbInput.close();
            Log.d("dbCopy", "Finished copying " + DB_NAME);
        } catch (IOException e)
        {
            throw new Error("Problems copying DB!");
        }
    }


    public void addCombatants(InitiativeDB combatantInfo) {

        ContentValues values = new ContentValues();
        values.put(COL_NAME, combatantInfo.getName());
        values.put(COL_INITIATIVE, combatantInfo.getInitValue());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TBL_INITIATIVE, null, values);
        db.close();
    }

    public InitiativeDB findCombatant(int ID) {
        String query = "Select * FROM " + TBL_INITIATIVE + " WHERE " + COL_COMBATANTID + " =  \"" + ID + "\"";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        InitiativeDB CombatantInfo = new InitiativeDB();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            CombatantInfo.setCombatantID(Integer.parseInt(cursor.getString(0)));
            CombatantInfo.setName(cursor.getString(1));
            CombatantInfo.setInitValue(Integer.parseInt(cursor.getString(2)));
            cursor.close();
        } else {
            CombatantInfo = null;
        }
        db.close();
        return CombatantInfo;
    }

    public boolean removeCombatant(int ID) {

        boolean result = false;

        String query = "Select * FROM " + TBL_INITIATIVE + " WHERE " + COL_COMBATANTID + " =  \"" + ID + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        InitiativeDB CombatantInfo = new InitiativeDB();

        if (cursor.moveToFirst()) {
            CombatantInfo.setCombatantID(Integer.parseInt(cursor.getString(0)));
            Log.d("Removing", String.valueOf(CombatantInfo.getCombatantID()));
            db.delete(TBL_INITIATIVE, COL_COMBATANTID + " = ?", new String[] { String.valueOf(CombatantInfo.getCombatantID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public long CountDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();

        long value = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM TBL_INITIATIVE", null);
        return value;
    }

    public ArrayList<String> GetNameInDB(long noInDB)
    {
        ArrayList<String> combatantNameLst = new ArrayList<>();
        for (int i = 0; i < noInDB; i++)
        {
            InitiativeDB tempComb;
            Log.d("ID No: ", Integer.toString(i+1));
            tempComb = findCombatant(i+1);
            combatantNameLst.add(tempComb.getName());
        }
        return combatantNameLst;
    }
    public ArrayList<Integer> GetInitInDB(long noInDB)
    {
        ArrayList<Integer> combatantInitLst = new ArrayList<>();
        for (int i = 0; i < noInDB; i++)
        {
            InitiativeDB tempComb;
            tempComb = findCombatant(i+1);
            combatantInitLst.add(tempComb.getInitValue());
        }
        return combatantInitLst;
    }
}