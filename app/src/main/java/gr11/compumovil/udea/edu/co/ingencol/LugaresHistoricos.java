package gr11.compumovil.udea.edu.co.ingencol;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by sala__000 on 9/06/2016.
 */
public class LugaresHistoricos {

    public LugaresHistoricos() {}

    public static abstract class LugarHistorico implements BaseColumns {
        public static final String TABLE_NAME = "lugarHistorico";
        public static final String COLUMN_LUGAR_ID = "lugarHistoricoid";
        public static final String COLUMN_NAME = "lugarHistoricoName";
        public static final String COLUMN_DESCRIPTION = "lugarHistoricoDescripcion";
        public static final String COLUMN_IMAGE = "lugarHistoricoImage";
        public static final String COLUMN_COORDENADA_X = "lugarHistoricoCoordX";
        public static final String COLUMN_COORDENADA_Y = "lugarHistoricoCoordY";
        public static final String COLUMN_PERSONAJE = "lugarHistoricoPersonaje";
        public static final String COLUMN_PERS_DESCR= "lugarHistoricoPersDescr";
        public static final String COLUMN_PERS_IMAGE = "lugarHistoricoPersImage";

        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        public static final String CREATE_TABLA_LUGARES =
                "CREATE TABLE " + LugarHistorico.TABLE_NAME + " (" +
                        LugarHistorico.COLUMN_LUGAR_ID + " INTEGER PRIMARY KEY," +
                        LugarHistorico.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                        LugarHistorico.COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                        LugarHistorico.COLUMN_IMAGE + TEXT_TYPE + COMMA_SEP +
                        LugarHistorico.COLUMN_COORDENADA_X + TEXT_TYPE + COMMA_SEP +
                        LugarHistorico.COLUMN_COORDENADA_Y + TEXT_TYPE + COMMA_SEP +
                        LugarHistorico.COLUMN_PERSONAJE + TEXT_TYPE + COMMA_SEP +
                        LugarHistorico.COLUMN_PERS_DESCR + TEXT_TYPE + COMMA_SEP +
                        LugarHistorico.COLUMN_PERS_IMAGE + TEXT_TYPE +
                " )";

        public static final String DELETE_TABLA_LUGARES =
                "DROP TABLE IF EXISTS " + LugarHistorico.TABLE_NAME;

    }

}
