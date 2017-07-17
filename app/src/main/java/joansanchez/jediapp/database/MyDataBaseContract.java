package joansanchez.jediapp.database;

import android.provider.BaseColumns;

/**
 * Created by JoanPad on 05/07/2017.
 */



public final class MyDataBaseContract {
    private MyDataBaseContract() {
    }

    //We create as much public static classes as tables we have in our database
    public static class Table1 implements BaseColumns {
        public static final String TABLE_NAME = "usuaris";
        //public static final String COLUMN_ID = "id"; <- Actually we don't need a dedicated COLUMN_ID
        //because this class implements BaseColumns which has a _ID constant for that
        public static final String COLUMN_USER = "usuario";
        public static final String COLUMN_PASS = "contrasenya";
        public static final String COLUMN_PHOTO = "foto";
        public static final String COLUMN_ADRESS = "direccion";
        public static final String COLUMN_CITY = "ciudad";
        public static final String COLUMN_BEST = "bestpoints";
        public static final String COLUMN_TIPONOTI = "tiponoti";
        public static final String COLUMN_NOTI = "lastnoti";
    }

    public static class Table2 implements BaseColumns {
        public static final String TABLE_NAME1 = "puntuacions";
        //public static final String COLUMN_ID = "id"; <- Actually we don't need a dedicated COLUMN_ID
        //because this class implements BaseColumns which has a _ID constant for that
        public static final String COLUMN_USER1 = "usuario";
        public static final String COLUMN_BEST1 = "bestpoints";
    }
}
