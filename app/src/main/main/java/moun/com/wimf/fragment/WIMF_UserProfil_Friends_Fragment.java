package moun.com.wimf.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import moun.com.wimf.R;
import moun.com.wimf.adapter.WIMF_UserListAdapter;
import moun.com.wimf.database.WIMF_FriendDAO;
import moun.com.wimf.database.WIMF_ItemsDAO;
import moun.com.wimf.helper.PostClass;
import moun.com.wimf.helper.RestHelper;
import moun.com.wimf.model.WIMF_Ami;
import moun.com.wimf.model.WIMF_UserItems;
import moun.com.wimf.util.AppUtils;

/**
 * This Fragment used to handle the list of items under Sandwich category using
 * {@link RecyclerView} with a {@link LinearLayoutManager}.
 */
public class WIMF_UserProfil_Friends_Fragment extends Fragment implements WIMF_UserListAdapter.ClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private WIMF_UserListAdapter menuListAdapter;
    ArrayList<WIMF_UserItems> listItems;
    private static final String ITEMS_STATE = "items_state";
    private AlphaInAnimationAdapter alphaAdapter;
    private WIMF_ItemsDAO itemDAO;
    private AddItemTask task;
    public List<WIMF_Ami> amis = new ArrayList<WIMF_Ami>();
    private WIMF_UserItems menuItemsFavorite = null;

    @SuppressLint("ValidFragment")
    public WIMF_UserProfil_Friends_Fragment(List<WIMF_Ami> amis)
    {
        super();
        this.amis = amis;
        for(WIMF_Ami ami : amis)
        {
            Log.d("amis",ami.toString());
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        itemDAO = new WIMF_ItemsDAO(getActivity());
        for(WIMF_Ami ami : amis)
        {
            Log.d("amis",ami.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu_list_items, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.sandwich_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Used for orientation change.
        if (savedInstanceState != null) {
            // We will restore the state of data list when the activity is re-created
            listItems = savedInstanceState.getParcelableArrayList(ITEMS_STATE);
        } else {
            // Initialize listItems.
            listItems = getFriendsList();

        }
        menuListAdapter = new WIMF_UserListAdapter(getActivity(), listItems, inflater, R.layout.wimf_single_row_user_list);
        //menuListAdapter = new WIMF_UserListAdapter(getActivity(), listItems, inflater, R.layout.single_row_menu_list);
        mRecyclerView.setAdapter(menuListAdapter);
        menuListAdapter.setClickListener(this);

        return rootView;
    }

    /**
     * Before the activity is destroyed, onSaveInstanceState() gets called.
     * The onSaveInstanceState() method saves the list of data.
     *
     * @param outState bundle
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ITEMS_STATE, listItems);
    }

    @Override
    public void itemClicked(View view, int position, boolean isLongClick) {
        WIMF_UserItems menuItems = getFriendsList().get(position);
        if (isLongClick) {
            if (itemDAO.getItemFavorite(menuItems.getItemName()) == null) {
                menuItemsFavorite = new WIMF_UserItems();
                menuItemsFavorite.setItemName(menuItems.getItemName());
                menuItemsFavorite.setItemDescription(menuItems.getItemDescription());
                menuItemsFavorite.setItemImage(menuItems.getItemImage());
                menuItemsFavorite.setItemPrice(menuItems.getItemPrice());
                task = new AddItemTask(getActivity());
                task.execute((Void) null);
                // set heart_red drawable
                ImageView heart = (ImageView) view.findViewById(R.id.heart);
                heart.setImageResource(R.mipmap.ic_favorite_red_24dp);
            } else {
                AppUtils.CustomToast(getActivity(), getString(R.string.already_added_to_favorites));
            }

        } else {

            if (menuItems != null) {
                Bundle arguments = new Bundle();
                arguments.putParcelable("selectedItem", menuItems);
                // Create an instance of the dialog fragment and give it an argument for the selected article
                // and show it
                /*
                CustomDialogFragment customDialogFragment = new CustomDialogFragment();
                customDialogFragment.setArguments(arguments);
                customDialogFragment.show(getFragmentManager(),
                        CustomDialogFragment.ARG_ITEM_ID);
                        */
            }
        }
    }

    /**
     * Save the item to Favorite table asynchronously.
     */
    public class AddItemTask extends AsyncTask<Void, Void, Long> {

        private final WeakReference<Activity> activityWeakRef;

        public AddItemTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected Long doInBackground(Void... arg0) {
            long result = itemDAO.saveToFavoriteTable(menuItemsFavorite);
            return result;
        }

        @Override
        protected void onPostExecute(Long result) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                if (result != -1)
                    AppUtils.CustomToast(getActivity(), getString(R.string.added_to_favorites));
                Log.d("ITEM: ", menuItemsFavorite.toString());
            }
        }
    }

    /**
     * Generates data for RecyclerView's adapter, this data would usually come from a local content provider
     * or remote server.
     *
     * @return items list
     */

    private ArrayList<WIMF_UserItems> getFriendsList() {
        WIMF_FriendDAO amiDAO = new WIMF_FriendDAO(this.getActivity());
        amis = amiDAO.getUserFriends(1);

        ArrayList<WIMF_UserItems> menuItems = new ArrayList<WIMF_UserItems>();
        for(WIMF_Ami ami : amis) {
            menuItems.add(new WIMF_UserItems(ami.get_idU_rcv(),ami.get_idU_snd()));
        }
        return menuItems;
    }

}
