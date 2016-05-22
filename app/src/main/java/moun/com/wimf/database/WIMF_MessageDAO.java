package moun.com.wimf.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import moun.com.wimf.model.WIMF_Message;


/**
 * Created by maiga mariam on 25/04/2016.
 */
public class WIMF_MessageDAO extends WIMF_ItemsDBDAO{

    public WIMF_MessageDAO(Context context) {
        super(context);
    }

    public long saveMessageToTable(WIMF_Message mes) {
        ContentValues values = new ContentValues();
        values.put(WIMF_DataBaseHelper.MESSAGE_COLUMN, mes.get_valeur());
        values.put(WIMF_DataBaseHelper.SENDER_TEL_COLOMN, mes.get_tel_snd());
        values.put(WIMF_DataBaseHelper.RECEIVER_TEL_COLOMN, mes.get_tel_rcv());
        values.put(WIMF_DataBaseHelper.STATE_COLUMN, mes.get_etat());
        values.put(WIMF_DataBaseHelper.CREATION_DATE_COLUMN, mes.get_date_create().toString());
        values.put(WIMF_DataBaseHelper.UPDATE_DATE_COLUMN, mes.get_date_open().toString());

        return database.insert(WIMF_DataBaseHelper.Messages_TABLE, null, values);
    }


    public List<WIMF_Message> getAllUserMessages(String SenderPhone) {
        List<WIMF_Message> messages = new ArrayList<WIMF_Message>();

        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.Messages_TABLE
                + " WHERE " + WIMF_DataBaseHelper.SENDER_TEL_COLOMN + " = ?";
        Cursor cursor = database.rawQuery(sql, new String[] { SenderPhone + "" });
        // Move to first row
        cursor.moveToFirst();
        do {
            if (cursor.getCount() > 0) {
                WIMF_Message message;
                message = new WIMF_Message(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),new Date(cursor.getLong(5)),new Date(cursor.getLong(6)));
                //Add to list
                messages.add(message);
            }
        }
        while(cursor.moveToNext());
        cursor.close();

        return messages;
    }




    public List<WIMF_Message> searchForMessageByState(String state) {
        List<WIMF_Message> messages = new ArrayList<WIMF_Message>();

        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.Messages_TABLE
                + " WHERE " + WIMF_DataBaseHelper.STATE_COLUMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { state + "" });

        cursor.moveToFirst();
        do {
            if (cursor.getCount() > 0) {
                WIMF_Message message;
                message = new WIMF_Message(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),new Date(cursor.getLong(5)),new Date(cursor.getLong(6)));

                //Add to list
                messages.add(message);
            }
        }
        while(cursor.moveToNext());
        cursor.close();

        return messages;
    }

    public int removeMessageWithID(int id){
        //Suppression d'un Message de la BDD grâce à l'ID
        return database.delete(WIMF_DataBaseHelper.Messages_TABLE, WIMF_DataBaseHelper.ID_COLUMN_MESSAGE + " = " +id, null);
    }

    public int removeUserMessages(String sender){

        return database.delete(WIMF_DataBaseHelper.Messages_TABLE, WIMF_DataBaseHelper.SENDER_TEL_COLOMN + " = " +sender, null);
    }
}
