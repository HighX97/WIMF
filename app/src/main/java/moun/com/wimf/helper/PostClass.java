package moun.com.wimf.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by maiga mariam on 22/05/2016.
 */
public class PostClass extends AsyncTask<String, Void, Void> {

    private ProgressDialog progress;
    private Activity activity;
    private final String url;
    private final HashMap<String, String> parametres;

    public PostClass(Activity activity, HashMap<String, String> parametres, String url) {
        this.activity = activity;
        this.parametres = parametres;
        this.url = url;
    }

    @Override
    protected Void doInBackground(String... params) {
        final String post_result = RestHelper.executePOST(this.url, this.parametres);

            this.activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                  Log.d("post_result", post_result);
                    JSONObject jsonRootObject = null;
                    try {
                        jsonRootObject = new JSONObject(post_result);
                        //Get the instance of JSONArray that contains JSONObjects
                        JSONArray jsonArray = jsonRootObject.optJSONArray("data");

                        //Iterate the jsonArray and print the info of JSONObjects
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            int idU = Integer.parseInt(jsonObject.optString("idU").toString());
                          Log.d("idU", "" + idU);
                            String nom = jsonObject.optString("nom").toString();
                          Log.d("nom", nom);
                            String tel = jsonObject.optString("tel").toString();
                          Log.d("tel", tel);
                            String gps_lat = jsonObject.optString("gps_lat").toString();
                          Log.d("gps_lat", gps_lat);
                            String gps_long = jsonObject.optString("gps_long").toString();
                          Log.d("gps_long", gps_long);
                            String password = jsonObject.optString("password").toString();
                          Log.d("password", password);
                            String datetimeCrea = jsonObject.optString("datetimeCrea").toString();
                          Log.d("datetimeCrea", datetimeCrea);
                            String datetimeMaj = jsonObject.optString("datetimeMaj").toString();
                          Log.d("datetimeMaj", datetimeMaj);
                            if (jsonArray.length() == 0) {
                              Log.d("Couldn't Sign In!", "Please check your username and password and try again.");
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
        progress = new ProgressDialog(this.activity);
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
