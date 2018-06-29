package com.shoaibnwar.iwsm.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoaibnwar.iwsm.Adapters.CustomeAdapterVerticallScrollItems;
import com.shoaibnwar.iwsm.Adapters.EnvoiceAdapter;
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
    ImageView iv_back_arrow;
    TextView tv_confirm;

    int mPrice = 0;
    ArrayList<HashMap<String, String>> dataList;
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
        iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);


        linearLayoutManager = new LinearLayoutManager(Envoice.this, LinearLayoutManager.VERTICAL, false);
        rc_items.setLayoutManager(linearLayoutManager);
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

        tv_total_price.setText("Total Prices Rs." +String.valueOf(mPrice));
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

                Intent i = new Intent(Envoice.this, MainMaps.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
}
