package moun.com.wimf.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import moun.com.wimf.model.WIMF_Friend;


/**
 * Created by maiga mariam on 25/04/2016.
 */
public class WIMF_FriendDAO extends ItemsDBDAO{

    public WIMF_FriendDAO(Context context) {
        super(context);
    }

    public long saveFriendToTable(WIMF_Friend friend) {

        ContentValues values = new ContentValues();
        values.put(WIMF_DataBaseHelper.USER1_COLUMN, friend.getUser1());
        values.put(WIMF_DataBaseHelper.USER2_COLUMN, friend.getUser2());
        values.put(WIMF_DataBaseHelper.STATE_COLUMN, friend.getState());
        values.put(WIMF_DataBaseHelper.CREATION_DATE_COLUMN, friend.getCreation_date());
        values.put(WIMF_DataBaseHelper.UPDATE_DATE_COLUMN, friend.getUpdate_date());

        return database.insert(WIMF_DataBaseHelper.Friends_TABLE, null, values);
    }


    public List<WIMF_Friend> getUserFriends(String UserPhone) {
        List<WIMF_Friend> friends = new ArrayList<WIMF_Friend>();

        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.Friends_TABLE
                + " WHERE " + WIMF_DataBaseHelper.USER1_COLUMN + " = ?";
        Cursor cursor = database.rawQuery(sql, new String[] { UserPhone + "" });
        // Move to first row
        cursor.moveToFirst();
        do {
            if (cursor.getCount() > 0) {
                WIMF_Friend frd;
                frd = new WIMF_Friend();
                frd.setId(cursor.getInt(0));
                frd.setUser1(cursor.getString(1));
                frd.setUser2(cursor.getString(2));
                frd.setState(cursor.getInt(3));
                frd.setCreation_date(cursor.getString(4));
                frd.setUpdate_date(cursor.getString(5));

                //Add to list
                friends.add(frd);
            }
        }
        while(cursor.moveToNext());
        cursor.close();

        return friends;
    }


    public List<WIMF_Friend> searchForFriendByState(String state) {
        List<WIMF_Friend> friends = new ArrayList<WIMF_Friend>();

        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.Friends_TABLE
                + " WHERE " + WIMF_DataBaseHelper.STATE_COLUMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { state + "" });

        cursor.moveToFirst();
        do {
            //pas la peine de rajouter le if
            if (cursor.getCount() > 0)
            {
                WIMF_Friend frd;
                frd = new WIMF_Friend();
                frd.setId(cursor.getInt(0));
                frd.setUser1(cursor.getString(1));
                frd.setUser2(cursor.getString(2));
                frd.setState(cursor.getInt(3));
                frd.setCreation_date(cursor.getString(4));
                frd.setUpdate_date(cursor.getString(5));

                //Add to list
                friends.add(frd);
            }
        }
        while(cursor.moveToNext());
        cursor.close();

        return friends;
    }

    public WIMF_Friend searchForFriendByNumber(String friendNumber) {
        WIMF_Friend frd = null;

        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.Friends_TABLE
                + " WHERE " + WIMF_DataBaseHelper.USER2_COLUMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { friendNumber + "" });

        cursor.moveToFirst();
        if (cursor.moveToNext()) {

                frd = new WIMF_Friend();
                frd.setId(cursor.getInt(0));
                frd.setUser1(cursor.getString(1));
                frd.setUser2(cursor.getString(2));
                frd.setState(cursor.getInt(3));
                frd.setCreation_date(cursor.getString(4));
                frd.setUpdate_date(cursor.getString(5));
            }



        return frd;
    }

    public int removeFriendWithNumber(String number){
        //Suppression d'un Message de la BDD grâce à l'ID
        return database.delete(WIMF_DataBaseHelper.Friends_TABLE, WIMF_DataBaseHelper.USER2_COLUMN + " = " +number, null);
    }

    public void removeAllFriends(){
        // Delete All Rows
        database.delete(WIMF_DataBaseHelper.Messages_TABLE, null, null);

        database.close();
    }
}
