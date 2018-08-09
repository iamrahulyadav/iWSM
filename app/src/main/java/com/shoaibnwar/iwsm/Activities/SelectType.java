package com.shoaibnwar.iwsm.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shoaibnwar.iwsm.R;

public class SelectType extends AppCompatActivity {

    RelativeLayout rl_view_customers, rl_view_assignments, rl_view_routes, rl_assignment_history;
    public int READ_CONTACTS_PERMISSION_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selct_type);

        init();


        if (!isContactsPermissionGranted()) {
            showRequestPermissionsInfoAlertDialog();
        }

        rl_view_customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SelectType.this, ContactsList.class);
                startActivity(i);
            }
        });

        rl_view_assignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SelectType.this, VerticalListActivity.class);
                startActivity(i);
            }
        });

        rl_assignment_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SelectType.this, AssignmentsHistory.class);
                startActivity(i);
            }
        });

        rl_view_routes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SelectType.this, MainMaps.class);
                startActivity(i);
            }
        });

    }

    private void init(){

        rl_view_customers = (RelativeLayout) findViewById(R.id.rl_view_customers);
        rl_view_assignments = (RelativeLayout) findViewById(R.id.rl_view_assignments);
        rl_view_routes = (RelativeLayout) findViewById(R.id.rl_view_routes);
        rl_assignment_history = (RelativeLayout) findViewById(R.id.rl_assignment_history);


    }






    public boolean isContactsPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request runtime READ CONTACTS permission
     */
    private void requestReadContactsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
            // You may display a non-blocking explanation here, read more in the documentation:
            // https://developer.android.com/training/permissions/requesting.html
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_PERMISSION_CODE);
    }

    /* And a method to override */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode)
        {
            case 123:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(SelectType.this,
                            Manifest.permission.READ_CONTACTS) ==  PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No Permission granted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void showRequestPermissionsInfoAlertDialog() {
        showRequestPermissionsInfoAlertDialog(true);
    }

    public void showRequestPermissionsInfoAlertDialog(final boolean makeSystemRequest) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("READ Contacts Permission"); // Your own title
        builder.setMessage("iWFM will now request READ Contact permission on your device. \nThis is require to use iWFM"); // Your own message

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // Display system runtime permission request?
                if (makeSystemRequest) {
                    requestReadContactsPermission();
                }
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

}
