package moun.com.wimf.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import moun.com.wimf.WIMF_MainActivity;
import moun.com.wimf.service.WIMF_Gps;

/**
 * This class maintains session data across the app using the SharedPreferences.
 * We store a boolean flag isLoggedIn in shared preferences to check the login status.
 */
public class SessionManager {
    // LogCat tag
    private static final String LOG_TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "RestaurantUserLogin";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        if(isLoggedIn)
        {

            Intent intent = new Intent(_context, WIMF_Gps.class);
            _context.startService(intent);
        }
        else
        {
            Intent intent = new Intent(_context, WIMF_Gps.class);
            _context.stopService(intent);
        }

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();
        Log.d(LOG_TAG, "User login session modified!");

;
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}
