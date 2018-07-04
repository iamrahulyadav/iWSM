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
import com.shoaibnwar.iwsm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by gold on 6/26/2018.
 */

public class EnvoiceAdapter extends RecyclerView.Adapter<EnvoiceAdapter.MyViewHolder>  {

    private ArrayList<HashMap<String, String>> dataArray;
    private Context mContext;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    public static final int REQUEST_PERMISSION_CODE = 300;


    public class MyViewHolder extends RecyclerView.ViewHolder {


        private ImageView iv_item_image;
        private TextView iv_order_id;
        private TextView tv_item_name, tv_item_code, tv_item_unit_price, tv_item_quantity, tv_item_discount, tv_item_total_price;

        public MyViewHolder(final View view) {
            super(view);

            iv_order_id = (TextView) view.findViewById(R.id.iv_order_id);
            iv_item_image = (ImageView) view.findViewById(R.id.iv_item_image);
            tv_item_name = (TextView) view.findViewById(R.id.tv_item_name);
            tv_item_code = (TextView) view.findViewById(R.id.tv_item_code);
            tv_item_unit_price = (TextView) view.findViewById(R.id.tv_item_unit_price);
            tv_item_quantity = (TextView) view.findViewById(R.id.tv_item_quantity);
            tv_item_discount = (TextView) view.findViewById(R.id.tv_item_discount);
            tv_item_total_price = (TextView) view.findViewById(R.id.tv_item_total_price);


        }
    }


    public EnvoiceAdapter(Context context , ArrayList<HashMap<String, String>> appealList) {
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

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_layout_confirmation_screen, parent, false);
        viewHolder = new MyViewHolder(itemView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            int pp = position+1;

            String ordreItemId = dataArray.get(position).get("ordreItemId").toString();
            String itemName = dataArray.get(position).get("itemName").toString();
            String itemCode = dataArray.get(position).get("itemCode").toString();
            String unitPrice = dataArray.get(position).get("unitPrice").toString();
            String itemQuantity = dataArray.get(position).get("itemQuantity").toString();
            String itemDiscount = dataArray.get(position).get("itemDiscount").toString();
            String itemPrice = dataArray.get(position).get("itemPrice").toString();

            holder.iv_order_id.setText(ordreItemId);
            holder.tv_item_name.setText(itemName);
            holder.tv_item_code.setText(itemCode);
            holder.tv_item_unit_price.setText(unitPrice);
            holder.tv_item_quantity.setText(itemQuantity);
            holder.tv_item_discount.setText(itemDiscount);
            holder.tv_item_total_price.setText(itemPrice);


        }

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

