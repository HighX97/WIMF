package moun.com.wimf.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class WIMF_DataBaseHelper_new extends SQLiteOpenHelper {



    public static final String USER_TABLE = "Utilisateur";
    public static final String ID_COLUMN_USER = "idU";
    public static final String NAME_COLUMN = "nom";
    public static final String PHONE_COLOMN = "tel";
    public static final String PASSWORD_COLOMN = "password";
    public static final String GPS_lat_COLOMN = "gps_lat";
    public static final String GPS_long_COLOMN = "gps_long";

    public static final String Friends_TABLE = "Amis";
    public static final String SENDER_ID_COLOMN = "idU_snd";
    public static final String RECEIVER_ID_COLOMN = "idU_rcv";

    public static final String Messages_TABLE = "Message";
    public static final String ID_COLUMN_MESSAGE = "idMsg";
    public static final String MESSAGE_COLUMN = "valeur";
    public static final String SENDER_TEL_COLOMN = "tel_snd";
    public static final String RECEIVER_TEL_COLOMN = "tel_rcv";

    public static final String ID_COLUMN = "id";

    public static final String CREATION_DATE_COLUMN = "datetimeCrea";
    public static final String UPDATE_DATE_COLUMN = "datetimeMaj";

    public static final String STATE_COLUMN = "etat";


    public static final String CREATE_USER_TABLE = "CREATE TABLE "
            + USER_TABLE + "(" + ID_COLUMN_USER + " INTEGER PRIMARY KEY, "
            + NAME_COLUMN + " TEXT, " + PHONE_COLOMN + " TEXT, "
             + GPS_lat_COLOMN + " REAL, "+  GPS_long_COLOMN + " REAL, "+
             PASSWORD_COLOMN + " TEXT, "
            + CREATION_DATE_COLUMN + " TEXT, " + UPDATE_DATE_COLUMN + " TEXT "
            + ")";

    public static final String CREATE_Friends_TABLE = "CREATE TABLE "
            + Friends_TABLE + "(" + SENDER_ID_COLOMN + " TEXT, " + RECEIVER_ID_COLOMN + " TEXT, "
            + STATE_COLUMN + " INTEGER, "  + CREATION_DATE_COLUMN + " TEXT, " + UPDATE_DATE_COLUMN + " TEXT ,"
            + "PRIMARY KEY ("+SENDER_ID_COLOMN+","+RECEIVER_ID_COLOMN+"))";

    public static final String CREATE_Messages_TABLE = "CREATE TABLE "
            + Messages_TABLE  + "(" + ID_COLUMN_MESSAGE + " INTEGERAUTOINCREMENT, "
            + MESSAGE_COLUMN + " TEXT, "
            + SENDER_TEL_COLOMN + " TEXT, " + RECEIVER_TEL_COLOMN + " TEXT, "
            + STATE_COLUMN + " INTEGER, "
            + CREATION_DATE_COLUMN + " TEXT, " + UPDATE_DATE_COLUMN + " TEXT ,"
            + "PRIMARY KEY ("+SENDER_TEL_COLOMN+","+SENDER_TEL_COLOMN+","+CREATION_DATE_COLUMN+"))";



    private static WIMF_DataBaseHelper_new instance;

    private static final String DATABASE_NAME = "WIMFdb";
    private static final int DATABASE_VERSION = 1;

    public static synchronized WIMF_DataBaseHelper_new getHelper(Context context) {
        if (instance == null)
            instance = new WIMF_DataBaseHelper_new(context,DATABASE_NAME,null,DATABASE_VERSION);
        return instance;
    }

    public WIMF_DataBaseHelper_new(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_Friends_TABLE);
        db.execSQL(CREATE_Messages_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Friends_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Messages_TABLE);
        onCreate(db);
    }
}
