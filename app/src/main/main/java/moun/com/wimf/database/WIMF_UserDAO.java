package moun.com.wimf.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import moun.com.wimf.WIMF_LoginActivity;
import moun.com.wimf.model.User;
import moun.com.wimf.model.WIMF_Utilisateur;

/**
 * This Class using SQLiteDatabase object provides methods for SQLite CRUD
 * (Create, Read, Update, Delete) operations.
 */
public class WIMF_UserDAO extends WIMF_ItemsDBDAO{
    private static final String LOG_TAG = WIMF_UserDAO.class.getSimpleName();
    WIMF_DataBaseHelper_new wimf_db;

    private static final String DATABASE_NAME = "WIMFdb";
    private static final int DATABASE_VERSION = 1;

    public WIMF_UserDAO(Context context)
    {
        super(context);
        wimf_db = new WIMF_DataBaseHelper_new(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public long saveUserToTable(WIMF_Utilisateur user) {
        ContentValues values = new ContentValues();
        values.put(WIMF_DataBaseHelper.ID_COLUMN_USER, user.get_idU());
        values.put(WIMF_DataBaseHelper.NAME_COLUMN, user.get_nom());
        values.put(WIMF_DataBaseHelper.PHONE_COLOMN, user.get_tel());
        values.put(WIMF_DataBaseHelper.GPS_lat_COLOMN, user.get_gps_lat());
        values.put(WIMF_DataBaseHelper.GPS_long_COLOMN, user.get_gps_lat());
        values.put(WIMF_DataBaseHelper.PASSWORD_COLOMN, user.get_password());
         values.put(WIMF_DataBaseHelper.CREATION_DATE_COLUMN, user.get_datetimeCrea().toString());
        values.put(WIMF_DataBaseHelper.UPDATE_DATE_COLUMN, user.get_datetimeMaj().toString());
        Log.d("WIMF_Utilisateur",user.toString());
        return database.insert(WIMF_DataBaseHelper.USER_TABLE, null, values);
    }

    public long UpdateUser(int idU, WIMF_Utilisateur user) {
        ContentValues values = new ContentValues();

        values.put(WIMF_DataBaseHelper.NAME_COLUMN, user.get_nom());
        values.put(WIMF_DataBaseHelper.PHONE_COLOMN, user.get_tel());
        values.put(WIMF_DataBaseHelper.GPS_lat_COLOMN, user.get_gps_lat());
        values.put(WIMF_DataBaseHelper.GPS_long_COLOMN, user.get_gps_long());
        values.put(WIMF_DataBaseHelper.PASSWORD_COLOMN, user.get_password());
        values.put(WIMF_DataBaseHelper.UPDATE_DATE_COLUMN, new Date().toString());
        return database.update(WIMF_DataBaseHelper.USER_TABLE, values, WIMF_DataBaseHelper.ID_COLUMN_USER + " = " + idU, null);

    }

    // Getting user data from WIMF_Utilisateur table
    public WIMF_Utilisateur getUserDetails() {
        WIMF_Utilisateur user = null;
        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.USER_TABLE;
        Cursor cursor = database.rawQuery(sql, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user = new WIMF_Utilisateur();
            user.set_idU(cursor.getInt(0));
            user.set_nom(cursor.getString(1));
            user.set_tel(cursor.getString(2));
            user.set_gps_lat(cursor.getDouble(3));
            user.set_gps_long(cursor.getDouble(4));
            user.set_password(cursor.getString(5));

        }

        // return user
        //Log.d(LOG_TAG, "Fetching user from Sqlite: " + user.toString());
        return user;
    }
    public List<WIMF_Utilisateur> getAllUserDetails() {
        List<WIMF_Utilisateur> users = new ArrayList<WIMF_Utilisateur>();
        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.USER_TABLE;
        Cursor cursor = database.rawQuery(sql, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            WIMF_Utilisateur user;
            user = new WIMF_Utilisateur();
            user.set_idU(cursor.getInt(0));
            user.set_nom(cursor.getString(1));
            user.set_tel(cursor.getString(2));
            user.set_gps_lat(cursor.getDouble(3));
            user.set_gps_long(cursor.getDouble(4));
            user.set_password(cursor.getString(5));

            //Add to list
                users.add(user);
        }

        // return user
        //Log.d(LOG_TAG, "Fetching user from Sqlite: " + user.toString());
        return users;
    }


    public WIMF_Utilisateur searchForUser(String name) {
        WIMF_Utilisateur user = null;

        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.USER_TABLE
                + " WHERE " + WIMF_DataBaseHelper.NAME_COLUMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { name + "" });

        if (cursor.moveToNext()) {
            user = new WIMF_Utilisateur();
            user = new WIMF_Utilisateur();
            user.set_idU(cursor.getInt(0));
            user.set_nom(cursor.getString(1));
            user.set_tel(cursor.getString(2));
            user.set_gps_lat(cursor.getDouble(3));
            user.set_gps_long(cursor.getDouble(4));
            user.set_password(cursor.getString(5));
        }

        return user;
    }
    public WIMF_Utilisateur searchUserByNumber (String number) {
        WIMF_Utilisateur user = null;

        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.USER_TABLE
                + " WHERE " + WIMF_DataBaseHelper.PHONE_COLOMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { number + "" });

        if (cursor.moveToNext()) {
            user = new WIMF_Utilisateur();
            user = new WIMF_Utilisateur();
            user.set_idU(cursor.getInt(0));
            user.set_nom(cursor.getString(1));
            user.set_tel(cursor.getString(2));
            user.set_gps_lat(cursor.getDouble(3));
            user.set_gps_long(cursor.getDouble(4));
            user.set_password(cursor.getString(5));
        }

        return user;
    }


    // Delete user table and re-create again
    public void deleteUser() {
        // Delete All Rows
        database.delete(WIMF_DataBaseHelper.USER_TABLE, null, null);
        database.close();

        Log.d(LOG_TAG, "Deleted all user info from user table");
    }
}
