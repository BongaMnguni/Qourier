package com.icourier.qourier;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UploadSlip extends AppCompatActivity {
    TextView tvname;
    private RecyclerView recyclerView;
    //adapter object
    private RecyclerView.Adapter adapter;

    //database reference
    private DatabaseReference mDatabase;

    //progress dialog
    private ProgressDialog progressDialog;

    private List<Courier> packages;
    String fullna,username;
    private String street;
    private String city;
    private String code;
    private String phone;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_slip);
        getSupportActionBar().setTitle("Package List");
// change id
        recyclerView = (RecyclerView) findViewById(R.id.service_recyclerView);
        recyclerView.setHasFixedSize(true);

        Bundle bundle = getIntent().getExtras();
         fullna = bundle.getString("fullname");
         username = bundle.getString("username");

        street = bundle.getString("street");
        city = bundle.getString("city");
        code = bundle.getString("code");
        phone = bundle.getString("phone");
        email = bundle.getString("email");

        tvname = (TextView)findViewById(R.id.txtpnamez);
        tvname.setText(fullna);



        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpWidth  = outMetrics.widthPixels / density;
        int columns = Math.round(dpWidth/300);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),columns);
        recyclerView.setLayoutManager(layoutManager);

        progressDialog = new ProgressDialog(this);

        packages = new ArrayList<>();

        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_DEVICES);

        //adding an event listener to fetch values
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //dismissing the progress dialog
                progressDialog.dismiss();

                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Courier doc = postSnapshot.getValue(Courier.class);
                    String key = postSnapshot.getKey();
                    doc.setKey(key);

                    if(doc.getUsername().equals(username) && doc.getDelete().equals("false")){
                        packages.add(doc);
                    }
                }
                //creating adapter
                adapter = new ListPackageAdapter(getApplicationContext(), packages);

                //adding adapter to recyclerview
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) {
            // pass back the value to main
                    Intent i = new Intent(UploadSlip.this,Options.class);
                            i.putExtra("fullname",fullna);
                            i.putExtra("username",username);
                            i.putExtra("street",street);
                            i.putExtra("city",city);
                            i.putExtra("code",code);
                            i.putExtra("phone",phone);
                            i.putExtra("email",email);
                    startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
