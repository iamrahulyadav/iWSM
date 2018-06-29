package com.shoaibnwar.iwsm.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shoaibnwar.iwsm.Database.ContactDatabase;
import com.shoaibnwar.iwsm.Listeners.AsyncTaskCompleteListener;
import com.shoaibnwar.iwsm.R;
import com.shoaibnwar.iwsm.Services.HttpRequester;
import com.shoaibnwar.iwsm.Utils.Logger;
import com.shoaibnwar.iwsm.Utils.SharedPrefs;
import com.shoaibnwar.iwsm.Utils.Urls;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class SingleItemDetailActivityForCabs extends AppCompatActivity implements AsyncTaskCompleteListener {

    ImageView iv_back_arrow;
    ImageView iv_person2;
    Button bt_start_assignment;

    TextView tv_m_name, tv_m_phome;
    LinearLayout ll_address;


    JSONArray jsonArray;
    RelativeLayout rrRL;
    ImageView loading_img_rr;
    Animation rotation;

    String Aid = "";
    String VType = "";
    String imageUrl;
    String contacttName=null, contactNumber=null;
    String contacttAddress=null, contactLat=null, contactLng=null;
    String contactId=null, contactStatus=null;

    TextView tv_address_line_1, tv_address_line_2;
    TextView tv_assign_now;
    int checkedIdis = 0;

    String saleManName = null;
    String saleManContact = null;
    String saleManAddress = null;
    String saleManLat = null;
    String saleManLng = null;
    String saleManId = null;


    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_detail_for_cabs);
        System.gc();

        init();
        onBackArrowPress();
        overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
        assignNowButtonClickHandler();

        Intent intentData = getIntent();
        contacttName = intentData.getStringExtra("contactName");
        contactNumber = intentData.getStringExtra("contactNumber");
        contacttAddress = intentData.getStringExtra("contactAddress");
        contactLat = intentData.getStringExtra("contactLat");
        contactLng = intentData.getStringExtra("contactLng");
        contactId = intentData.getStringExtra("contactId");
        contactStatus = intentData.getStringExtra("status");





        imageUrl = intentData.getStringExtra("imageUrl");

        if (!imageUrl.equals("-1")) {
            Log.e("TAg", "the image url is: " + imageUrl);
            Picasso.with(SingleItemDetailActivityForCabs.this)
                    .load(imageUrl)
                    .placeholder(R.drawable.amu_bubble_shadow)
                    //.fit()
                    .into(iv_person2);
        }
        else {
            Picasso.with(SingleItemDetailActivityForCabs.this)
                    .load(R.drawable.person_icon)
                    .placeholder(R.drawable.amu_bubble_shadow)
                    //.fit()
                    .into(iv_person2);

        }



        if (contacttName!=null){
            tv_m_name.setText(contacttName);
        }

        if (contactNumber!=null){
            tv_m_phome.setText(contactNumber);
        }
        if (contacttAddress!=null){
            tv_address_line_1.setText(contacttAddress);
            tv_address_line_2.setVisibility(View.GONE);
        }

        if (!contactStatus.equals("0")){

            tv_assign_now.setText("Assigned");
            tv_assign_now.setTextColor(getResources().getColor(R.color.blue));
        }

       /* if (contactLat!=null){
            tv_m_phome.setText(contactNumber);
        }
        if (contactNumber!=null){
            tv_m_phome.setText(contactNumber);
        }*/


      /*  //HttpRequest
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("url", Urls.GET_ASSET_DETAIL);
        map.put("Aid",  Aid);
        map.put("VType",  VType);
        new HttpRequester(SingleItemDetailActivityForCabs.this, map, 6, SingleItemDetailActivityForCabs.this);
*/

      startAssigmentButtonClick();

    }//end of onCreate method

    private void init(){

        sharedPreferences = getSharedPreferences(SharedPrefs.PREF_NAME, Context.MODE_PRIVATE);
        iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);
        rrRL  = (RelativeLayout) findViewById(R.id.rrRL);
        loading_img_rr = (ImageView) findViewById(R.id.loading_img_rr);
        iv_person2 = (ImageView) findViewById(R.id.iv_person2);

        tv_m_phome = (TextView) findViewById(R.id.tv_m_phome);
        tv_m_name = (TextView) findViewById(R.id.tv_m_name);

        tv_address_line_1 = (TextView) findViewById(R.id.tv_address_line_1);
        tv_address_line_2 = (TextView) findViewById(R.id.tv_address_line_2);

        bt_start_assignment = (Button) findViewById(R.id.bt_start_assignment);
        tv_assign_now = (TextView) findViewById(R.id.tv_assign_now);


        rotation = AnimationUtils.loadAnimation(SingleItemDetailActivityForCabs.this, R.anim.rotate);
        rotation.setFillAfter(true);

        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        ll_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SingleItemDetailActivityForCabs.this, MapsAssignemntSearch.class);
                startActivity(i);
            }
        });

    }
    private void onBackArrowPress(){
        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {

        rotation.cancel();

        if(serviceCode == 12)
        {
            //isServiceInProgressFlag = false;

            try {
                Logger.log(response);
//                BaseService.dismissProgressDialog();
                JSONObject obj = new JSONObject(response);
                jsonArray = new JSONArray();
                String error = obj.getString("Error");
                if (error.equals("You have already booked it."))
                {
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                }
                else {
                    jsonArray = obj.getJSONArray("Table");
                    String IsCusscess = jsonArray.getJSONObject(0).get("IsSuccess").toString();

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    private void startAssigmentButtonClick(){
        bt_start_assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent rideNowActivity = new Intent(SingleItemDetailActivityForCabs.this, StartTripMaps.class);
                startActivity(rideNowActivity);
            }
        });
    }

    private void assignNowButtonClickHandler(){

        tv_assign_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textTag = tv_assign_now.getText().toString();
                if (textTag.equals("Assigned")){

                    final Dialog dialog =  new Dialog(SingleItemDetailActivityForCabs.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.custome_dialog_assigned_detail);
                    TextView tv_confirm = (TextView) dialog.findViewById(R.id.tv_confirm);
                    TextView dialog_tv_name = (TextView) dialog.findViewById(R.id.dialog_tv_name);
                    TextView dialog_tv_phone = (TextView) dialog.findViewById(R.id.dialog_tv_phone);
                    TextView dialog_tv_address = (TextView) dialog.findViewById(R.id.dialog_tv_address);
                    if (contactStatus.equals("1")){

                        dialog_tv_name.setText("Nadeem Ali");
                        dialog_tv_phone.setText("+923234524510");
                        dialog_tv_address.setText("Ravi Road Lahore, Pakistan");
                    }
                    if (contactStatus.equals("2")){

                        dialog_tv_name.setText("Malik Ahmad");
                        dialog_tv_phone.setText("+923450125462");
                        dialog_tv_address.setText("Muzang Chungi Lahore, Pakistan");

                    }
                    if (contactStatus.equals("3")){

                        dialog_tv_name.setText("Rana Naveed");
                        dialog_tv_phone.setText("+9232145256036");
                        dialog_tv_address.setText("Township road Lahore, Pakistan");
                    }
                    if (contactStatus.equals("4")){

                        dialog_tv_name.setText("Anas Saeed");
                        dialog_tv_phone.setText("+923124524520");
                        dialog_tv_address.setText("Mughal pura Lahore, Pakistan");

                    }
                    if (contactStatus.equals("5")){

                        dialog_tv_name.setText("Umar Lhan");
                        dialog_tv_phone.setText("+923335420125");
                        dialog_tv_address.setText("Sanda road Lahore, Pakistan");
                    }

                    tv_confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTooDouen;
                    dialog.show();

                }
                else if (textTag.equals("Assign Now")){

                final Dialog dialog =  new Dialog(SingleItemDetailActivityForCabs.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custome_dialog_assign);
                TextView tv_confirm = (TextView) dialog.findViewById(R.id.tv_confirm);
                RadioGroup radio_group = (RadioGroup) dialog.findViewById(R.id.radio_group);

                radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        // find the radiobutton by returned id
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String text =  radioButton.getText().toString();
                        if (text.equals("Nadeem Ali")) {
                            checkedIdis = 1;
                        }
                        if (text.equals("Malik Ahmad")) {
                            checkedIdis = 2;
                        }
                        if (text.equals("Rana Naveed")) {
                            checkedIdis = 3;
                        }
                        if (text.equals("Anas Saeed")) {
                            checkedIdis = 4;
                        }
                        if (text.equals("Umar Khan")) {
                            checkedIdis = 5;
                        }


                    }
                });

                tv_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (checkedIdis==0){
                            Toast.makeText(SingleItemDetailActivityForCabs.this, "Choose a person first", Toast.LENGTH_SHORT).show();
                        }
                        else {


                            if (checkedIdis==1){

                                 saleManName ="Nadeem Ali";
                                 saleManContact = "+923234524510";
                                 saleManAddress = "Ravi Road Lahore, Pakistan";
                                 saleManLat = "32.2546522";
                                 saleManLng = "74.25463254";
                                 saleManId = "1";



                            }else if (checkedIdis==2){

                                saleManName ="Malik Ahmad";
                                saleManContact = "+923450125462";
                                saleManAddress = "Muzang Chungi Lahore, Pakistan";
                                saleManLat = "32.5426325";
                                saleManLng = "74.78154566";
                                saleManId = "2";

                            }
                            else if (checkedIdis==3){


                                saleManName ="Rana Naveed";
                                saleManContact = "+9232145256036";
                                saleManAddress = "Township road Lahore, Pakistan";
                                saleManLat = "32.5621464";
                                saleManLng = "74.78941654";
                                saleManId = "3";
                            }
                            else if (checkedIdis==4){

                                saleManName ="Anas Saeed";
                                saleManContact = "+923124524520";
                                saleManAddress = "Mughal pura Lahore, Pakistan";
                                saleManLat = "32.74469745";
                                saleManLng = "74.49676545646";
                                saleManId = "4";

                            }
                            else if (checkedIdis==5){

                                saleManName ="Umar Lhan";
                                saleManContact = "+923335420125";
                                saleManAddress = "Sanda road Lahore, Pakistan";
                                saleManLat = "32.21654646546";
                                saleManLng = "74.3216543478";
                                saleManId = "1";

                            }

                            ContactDatabase db = new ContactDatabase(SingleItemDetailActivityForCabs.this);
                             boolean isUpdated = db.updateTable(Integer.valueOf(contactId), String.valueOf(checkedIdis));
                            Log.e("TAG", "the value is update or not " + isUpdated);
                            if (isUpdated){
                                Toast.makeText(SingleItemDetailActivityForCabs.this, "Assignment Assigned Successfully", Toast.LENGTH_SHORT).show();
                                tv_assign_now.setText("Assigned");
                            }

                            dialog.dismiss();
                        }



                    }
                });
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTooDouen;
                dialog.show();

            }
            }
        });
    }
}
