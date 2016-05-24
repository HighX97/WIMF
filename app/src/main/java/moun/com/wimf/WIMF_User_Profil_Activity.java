package moun.com.wimf;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import moun.com.wimf.helper.PostClass;
import moun.com.wimf.helper.RestHelper;
import moun.com.wimf.model.WIMF_Ami;
import moun.com.wimf.util.AppUtils;
import moun.com.wimf.util.WIMF_MenuPagerAdapter;

/**
 * An Activity handling two custom {@link android.support.v4.app.Fragment}s,
 * with {@link TabLayout} and {@link ViewPager}.
 */
public class WIMF_User_Profil_Activity extends AppCompatActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    FloatingActionButton fab;
    private static final String LOG_TAG = LocationActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private TextView mTitle;
    public static List<WIMF_Ami> amis = new ArrayList<WIMF_Ami>();
    private ProgressDialog progress;

    private static final LatLng MAIN_BRANCH = new LatLng(47.840769, -3.510437);
    private static final LatLng BRANCH_TWO = new LatLng(47.969653, -1.813049);
    private static final LatLng BRANCH_THREE = new LatLng(47.146390, -1.348824);
    private static final LatLng BRANCH_FOUR = new LatLng(48.558983, -1.783860);

    private Marker mainBranch;
    private Marker branchTwo;
    private Marker branchThree;
    private Marker branchFour;

    private GoogleMap mMap;
    /**
     * Keeps track of the last selected marker (though it may no longer be selected).  This is
     * useful for refreshing the info window.
     */
    private Marker mLastSelectedMarker;

    // Declaring Map Types
    private final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};

    private int curMapTypeIndex = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(getString(R.string.our_profil));
        mTitle.setTypeface(AppUtils.getTypeface(this, AppUtils.FONT_BOLD));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Infos"));
        tabLayout.addTab(tabLayout.newTab().setText("Amis"));
        tabLayout.addTab(tabLayout.newTab().setText("Messages"));
        tabLayout.addTab(tabLayout.newTab().setText("Locations"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        final WIMF_MenuPagerAdapter adapter = new WIMF_MenuPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(),amis);
        viewPager.setAdapter(adapter);
        int i = getIntent().getIntExtra("currentItem", 0);
        if (i == 1) {
            viewPager.setCurrentItem(0);
        }
        if (i == 2) {
            viewPager.setCurrentItem(1);
        }
        if (i == 3) {
            viewPager.setCurrentItem(2);
        }
        if (i == 4) {
            viewPager.setCurrentItem(3);
        }
        if (i == 5) {
            viewPager.setCurrentItem(4);
        }
        if (i == 6) {
            viewPager.setCurrentItem(5);
        }
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("MyApp","-------------------------------I am here : "+tab.getPosition());
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent intent = new Intent(WIMF_User_Profil_Activity.this, MyCartActivity.class);
                startActivity(intent);
                finish();
                */

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_our_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_hot) {
            /*
            Intent intent = new Intent(this, HotDealsActivity.class);
            startActivity(intent);
            finish();
            */
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        addMarkersToMap();

        mMap.setOnMarkerClickListener(this);

        // Override the default content description on the view, for accessibility mode.
        // Ideally this string would be localised.
        map.setContentDescription("Wimf Restaurant Locations");

        // Set some properties for the map
        mMap.setMapType(MAP_TYPES[curMapTypeIndex]);
        mMap.setTrafficEnabled(true);
        // setMyLocationEnabled adds a button to the top right corner of the MapFragment
        // that automatically moves the camera to your user's location when pressed
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        // setZoomControlsEnabled adds + and - buttons in the lower right corner,
        // allowing the user to change the map zoom level without having to use gestures
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Pan to see all markers in view.
        // Cannot zoom to bounds until the map has a size.
        final View mapView = getSupportFragmentManager().findFragmentById(R.id.map).getView();
        if (mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation") // We use the new method when supported
                @SuppressLint("NewApi") // We check which build version we are using.
                @Override
                public void onGlobalLayout() {
                    LatLngBounds bounds = new LatLngBounds.Builder()
                            .include(MAIN_BRANCH)
                            .include(BRANCH_TWO)
                            .include(BRANCH_THREE)
                            .include(BRANCH_FOUR)
                            .build();
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));
                }
            });
        }
    }


    private void addMarkersToMap() {
        mainBranch = mMap.addMarker(new MarkerOptions()
                .position(MAIN_BRANCH)
                .title("Main Branch")
                .snippet("Le Mant st. / Tel: + 123 456789")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.usericon1)));

        branchTwo = mMap.addMarker(new MarkerOptions()
                .position(BRANCH_TWO)
                .title("Branch II")
                .snippet("Saint-Brieuc / Tel: + 123 456789")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.usericon2)));

        branchThree = mMap.addMarker(new MarkerOptions()
                .position(BRANCH_THREE)
                .title("Branch III")
                .snippet("Lorient / Tel: + 123 456789")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.usericon1)));

        branchFour = mMap.addMarker(new MarkerOptions()
                .position(BRANCH_FOUR)
                .title("Branch IV")
                .snippet("Mont-Dol / Tel: + 123 456789")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.usericon2)));

    }


    @Override
    public boolean onMarkerClick(final Marker marker) {
        if (marker.equals(mainBranch)) {
            // This causes the marker at Perth to bounce into position when it is clicked.
            final Handler handler = new Handler();
            final long start = SystemClock.uptimeMillis();
            final long duration = 1500;

            final BounceInterpolator interpolator = new BounceInterpolator();

            handler.post(new Runnable() {
                @Override
                public void run() {
                    long elapsed = SystemClock.uptimeMillis() - start;
                    float t = Math.max(
                            1 - interpolator.getInterpolation((float) elapsed / duration), 0);
                    marker.setAnchor(0.5f, 1.0f + 2 * t);

                    if (t > 0.0) {
                        // Post again 16ms later.
                        handler.postDelayed(this, 16);
                    }
                }
            });
        }

        mLastSelectedMarker = marker;
        // We return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    private class PostClass_U extends AsyncTask<String, Void, Void> {

        private Activity activity;
        private final String url;
        private final HashMap<String, String> parametres;
        private final Context context;

        public PostClass_U(Context context, HashMap<String, String> parametres, String url) {
            this.context = context;
            this.parametres = parametres;
            this.url = url;
        }

        @Override
        protected Void doInBackground(String... params) {
            final String post_result = RestHelper.executePOST(this.url, this.parametres);
            WIMF_User_Profil_Activity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonRootObject = null;
                    try {
                        jsonRootObject = new JSONObject(post_result);
                        String message = jsonRootObject.optString("message");
                        String[] separated_message = message.split(":");
                        String route = separated_message[0].trim();
                        ; // this will contain "Fruit"
                        String err = separated_message[1].trim();
                        ; // this will contain "Fruit"
                        String[] separated_route = route.split("/"); // this will contain "Fruit"
                        String table = separated_route[0].trim();
                        ; // this will contain " they taste good"
                        String action = separated_route[1].trim();
                        ; // this will contain " they taste good"
                        JSONArray jsonArray = null;
                        Log.d("route", route);
                        Log.d("action", action);
                        Log.d("err", err);
                        Log.d("table", table);
                        Boolean bool = table.equalsIgnoreCase("Utilisateur");
                        Boolean bool2 = action.equalsIgnoreCase("connect");
                        Log.d("table.equalsIgnoreCase(Utilisateur)", bool.toString());
                        Log.d("action.equalsIgnoreCase(connect)", bool2.toString());

                        if (err.equalsIgnoreCase("failed")) {
                            Log.d(route, "failed");
                        }
                        else {
                            Log.d(route, "not failed");
                            //Get the instance of JSONArray that contains JSONObjects

                            //Get the instance of JSONArray that contains JSONObjects
                            jsonArray = jsonRootObject.optJSONArray("data");

                            //Iterate the jsonArray and print the info of JSONObjects
                        /*
                            "idU": 4,
                            "nom": "chahinaz",
                            "tel": "0783573458",
                            "datetimeCrea": "2016-05-22T08:47:38.000Z",
                            "etat": 0
                         */
                            //
                            List<WIMF_Ami> amis = new ArrayList<WIMF_Ami>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                WIMF_Ami ami = new WIMF_Ami();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                int idU = Integer.parseInt(jsonObject.optString("idU").toString());
                                ami.set_idU(idU);
                                Log.d("idU", "" + idU);
                                String nom = jsonObject.optString("nom").toString();
                                ami.set_nom(nom);
                                Log.d("nom", nom);
                                String tel = jsonObject.optString("tel").toString();
                                ami.set_tel(tel);
                                Log.d("tel", tel);
                                String gps_lat = jsonObject.optString("gps_lat").toString();
                                Log.d("gps_lat", gps_lat);
                                if (gps_lat != "null" && !gps_lat.isEmpty()) {
                                    ami.set_gps_lat(Double.parseDouble(gps_lat));
                                }
                                String gps_long = jsonObject.optString("gps_long").toString();
                                Log.d("gps_long", gps_long);
                                if (gps_lat != "null" && !gps_lat.isEmpty()) {
                                    ami.set_gps_long(Double.parseDouble(gps_long));
                                }
                                String datetimeCrea = jsonObject.optString("datetimeCrea").toString();
                                String datetimeMaj = jsonObject.optString("datetimeMaj").toString();
                                ami.set_datetimeCrea(datetimeCrea);
                                Log.d("datetimeCrea", datetimeCrea);
                                ami.set_datetimeMaj(datetimeMaj);
                                Log.d("datetimeMaj", datetimeMaj);
                                amis.add(ami);
                                Log.d("ami", ami.toString());
                            }
                            WIMF_User_Profil_Activity.amis = amis;
                            for (WIMF_Ami ami : amis)
                            {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                        context);

                                // set title
                                alertDialogBuilder.setTitle("Your Title");

                                // set dialog message
                                alertDialogBuilder
                                        .setMessage(ami.toString())
                                        .setCancelable(false)
                                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                // if this button is clicked, close
                                                // current activity
                                                WIMF_User_Profil_Activity.this.finish();
                                            }
                                        })
                                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                // if this button is clicked, just close
                                                // the dialog box and do nothing
                                                dialog.cancel();
                                            }
                                        });

                                // create alert dialog
                                AlertDialog alertDialog = alertDialogBuilder.create();

                                // show it
                                alertDialog.show();
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

        protected void onPostExecute() {
            progress.dismiss();
        }

    }
}
