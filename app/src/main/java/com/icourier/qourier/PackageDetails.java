package com.icourier.qourier;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PackageDetails extends AppCompatActivity {

    String name,address,phone,pName,pDescr,amount,date ,url;
    TextView nam,addres,phon,pNam,pDescription,amountBid,dateS;

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        address = bundle.getString("address");
        phone = bundle.getString("phone");
        pName = bundle.getString("package Name");
        pDescr = bundle.getString("package description");
        amount = bundle.getString("amount");
        date = bundle.getString("date");
        url = bundle.getString("image");

        nam = (TextView)findViewById(R.id.dname);

       // addres = (TextView)findViewById(R.id.dAddress);

        phon = (TextView)findViewById(R.id.dphone);
        pNam = (TextView)findViewById(R.id.dpname);
        pDescription = (TextView)findViewById(R.id.dpDescription);
        amountBid = (TextView)findViewById(R.id.dAmount);
        dateS = (TextView)findViewById(R.id.dDate);
        imageView = (ImageView) findViewById(R.id.dImage);

        nam.setText(name);
       // addres.setText(address);
        phon.setText(phone);
        pNam.setText(pName);
        pDescription.setText(pDescr);
        amountBid.setText("R"+amount);
        dateS.setText(date);

        Glide.with(getApplicationContext()).load(url)
       .into(imageView);


    }
}
