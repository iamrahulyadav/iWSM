package com.shoaibnwar.iwsm.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shoaibnwar.iwsm.Activities.OrderItemsDetail;
import com.shoaibnwar.iwsm.Activities.SingleItemDetailActivityForCabs;
import com.shoaibnwar.iwsm.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gold on 7/4/2018.
 */

public class OrderHistory extends RecyclerView.Adapter<OrderHistory.MyViewHolder>  {

    private ArrayList<HashMap<String, String>> dataArray;
    private Activity mContext;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    public static final int REQUEST_PERMISSION_CODE = 300;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_order_item_image ;
        //public TextView custome_tv;
        public RelativeLayout rl_single_item;
        private TextView tv_assigner_name, tv_saleman_name, tv_total_items, tv_total_price, tv_start_date, tv_stat_time, tv_order_id;



        public MyViewHolder(final View view) {
            super(view);


            iv_order_item_image = (ImageView) view.findViewById(R.id.iv_order_item_image);
            rl_single_item = (RelativeLayout) view.findViewById(R.id.rl_single_item);
            tv_assigner_name = (TextView) view.findViewById(R.id.tv_assigner_name);
            tv_saleman_name = (TextView) view.findViewById(R.id.tv_saleman_name);
            tv_total_items = (TextView) view.findViewById(R.id.tv_total_items);
            tv_total_price = (TextView) view.findViewById(R.id.tv_total_price);
            tv_start_date = (TextView) view.findViewById(R.id.tv_start_date);
            tv_stat_time = (TextView) view.findViewById(R.id.tv_stat_time);
            tv_order_id = (TextView) view.findViewById(R.id.tv_order_id);


        }
    }


    public OrderHistory(Activity context , ArrayList<HashMap<String, String>> appealList) {
        this.mContext = context;
        this.dataArray = appealList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        Log.e("TAg", "the view type : " + viewType);

        //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_layout_vertical_items, parent, false);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_order_history_layout, parent, false);
        viewHolder = new MyViewHolder(itemView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {

            String assignerID = dataArray.get(position).get("assignerId");
            if (assignerID.equals("+923454166554")){assignerID = "Hamza Khan";}
            if (assignerID.equals("+923252654695")){assignerID = "Abaas Ali";}
            if (assignerID.equals("+923214585425")){assignerID = "Rizwan Nisar";}
            if (assignerID.equals("+923458251254")){assignerID = "Kashif latif";}
            if (assignerID.equals("+923135245265")){assignerID = "Adeel Khalil";}
            if (assignerID.equals("+923334525485")){assignerID = "Salman saeed";}
            if (assignerID.equals("+9233452545216")){assignerID = "Usman Rafique";}
            if (assignerID.equals("+923215426330")){assignerID = "Ammar suhail";}
            if (assignerID.equals("+923224502150")){assignerID = "Akranm ismaeil";}

            String salmanId = dataArray.get(position).get("salemanId");
            if (salmanId.equals("+923234524510")){salmanId = "Nadeem Ali";}
            if (salmanId.equals("+923450125462")){salmanId = "Malik Ahmad";}
            if (salmanId.equals("+9232145256036")){salmanId = "Rana Naveed";}
            if (salmanId.equals("+923124524520")){salmanId = "Anas Saeed";}
            if (salmanId.equals("+923335420125")){salmanId = "Umar Khan";}

            holder.tv_order_id.setText(dataArray.get(position).get("orderid"));
            holder.tv_assigner_name.setText(assignerID);
            holder.tv_saleman_name.setText(salmanId);
            holder.tv_total_price.setText(dataArray.get(position).get("totalPrice"));
            holder.tv_total_items.setText(dataArray.get(position).get("totalItem"));
            holder.tv_start_date.setText(dataArray.get(position).get("startDate"));
            holder.tv_stat_time.setText(dataArray.get(position).get("startTime"));

        }

        holder.rl_single_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String orderId = holder.tv_order_id.getText().toString();
                Log.e("TAg", "here is order id: " + orderId);
                Intent i = new Intent(mContext, OrderItemsDetail.class);
                i.putExtra("orderId", orderId);
                mContext.startActivity(i);
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

