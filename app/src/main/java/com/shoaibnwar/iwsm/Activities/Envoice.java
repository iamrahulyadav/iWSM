package com.shoaibnwar.iwsm.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shoaibnwar.iwsm.Adapters.CustomeAdapterVerticallScrollItems;
import com.shoaibnwar.iwsm.Adapters.EnvoiceAdapter;
import com.shoaibnwar.iwsm.Database.OrdersDB;
import com.shoaibnwar.iwsm.Database.OrdersHelper;
import com.shoaibnwar.iwsm.R;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;

public class Envoice extends AppCompatActivity {

    TextView tv_item_count;
    RecyclerView rc_items;
    TextView tv_total_price;
    LinearLayoutManager linearLayoutManager;
    EnvoiceAdapter envoiceAdapter;
    RelativeLayout iv_back_arrow;
    TextView tv_confirm;

    int mPrice = 0;
    ArrayList<HashMap<String, String>> dataList;
    String bookingDate = "";
    String bookingTime = "";
    String assignerID = "";
    String salemanID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envoice);

        init();
        onBackArrowPress();
        onConfirmTextClick();


    }

    private void init(){

        tv_item_count = (TextView) findViewById(R.id.tv_item_count);
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);
        rc_items = (RecyclerView) findViewById(R.id.rc_items);
        iv_back_arrow = (RelativeLayout) findViewById(R.id.iv_back_arrow);
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);


        linearLayoutManager = new LinearLayoutManager(Envoice.this, LinearLayoutManager.VERTICAL, false);
        rc_items.setLayoutManager(linearLayoutManager);
        //getting intent values
        Intent intent = getIntent();
        bookingDate = intent.getStringExtra("bookingDate");
        bookingTime = intent.getStringExtra("bookingTime");
        assignerID = intent.getStringExtra("assignerID");
        salemanID = intent.getStringExtra("salemanID");

        dataList = ( ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("mylist");

        envoiceAdapter = new EnvoiceAdapter(getApplicationContext(), dataList);
        rc_items.setAdapter(envoiceAdapter);

        Log.e("TAg", "list size is: " + dataList.size());
        if (dataList.size()>0) {
            for (int i = 0; i < dataList.size(); i++) {
                String price = dataList.get(i).get("itemPrice");
                int intPrice = Integer.valueOf(price);
                mPrice = mPrice + intPrice;

            }
        }

        tv_total_price.setText("Total Sum Rs." +String.valueOf(mPrice));
        tv_item_count.setText("Total Items " +String.valueOf(dataList.size()));
    }

    @Override
    public void onBackPressed() {
        dataList.clear();
        super.onBackPressed();
    }

    private void onBackArrowPress(){
        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList.clear();
                finish();
            }
        });
    }

    private void onConfirmTextClick(){
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(Envoice.this);
                alert.setTitle("Confirmation!");
                alert.setMessage("Prese Yes To Confirm Order, or No to cancel");
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                        OrdersDB db = new OrdersDB(Envoice.this);
                        OrdersHelper ordersHelper = new OrdersHelper();

                        String totalPriceForOrder = String.valueOf(mPrice);
                        String totalITemsInOrder = String.valueOf(dataList.size());
                        ordersHelper.setAssignerId(assignerID);
                        ordersHelper.setSalemanId(salemanID);
                        ordersHelper.setTotalPrice(totalPriceForOrder);
                        ordersHelper.setTotalItems(totalITemsInOrder);
                        ordersHelper.setStartDate(bookingDate);
                        ordersHelper.setStartTime(bookingTime);

                        long insetingData  = db.insertDatatoOrderDb(ordersHelper);
                        if (insetingData>-1){

                            for(int i=0;i<dataList.size();i++){

                                String orderID = String.valueOf((int)insetingData);
                                String itemName = dataList.get(i).get("itemName");
                                String itemCode = dataList.get(i).get("itemCode");
                                String itemUnitPrice = dataList.get(i).get("unitPrice");
                                String itemQuantity = dataList.get(i).get("itemQuantity");
                                String itemDiscount = dataList.get(i).get("itemDiscount");
                                String itemTotalPrice = dataList.get(i).get("itemPrice");

                                ordersHelper.setOrderId(orderID);
                                ordersHelper.setItemName(itemName);
                                ordersHelper.setItemCode(itemCode);
                                ordersHelper.setUnitPrice(itemUnitPrice);
                                ordersHelper.setQuantity(itemQuantity);
                                ordersHelper.setDiscount(itemDiscount);
                                ordersHelper.setTotalPrice(itemTotalPrice);

                                long inserted  = db.insertDatatoOrderDetailDb(ordersHelper);
                                if (inserted>-1)
                                    Log.e("TAg", "the date into order table is inserted " + insetingData);

                            }
                            Intent i = new Intent(Envoice.this, SelectType.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            Toast.makeText(Envoice.this, "Order Confirmed", Toast.LENGTH_SHORT).show();
                            startActivity(i);
                        }
                    }
                });
                alert.show();

            }
        });
    }
}
