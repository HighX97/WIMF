package moun.com.wimf.fragment;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import moun.com.wimf.R;
import moun.com.wimf.adapter.WIMF_ConversationAdapter;

import moun.com.wimf.database.WIMF_FriendDAO;
import moun.com.wimf.database.WIMF_ItemsDAO;
import moun.com.wimf.database.WIMF_MessageDAO;
import moun.com.wimf.database.WIMF_UserDAO;
import moun.com.wimf.model.WIMF_Ami;
import moun.com.wimf.model.WIMF_Message;
import moun.com.wimf.model.WIMF_UserItems;
import moun.com.wimf.model.WIMF_Utilisateur;
import moun.com.wimf.util.AppUtils;

/**
 * This Fragment used to handle the list of items under Sandwich category using
 * {@link RecyclerView} with a {@link LinearLayoutManager}.
 */
public class WIMF_UserProfil_Conversations_Fragment extends Fragment implements WIMF_ConversationAdapter.ClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private WIMF_ConversationAdapter menuListAdapter;
    ArrayList<WIMF_UserItems> listItems;
    List<WIMF_Message> messages;
    private static final String ITEMS_STATE = "items_state";
    private AlphaInAnimationAdapter alphaAdapter;
    private WIMF_ItemsDAO itemDAO;
    private AddItemTask task;
    private WIMF_UserItems menuItemsFavorite = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        itemDAO = new WIMF_ItemsDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

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
        menuListAdapter = new WIMF_ConversationAdapter(getActivity(), (ArrayList<WIMF_Message>) messages, inflater, R.layout.wimf_single_row_conversation_list);
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

        ArrayList<WIMF_UserItems> menuItems = new ArrayList<WIMF_UserItems>();
        WIMF_UserDAO userDAO = new WIMF_UserDAO(this.getActivity());
        WIMF_Utilisateur utilisateur = userDAO.getUserDetails();
        WIMF_MessageDAO messageDAO = new WIMF_MessageDAO(this.getActivity());
        messages = messageDAO.getAllUserMessages(utilisateur.get_tel());
        if (messages == null || messages.size() < 1 )
        {
            menuItems.add(new WIMF_UserItems(getString(R.string.cheeze), R.drawable.usericon1, 11.50, getString(R.string.short_lorem)));
        }
        else
        {
            for (WIMF_Message message : messages) {
                menuItems.add(new WIMF_UserItems(message.get_idMsg(), message.get_idMsg()));
            }
        }
        return menuItems;
    }

}