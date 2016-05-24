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
import java.util.List;

import moun.com.wimf.R;
import moun.com.wimf.database.WIMF_ItemsDBDAO;
import moun.com.wimf.model.WIMF_UserItems;
import moun.com.wimf.util.AppUtils;

/**
 * Created by maiga mariam on 24/05/2016.
 */

public class WIMF_Location_Adapter extends RecyclerView.Adapter<WIMF_Location_Adapter.ViewHolder>{
    private static final String LOG_TAG = WIMF_Location_Adapter.class.getSimpleName();
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
    public WIMF_Location_Adapter(Context context, ArrayList<WIMF_UserItems> itemList, LayoutInflater inflater, int resourceId)
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

        public ViewHolder(View v) {
            super(v);
            //

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
