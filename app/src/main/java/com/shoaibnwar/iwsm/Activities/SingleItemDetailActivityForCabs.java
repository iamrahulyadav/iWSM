package com.shoaibnwar.iwsm.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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



    JSONArray jsonArray;
    RelativeLayout rrRL;
    ImageView loading_img_rr;
    Animation rotation;

    String Aid = "";
    String VType = "";
    String imageUrl;


    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_detail_for_cabs);
        System.gc();

        init();
        onBackArrowPress();
        overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );

        Intent intentData = getIntent();
        /*Aid = intentData.getStringExtra("Vid");
        VType = intentData.getStringExtra("VType");
*/
        imageUrl = intentData.getStringExtra("imageUrl");
        Log.e("TAg", "the image url is: " + imageUrl);
        Picasso.with(SingleItemDetailActivityForCabs.this)
                .load(imageUrl)
                .placeholder(R.drawable.amu_bubble_shadow)
                //.fit()
                .into(iv_person2);


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


        bt_start_assignment = (Button) findViewById(R.id.bt_start_assignment);


        rotation = AnimationUtils.loadAnimation(SingleItemDetailActivityForCabs.this, R.anim.rotate);
        rotation.setFillAfter(true);



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
}
