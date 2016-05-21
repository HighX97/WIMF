package moun.com.wimf;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moun.com.wimf.database.UserDAO;
import moun.com.wimf.fragment.ResetPasswordDialogFragment;
import moun.com.wimf.model.User;
import moun.com.wimf.util.AppUtils;
import moun.com.wimf.util.SessionManager;


public class LoginActivity extends AppCompatActivity {

    private ProgressDialog progress;
    private static final String LOG_TAG = LoginActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private TextView mTitle;
    private EditText mInputUsername;
    private EditText mInputPassword;
    private UserDAO userDAO;
    private SessionManager session;
    LoginActivity loginActivity = this;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //setContentView(R.layout.activity_signin);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        //mTitle.setText(getString(R.string.login));
        mTitle.setText("Sign IN");
        mTitle.setTypeface(AppUtils.getTypeface(this, AppUtils.FONT_BOLD));
        mInputUsername = (EditText) findViewById(R.id.username);

        mInputPassword = (EditText) findViewById(R.id.password);
        userDAO = new UserDAO(this);
        List<User> listUsers = userDAO.getAllUserDetails();
        if (listUsers != null) {
            for (User u : listUsers) {
                Toast.makeText(this, listUsers.size() + " " + u.toString(), Toast.LENGTH_LONG).show();
            }
        }
        // Session manager
        session = new SessionManager(getApplicationContext());


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    // Login button Click Event
    public void LoginClick(View view) {
        boolean isEmptyUsername = isEmpty(mInputUsername);
        boolean isEmptyPassword = isEmpty(mInputPassword);
        // Check for empty data in the form
        if (isEmptyUsername) {
            mInputUsername.setError("Enter your username");
            mInputPassword.setError(null);
        } else if (isEmptyPassword) {
            mInputPassword.setError("Enter your password");
            mInputUsername.setError(null);
        } else {
            mInputUsername.setError(null);
            mInputPassword.setError(null);
            final String tel = mInputUsername.getText().toString().trim();
            final String password = mInputPassword.getText().toString().trim();
            /*
            String url = "http://46.101.40.23:8585/utilisateur/connect";
            HashMap<String, String> parametres = new HashMap<String, String>();
            parametres.put("tel", tel);
            parametres.put("password", password);
            final String post_result = RestHelper.executePOST(url, parametres);
            Log.d("post_result ", " post_result: " + post_result);
            */
            new PostClass(this).execute();

        }

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://moun.com.wimf/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://moun.com.wimf/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private class PostClass extends AsyncTask<String, Void, Void> {

        private final Context context;

        public PostClass(Context c) {
            this.context = c;
        }

        @Override
        protected Void doInBackground(String... params) {
            String url = "http://46.101.40.23:8585/utilisateur/connect";
            HashMap<String, String> parametres = new HashMap<String, String>();
            parametres.put("tel", "0695504940");
            parametres.put("password", "password");
            final String post_result = RestHelper.executePOST(url, parametres);

            LoginActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    dialogMessage("post_result", post_result);
                    JSONObject jsonRootObject = null;
                    try {
                        jsonRootObject = new JSONObject(post_result);
                        //Get the instance of JSONArray that contains JSONObjects
                        JSONArray jsonArray = jsonRootObject.optJSONArray("data");

                        //Iterate the jsonArray and print the info of JSONObjects
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            int idU = Integer.parseInt(jsonObject.optString("idU").toString());
                            dialogMessage("idU", "" + idU);
                            String nom = jsonObject.optString("nom").toString();
                            dialogMessage("nom", nom);
                            String tel = jsonObject.optString("tel").toString();
                            dialogMessage("tel", tel);
                            String gps_lat = jsonObject.optString("gps_lat").toString();
                            dialogMessage("gps_lat", gps_lat);
                            String gps_long = jsonObject.optString("gps_long").toString();
                            dialogMessage("gps_long", gps_long);
                            String password = jsonObject.optString("password").toString();
                            dialogMessage("password", password);
                            String datetimeCrea = jsonObject.optString("datetimeCrea").toString();
                            dialogMessage("datetimeCrea", datetimeCrea);
                            String datetimeMaj = jsonObject.optString("datetimeMaj").toString();
                            dialogMessage("datetimeMaj", datetimeMaj);
                            if (jsonArray.length() == 0) {
                                dialogMessage("Couldn't Sign In!", "Please check your username and password and try again.");
                            } else {
                                // user successfully logged in
                                // Create login session
                                session.setLogin(true);
                                /*
                                // Launch main activity
                                Intent intent = new Intent(this, WIMF_MainActivity.class);
                                // Closing all the Activities
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                // Add new Flag to start new Activity
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                // Staring Main Activity
                                startActivity(intent);
                                finish();
                                */


                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    progress.dismiss();

                }
            });


            return null;
        }

        protected void onPreExecute() {
            progress = new ProgressDialog(this.context);
            progress.setMessage("Loading");
            progress.show();
        }

        private String getQuery(HashMap<String, String> params) throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();
            boolean first = true;

            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }

            return result.toString();
        }

        protected void onPostExecute() {
            progress.dismiss();
        }

    }

    // Link to reset password dialog fragment
    public void ResetPassword(View view) {
        ResetPasswordDialogFragment.show(loginActivity);

    }

    // Link to Register Screen
    public void RegisteronClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    // Method to check for empty data in the form
    private boolean isEmpty(EditText editText) {
        return editText.getText() == null
                || editText.getText().toString() == null
                || editText.getText().toString().isEmpty();

    }

    // Custom dialog fragment using SimpleDialogFragment library
    private void dialogMessage(String title, String message) {
        SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButtonText(getString(R.string.ok))
                .setCancelable(false)
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }
}
