package com.icourier.qourier;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Options extends AppCompatActivity {

    RelativeLayout firstLayout,secLayout;
    TextView tvcreatePackage,tvmyPackage,tvpayment,tvkey;
    String fullname,user,street,city,code,phone,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        getSupportActionBar().setTitle("Choose Action");

        firstLayout = (RelativeLayout)findViewById(R.id.first_layout);
        secLayout = (RelativeLayout)findViewById(R.id.sec_layout);
        Typeface font = Typeface.createFromAsset(getAssets(),"custom_font.ttf");
        tvcreatePackage =(TextView) findViewById(R.id.txt_create);
        tvmyPackage =(TextView) findViewById(R.id.txt_mypackage);
        tvpayment = (TextView)findViewById(R.id.txt_payment);
        tvkey = (TextView)findViewById(R.id.txt_key);

        tvcreatePackage.setTypeface(font);
        tvmyPackage.setTypeface(font);
        tvpayment.setTypeface(font);
        tvkey.setTypeface(font);

        firstLayout.getBackground().setAlpha(150);
        secLayout.getBackground().setAlpha(150);

        Bundle b = getIntent().getExtras();
        fullname = b.getString("fullname");
        user = b.getString("username");
        street = b.getString("street");
        city = b.getString("city");
        code = b.getString("code");
        phone = b.getString("phone");
        email = b.getString("email");

        findViewById(R.id.cat_create_package).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Options.this,MainActivity.class);
                i.putExtra("fullname",fullname);
                i.putExtra("username",user);
                i.putExtra("street",street);
                i.putExtra("city",city);
                i.putExtra("code",code);
                i.putExtra("phone",phone);
                i.putExtra("email",email);

                startActivity(i);
            }
        });

        findViewById(R.id.cat_key).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Options.this,Key.class);
                i.putExtra("fullname",fullname);
                i.putExtra("username",user);
                i.putExtra("street",street);
                i.putExtra("city",city);
                i.putExtra("code",code);
                i.putExtra("phone",phone);
                i.putExtra("email",email);

                startActivity(i);
            }
        });

        findViewById(R.id.cat_mypackage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Options.this,UploadSlip.class);
                i.putExtra("fullname",fullname);
                i.putExtra("username",user);
                i.putExtra("street",street);
                i.putExtra("city",city);
                i.putExtra("code",code);
                i.putExtra("phone",phone);
                i.putExtra("email",email);

                startActivity(i);
            }
        });

        findViewById(R.id.cat_payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.payment_layout);
                dialog.setCancelable(true);

                dialog.show();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_logout) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage(Html.fromHtml("<font color='#627984'>Are you sure you want to logout?</font>"))
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(getApplicationContext(),TabActivity.class));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
