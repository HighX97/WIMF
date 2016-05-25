package moun.com.wimf;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import moun.com.wimf.database.WIMF_UserDAO;
import moun.com.wimf.helper.PostClass;
import moun.com.wimf.model.WIMF_Utilisateur;
import moun.com.wimf.service.WIMF_Gps;
import moun.com.wimf.util.AppUtils;
import android.location.LocationListener;
import android.widget.Toast;

import java.util.HashMap;

/**
 * An Activity handling the Google maps and locations integration with your app.
 */
public class WIMF_LocationActivity extends AppCompatActivity implements  LocationListener,
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    private static final String LOG_TAG = WIMF_LocationActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private TextView mTitle;

    private static final LatLng MAIN_BRANCH = new LatLng(47.840769, -3.510437);
    private static final LatLng BRANCH_TWO = new LatLng(47.969653, -1.813049);
    private static final LatLng BRANCH_THREE = new LatLng(47.146390, -1.348824);
    private static final LatLng BRANCH_FOUR = new LatLng(48.558983, -1.783860);

    private Marker mainBranch;
    private Marker branchTwo;
    private Marker branchThree;
    private Marker branchFour;

    private LocationManager locationManager;
    private GoogleMap gMap;
    private Marker marker;
    private WIMF_UserDAO userDAO;

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
        setContentView(R.layout.activity_location);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(getString(R.string.locations));
        mTitle.setTypeface(AppUtils.getTypeface(this, AppUtils.FONT_BOLD));

        Intent intent_service = new Intent(this, WIMF_Gps.class);
        ComponentName service;
        service = this.startService(intent_service);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        int height = 100;
        int width = 100;
        BitmapDrawable bitmapdraw1=(BitmapDrawable)getResources().getDrawable(R.drawable.usericon1);
        BitmapDrawable bitmapdraw2=(BitmapDrawable)getResources().getDrawable(R.drawable.usericon2);
        Bitmap b1=bitmapdraw1.getBitmap();
        Bitmap b2=bitmapdraw2.getBitmap();
        Bitmap smallMarkerU1 = Bitmap.createScaledBitmap(b1, width, height, false);
        Bitmap smallMarkerU2 = Bitmap.createScaledBitmap(b2, width, height, false);


        mainBranch = mMap.addMarker(new MarkerOptions()
                .position(MAIN_BRANCH)
                .title("Main Branch")
                .snippet("Le Mant st. / Tel: + 123 456789")
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarkerU2)));

        branchTwo = mMap.addMarker(new MarkerOptions()
                .position(BRANCH_TWO)
                .title("Branch II")
                .snippet("Saint-Brieuc / Tel: + 123 456789")
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarkerU1)));

        branchThree = mMap.addMarker(new MarkerOptions()
                .position(BRANCH_THREE)
                .title("Branch III")
                .snippet("Lorient / Tel: + 123 456789")
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarkerU2)));

        branchFour = mMap.addMarker(new MarkerOptions()
                .position(BRANCH_FOUR)
                .title("Branch IV")
                .snippet("Mont-Dol / Tel: + 123 456789")
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarkerU1)));

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


    public void abonnementGPS() {
        //On s'abonne

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
    }


    public void desabonnementGPS() {
        //Si le GPS est disponible, on s'y abonne

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
    }


    @Override
    public void onLocationChanged(final Location location) {
        //On affiche dans un Toat la nouvelle Localisation
        final StringBuilder msg = new StringBuilder("lat : ");
        msg.append(location.getLatitude());
        msg.append( "; lng : ");
        msg.append(location.getLongitude());

        Toast.makeText(this, msg.toString(), Toast.LENGTH_LONG).show();

        //Mise à jour des coordonnées
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        gMap.addMarker(new MarkerOptions().title("Vous êtes ici").position(latLng));

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        marker.setPosition(latLng);
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        gMap.addMarker(new MarkerOptions().title("Vous êtes ici").position(latLng));

        WIMF_Utilisateur utilisateur = userDAO.getUserDetails();
        Log.d("gps", utilisateur.toString());
        Log.d("gps","-------------------------------");
        String url = "http://46.101.40.23:8585/utilisateur/update_gps";
        Log.d("url", url);
        HashMap<String, String> parametres = new HashMap<String, String>();
        parametres.put("tel", "1234567890");
        parametres.put("gps_lat", String.valueOf(location.getLatitude()));
        parametres.put("gps_long", String.valueOf(location.getLatitude()));
        new PostClass(parametres,url).execute();
    }


    @Override
    public void onProviderDisabled(final String provider) {
        //Si le GPS est désactivé on se désabonne
        if("gps".equals(provider)) {
            desabonnementGPS();
        }
    }


    @Override
    public void onProviderEnabled(final String provider) {
        //Si le GPS est activé on s'abonne
        if("gps".equals(provider)) {
            abonnementGPS();
        }
    }


    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) { }
}
