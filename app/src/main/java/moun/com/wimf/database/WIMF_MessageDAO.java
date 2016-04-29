package moun.com.wimf.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import moun.com.wimf.model.WIMF_Message;


/**
 * Created by maiga mariam on 25/04/2016.
 */
public class WIMF_MessageDAO extends ItemsDBDAO{

    public WIMF_MessageDAO(Context context) {
        super(context);
    }

    public long saveMessageToTable(WIMF_Message mes) {
        ContentValues values = new ContentValues();
        values.put(WIMF_DataBaseHelper.MESSAGE_COLUMN, mes.getMessage());
        values.put(WIMF_DataBaseHelper.STATE_COLUMN, mes.getState());
        values.put(WIMF_DataBaseHelper.SENDER_COLOMN, mes.getSender());
        values.put(WIMF_DataBaseHelper.RECEIVER_COLOMN, mes.getReceiver());
        values.put(WIMF_DataBaseHelper.CREATION_DATE_COLUMN, mes.getCreation_date());
        values.put(WIMF_DataBaseHelper.UPDATE_DATE_COLUMN, mes.getUpdate_date());

        return database.insert(WIMF_DataBaseHelper.Messages_TABLE, null, values);
    }


    public List<WIMF_Message> getAllUserMessages(String SenderPhone) {
        List<WIMF_Message> messages = new ArrayList<WIMF_Message>();

        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.Messages_TABLE
                + " WHERE " + WIMF_DataBaseHelper.SENDER_COLOMN + " = ?";
        Cursor cursor = database.rawQuery(sql, new String[] { SenderPhone + "" });
        // Move to first row
        cursor.moveToFirst();
        do {
            if (cursor.getCount() > 0) {
                WIMF_Message message;
                message = new WIMF_Message();
                message.setId(cursor.getInt(0));
                message.setMessage(cursor.getString(1));
                message.setState(cursor.getInt(2));
                message.setSender(cursor.getString(3));
                message.setReceiver(cursor.getString(4));
                message.setCreation_date(cursor.getString(5));
                message.setUpdate_date(cursor.getString(6));
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
                message = new WIMF_Message();
                message.setId(cursor.getInt(0));
                message.setMessage(cursor.getString(1));
                message.setState(cursor.getInt(2));
                message.setSender(cursor.getString(3));
                message.setReceiver(cursor.getString(4));
                message.setCreation_date(cursor.getString(5));
                message.setUpdate_date(cursor.getString(6));
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
        return database.delete(WIMF_DataBaseHelper.Messages_TABLE, WIMF_DataBaseHelper.ID_COLUMN + " = " +id, null);
    }

    public int removeUserMessages(String sender){

        return database.delete(WIMF_DataBaseHelper.Messages_TABLE, WIMF_DataBaseHelper.SENDER_COLOMN + " = " +sender, null);
    }
}
