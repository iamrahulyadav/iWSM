package com.shoaibnwar.iwsm.Activities;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.shoaibnwar.iwsm.Adapters.CustomeAdapterVerticallScrollItems;
import com.shoaibnwar.iwsm.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class VerticalListActivity extends AppCompatActivity {

    //edited by shoaib anwar
    RecyclerView rc_list;
    CustomeAdapterVerticallScrollItems customeAdapterForImage;
    LinearLayoutManager linearLayoutManager;
    ImageView iv_back_arrow;
    ArrayList<HashMap<String, String>> dataList;

    private HorizontalCalendar horizontalCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_list);

        /*getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(VerticalListActivity.this ,R.color.colorPink)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        init();
        overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );


        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH+1, -1);

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(5)
                .dayNameFormat("EEE")
                .dayNumberFormat("dd")
                .monthFormat("MMM")
                .textSize(14f, 24f, 14f)
                .showDayName(true)
                .showMonthName(true)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                Toast.makeText(getApplicationContext(), DateFormat.getDateInstance().format(date) + " is selected!", Toast.LENGTH_SHORT).show();

                /*customeAdapterForImage = new CustomeAdapterVerticallScrollItems(getApplicationContext(), dataList);
                rc_list.setAdapter(customeAdapterForImage);
                customeAdapterForImage.notifyDataSetChanged();*/
            }

        });


    }

    private void init(){

        rc_list = findViewById(R.id.rc_list);
        rc_list.bringToFront();
        iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);
        linearLayoutManager = new LinearLayoutManager(VerticalListActivity.this, LinearLayoutManager.VERTICAL, false);
        rc_list.setLayoutManager(linearLayoutManager);
        dataList = ( ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("mylist");

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

    private void backPressHandler(){
        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
