package com.shoaibnwar.iwsm.Activities;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.shoaibnwar.iwsm.Adapters.CustomeAdapterVerticallScrollItems;
import com.shoaibnwar.iwsm.R;

import java.util.ArrayList;
import java.util.HashMap;

public class VerticalListActivity extends AppCompatActivity {

    //edited by shoaib anwar
    RecyclerView rc_list;
    CustomeAdapterVerticallScrollItems customeAdapterForImage;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_list);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(VerticalListActivity.this ,R.color.colorPink)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
    }

    private void init(){

        rc_list = findViewById(R.id.rc_list);
        rc_list.bringToFront();
        linearLayoutManager = new LinearLayoutManager(VerticalListActivity.this, LinearLayoutManager.VERTICAL, false);
        rc_list.setLayoutManager(linearLayoutManager);
        ArrayList<HashMap<String, String>> dataList = ( ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("mylist");

        Log.e("TAG", "the array list size is: " + dataList.size());
        //edit by shoaib anwar
        customeAdapterForImage = new CustomeAdapterVerticallScrollItems(getApplicationContext(), dataList);
        rc_list.setAdapter(customeAdapterForImage);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
