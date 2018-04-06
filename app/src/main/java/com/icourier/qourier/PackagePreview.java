package com.icourier.qourier;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackagePreview extends AppCompatActivity {

    String names,street,city,bcode,phone,pname,pdescription,amount,date,pimage,username,fullname,email,account,pkey,mykey,fulnam,phne,bankname,bankaccount,branch;;
    RequestQueue requestQueue;
    String insertUrlMessage = "http://mydm.co.za/Icourier/InsertKey.php";

    SharedPreferences prefs ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_preview);
        Bundle b = getIntent().getExtras();
        pname = b.getString("pname");
        getSupportActionBar().setTitle(pname);

        Firebase.setAndroidContext(getApplicationContext());
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        prefs = getSharedPreferences("Packages", Context.MODE_PRIVATE);

        ImageView imageb = (ImageView)findViewById(R.id.dImage);
        TextView tvnam = (TextView)findViewById(R.id.dname);

        TextView tvstreet = (TextView)findViewById(R.id.dstreet);
        TextView tvcity = (TextView)findViewById(R.id.dcity);
        TextView tvcode = (TextView)findViewById(R.id.dcode);

        TextView tvphon = (TextView)findViewById(R.id.dphone);
        TextView tvDescription = (TextView)findViewById(R.id.dpDescription);
        TextView tvamountBid = (TextView)findViewById(R.id.dAmount);
        TextView tvdateS = (TextView)findViewById(R.id.dDate);


        username = b.getString("username");
        fullname = b.getString("fullname");

        names =  b.getString("name");
        tvnam.setText(names);
        street = b.getString("street");
        tvstreet.setText(street);
        city = b.getString("city");
        tvcity.setText(city);
        bcode = b.getString("code");
        tvcode.setText(bcode);
        phone = b.getString("number");
        tvphon.setText(phone);

        pdescription = b.getString("description");
        tvDescription.setText(pdescription);
        amount = b.getString("amount");
        tvamountBid.setText(amount);
        date = b.getString("date");
        tvdateS.setText(date);
        pimage = b.getString("image");
        email = b.getString("email");
        account = b.getString("account");
        pkey = b.getString("pkey");
        mykey = b.getString("mykey");

        Glide.with(getApplicationContext()).load(pimage).into(imageb);

        // cancel
        findViewById(R.id.dcancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MakeDelivery.class);
                i.putExtra("fullname",fullname);
                i.putExtra("username",username);
                startActivity(i);
            }
        });
        // accept
        findViewById(R.id.dapprove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fulnam = prefs.getString("fullname", null);
                phne = prefs.getString("phone", null);
                bankname = prefs.getString("bankname", null);
                bankaccount = prefs.getString("bankaccount", null);
                branch = prefs.getString("branch", null);

                final Dialog dialog = new Dialog(v.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.action_layout);
                dialog.setCancelable(false);

                ImageView Dcall = (ImageView) dialog.findViewById(R.id.dcall);
                ImageView Dmessage = (ImageView) dialog.findViewById(R.id.dmessage);
                ImageView Demail = (ImageView) dialog.findViewById(R.id.demail);
                ImageView close = (ImageView) dialog.findViewById(R.id.cancel);

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                Dcall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+phone));
                        startActivity(intent);
                        dialog.dismiss();

                    }
                });

                Dmessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",phone, null)));
                        dialog.dismiss();
                    }
                });

                Demail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",email, null));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Accept Delivery");
                        startActivity(Intent.createChooser(emailIntent, "Send email..."));
                        dialog.dismiss();

                    }
                });

                final String usern = prefs.getString("username", null);

                Firebase reference = new Firebase("https://i-courier-18c0e.firebaseio.com/");
                String key = reference.child("I-Courier").child("Packages").push().getKey();
                String uniqueID = username+"*"+usern;

                Courier pack = new Courier(pkey,date,"false",amount,pname,pdescription, names, phone,street,city,bcode, email, account,uniqueID.toString().trim() ,pimage,fulnam,phne,bankname, bankaccount,branch);


                reference.child("I-Courier").child("Packages").child(key).setValue(pack);

                dialog.show();

                Firebase ref = new Firebase("https://i-courier-18c0e.firebaseio.com/I-Courier/Poster");
                ref.child(mykey).child("check").setValue("delivered");

                try {

                    sendKey(usern,pkey,username);


                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });
    }

    public void sendKey(final String name, final String mykey, final String user){

        StringRequest request = new StringRequest(Request.Method.POST, insertUrlMessage, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response.toString());
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("name", name);
                parameters.put("mykey", mykey);
                parameters.put("username", user);


                return parameters;
            }
        };
        requestQueue.add(request);
        // Toast.makeText(c, " Key sent...", Toast.LENGTH_LONG).show();


    }
}
