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

import moun.com.wimf.database.WIMF_UserDAO;
import moun.com.wimf.helper.PostClass;
import moun.com.wimf.helper.RestHelper;
import moun.com.wimf.model.WIMF_Ami;
import moun.com.wimf.model.WIMF_Utilisateur;
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
    private static final String LOG_TAG = WIMF_LocationActivity.class.getSimpleName();
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
        WIMF_UserDAO userDAO = new WIMF_UserDAO(this);
        WIMF_Utilisateur utilisateur = userDAO.getUserDetails();
        if(utilisateur!=null) {
            tabLayout.addTab(tabLayout.newTab().setText("Amis"));
            String urlAmi = "http://46.101.40.23:8585/ami/list";
            HashMap<String, String> parametres = new HashMap<String, String>();
            parametres.put("tel", utilisateur.get_tel());


        new PostClass(this,parametres,urlAmi).execute();

        tabLayout.addTab(tabLayout.newTab().setText("Messages"));
        String url_messsage = "http://46.101.40.23:8585/message/list";
        new PostClass(this,parametres,url_messsage).execute();
        }
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
}
