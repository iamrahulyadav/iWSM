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


        private TextView tv_item_name, tv_item_quantity, tv_item_price, tv_count;


        public MyViewHolder(final View view) {
            super(view);

            tv_item_name = (TextView) view.findViewById(R.id.tv_item_name);
            tv_item_quantity = (TextView) view.findViewById(R.id.tv_item_quantity);
            tv_item_price = (TextView) view.findViewById(R.id.tv_item_price);
            tv_count = (TextView) view.findViewById(R.id.tv_count);


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

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_invoice_layout, parent, false);
        viewHolder = new MyViewHolder(itemView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            int pp = position+1;
            holder.tv_count.setText(dataArray.get(position).get("itemName").toString());
            holder.tv_item_name.setText(dataArray.get(position).get("itemName").toString());
            holder.tv_item_quantity.setText(dataArray.get(position).get("itemQuantity").toString());
            holder.tv_item_price.setText(dataArray.get(position).get("itemPrice").toString());


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

