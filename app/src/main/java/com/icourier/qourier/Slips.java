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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Slips extends AppCompatActivity {
    TextView tvname;
    private RecyclerView recyclerView;

    //adapter object
    private RecyclerView.Adapter adapter;

    //database reference
    private DatabaseReference mDatabase;

    //progress dialog
    private ProgressDialog progressDialog;

    private List<Courier> packages;
    String username,fullna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slips);
        getSupportActionBar().setTitle("Accepted Items");
// change id
        recyclerView = (RecyclerView) findViewById(R.id.package_recyclerView);
        recyclerView.setHasFixedSize(true);

        Bundle bundle = getIntent().getExtras();
        fullna = bundle.getString("fullname");
        username = bundle.getString("username");


        tvname = (TextView)findViewById(R.id.txtPname);
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
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_PACKAGES);

        //adding an event listener to fetch values
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //dismissing the progress dialog
                progressDialog.dismiss();

                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Courier doc = postSnapshot.getValue(Courier.class);

                    String temp = doc.getUniqueID();
                    int indexOf = temp.indexOf('*');
                    String newstr = temp.substring(indexOf+1);

                    if(newstr.equals(username) && doc.getDelete().equals("false")){
                        packages.add(doc);
                    }

                }
                //creating adapter
                adapter = new PackageAdapter(getApplicationContext(), packages);

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
            Intent i = new Intent(Slips.this,MakeDelivery.class);
            i.putExtra("fullname",fullna);
            i.putExtra("username",username);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
