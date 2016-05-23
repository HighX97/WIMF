package moun.com.wimf.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import moun.com.wimf.model.WIMF_Utilisateur;
import moun.com.wimf.util.SessionManager;

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
      Log.d("post_result", post_result);
    this.activity.runOnUiThread(new Runnable() {

      @Override
      public void run() {
        Log.d("post_result", post_result);
        JSONObject jsonRootObject = null;
        try {

          jsonRootObject = new JSONObject(post_result);


          String message = jsonRootObject.optString("message");
          String[] separated_message = message.split(":");
            String route = separated_message[0].trim();; // this will contain "Fruit"
            String err = separated_message[1].trim();; // this will contain "Fruit"
          String[] separated_route = route.split("/"); // this will contain "Fruit"
          String table = separated_route[0].trim();; // this will contain " they taste good"
          String action = separated_route[1].trim();; // this will contain " they taste good"
            JSONArray jsonArray =null;
            Log.d("route", route);
            Log.d("action", action);
            Log.d("err", err);
            Log.d("table", table);
            Boolean bool = table.equalsIgnoreCase("Utilisateur");
            Boolean bool2 = action.equalsIgnoreCase("connect");
            Log.d("table.equalsIgnoreCase(Utilisateur)", bool.toString());
            Log.d("action.equalsIgnoreCase(connect)", bool2.toString());

          if(table.equalsIgnoreCase("Amis"))
            {
                if(action.equalsIgnoreCase("update_state"))
                {

                }
                else if (action.equalsIgnoreCase("update_state"))
                {

                }
                else if (action.equalsIgnoreCase("list"))
                {

                }
                else if (action.equalsIgnoreCase("one"))
                {

                }
                else if (action.equalsIgnoreCase("new"))
                {

                }
            }
            else if (table.equalsIgnoreCase("Utilisateur"))
            {
                Log.d("route", route);
              if (action.equalsIgnoreCase("update_state"))
              {

              }
              else if (action.equalsIgnoreCase("list"))
              {

              }
              else if (action.equalsIgnoreCase("connect")) {
                  Log.d("route", route);
                  if (err.equalsIgnoreCase("failed")) {
                      Log.d(route, "failed");





                  }
                  else {
                      Log.d(route, "not failed");
                      //Get the instance of JSONArray that contains JSONObjects

                      //Get the instance of JSONArray that contains JSONObjects
                      jsonArray = jsonRootObject.optJSONArray("data");

                      //Iterate the jsonArray and print the info of JSONObjects
                      WIMF_Utilisateur utilisateur_connection = new WIMF_Utilisateur();
                      for (int i = 0; i < jsonArray.length(); i++) {
                          JSONObject jsonObject = jsonArray.getJSONObject(i);

                          int idU = Integer.parseInt(jsonObject.optString("idU").toString());
                          utilisateur_connection.set_idU(idU);
                          Log.d("idU", "" + idU);
                          String nom = jsonObject.optString("nom").toString();
                          utilisateur_connection.set_nom(nom);
                          Log.d("nom", nom);
                          String tel = jsonObject.optString("tel").toString();
                          utilisateur_connection.set_tel(tel);
                          Log.d("tel", tel);
                          String gps_lat = jsonObject.optString("gps_lat").toString();
                          Log.d("gps_lat", gps_lat);
                          if(gps_lat != "null" && !gps_lat.isEmpty()) {
                              utilisateur_connection.set_gps_lat(Double.parseDouble(gps_lat));
                          }
                          String gps_long = jsonObject.optString("gps_long").toString();
                          Log.d("gps_long", gps_long);
                          if(gps_lat != "null" && !gps_lat.isEmpty()) {
                              utilisateur_connection.set_gps_long(Double.parseDouble(gps_long));
                          }
                          String password = jsonObject.optString("password").toString();
                          utilisateur_connection.set_password(password);
                          Log.d("password", password);
                          String datetimeCrea = jsonObject.optString("datetimeCrea").toString();
                          String datetimeMaj = jsonObject.optString("datetimeMaj").toString();
                          Log.d("password", password);
                          Log.d("password", password);
                          SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-ddThh:mm:ss.000Z");
                          Date converted_datetimeCrea = new Date();
                          Date converted_datetimeMaj = new Date();
                          try {
                              converted_datetimeCrea = dateFormat.parse(datetimeCrea);
                              converted_datetimeMaj = dateFormat.parse(datetimeMaj);
                              utilisateur_connection.set_datetimeCrea(converted_datetimeCrea);
                              Log.d("datetimeCrea", datetimeCrea);
                              utilisateur_connection.set_datetimeMaj(converted_datetimeMaj);
                              Log.d("datetimeMaj", datetimeMaj);
                          } catch (java.text.ParseException e) {
                              e.printStackTrace();
                          }
                          // Session manager
                          SessionManager session = new SessionManager(activity);
                          session.setLogin(true);
                      }
                  }
              }
              else if (action.equalsIgnoreCase("new"))
              {
                  if (err.equalsIgnoreCase("failed"))
                  {
                      Log.d(route, "failed");
                  }
                  else
                  {
                      Log.d(route, "not failed");
                  }
                }
            }
            else if (action.equalsIgnoreCase("Message"))
            {
              if (action.equalsIgnoreCase("update_state"))
              {

              }
              else if (action.equalsIgnoreCase("list"))
              {

              }
              else if (action.equalsIgnoreCase("one"))
              {

              }
              else if (action.equalsIgnoreCase("new"))
              {

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
