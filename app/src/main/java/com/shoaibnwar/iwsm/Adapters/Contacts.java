package com.shoaibnwar.iwsm.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LogPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shoaibnwar.iwsm.Activities.OrderTaking;
import com.shoaibnwar.iwsm.Activities.SingleItemDetailActivityForCabs;
import com.shoaibnwar.iwsm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by gold on 6/28/2018.
 */

public class Contacts extends RecyclerView.Adapter<Contacts.MyViewHolder>  {

    private ArrayList<HashMap<String, String>> dataArray;
    private Activity mContext;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    public static final int REQUEST_PERMISSION_CODE = 300;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_image ;
        //public TextView custome_tv;
        public RelativeLayout rl_single_item;
        private TextView tv_contact_name, tv_contact_number, tv_image_uri, tv_contact_company;
        public TextView tv_address, tv_lat, tv_lng;
        public TextView tv_id, tv_status;
        public TextView tv_assign;


        public MyViewHolder(final View view) {
            super(view);


            iv_image = (ImageView) view.findViewById(R.id.iv_image);
            rl_single_item = (RelativeLayout) view.findViewById(R.id.rl_single_item);
            tv_contact_name = (TextView) view.findViewById(R.id.tv_contact_name);
            tv_contact_number = (TextView) view.findViewById(R.id.tv_contact_number);
            tv_contact_company = (TextView) view.findViewById(R.id.tv_contact_company);

            tv_image_uri = (TextView) view.findViewById(R.id.tv_image_uri);
            tv_address = (TextView) view.findViewById(R.id.tv_address);
            tv_lat = (TextView) view.findViewById(R.id.tv_lat);
            tv_lng = (TextView) view.findViewById(R.id.tv_lng);
            tv_id = (TextView) view.findViewById(R.id.tv_id);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            tv_assign = (TextView) view.findViewById(R.id.tv_assign);

        }
    }


    public Contacts(Activity context , ArrayList<HashMap<String, String>> appealList) {
        this.mContext = context;
        this.dataArray = appealList;
    }

    @Override
    public Contacts.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.pharmacy_custom_row, parent, false);
//        return new MyViewHolder(itemView);

        Contacts.MyViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        Log.e("TAg", "the view type : " + viewType);

        //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_layout_vertical_items, parent, false);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_contact_list, parent, false);
        viewHolder = new Contacts.MyViewHolder(itemView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final Contacts.MyViewHolder holder, final int position) {
        if (holder instanceof Contacts.MyViewHolder) {

            String mStatus = dataArray.get(position).get("staus");
            String companyName = dataArray.get(position).get("company");
            holder.tv_contact_name.setText(dataArray.get(position).get("name"));
            holder.tv_contact_number.setText(dataArray.get(position).get("number"));
            holder.tv_address.setText(dataArray.get(position).get("address"));
            holder.tv_contact_company.setText(companyName);
            holder.tv_lat.setText(dataArray.get(position).get("lat"));
            holder.tv_lng.setText(dataArray.get(position).get("lng"));
            holder.tv_id.setText(dataArray.get(position).get("id"));
            if (!mStatus.equals("0")){

                holder.tv_assign.setText("Assinged");
                holder.tv_assign.setTextColor(mContext.getResources().getColor(R.color.blue));

            }
            holder.tv_status.setText(dataArray.get(position).get("staus"));
            String imageUri = dataArray.get(position).get("imageuri");
            /*if (!imageUri.equals("-1")) {
                holder.tv_image_uri.setText(imageUri);
                Picasso.with(mContext)
                        .load(dataArray.get(position).get("imageuri"))
                        .placeholder(R.drawable.person_icon)
                        //.fit()
                        .into(holder.iv_image);
            }else {
                holder.tv_image_uri.setText("-1");
                Picasso.with(mContext)
                        .load(R.drawable.person_icon)
                        .placeholder(R.drawable.person_icon)
                        //.fit()
                        .into(holder.iv_image);

            }*/

        }

        holder.rl_single_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                String contactName = holder.tv_contact_name.getText().toString();
                String contactNumber = holder.tv_contact_number.getText().toString();
                String imageUri = holder.tv_image_uri.getText().toString();
                String address = holder.tv_address.getText().toString();
                String lat = holder.tv_lat.getText().toString();
                String lng = holder.tv_lng.getText().toString();
                String companyName = holder.tv_contact_company.getText().toString();

                String contact_id = holder.tv_id.getText().toString();
                String status = holder.tv_status.getText().toString();

                Log.e("TAg", "the select contact name: " + contactName);
                Log.e("TAg", "the select contact name: " + contactNumber);
                Log.e("TAg", "the select contact name: " + imageUri);

                Intent i = new Intent(mContext, SingleItemDetailActivityForCabs.class);
                i.putExtra("imageUrl", imageUri);
                i.putExtra("contactName", contactName);
                i.putExtra("contactNumber", contactNumber);
                i.putExtra("contactAddress", address);
                i.putExtra("contactCompany", companyName);
                i.putExtra("contactLat", lat);
                i.putExtra("contactLng", lng);
                i.putExtra("contactId", contact_id);
                i.putExtra("status", status);
                mContext.startActivity(i);

                /*
                final Dialog dialog =  new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_select_location_from_map);
                Button btGotoMao = (Button) dialog.findViewById(R.id.bt_goto_maps);

                btGotoMao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        dialog.dismiss();



                    }
                });
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTooDouen;
                dialog.show();
*/
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

