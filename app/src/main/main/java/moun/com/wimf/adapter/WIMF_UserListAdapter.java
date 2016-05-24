package moun.com.wimf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import moun.com.wimf.R;
import moun.com.wimf.database.WIMF_ItemsDBDAO;
import moun.com.wimf.helper.PostClass;
import moun.com.wimf.helper.RestHelper;
import moun.com.wimf.model.WIMF_UserItems;
import moun.com.wimf.model.WIMF_UserItems;
import moun.com.wimf.util.AppUtils;
import moun.com.wimf.util.SessionManager;

/**
 * Provide view to Menu list RecyclerView with data from WIMF_UserItems object.
 */
public class WIMF_UserListAdapter extends RecyclerView.Adapter<WIMF_UserListAdapter.ViewHolder>{
    private static final String LOG_TAG = WIMF_UserListAdapter.class.getSimpleName();
    private LayoutInflater mLayoutInflater;
    private int mResourceId;
    private List<WIMF_UserItems> itemList;
    private Context context;
    private ClickListener clickListener;
    private WIMF_ItemsDBDAO itemsDAO;

    /**
     * Create a new instance of {@link WIMF_UserListAdapter}.
     *  @param context    host Activity.
     * @param itemList   List of data.
     * @param inflater   The layout inflater.
     * @param resourceId The resource ID for the layout to be used. The layout should contain an
     */
    public WIMF_UserListAdapter(Context context, ArrayList<WIMF_UserItems> itemList, LayoutInflater inflater, int resourceId)
    {
        this.itemList = itemList;
        this.context = context;
        mLayoutInflater = inflater;
        mResourceId = resourceId;
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
      //  public TextView txtV_header;
       // public TextView nom;
       // public TextView tel;
       //  public TextView tel_expediteur;
      //  public TextView message_text;
       // public TextView txtV_footer;

        private ImageView img_utilisateur;

        public ViewHolder(View v) {
            super(v);
            //
           // txtV_header= (TextView) v.findViewById(R.id.txtV_header);
           // nom= (TextView) v.findViewById(R.id.nom);
           // tel= (TextView) v.findViewById(R.id.tel);
           // tel_expediteur= (TextView) v.findViewById(R.id.tel_expediteur);
          //  message_text= (TextView) v.findViewById(R.id.message_text);
          //  txtV_footer= (TextView) v.findViewById(R.id.txtV_footer);
           // img_utilisateur= (ImageView) v.findViewById(R.id.img_utilisateur);
            //
           // this.txtV_header.setTypeface(AppUtils.getTypeface(v.getContext(), AppUtils.FONT_BOLD));
           // this.txtV_footer.setTypeface(AppUtils.getTypeface(v.getContext(), AppUtils.FONT_BOLD));
            itemsDAO = new WIMF_ItemsDBDAO(v.getContext());


            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.itemClicked(v, getAdapterPosition(), false);
                        Log.d(LOG_TAG, "Element " + getAdapterPosition() + " clicked.");
                    }
                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    if (clickListener != null) {
                        clickListener.itemClicked(v, getAdapterPosition(), true);
                    }

                    return true;
                }
            });
        }
    }

    // Create new view (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mLayoutInflater.inflate(mResourceId, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        //    Log.d(LOG_TAG, "Element " + position + " set.");
       /* SessionManager session;
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);


        String url = "http://46.101.40.23:8585/ami/list";
        HashMap<String, String> parametres = new HashMap<String, String>();
        parametres.put("tel", tel);
        parametres.put("password", password);
        final String post_result = RestHelper.executePOST(url, parametres);
        Log.d("post_result ", " post_result: " + post_result);



        new PostClass(this,parametres,url).execute();*/
        WIMF_UserItems menuItems = itemList.get(position);
        // Get element from WIMF_UserItems object at this position and replace the contents of the view
        // with that element
<<<<<<< HEAD
        //viewHolder.img_utilisateur.setImageResource(menuItems.getItemImage());
       // viewHolder.txtV_header.setText("");
        //viewHolder.nom.setText("Nom :");
        //viewHolder.tel.setText("Numero :");
      //  viewHolder.tel_expediteur.setText("Expediteur :");
       // viewHolder.message_text.setText("Message :");
       // viewHolder.txtV_footer.setText("");
=======
        viewHolder.img_utilisateur.setImageResource(menuItems.getItemImage());
        viewHolder.txtV_header.setText("");
        viewHolder.nom.setText("Nom :"+menuItems.get_id_rcv());
        viewHolder.tel.setText("Telephone :"+menuItems.get_id_snd());
        viewHolder.txtV_footer.setText("");
>>>>>>> 565d25d4dd5a23fa7bf54971b7ab37f3ef8a9aae

        // If an item exists in favorite table then set heart_red drawable
        /*
        if(itemsDAO.getItemFavorite(menuItems.getItemName()) == null){
            viewHolder.heart.setImageResource(R.mipmap.ic_favorite_white_24dp);
        } else {
            viewHolder.heart.setImageResource(R.mipmap.ic_favorite_red_24dp);
        }
        */
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;

    }

    // An interface to Define click listener for the ViewHolder's View from any where.
    public interface ClickListener{
        public void itemClicked(View view, int position, boolean isLongClick);
    }
}
