package moun.com.wimf.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import moun.com.wimf.LoginActivity;
import moun.com.wimf.model.User;
import moun.com.wimf.model.WIMF_User;

/**
 * This Class using SQLiteDatabase object provides methods for SQLite CRUD
 * (Create, Read, Update, Delete) operations.
 */
public class WIMF_UserDAO extends ItemsDBDAO{
    private static final String LOG_TAG = UserDAO.class.getSimpleName();


    public WIMF_UserDAO(Context context) {
        super(context);
    }

    public long saveUserToTable(WIMF_User user) {
        ContentValues values = new ContentValues();
        values.put(WIMF_DataBaseHelper.NAME_COLUMN, user.getUserName());
        //values.put(WIMF_DataBaseHelper.EMAIL_COLOMN, user.getEmail());
        //values.put(WIMF_DataBaseHelper.ADDRESS_COLOMN, user.getAddress());
        values.put(WIMF_DataBaseHelper.PHONE_COLOMN, user.getPhone());
        values.put(WIMF_DataBaseHelper.PASSWORD_COLOMN, user.getPassword());
        values.put(WIMF_DataBaseHelper.GPS_COLOMN, user.getGPS());
        values.put(WIMF_DataBaseHelper.CREATION_DATE_COLUMN, user.getCreation_date());
        values.put(WIMF_DataBaseHelper.UPDATE_DATE_COLUMN, user.getUpdate_date());

        return database.insert(WIMF_DataBaseHelper.USER_TABLE, null, values);
    }

    public long UpdateUser(String number, WIMF_User user) {
        ContentValues values = new ContentValues();
        values.put(WIMF_DataBaseHelper.NAME_COLUMN, user.getUserName());
        //values.put(WIMF_DataBaseHelper.EMAIL_COLOMN, user.getEmail());
        //values.put(WIMF_DataBaseHelper.ADDRESS_COLOMN, user.getAddress());
       // values.put(WIMF_DataBaseHelper.PHONE_COLOMN, user.getPhone());
        values.put(WIMF_DataBaseHelper.PASSWORD_COLOMN, user.getPassword());
        values.put(WIMF_DataBaseHelper.GPS_COLOMN, user.getGPS());
        values.put(WIMF_DataBaseHelper.UPDATE_DATE_COLUMN, new Date().toString());
        return database.update(WIMF_DataBaseHelper.USER_TABLE, values, WIMF_DataBaseHelper.PHONE_COLOMN + " = " + number, null);

    }

    // Getting user data from WIMF_User table
    public WIMF_User getUserDetails() {
        WIMF_User user = null;
        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.USER_TABLE;
        Cursor cursor = database.rawQuery(sql, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user = new WIMF_User();
            user.setId(cursor.getInt(0));
            user.setUserName(cursor.getString(1));
            user.setPhone(cursor.getString(2));
            //user.setEmail(cursor.getString(2));
           // user.setAddress(cursor.getString(3));
            user.setPassword(cursor.getString(3));
            user.setGPS(cursor.getString(4));
        }

        // return user
        //Log.d(LOG_TAG, "Fetching user from Sqlite: " + user.toString());
        return user;
    }
    public List<WIMF_User> getAllUserDetails() {
        List<WIMF_User> users = new ArrayList<WIMF_User>();
        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.USER_TABLE;
        Cursor cursor = database.rawQuery(sql, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            WIMF_User user;
            user = new WIMF_User();
            user.setId(cursor.getInt(0));
            user.setUserName(cursor.getString(1));
            //user.setEmail(cursor.getString(2));
           // user.setAddress(cursor.getString(3));
            user.setPhone(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            user.setGPS(cursor.getString(4));
            //Add to list
                users.add(user);
        }

        // return user
        //Log.d(LOG_TAG, "Fetching user from Sqlite: " + user.toString());
        return users;
    }


    public WIMF_User searchForUser(String name) {
        WIMF_User user = null;

        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.USER_TABLE
                + " WHERE " + WIMF_DataBaseHelper.NAME_COLUMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { name + "" });

        if (cursor.moveToNext()) {
            user = new WIMF_User();
            user.setId(cursor.getInt(0));
            user.setUserName(cursor.getString(1));
            //user.setEmail(cursor.getString(2));
            //user.setAddress(cursor.getString(3));
            user.setPhone(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            user.setGPS(cursor.getString(4));
        }

        return user;
    }
    public WIMF_User searchUserByNumber (String number) {
        WIMF_User user = null;

        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.USER_TABLE
                + " WHERE " + WIMF_DataBaseHelper.PHONE_COLOMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { number + "" });

        if (cursor.moveToNext()) {
            user = new WIMF_User();
            user.setId(cursor.getInt(0));
            user.setUserName(cursor.getString(1));
            //user.setEmail(cursor.getString(2));
            //user.setAddress(cursor.getString(3));
            user.setPhone(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            user.setGPS(cursor.getString(4));
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
