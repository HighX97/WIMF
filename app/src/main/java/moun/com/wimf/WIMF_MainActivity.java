package moun.com.wimf;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import moun.com.wimf.database.WIMF_FriendDAO;
import moun.com.wimf.database.WIMF_MessageDAO;
import moun.com.wimf.database.WIMF_UserDAO;
import moun.com.wimf.fragment.WIMF_Main_Fragment;
import moun.com.wimf.fragment.WIMF_UserProfil_Friends_Fragment;
import moun.com.wimf.fragment.WIMF_UserProfil_Info_Fragment;
import moun.com.wimf.helper.RestHelper;
import moun.com.wimf.model.WIMF_Ami;
import moun.com.wimf.service.WIMF_Gps;
import moun.com.wimf.util.AppUtils;
import moun.com.wimf.util.SessionManager;

/**
 * An Activity that serves as the main entry point to your app's user interface.
 * <p/>
 * For devices with displays with a width of 600dp or greater, the Multi-pane view is visible,
 * on other devices the single-pane view is visible.
 */

public class WIMF_MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        WIMF_Main_Fragment.OnItemSelectedListener {

    private Toolbar mToolbar;
    private TextView mTitle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private boolean mUserSawDrawer = false;
    private static final String FIRST_TIME = "first_time";
    private static final String SELECTED_ITEM_ID = "selected_item_id";
    private int mSelectedId;
    private SessionManager session;
    private WIMF_UserDAO userDAO;
    private WIMF_FriendDAO friendDAO;
    private WIMF_MessageDAO msgDAO;
    private boolean isTwoPane = false;
    public static List<WIMF_Ami> amis = new ArrayList<WIMF_Ami>();
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        // Set the user interface layout for this Activity
        // The layout file is defined in the project res/layout/activity_main.xml file
        setContentView(R.layout.activity_main);
        //  Initialize the Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Initialize the title of Toolbar
        mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        if (isMyServiceRunning(WIMF_Gps.class))
        {
            mTitle.setText(getString(R.string.app_name)+" ON");
        }
        else
        {
            mTitle.setText(getString(R.string.app_name)+" OFF");
        }

        // Add a custom font to the title
        mTitle.setTypeface(AppUtils.getTypeface(this, AppUtils.FONT_BOLD));

        //  Initialize the navigation drawer's list of items
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer = (NavigationView) findViewById(R.id.navigation_view);
        // // Set the list's click listener
        mDrawer.setNavigationItemSelectedListener(this);

        mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                mToolbar, /* toolbar */
                R.string.drawer_open, /* "open drawer" description */
                R.string.drawer_close); /* "close drawer" description */

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
        if (!didUserSeeDrawer()) {
            showDrawer();
            markDrawerSeen();
        } else {
            hideDrawer();
        }
        navigate(mSelectedId);


        // Determine the Current Layout
        FrameLayout fragmentItemDetail = (FrameLayout) findViewById(R.id.content_detail_fragment);
        // Check that the activity is using the layout version with
        // the content_detail_fragment FrameLayout
        if (fragmentItemDetail != null) {
            /* The application is in the dual-pane mode, clicking on an item on the left pane will
            simply display the content on the right pane. */
            isTwoPane = true;
            // Create a new Fragment to be placed on the right pane.
            // and create MenuSandwichFragment and place it by default.
            WIMF_UserProfil_Info_Fragment menuSandwichFragment = new WIMF_UserProfil_Info_Fragment();
            switchContent(menuSandwichFragment);
        }
        if (savedInstanceState == null) {
            // Add the main fragment (whatever current layout) to the 'content_fragment' FrameLayout.
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // Create a new Fragment to be placed in the activity layout
            WIMF_Main_Fragment mainFragment = new WIMF_Main_Fragment();
            transaction.replace(R.id.content_fragment, mainFragment);
            // Commit the transaction
            transaction.commit();
        }

        // Session manager
        session = new SessionManager(getApplicationContext());

        userDAO = new WIMF_UserDAO(this);
        msgDAO = new WIMF_MessageDAO(this);
        friendDAO = new WIMF_FriendDAO(this);


    }

    /**
     * retrieve the value from shared preferences file to define if the user
     * saw the drawer open for the first time or not.
     */
    private boolean didUserSeeDrawer() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserSawDrawer = sharedPreferences.getBoolean(FIRST_TIME, false);
        return mUserSawDrawer;
    }


    /**
     * Mark the drawer as true when the user see the drawer for the first time.
     */
    private void markDrawerSeen() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserSawDrawer = true;
        sharedPreferences.edit().putBoolean(FIRST_TIME, mUserSawDrawer).apply();
    }

    /**
     * This method provides callbacks for drawer events such as openDrawer(int gravity).
     */
    private void showDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    /**
     * This method provides callbacks for drawer events such as closeDrawer(int gravity).
     */
    private void hideDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);

    }

    /**
     * Handle Navigation Click Events
     *
     * @param mSelectedId the drawer list item id's
     */
    private void navigate(int mSelectedId) {
        //    mDrawer.getMenu().getItem(mSelectedId).setCheckable(false);

        if (mSelectedId == R.id.breakfast) {
            // Close the drawer.
            hideDrawer();
            // A Handler allows you to schedule messages and runnables to be executed as some point in the future,
            // and in our situation we use it to Start a new activity after 200ms when a drawer has settled in a completely closed state.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Start an new activity.
                    Intent intent = new Intent(WIMF_MainActivity.this, WIMF_User_Profil_Activity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
            }, 200);

        } /*
        else if (mSelectedId == R.id.cart) {
            hideDrawer();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(WIMF_MainActivity.this, MyCartActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
            }, 200);

        }
        */ else if (mSelectedId == R.id.location) {
            hideDrawer();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(WIMF_MainActivity.this, WIMF_LocationActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
            }, 200);


        }
        /*
        else if (mSelectedId == R.id.orders) {
            hideDrawer();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(WIMF_MainActivity.this, ProfileActivityWithTabs.class);
                    intent.putExtra("historyTab", 1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
            }, 200);

        }
        */


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        mSelectedId = menuItem.getItemId();
        hideDrawer();
        navigate(mSelectedId);
        menuItem.setCheckable(false);
        menuItem.setChecked(false);
        return true;
    }

    // Before the activity is destroyed, onSaveInstanceState() gets called.
    // The onSaveInstanceState() method saves the selected item from drawer.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_ID, mSelectedId);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem logintItem = menu.findItem(R.id.action_login);
        MenuItem logoutItem = menu.findItem(R.id.action_logout);
        // Check if user is already logged in or not
        if (!session.isLoggedIn()) {
            // User is already logged in, hide Login button from the menu and show up the Logout button.
            logoutItem.setVisible(false);
        } else {
            logintItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent = null;

        switch (id) {
            case android.R.id.home:
                //  open the navigation drawer with a swipe gesture from or towards the left edge of the screen.
                showDrawer();
                return true;
            case R.id.our_menu:
                intent = new Intent(this, WIMF_User_Profil_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                return true;
            case R.id.location:
                intent = new Intent(this, WIMF_LocationActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                return true;
            case R.id.action_login:
                Intent intentLogin = new Intent(this, WIMF_LoginActivity.class);
                // Closing all the Activities
                intentLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Add new Flag to start new Activity
                intentLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intentLogin);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                return true;
            case R.id.action_logout:
                LogoutUser();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences, Clears the user data from SQLite user table.
     */
    public void LogoutUser() {
        msgDAO.removeAllMessages();
        friendDAO.removeAllFriends();
        userDAO.deleteUser();
        userDAO.close();
        session.setLogin(false);
        Intent intent_service = new Intent(this, WIMF_Gps.class);
        this.stopService(intent_service);
        Intent intentLogout = new Intent(this, WIMF_LoginActivity.class);
        // Closing all the Activities
        intentLogout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        intentLogout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // re-create the Main Activity
        startActivity(intentLogout);
        finish();
    }


    @Override
    public void onItemSelected(int position) {
        // The user selected an item from the WIMF_Main_Fragment.
        if (isTwoPane) {
            /* display article on the right pane */
            if (position == 1) {
                WIMF_UserProfil_Info_Fragment menuSandwichFragment = new WIMF_UserProfil_Info_Fragment();
                switchContent(menuSandwichFragment);
            } else if (position == 3) {
                for (WIMF_Ami ami : WIMF_MainActivity.amis )
                {
                    Toast.makeText(WIMF_MainActivity.this,ami.toString(),Toast.LENGTH_LONG).show();
                }
                WIMF_UserProfil_Friends_Fragment menuPizzaFragment = new WIMF_UserProfil_Friends_Fragment(amis);
                switchContent(menuPizzaFragment);
            }

        } else {
            /* The application is in single-pane mode, the content should be displayed on its own (in a different activity). */
            Intent intent = new Intent(WIMF_MainActivity.this, WIMF_User_Profil_Activity.class);
            intent.putExtra("currentItem", position);
            startActivity(intent);
        }

    }

    /**
     * Using This method to replace One Fragment with Another.
     *
     * @param fragment the fragment.
     */
    public void switchContent(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                            android.R.anim.fade_in, android.R.anim.fade_out);
            // Replace whatever is in the content_detail_fragment view with this fragment.
            transaction.replace(R.id.content_detail_fragment, fragment);
            // Commit the transaction
            transaction.commit();
        }
    }
}
