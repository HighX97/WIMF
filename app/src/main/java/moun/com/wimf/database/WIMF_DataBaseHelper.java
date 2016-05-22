package moun.com.wimf.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;


public class WIMF_DataBaseHelper extends SQLiteOpenHelper {

    //DataBase
    private static final String DATABASE_NAME = "WIMFdb";
    private static final int DATABASE_VERSION = 1;

    public static final String USER_TABLE = "Utilisateur";
    public static final String Friends_TABLE = "Amis";
    public static final String Messages_TABLE = "Message";
    public static final String ITEMS_TABLE = "items";
    public static final String ORDERS_TABLE = "orders";
    public static final String FAVORITE_TABLE = "favorite";

    public static final String ID_COLUMN_USER = "idU";
    public static final String ID_COLUMN = "id";
    public static final String ID_COLUMN_MESSAGE = "idMsg";
    public static final String NAME_COLUMN = "nom";
    public static final String DESCRIPTION_COLOMN = "decription";
    public static final String IMAGE_COLOMN = "image";
    public static final String PRICE_COLOMN = "price";
    public static final String QUANTITY_COLOMN = "quantity";

    public static final String PHONE_COLOMN = "tel";
    public static final String PASSWORD_COLOMN = "password";
    public static final String GPS_lat_COLOMN = "gps_lat";
    public static final String GPS_long_COLOMN = "gps_long";
    public static final String ORDER_ID = "order_id";
    public static final String ORDERED = "ordered";
    public static final String CREATED_AT = "created_at";
    public static final String CREATION_DATE_COLUMN = "datetimeCrea";
    public static final String UPDATE_DATE_COLUMN = "datetimeMaj";
    public static final String USER1_COLUMN = "user1";
    public static final String USER2_COLUMN = "user2";
    public static final String STATE_COLUMN = "etat";
    public static final String MESSAGE_COLUMN = "valeur";
    public static final String SENDER_ID_COLOMN = "idU_snd";
    public static final String SENDER_TEL_COLOMN = "tel_snd";
    public static final String RECEIVER_TEL_COLOMN = "tel_rcv";
    public static final String RECEIVER_ID_COLOMN = "idU_rcv";

    /*public static final String CREATE_USER_TABLE = "CREATE TABLE "
            + USER_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + NAME_COLUMN + " TEXT, " + EMAIL_COLOMN + " TEXT, "
            + ADDRESS_COLOMN + " TEXT, " + PHONE_COLOMN + " TEXT"
            + ")";*/

    public static final String CREATE_USER_TABLE = "CREATE TABLE "
            + USER_TABLE + "(" + ID_COLUMN_USER + " INTEGER PRIMARY KEY, "
            + NAME_COLUMN + " TEXT, " + PHONE_COLOMN + " TEXT, "
             + GPS_lat_COLOMN + " REAL, "+  GPS_long_COLOMN + " REAL, "+
             PASSWORD_COLOMN + " TEXT, "
            + CREATION_DATE_COLUMN + " TEXT, " + UPDATE_DATE_COLUMN + " TEXT "
            + ")";

    public static final String CREATE_Friends_TABLE = "CREATE TABLE "
            + Friends_TABLE + "(" + SENDER_ID_COLOMN + " TEXT, " + RECEIVER_ID_COLOMN + " TEXT, "
            + STATE_COLUMN + " INTEGER, "  + CREATION_DATE_COLUMN + " TEXT, " + UPDATE_DATE_COLUMN + " TEXT "
            + ")";

    public static final String CREATE_Messages_TABLE = "CREATE TABLE "
            + Messages_TABLE  + "(" + ID_COLUMN_MESSAGE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MESSAGE_COLUMN + " TEXT, "
            + SENDER_TEL_COLOMN + " TEXT, " + RECEIVER_TEL_COLOMN + " TEXT, "
            + STATE_COLUMN + " INTEGER, "
            + CREATION_DATE_COLUMN + " TEXT, " + UPDATE_DATE_COLUMN + " TEXT "
            + ")";

    public static final String CREATE_ITEMS_TABLE = "CREATE TABLE "
            + ITEMS_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + NAME_COLUMN + " TEXT, " + DESCRIPTION_COLOMN + " TEXT, "
            + IMAGE_COLOMN + " INTEGER, " + PRICE_COLOMN + " DOUBLE, "
            + QUANTITY_COLOMN + " INTEGER, " + ORDER_ID + " INTEGER, "
            + "FOREIGN KEY(" + ORDER_ID + ") REFERENCES "
            + ORDERS_TABLE + "(id) " + ")";

    public static final String CREATE_ORDERS_TABLE = "CREATE TABLE "
            + ORDERS_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY,"
            + ORDERED + " INTEGER," + CREATED_AT + " TEXT" + ")";

    public static final String CREATE_FAVORITE_TABLE = "CREATE TABLE "
            + FAVORITE_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + NAME_COLUMN + " TEXT, " + DESCRIPTION_COLOMN + " TEXT, "
            + IMAGE_COLOMN + " INTEGER, " + PRICE_COLOMN + " DOUBLE"
            + ")";


    private static WIMF_DataBaseHelper instance;

    public static synchronized WIMF_DataBaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new WIMF_DataBaseHelper(context);
        return instance;
    }

    private WIMF_DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_Friends_TABLE);
        db.execSQL(CREATE_Messages_TABLE);
        db.execSQL(CREATE_ITEMS_TABLE);
        db.execSQL(CREATE_ORDERS_TABLE);
        db.execSQL(CREATE_FAVORITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Friends_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Messages_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ITEMS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ORDERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FAVORITE_TABLE);

    }
}
