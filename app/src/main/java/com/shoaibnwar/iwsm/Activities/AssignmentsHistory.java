package com.shoaibnwar.iwsm.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.shoaibnwar.iwsm.Adapters.EnvoiceAdapter;
import com.shoaibnwar.iwsm.Adapters.OrderHistory;
import com.shoaibnwar.iwsm.Database.OrdersDB;
import com.shoaibnwar.iwsm.Database.OrdersHelper;
import com.shoaibnwar.iwsm.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AssignmentsHistory extends AppCompatActivity {

    ArrayList<HashMap<String, String>> dataList;
    RecyclerView rc_items;
    LinearLayoutManager linearLayoutManager;
    OrderHistory orderHistory;
    ImageView iv_back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments_history);

        init();
        onBackArrowPressListener();
        fetchingOrdersFromDatabase();
    }

    private void init(){
        dataList = new ArrayList<>();
        iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);
        rc_items = (RecyclerView) findViewById(R.id.rc_items);
        linearLayoutManager = new LinearLayoutManager(AssignmentsHistory.this, LinearLayoutManager.VERTICAL, false);
        rc_items.setLayoutManager(linearLayoutManager);
    }

    private void fetchingOrdersFromDatabase(){

        OrdersDB db = new OrdersDB(AssignmentsHistory.this);
        ArrayList<OrdersHelper> ordersList = db.getAllOrders();
        for (OrdersHelper helper : ordersList){

            HashMap<String, String> list = new HashMap<>();
            String id = helper.getId();
            String assignerId = helper.getAssignerId();
            String salemanId = helper.getSalemanId();
            String totalPrice = helper.getTotalPrice();
            String totalItem = helper.getTotalItems();
            String startDate = helper.getStartDate();
            String startTime = helper.getStartTime();

            Log.e("TAg", "the order detail date id " + id);
            Log.e("TAg", "the order detail date assignerId " + assignerId);
            Log.e("TAg", "the order detail date salemanId " + salemanId);
            Log.e("TAg", "the order detail date totalPrice " + totalPrice);
            Log.e("TAg", "the order detail date totalItem " + totalItem);
            Log.e("TAg", "the order detail date startDate " + startDate);
            Log.e("TAg", "the order detail date startTime " + startTime);

            list.put("orderid", id);
            list.put("assignerId", assignerId);
            list.put("salemanId", salemanId);
            list.put("totalPrice", totalPrice);
            list.put("totalItem", totalItem);
            list.put("startDate", startDate);
            list.put("startTime", startTime);

            dataList.add(list);

        }

        orderHistory = new OrderHistory(AssignmentsHistory.this, dataList);
        rc_items.setAdapter(orderHistory);

    }

    private void onBackArrowPressListener()
    {

        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
