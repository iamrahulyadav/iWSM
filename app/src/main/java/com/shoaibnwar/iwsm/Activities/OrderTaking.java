package com.shoaibnwar.iwsm.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shoaibnwar.iwsm.Adapters.CustomeItem;
import com.shoaibnwar.iwsm.R;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderTaking extends AppCompatActivity {

    ImageView top_iv_add_more;
    ImageView top_iv_back_arrow;
    LinearLayout ll_main_item_views;
    TextView top_tv_confirm;
    TextView top_et_quantitiy;
    ImageView top_iv_item_quantity_less, top_iv_item_quantity_more;
    AutoCompleteTextView top_et_item_name;
    TextView top_tv_price_text;
    TextView top_tv_price;
    RelativeLayout top_rl_price;

    int mPrice = 0;
    ArrayList<HashMap<String, String>> itemList;
    ArrayList<HashMap<String, String>> itemListConfirm;

    Animation animShake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_taking);

        init();
        addMoreClickHandler();
        onBackArrwoPressListener();
        confirmTextClickHandler();
        hardCodedData();

        addMoreItemClickHandler(top_iv_item_quantity_more, top_et_quantitiy, top_tv_price_text, top_tv_price);
        lestItemQuantityClickHandler(top_iv_item_quantity_less, top_et_quantitiy, top_tv_price_text, top_tv_price);
        onTaxkChangeListener(itemList, top_et_item_name, top_et_quantitiy, top_tv_price_text, top_tv_price, top_rl_price);




    }

    private void init(){

        ll_main_item_views = (LinearLayout) findViewById(R.id.ll_main_item_views);
        top_iv_add_more = (ImageView) findViewById(R.id.iv_add_more);
        top_iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);
        top_tv_confirm = (TextView) findViewById(R.id.tv_confirm);
        top_iv_item_quantity_less = (ImageView) findViewById(R.id.iv_item_quantity_less);
        top_iv_item_quantity_more = (ImageView) findViewById(R.id.iv_item_quantity_more);
        top_et_quantitiy = (TextView) findViewById(R.id.et_quantitiy);
        top_tv_price_text = (TextView) findViewById(R.id.tv_price_text);
        top_tv_price = (TextView) findViewById(R.id.tv_price);
        top_rl_price = (RelativeLayout) findViewById(R.id.rl_price);

        itemList = new ArrayList<>();
        itemListConfirm = new ArrayList<>();
        top_et_item_name = (AutoCompleteTextView) findViewById(R.id.et_item_name) ;
        animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);


    }

    private void addMoreClickHandler(){

        top_iv_add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.custome_addmore_product
                        , null);

                LinearLayout ll_inflate_document_for_experienceInner = (LinearLayout) rowView.findViewById(R.id.ll_to_inflat);
                ll_main_item_views.addView(rowView, ll_main_item_views.getChildCount());
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.let_to_right);
                rowView.setAnimation(animation);
                ImageView ivCrose = (ImageView) rowView.findViewById(R.id.iv_cross_item_view);
                ivCrose.bringToFront();

                AutoCompleteTextView et_item_name = (AutoCompleteTextView) rowView.findViewById(R.id.et_item_name) ;
                TextView tv_quantitiy =  (TextView) rowView.findViewById(R.id.tv_quantitiy);
                TextView et_quantitiy = (TextView) rowView.findViewById(R.id.et_quantitiy);
                ImageView iv_item_quantity_less = (ImageView) rowView.findViewById(R.id.iv_item_quantity_less);
                ImageView iv_item_quantity_more = (ImageView) rowView.findViewById(R.id.iv_item_quantity_more);
                TextView tv_price_text = (TextView) rowView.findViewById(R.id.tv_price_text);
                TextView tv_price = (TextView) rowView.findViewById(R.id.tv_price);
                EditText et_item_detail = (EditText) rowView.findViewById(R.id.et_item_detail);
                RelativeLayout rl_price = (RelativeLayout) rowView.findViewById(R.id.rl_price);


                addDapter(itemList, et_item_name, et_quantitiy, tv_price_text, tv_price, rl_price);
                addMoreItemClickHandler(iv_item_quantity_more, et_quantitiy, tv_price_text, tv_price);
                lestItemQuantityClickHandler(iv_item_quantity_less, et_quantitiy, tv_price_text, tv_price);
                onTaxkChangeListener(itemList, et_item_name, et_quantitiy, tv_price_text, tv_price, rl_price);

                deleteingView(rowView, ivCrose);

               /* int indecator = ll_main_item_views.getChildCount();
                indecator = indecator + 2;
                Log.e("TAG", "the count of view in experience: " + indecator);*/


            }
        });
    }


    public void deleteingView(final View myView, ImageView imageView){

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup parent = (ViewGroup) myView.getParent();
                parent.removeView(myView);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_to_left);
                myView.setAnimation(animation);

            }
        });
     /*   Log.e("TAG", "Image button Clicked: " + ll_main_item_views.getChildCount());
        for (int i = 0; i<ll_main_item_views.getChildCount(); i++){
            final View timingView = ll_main_item_views.getChildAt(i);
            ImageView im_remove_view = (ImageView) timingView.findViewById(R.id.iv_cross_item_view);

            im_remove_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewGroup parent = (ViewGroup) timingView.getParent();
                    parent.removeView(timingView);

                    //ll_to_inflat.removeView((View) (myView).getParent());
                }
            });
        }*/

    }
    private void onBackArrwoPressListener(){
        top_iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void confirmTextClickHandler(){
        top_tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isAnyFieldEmpty = false;
                int chiledCountForTopDocuments = ll_main_item_views.getChildCount();
                Log.e("TAg", "the child count for top documents are: " + chiledCountForTopDocuments);
                if (itemListConfirm.size()>0){
                    itemListConfirm.clear();
                }
                if (chiledCountForTopDocuments>0){

                    for (int i = 0; i<chiledCountForTopDocuments;i++){

                        HashMap<String, String> dataList = new HashMap<>();

                        final View mViews = ll_main_item_views.getChildAt(i);
                        AutoCompleteTextView et_item_name = (AutoCompleteTextView) mViews.findViewById(R.id.et_item_name) ;
                        TextView tv_quantitiy =  (TextView) mViews.findViewById(R.id.tv_quantitiy);
                        TextView et_quantitiy = (TextView) mViews.findViewById(R.id.et_quantitiy);
                        ImageView iv_item_quantity_less = (ImageView) mViews.findViewById(R.id.iv_item_quantity_less);
                        ImageView iv_item_quantity_more = (ImageView) mViews.findViewById(R.id.iv_item_quantity_more);
                        TextView tv_price_text = (TextView) mViews.findViewById(R.id.tv_price_text);
                        TextView tv_price = (TextView) mViews.findViewById(R.id.tv_price);
                        EditText et_item_detail = (EditText) mViews.findViewById(R.id.et_item_detail);
                        String itemName = et_item_name.getText().toString();
                        String itemQuantity = et_quantitiy.getText().toString();
                        String itemDetail = et_item_detail.getText().toString();
                        String itemPrice = tv_price.getText().toString();

                        if (itemName.length()==0){
                            et_item_name.setError("Should not be empty");
                            et_item_name.setAnimation(animShake);
                            isAnyFieldEmpty = true;
                        }else {

                            Log.e("TAg", "the item detail here item name: " + itemName);
                            Log.e("TAg", "the item detail here item itemQuantity: " + itemQuantity);
                            Log.e("TAg", "the item detail here item itemDetail: " + itemDetail);
                            Log.e("TAg", "the item detail here item itemPrice: " + itemPrice);

                            dataList.put("itemName", itemName);
                            dataList.put("itemPrice", itemPrice);
                            dataList.put("itemQuantity", itemQuantity);
                            dataList.put("itemDetail", itemDetail);
                            itemListConfirm.add(dataList);
                        }
                    }


                    if (!isAnyFieldEmpty) {
                        Intent i = new Intent(OrderTaking.this, Envoice.class);
                        i.putExtra("mylist", itemListConfirm);
                        startActivity(i);
                    }


                }//end of for loop for top documents
            }
        });
    }

    private void hardCodedData(){

        String[] some_array = getResources().getStringArray(R.array.items);
        for (int i=0;i<some_array.length;i++){
            String arrayData = some_array[i];
            String[] ss = arrayData.split(",");
            String s1 = ss[0];
            String s2 = ss[1];
            Log.e("TAG", "the item name is: " + s1);
            Log.e("TAG", "the item price is: " + s2);
            HashMap<String, String> items = new HashMap<>();
            items.put("Name", s1);
            items.put("Price", s2);
            itemList.add(items);
        }


        addDapter(itemList, top_et_item_name, top_et_quantitiy, top_tv_price_text, top_tv_price, top_rl_price);
    }

    private void addDapter(ArrayList<HashMap<String, String>> datList, final AutoCompleteTextView et_item_name, final TextView et_quantitiy, final TextView tv_price_text, final TextView tv_price, final RelativeLayout rl_price) {

        CustomeItem customeItem = new CustomeItem(OrderTaking.this, datList);

        //customCityNewAdapter = new CustomCityNewAdapter(getActivity() , GetAllCitiesListService.CityList);
        et_item_name.setAdapter(customeItem);
        et_item_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView title = (TextView) view.findViewById(R.id.tv_title);
                TextView price = (TextView) view.findViewById(R.id.tv_price);
                String mName = title.getText().toString();
                String PRICE = price.getText().toString();

                et_item_name.setText(mName);
                et_quantitiy.setText("1");
                Log.e("TAg", "Name of product " + mName);
                Log.e("TAg", "Name of product price " + PRICE);
                rl_price.setVisibility(View.VISIBLE);
                tv_price_text.setText("Price Rs ");
                tv_price.setText(PRICE);
                mPrice = Integer.valueOf(PRICE);




                //Toast.makeText(SignIn.this, "Pos "+text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addMoreItemClickHandler(ImageView ivMore, final TextView et_quantitiy, final TextView top_tv_price_text, final TextView tv_price){
        ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_quantitiy.length()>0) {
                    addMoreQuanity(et_quantitiy, top_tv_price_text, tv_price);
                }

            }
        });
    }
    private void lestItemQuantityClickHandler(ImageView ivLess, final TextView et_quantitiy, final TextView top_tv_price_text, final TextView tv_price){
        ivLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_quantitiy.length()>0) {
                    leesQuanity(et_quantitiy, top_tv_price_text, tv_price);

                }
            }
        });

    }

    private void addMoreQuanity(TextView view, TextView tv_price_text, TextView tvPrice){

        String text = view.getText().toString();
        int intValue = Integer.valueOf(text);
        intValue = intValue+1;
        String toSet = String.valueOf(intValue);
        view.setText(toSet);
        if (tvPrice.length()>0) {
            int pri = Integer.valueOf(tvPrice.getText().toString());
            int price = pri + mPrice;

            String curentPrice = String.valueOf(price);
            tv_price_text.setText("Price Rs.");
            tvPrice.setText(curentPrice);
        }

    }
    private void leesQuanity(TextView view, TextView tv_price_text, TextView tvPrice){

        String text = view.getText().toString();
        int intValue = Integer.valueOf(text);
        if (intValue==0){

        }else {
            intValue = intValue - 1;
            String toSet = String.valueOf(intValue);
            view.setText(toSet);
            if (tvPrice.length()>0) {
            int pri = Integer.valueOf(tvPrice.getText().toString());
            int price = pri-mPrice;
            String curentPrice = String.valueOf(price);
            tv_price_text.setText("Price Rs.");
            tvPrice.setText(curentPrice);

        }}
    }

    private void onTaxkChangeListener(final ArrayList<HashMap<String, String>> dList, final AutoCompleteTextView editText, final TextView et_quatitiy, final TextView tv_price_text, final TextView tv_price, final RelativeLayout rl_price){


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                addDapter(dList, editText, et_quatitiy, tv_price_text,  tv_price, rl_price);
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length()==0){
                    tv_price_text.setText("");
                    tv_price.setText("");
                    mPrice = 0;
                    et_quatitiy.setText("0");
                    rl_price.setVisibility(View.GONE);
                }
            }
        });

    }

}
