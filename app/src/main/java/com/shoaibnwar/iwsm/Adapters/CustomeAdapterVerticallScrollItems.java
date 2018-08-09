package com.shoaibnwar.iwsm.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shoaibnwar.iwsm.Activities.SingleItemDetailActivity;
import com.shoaibnwar.iwsm.R;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by gold on 6/25/2018.
 */

public class CustomeAdapterVerticallScrollItems extends RecyclerView.Adapter<CustomeAdapterVerticallScrollItems.MyViewHolder>  {

    private ArrayList<HashMap<String, String>> dataArray;
    private Context mContext;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    public static final int REQUEST_PERMISSION_CODE = 300;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_image ;
        //public TextView custome_tv;
        public RelativeLayout rl_single_item;
        private TextView tv_assignment_name, tv_date, tv_time_to_reach, tv_meeting_time;


        public MyViewHolder(final View view) {
            super(view);


            iv_image = (ImageView) view.findViewById(R.id.iv_image);
            rl_single_item = (RelativeLayout) view.findViewById(R.id.rl_single_item);
            tv_assignment_name = (TextView) view.findViewById(R.id.tv_assignment_name);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_time_to_reach = (TextView) view.findViewById(R.id.tv_time_to_reach);
            tv_meeting_time = (TextView) view.findViewById(R.id.tv_meeting_time);
            // custome_tv = (TextView) view.findViewById(R.id.custome_tv);

        }
    }


    public CustomeAdapterVerticallScrollItems(Context context , ArrayList<HashMap<String, String>> appealList) {
        this.mContext = context;
        this.dataArray = appealList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.pharmacy_custom_row, parent, false);
//        return new MyViewHolder(itemView);

        MyViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        Log.e("TAg", "the view type : " + viewType);

        //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_layout_vertical_items, parent, false);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.temp_vertical_layout, parent, false);
        viewHolder = new MyViewHolder(itemView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {

            String mettingTime = dataArray.get(position).get("startTime").toString();
            String meetingDate = dataArray.get(position).get("startdate").toString();
            String assignerName = dataArray.get(position).get("assignerName").toString();

            int pp = position+2;
            String daa = String.valueOf(pp);
            String datee = "07/0"+daa+"/2018";
            int reachingTime = 15+position;

            holder.tv_assignment_name.setText(assignerName);
            holder.tv_date.setText(meetingDate);
            holder.tv_time_to_reach.setText("You will be reaching in "+String.valueOf(reachingTime)+" mins...");
            holder.tv_meeting_time.setText("Meeting Time " + mettingTime);
            //holder.custome_tv.setText(bloodAppealList.get(position).get("V_Title"));
            /*Picasso.with(mContext)
                    .load(dataArray.get(position).get("V_ThumbImg"))
                    .placeholder(R.drawable.amu_bubble_shadow)
                    //.fit()
                    .into(holder.iv_image);*/
            // Glide.with(mContext).load(bloodAppealList.get(position).get("V_ThumbImg")).into(holder.custome_image);

        }

        holder.rl_single_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent detailActivity = new Intent(mContext, SingleItemDetailActivity.class);

                detailActivity.putExtra("imageUrl", "-1");
                detailActivity.putExtra("contactName", dataArray.get(position).get("assignerName"));
                detailActivity.putExtra("contactNumber", dataArray.get(position).get("assignerContact"));
                detailActivity.putExtra("contactAddress", dataArray.get(position).get("assignerAddress"));
                detailActivity.putExtra("contactCompany", dataArray.get(position).get("assignerCompany"));
                detailActivity.putExtra("contactLat", dataArray.get(position).get("32.215245"));
                detailActivity.putExtra("contactLng", dataArray.get(position).get("74.235412"));
                detailActivity.putExtra("contactId", dataArray.get(position).get("tableId"));
                detailActivity.putExtra("status", dataArray.get(position).get("saleManId"));

                detailActivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(detailActivity);

            }
        });

    }


    @Override
    public int getItemCount() {
        return dataArray.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == dataArray.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

}

