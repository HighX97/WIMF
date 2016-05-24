package moun.com.wimf.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import moun.com.wimf.model.WIMF_Ami;


/**
 * Created by maiga mariam on 25/04/2016.
 */
public class WIMF_FriendDAO extends WIMF_ItemsDBDAO{

    public WIMF_FriendDAO(Context context) {
        super(context);
    }

    public long saveFriendToTable(WIMF_Ami friend) {

        ContentValues values = new ContentValues();
        values.put(WIMF_DataBaseHelper.SENDER_ID_COLOMN, friend.get_idU_snd());
        values.put(WIMF_DataBaseHelper.RECEIVER_ID_COLOMN, friend.get_idU_rcv());
        values.put(WIMF_DataBaseHelper.STATE_COLUMN, friend.get_etat());
        values.put(WIMF_DataBaseHelper.CREATION_DATE_COLUMN, friend.get_date_request());
        values.put(WIMF_DataBaseHelper.UPDATE_DATE_COLUMN, friend.get_date_response());
        Log.d("saveFriendToTable",values.toString());
        Log.d("saveFriendToTable","*********************************************************");
        return database.insert(WIMF_DataBaseHelper.Friends_TABLE, null, values);
    }


    public List<WIMF_Ami> getUserFriends(int idUser) {
        List<WIMF_Ami> friends = new ArrayList<WIMF_Ami>();

        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.Friends_TABLE
                + " WHERE " + WIMF_DataBaseHelper.SENDER_ID_COLOMN + " = ?";
        Cursor cursor = database.rawQuery(sql, new String[] { idUser + "" });
        // Move to first row
        cursor.moveToFirst();
        do {
            if (cursor.getCount() > 0) {
                WIMF_Ami frd;
                frd = new WIMF_Ami();
                frd.set_idU_snd(cursor.getInt(0));
                frd.set_idU_rcv(cursor.getInt(1));
                frd.set_etat(cursor.getInt(2));
                frd.set_date_request(cursor.getString(3));
                frd.set_date_response(cursor.getString(4));

                //Add to list
                friends.add(frd);
            }
        }
        while(cursor.moveToNext());
        cursor.close();

        return friends;
    }


    public List<WIMF_Ami> searchForFriendByState(String state) {
        List<WIMF_Ami> friends = new ArrayList<WIMF_Ami>();

        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.Friends_TABLE
                + " WHERE " + WIMF_DataBaseHelper.STATE_COLUMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { state + "" });

        cursor.moveToFirst();
        do {
            //pas la peine de rajouter le if
            if (cursor.getCount() > 0)
            {
                WIMF_Ami frd;
                frd = new WIMF_Ami();

                frd.set_idU_snd(cursor.getInt(0));
                frd.set_idU_rcv(cursor.getInt(1));
                frd.set_etat(cursor.getInt(2));
                frd.set_date_request(cursor.getString(3));
                frd.set_date_response(cursor.getString(4));

                //Add to list
                friends.add(frd);
            }
        }
        while(cursor.moveToNext());
        cursor.close();

        return friends;
    }

    public WIMF_Ami searchForFriendByNumber(String friendNumber) {
        WIMF_Ami frd = null;

        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.Friends_TABLE
                + " WHERE " + WIMF_DataBaseHelper.USER2_COLUMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { friendNumber + "" });

        cursor.moveToFirst();
        if (cursor.moveToNext()) {

                frd = new WIMF_Ami();

                frd.set_idU_snd(cursor.getInt(0));
                frd.set_idU_rcv(cursor.getInt(1));
                frd.set_etat(cursor.getInt(2));
                frd.set_date_request(cursor.getString(3));
                frd.set_date_response(cursor.getString(4));
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
