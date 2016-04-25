package moun.com.wimf.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import moun.com.wimf.LoginActivity;
import moun.com.wimf.model.User;

/**
 * This Class using SQLiteDatabase object provides methods for SQLite CRUD
 * (Create, Read, Update, Delete) operations.
 */
public class UserDAO extends ItemsDBDAO{
    private static final String LOG_TAG = UserDAO.class.getSimpleName();


    public UserDAO(Context context) {
        super(context);
    }

    public long saveUserToTable(User user) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.NAME_COLUMN, user.getUserName());
        values.put(DataBaseHelper.EMAIL_COLOMN, user.getEmail());
        values.put(DataBaseHelper.ADDRESS_COLOMN, user.getAddress());
        values.put(DataBaseHelper.PHONE_COLOMN, user.getPhone());

        return database.insert(DataBaseHelper.USER_TABLE, null, values);
    }

    // Getting user data from User table
    public User getUserDetails() {
        User user = null;
        String sql = "SELECT * FROM " + DataBaseHelper.USER_TABLE;
        Cursor cursor = database.rawQuery(sql, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user = new User();
            user.setId(cursor.getInt(0));
            user.setUserName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setAddress(cursor.getString(3));
            user.setPhone(cursor.getString(4));
        }

        // return user
        //Log.d(LOG_TAG, "Fetching user from Sqlite: " + user.toString());
        return user;
    }
    public List<User> getAllUserDetails() {
        List<User> users = new ArrayList<User>();
        String sql = "SELECT * FROM " + DataBaseHelper.USER_TABLE;
        Cursor cursor = database.rawQuery(sql, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            User user;
            user = new User();
            user.setId(cursor.getInt(0));
            user.setUserName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setAddress(cursor.getString(3));
            user.setPhone(cursor.getString(4));
            //Add to list
                users.add(user);
        }

        // return user
        //Log.d(LOG_TAG, "Fetching user from Sqlite: " + user.toString());
        return users;
    }


    public User searchForUser(String name) {
        User user = null;

        String sql = "SELECT * FROM " + DataBaseHelper.USER_TABLE
                + " WHERE " + DataBaseHelper.NAME_COLUMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { name + "" });

        if (cursor.moveToNext()) {
            user = new User();
            user.setId(cursor.getInt(0));
            user.setUserName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setAddress(cursor.getString(3));
            user.setPhone(cursor.getString(4));
        }

        return user;
    }

    // Delete user table and re-create again
    public void deleteUser() {
        // Delete All Rows
        database.delete(DataBaseHelper.USER_TABLE, null, null);
        database.close();

        Log.d(LOG_TAG, "Deleted all user info from user table");
    }
}
