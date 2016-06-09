package gr11.compumovil.udea.edu.co.ingencol;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import gr11.compumovil.udea.edu.co.ingencol.LugaresHistoricos.LugarHistorico;

/**
 * Created by sala__000 on 9/06/2016.
 */
public class HelpDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LugaresHistoricos.db";




    public HelpDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LugarHistorico.CREATE_TABLA_LUGARES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(LugarHistorico.DELETE_TABLA_LUGARES);
        onCreate(db);
    }
}
