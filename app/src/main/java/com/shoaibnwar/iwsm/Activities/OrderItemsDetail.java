package com.shoaibnwar.iwsm.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.shoaibnwar.iwsm.Adapters.EnvoiceAdapter;
import com.shoaibnwar.iwsm.Adapters.OrderHistory;
import com.shoaibnwar.iwsm.Database.OrdersDB;
import com.shoaibnwar.iwsm.Database.OrdersHelper;
import com.shoaibnwar.iwsm.R;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderItemsDetail extends AppCompatActivity {

    ArrayList<HashMap<String, String>> dataList;
    RecyclerView rc_items;
    LinearLayoutManager linearLayoutManager;
    EnvoiceAdapter envoiceAdapter;
    ImageView iv_back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_items_detail);

        init();
        onBackArrowClickHandler();
        gettingItemsDetailsFromDB();
    }

    private void init(){
        dataList = new ArrayList<>();
        rc_items = (RecyclerView) findViewById(R.id.rc_items);
        iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);
        linearLayoutManager = new LinearLayoutManager(OrderItemsDetail.this, LinearLayoutManager.VERTICAL, false);
        rc_items.setLayoutManager(linearLayoutManager);
    }

    private void gettingItemsDetailsFromDB(){

        Intent i =  getIntent();
        String orderId = i.getStringExtra("orderId");
        OrdersDB db = new OrdersDB(OrderItemsDetail.this);
        ArrayList<OrdersHelper> dbList = db.getAllItemsFrom(orderId);
        for(OrdersHelper helper : dbList){
            HashMap<String, String> list = new HashMap<>();
            String ordreItemId = helper.getId();
            String orderItemName = helper.getItemName();
            String orderItemCode = helper.getItemCode();
            String orderItemUnitPrice = helper.getUnitPrice();
            String orderItemQuantity = helper.getQuantity();
            String orderItemDiscount = helper.getDiscount();
            String orderItemTotalPrice = helper.getTotalPrice();


            list.put("ordreItemId", ordreItemId);
            list.put("itemName", orderItemName);
            list.put("itemCode", orderItemCode);
            list.put("unitPrice", orderItemUnitPrice);
            list.put("itemQuantity", orderItemQuantity);
            list.put("itemDiscount", orderItemDiscount);
            list.put("itemPrice", orderItemTotalPrice);
            dataList.add(list);
        }

        envoiceAdapter = new EnvoiceAdapter(getApplicationContext(), dataList);
        rc_items.setAdapter(envoiceAdapter);
    }

    private void onBackArrowClickHandler()
    {
        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
