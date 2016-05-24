package moun.com.wimf.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by maiga mariam on 22/05/2016.
 */
public class WIMF_ItemsDBDAO {
    protected SQLiteDatabase database;
    private WIMF_DataBaseHelper_new dbHelper;
    private Context mContext;

    public WIMF_ItemsDBDAO(Context context) {
        this.mContext = context;
        dbHelper = WIMF_DataBaseHelper_new.getHelper(mContext);
        open();

    }

    public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = WIMF_DataBaseHelper_new.getHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }

    public void close()
    {
        database.close();
    }

    public SQLiteDatabase get_bd()
    {
        return database;
    }
}
