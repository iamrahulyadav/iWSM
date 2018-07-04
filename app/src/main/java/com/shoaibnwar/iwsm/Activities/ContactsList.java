package com.shoaibnwar.iwsm.Activities;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.shoaibnwar.iwsm.Adapters.Contacts;
import com.shoaibnwar.iwsm.Database.ContactDatabase;
import com.shoaibnwar.iwsm.Database.ContactDbHelper;
import com.shoaibnwar.iwsm.R;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ContactsList extends AppCompatActivity {

    RecyclerView rc_list;
    LinearLayoutManager linearLayoutManager;
    ArrayList<HashMap<String, String>> contactsList;
    Contacts contactsAdapter;
    ProgressBar progress_bar;
    ImageView iv_back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        init();
        onBackArrowClick();
       // addingHardCodeData();

    }

    private void init() {

        iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);
        rc_list = findViewById(R.id.rc_list);
        rc_list.bringToFront();
        linearLayoutManager = new LinearLayoutManager(ContactsList.this, LinearLayoutManager.VERTICAL, false);
        rc_list.setLayoutManager(linearLayoutManager);
        contactsList = new ArrayList<>();
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);

        //reading contacts
        LoadContacts looadContacts = new LoadContacts();
        looadContacts.execute();

    }

    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                String displayPhotId = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.PHOTO_ID));
                Uri photoUri = null;
                if (displayPhotId != null) {
                    photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, Long.parseLong(displayPhotId));
                    Log.e("TAg", "the photo bitmap is: " + photoUri);
                }

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.e("TAG", "Name: " + name);
                        Log.e("TAG", "Phone Number: " + phoneNo);

                        HashMap<String, String> contactDetail = new HashMap<>();
                        contactDetail.put("number", phoneNo);
                        contactDetail.put("name", name);
                        if (photoUri != null) {
                            contactDetail.put("imageuri", photoUri.toString());
                        } else {
                            contactDetail.put("imageuri", "-1");
                        }

                        contactsList.add(contactDetail);
                    }
                    pCur.close();
                }
            }


        }
        if (cur != null) {
            cur.close();
        }
    }

    public class LoadContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            gettingAllDataFromDb();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress_bar.setVisibility(View.GONE);
            contactsAdapter = new Contacts(ContactsList.this, contactsList);
            rc_list.setAdapter(contactsAdapter);
        }
    }


    private void addingHardCodeData() {


    String name1 = "Hamza Khan";
    String name1_phone = "+923454166554";
    String name1_address = "Anarkali lahore, Pakistan";
    String name1Lat = "31.571359";
    String name1Lng = "74.310355";
    String name1ImageUri = "-1";

    HashMap<String, String> contactDetail1 = new HashMap<>();
        contactDetail1.put("number", name1_phone);
        contactDetail1.put("name", name1);
        contactDetail1.put("imageuri", name1ImageUri);
        contactDetail1.put("lat", name1Lat);
        contactDetail1.put("lng", name1Lng);
        contactDetail1.put("address", name1_address);

        contactsList.add(contactDetail1);

    String name2 = "Abaas Ali";
    String name2_phone = "+923252654695";
    String name2_address = "Model Town lahore, Pakistan";
    String name2Lat = "31.254564";
    String name2Lng = "74.264154";
    String name2ImageUri = "-1";

        HashMap<String, String> contactDetail2 = new HashMap<>();
        contactDetail2.put("number", name2_phone);
        contactDetail2.put("name", name2);
        contactDetail2.put("imageuri", name2ImageUri);
        contactDetail2.put("lat", name2Lat);
        contactDetail2.put("lng", name2Lng);
        contactDetail2.put("address", name2_address);
        contactsList.add(contactDetail2);

    String name3 = "Rizwan Nisar";
    String name3_phone = "+923214585425";
    String name3_address = "Gulberg II lahore, Pakistan";
    String name3Lat = "31.5465446";
    String name3Lng = "74.1313365";
    String name3ImageUri = "-1";

        HashMap<String, String> contactDetail3 = new HashMap<>();
        contactDetail3.put("number", name3_phone);
        contactDetail3.put("name", name3);
        contactDetail3.put("imageuri", name3ImageUri);
        contactDetail3.put("lat", name3Lat);
        contactDetail3.put("lng", name3Lng);
        contactDetail3.put("address", name3_address);
        contactsList.add(contactDetail3);

    String name4 = "Kashif latif";
    String name4_phone = "+923458251254";
    String name4_address = "Johar Town lahore, Pakistan";
    String name4Lat = "31.85857897";
    String name4Lng = "74.8798797";
    String name4ImageUri = "-1";

        HashMap<String, String> contactDetail4 = new HashMap<>();
        contactDetail4.put("number", name4_phone);
        contactDetail4.put("name", name4);
        contactDetail4.put("imageuri", name4ImageUri);
        contactDetail4.put("lat", name4Lat);
        contactDetail4.put("lng", name4Lng);
        contactDetail4.put("address", name4_address);
        contactsList.add(contactDetail4);

    String name5 = "Adeel Khalil";
    String name5_phone = "+923135245265";
    String name5_address = "Behria town lahore, Pakistan";
    String name5Lat = "31.49646546";
    String name5Lng = "74.16544656";
    String name5ImageUri = "-1";

        HashMap<String, String> contactDetail5 = new HashMap<>();
        contactDetail5.put("number", name5_phone);
        contactDetail5.put("name", name5);
        contactDetail5.put("imageuri", name5ImageUri);
        contactDetail5.put("lat", name5Lat);
        contactDetail5.put("lng", name5Lng);
        contactDetail5.put("address", name5_address);
        contactsList.add(contactDetail5);

    String name6 = "Salman saeed";
    String name6_phone = "+923334525485";
    String name6_address = "Shadra lahore, Pakistan";
    String name6Lat = "31.465456466";
    String name6Lng = "74.65465464";
    String name6ImageUri = "";

        HashMap<String, String> contactDetail6 = new HashMap<>();
        contactDetail6.put("number", name6_phone);
        contactDetail6.put("name", name6);
        contactDetail6.put("imageuri", name6ImageUri);
        contactDetail6.put("lat", name6Lat);
        contactDetail6.put("lng", name6Lng);
        contactDetail6.put("address", name6_address);
        contactsList.add(contactDetail6);

    String name7 = "Usman Rafique";
    String name7_phone = "+9233452545216";
    String name7_address = "Multan road lahore, Pakistan";
    String name7Lat = "31.3164344646";
    String name7Lng = "74.545465464646";
    String name7ImageUri = "-1";

        HashMap<String, String> contactDetail7 = new HashMap<>();
        contactDetail7.put("number", name7_phone);
        contactDetail7.put("name", name7);
        contactDetail7.put("imageuri", name7ImageUri);
        contactDetail7.put("lat", name7Lat);
        contactDetail7.put("lng", name7Lng);
        contactDetail7.put("address", name7_address);
        contactsList.add(contactDetail7);

    String name8 = "Ammar suhail";
    String name8_phone = "+923215426330";
    String name8_address = "Behria town lahore, Pakistan";
    String name8Lat = "31.875797987";
    String name8Lng = "74.579798";
    String name8ImageUri = "-1";

        HashMap<String, String> contactDetail8 = new HashMap<>();
        contactDetail8.put("number", name8_phone);
        contactDetail8.put("name", name8);
        contactDetail8.put("imageuri", name2ImageUri);
        contactDetail8.put("lat", name8Lat);
        contactDetail8.put("lng", name8Lng);
        contactDetail8.put("address", name8_address);
        contactsList.add(contactDetail8);

    String name9 = "Akranm ismaeil";
    String name9_phone = "+923224502150";
    String name9_address = "Urdu bazar lahore, Pakistan";
    String name9Lat = "31.546464";
    String name9Lng = "74.5445465";
    String name9ImageUri = "-1";

        HashMap<String, String> contactDetail9 = new HashMap<>();
        contactDetail9.put("number", name9_phone);
        contactDetail9.put("name", name9);
        contactDetail9.put("imageuri", name9ImageUri);
        contactDetail9.put("lat", name9Lat);
        contactDetail9.put("lng", name9Lng);
        contactDetail9.put("address", name9_address);
        contactsList.add(contactDetail9);


      /*  progress_bar.setVisibility(View.GONE);
        contactsAdapter = new Contacts(ContactsList.this, contactsList);
        rc_list.setAdapter(contactsAdapter);*/

}

    private void gettingAllDataFromDb(){

        ContactDatabase contactDatabase = new ContactDatabase(ContactsList.this);
        ArrayList<ContactDbHelper> list = contactDatabase.getContacts();
        for (ContactDbHelper helper:list){
            String contactId = helper.getId().toString();
            String ContactName = helper.getContactName().toString();
            String ContactNumber = helper.getContactPhone().toString();
            String ContactAddress = helper.getContactAddress().toString();
            String ContactLat = helper.getContactLat().toString();
            String ContactLng = helper.getContactLng().toString();
            String ContactStatus = helper.getContactStatus().toString();
            String ContactCompany = helper.getContactCompany().toString();

            HashMap<String, String> contactDetail = new HashMap<>();
            contactDetail.put("id", contactId);
            contactDetail.put("number", ContactNumber);
            contactDetail.put("name", ContactName);
            contactDetail.put("imageuri", "-1");
            contactDetail.put("lat", ContactLat);
            contactDetail.put("lng", ContactLng);
            contactDetail.put("address", ContactAddress);
            contactDetail.put("staus", ContactStatus);
            contactDetail.put("company", ContactCompany);
            contactsList.add(contactDetail);


        }
    }
    private void onBackArrowClick()
    {

        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
