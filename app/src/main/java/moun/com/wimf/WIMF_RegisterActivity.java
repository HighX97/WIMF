package moun.com.wimf;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.lang.ref.WeakReference;
import java.util.HashMap;

import moun.com.wimf.database.UserDAO;
import moun.com.wimf.helper.PostClass;
import moun.com.wimf.helper.RestHelper;
import moun.com.wimf.model.User;
import moun.com.wimf.util.AppUtils;
import moun.com.wimf.model.*;


public class WIMF_RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = RegisterActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private TextView mTitle;
    private EditText mInputUsername;
    private EditText mInputEmail;
    private EditText mInputAddress;
    private EditText mInputPhone;
    private EditText mInputPassword;
    private Button registerButton;
    private User user;
    private WIMF_Utilisateur utilisateur;
    private UserDAO userDAO;
    private UserRegisterTask task;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wimf_activity_register);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(getString(R.string.register));
        mTitle.setTypeface(AppUtils.getTypeface(this, AppUtils.FONT_BOLD));

        mInputUsername = (EditText) findViewById(R.id.name);
        //mInputEmail = (EditText) findViewById(R.id.email);
        ////mInputAddress = (EditText) findViewById(R.id.address);
        mInputPhone = (EditText) findViewById(R.id.phone);
        mInputPassword = (EditText) findViewById(R.id.password);
        registerButton = (Button) findViewById(R.id.register_btn);
        userDAO = new UserDAO(this);

        registerButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        boolean isEmptyUsername = isEmpty(mInputUsername);
        //boolean isEmptyEmail = isEmpty(MInputEmail);
        //boolean isEmptyAddress = isEmpty(mInputAddress);
        boolean isEmptyPhone = isEmpty(mInputPhone);
        boolean isEmptyPassword = isEmpty(mInputPassword);
        if (isEmptyUsername) {
            mInputUsername.setError("Please enter a username");
            //mInputEmail.setError(null);
            ////mInputAddress.setError(null);
            mInputPhone.setError(null);
            mInputPassword.setError(null);
        } else if (isEmptyPhone) {
            mInputPhone.setError("Please enter your phone number");
            mInputUsername.setError(null);
            //mInputEmail.setError(null);
            ////mInputAddress.setError(null);
            mInputPassword.setError(null);
        } else if (isEmptyPassword) {
            mInputPassword.setError("Please enter a password");
            mInputUsername.setError(null);
            //mInputEmail.setError(null);
            ////mInputAddress.setError(null);
            mInputPhone.setError(null);

        } else {
            String username = mInputUsername.getText().toString().trim();
            String phone = mInputPhone.getText().toString().trim();
            String password = mInputPassword.getText().toString().trim();
            if (!isValidPassword(password)) {
                mInputPassword.setError("Must be at least 6 characters");
                mInputUsername.setError(null);
                mInputPhone.setError(null);
            } else if (username.length() < 3) {
                mInputUsername.setError("Username is too short");
                mInputPhone.setError(null);
                mInputPassword.setError(null);
            } else if (username.length() > 15) {
                mInputUsername.setError("Username is too long");
                mInputPhone.setError(null);
                mInputPassword.setError(null);
            } else if (userDAO.searchForUser(username) != null) {
                mInputUsername.setError("Choose a unique name");
                mInputPhone.setError(null);
                mInputPassword.setError(null);
            } else {
                mInputUsername.setError(null);
                ////mInputAddress.setError(null);
                mInputPhone.setError(null);
                //mInputEmail.setError(null);
                mInputPassword.setError(null);
                //userRegister(username, email, phone, address, password);
                utilisateurRegister(username, phone, password);

            }

        }

    }

    /**
     * This is not a complete login and registration built system.
     * simply we get the user data from the input form and save it in local database, in our case (sqlite).
     * but you need to interact with database server by inserting and fetching data using (GET/POST methods) requests,
     * and get the response back in JSON format.
     *
     * @param nom
     * @param tel
     * @param password
     */
    public void utilisateurRegister(final String nom, final String tel, final String password) {

        utilisateur = new WIMF_Utilisateur();
        utilisateur.set_nom(nom);
        utilisateur.set_tel(tel);
        utilisateur.set_password(password);

        String url = "http://46.101.40.23:8585/utilisateur/new";
        HashMap<String, String> parametres = new HashMap<String, String>();
        parametres.put("tel", tel);
        parametres.put("password", password);
        parametres.put("nom", nom);
        final String post_result = RestHelper.executePOST(url, parametres);
        Log.d("post_result ", " post_result: " + post_result);
        new PostClass(this,parametres,url).execute();
    }





    public class UserRegisterTask extends AsyncTask<Void, Void, Long> {

        private final WeakReference<Activity> activityWeakRef;
        Context context;

        public UserRegisterTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
            this.context = context.getApplicationContext();
        }

        @Override
        protected Long doInBackground(Void... arg0) {
            // Inserting row in user table
            long result = userDAO.saveUserToTable(user);

            return result;
        }

        @Override
        protected void onPostExecute(Long result) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                if (result != -1) {
                    // successful registration
                    Intent intent = new Intent(context, LoginActivity.class);
                    // Closing all the Activities
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // Add new Flag to start new Activity
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    // Staring Login Activity
                    context.startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                    Log.d(LOG_TAG, user.toString());

                }


            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    // Method to check for empty data in the form
    private boolean isEmpty(EditText editText) {
        return editText.getText() == null
                || editText.getText().toString() == null
                || editText.getText().toString().isEmpty();

    }

    private boolean isValidPassword(String pass) {
        if (pass.length() >= 6) {
            return true;
        }
        return false;
    }


}