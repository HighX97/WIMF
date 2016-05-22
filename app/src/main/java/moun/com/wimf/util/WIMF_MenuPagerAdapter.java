package moun.com.wimf.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import moun.com.wimf.LocationActivity;
import moun.com.wimf.R;
import moun.com.wimf.adapter.WIMF_UserListAdapter;
import moun.com.wimf.database.WIMF_ItemsDBDAO;
import moun.com.wimf.fragment.MenuBurgersFragment;
import moun.com.wimf.fragment.MenuDessertsFragment;
import moun.com.wimf.fragment.MenuDrinksFragment;
import moun.com.wimf.fragment.MenuPizzaFragment;
import moun.com.wimf.fragment.MenuSaladsFragment;
import moun.com.wimf.fragment.WIMF_UserProfil_Conversations_Fragment;
import moun.com.wimf.fragment.WIMF_UserProfil_Friends_Fragment;
import moun.com.wimf.fragment.WIMF_UserProfil_Info_Fragment;
import moun.com.wimf.fragment.WIMF_UserProfil_Locations_Fragment;
import moun.com.wimf.model.WIMF_UserItems;

/**
 * Class define a view pager adapter for the swipe tabs feature.
 */

public class WIMF_MenuPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public WIMF_MenuPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    private static Context context;

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                // Create a new fragment
                WIMF_UserProfil_Info_Fragment tab1 = new WIMF_UserProfil_Info_Fragment();
                return tab1;
            case 1:
                WIMF_UserProfil_Friends_Fragment tab2 = new WIMF_UserProfil_Friends_Fragment();
                return tab2;
            case 2:
                WIMF_UserProfil_Conversations_Fragment tab3 = new WIMF_UserProfil_Conversations_Fragment();
                return tab3;
            case 3:
                WIMF_UserProfil_Locations_Fragment tab4 = new WIMF_UserProfil_Locations_Fragment();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }



    private static final String LOG_TAG = LocationActivity.class.getSimpleName();
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
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private WIMF_UserListAdapter menuListAdapter;
    ArrayList<WIMF_UserItems> listItems;
    private static final String ITEMS_STATE = "items_state";
    private AlphaInAnimationAdapter alphaAdapter;
    private WIMF_ItemsDBDAO itemDAO;
    private WIMF_UserProfil_Conversations_Fragment.AddItemTask task;
    private WIMF_UserItems menuItemsFavorite = null;
}
