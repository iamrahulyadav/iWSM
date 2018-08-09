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
import com.shoaibnwar.iwsm.Database.AssignmentDbHelper;
import com.shoaibnwar.iwsm.Database.AssignmentesDB;
import com.shoaibnwar.iwsm.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
                //Toast.makeText(getApplicationContext(), DateFormat.getDateInstance().format(date) + " is selected!", Toast.LENGTH_SHORT).show();
                String curretFormatedDate  = DateFormat.getDateInstance().format(date).toString();
                String resultDate = parseDateToddMMyyyy(curretFormatedDate);
                Log.e("TAG", "the selcted Date is current" + curretFormatedDate);
                Log.e("TAG", "the selcted Date is " + resultDate);

                AssignmentesDB db = new AssignmentesDB(VerticalListActivity.this);
                ArrayList<AssignmentDbHelper> assignmentList  = db.getAllCurrentDateRecord(resultDate);

                dataList = new ArrayList<>();

                for (AssignmentDbHelper assignmentDbHelper : assignmentList){

                    HashMap<String, String> itemList = new HashMap<>();

                    String tableId = assignmentDbHelper.getId();
                    String assignerContactId = assignmentDbHelper.getAssignerID();
                    String assignerName = assignmentDbHelper.getAssignerName();
                    String assignerContact  = assignmentDbHelper.getAssignerContact();
                    String assignerCompany = assignmentDbHelper.getAssignerCompany();
                    String assignerAddress = assignmentDbHelper.getAssignerAddress();
                    String startdate = assignmentDbHelper.getStartDate();
                    String startTime = assignmentDbHelper.getStartTime();
                    String saleManName = assignmentDbHelper.getSalemanName();
                    String saleManContact = assignmentDbHelper.getSalemanContact();
                    String saleManAddress = assignmentDbHelper.getSalemanAddress();
                    String saleManId = assignmentDbHelper.getSalemanId();

                    itemList.put("tableId", tableId);
                    itemList.put("assignerContactId", assignerContactId);
                    itemList.put("assignerName", assignerName);
                    itemList.put("assignerContact", assignerContact);
                    itemList.put("assignerCompany", assignerCompany);
                    itemList.put("assignerAddress", assignerAddress);
                    itemList.put("startdate", startdate);
                    Log.e("TAg", "the selected Date is from db " + startdate);
                    itemList.put("startTime", startTime);
                    itemList.put("saleManName", saleManName);
                    itemList.put("saleManContact", saleManContact);
                    itemList.put("saleManAddress", saleManAddress);
                    itemList.put("saleManId", saleManId);
                    dataList.add(itemList);

                }

                Log.e("TAG", "the array list size is: " + dataList.size());
                //edit by shoaib anwar
                customeAdapterForImage = new CustomeAdapterVerticallScrollItems(getApplicationContext(), dataList);
                rc_list.setAdapter(customeAdapterForImage);
                customeAdapterForImage.notifyDataSetChanged();


            }

        });


    }

    private void init(){

        rc_list = findViewById(R.id.rc_list);
        rc_list.bringToFront();
        linearLayoutManager = new LinearLayoutManager(VerticalListActivity.this, LinearLayoutManager.VERTICAL, false);
        rc_list.setLayoutManager(linearLayoutManager);

    }


    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "MMM dd, yyyy";
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

}
