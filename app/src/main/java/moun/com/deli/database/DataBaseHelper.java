package moun.com.deli.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mounzer on 12/5/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "restaurantdb";
    private static final int DATABASE_VERSION = 1;

    public static final String USER_TABLE = "user";
    public static final String CART_TABLE = "cart";
    public static final String ORDERS_TABLE = "cart";
    public static final String FAVORITE_TABLE = "favorite";

    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String DESCRIPTION_COLOMN = "decription";
    public static final String IMAGE_COLOMN = "image";
    public static final String PRICE_COLOMN = "price";
    public static final String QUANTITY_COLOMN = "quantity";
    public static final String EMAIL_COLOMN = "email";
    public static final String ADDRESS_COLOMN = "address";
    public static final String PHONE_COLOMN = "phone";
    public static final String NUMB_OF_ORDER_COLOMN = "orders";

    public static final String CREATE_USER_TABLE = "CREATE TABLE "
            + USER_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + NAME_COLUMN + " TEXT, " + EMAIL_COLOMN + " TEXT, "
            + ADDRESS_COLOMN + " TEXT, " + PHONE_COLOMN + " TEXT"
             + ")";

    public static final String CREATE_CART_TABLE = "CREATE TABLE "
            + CART_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + NAME_COLUMN + " TEXT, " + DESCRIPTION_COLOMN + " TEXT, "
            + IMAGE_COLOMN + " INTEGER, " + PRICE_COLOMN + " DOUBLE, "
            + QUANTITY_COLOMN + " INTEGER" + ")";

    public static final String CREATE_FAVORITE_TABLE = "CREATE TABLE "
            + FAVORITE_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + NAME_COLUMN + " TEXT, " + DESCRIPTION_COLOMN + " TEXT, "
            + IMAGE_COLOMN + " INTEGER, " + PRICE_COLOMN + " DOUBLE"
             + ")";

    private static DataBaseHelper instance;

    public static synchronized DataBaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DataBaseHelper(context);
        return instance;
    }

    private DataBaseHelper(Context context) {
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
        db.execSQL(CREATE_CART_TABLE);
        db.execSQL(CREATE_FAVORITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CART_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FAVORITE_TABLE);

    }
}