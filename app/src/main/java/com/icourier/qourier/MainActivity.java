package com.icourier.qourier;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.text.Html;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 234;

    private EditText uniqkeyz;
    private EditText destination;
    private EditText description;
    private EditText date;
    private EditText name;
    private EditText amount;
    private EditText pname;
    private CheckBox insurance;

    private Button buttonChoose;
    private Button buttonUpload;

    TextView file;
    private String username;

    private String street,uniqkey;
    private String city;
    private String code;
    private String phone;
    private String email;
    private double newbidamt;


    //uri to store file
    private Uri filePath;

    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    //firebase objects
    private StorageReference storageReference;
    private DatabaseReference mDatabase;

    private boolean fabExpanded = false;
    private FloatingActionButton fabSettings,FabPaypal,FabUploadSlip;
    private LinearLayout layoutFabSave;
    private LinearLayout layoutFabEdit;
     String fullna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Package");
        Firebase.setAndroidContext(getApplicationContext());

        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_DEVICES);

        uniqkeyz = (EditText)findViewById(R.id.etuniqkeyz);

        destination = (EditText)findViewById(R.id.etDestinationpcity);
        description = (EditText)findViewById(R.id.etPackageDescription);
        name = (EditText)findViewById(R.id.etName);
        amount = (EditText)findViewById(R.id.etPackageAmount);
        pname = (EditText)findViewById(R.id.etPackageName);

        buttonChoose = (Button) findViewById(R.id.btn_choose);
        buttonUpload = (Button) findViewById(R.id.btn_upload);

        file = (TextView) findViewById(R.id.title_choose);

        date = (EditText)findViewById(R.id.etxt_fromdate);
        date.setInputType(InputType.TYPE_NULL);
        date.requestFocus();

        // date
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        setDateTimeField();
        buttonChoose.setOnClickListener(this);
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    showFileChooser();
             }
        });

        buttonUpload.setOnClickListener(this);
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                uploadFile();
            }

        });


        FabPaypal = (FloatingActionButton) this.findViewById(R.id.fabPaypal);
        FabPaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Paypal.class));
            }
        });

        Bundle bundle = getIntent().getExtras();
        fullna = bundle.getString("fullname");
         username = bundle.getString("username");

        street = bundle.getString("street");
        city = bundle.getString("city");
        code = bundle.getString("code");
        phone = bundle.getString("phone");
        email = bundle.getString("email");

        FabUploadSlip = (FloatingActionButton) this.findViewById(R.id.fabUslip);
        FabUploadSlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this,UploadSlip.class);
                i.putExtra("fullname",fullna);
                i.putExtra("username",username);
                startActivity(i);
            }
        });

        // Fabs

        fabSettings = (FloatingActionButton) this.findViewById(R.id.fabSetting);

        layoutFabSave = (LinearLayout) this.findViewById(R.id.layoutFabSave);
        layoutFabEdit = (LinearLayout) this.findViewById(R.id.layoutFabEdit);
        //layoutFabSettings = (LinearLayout) this.findViewById(R.id.layoutFabSettings);

        //When main Fab (Settings) is clicked, it expands if not expanded already.
        //Collapses if main FAB was open already.
        //This gives FAB (Settings) open/close behavior
        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded == true){
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });

        //Only main FAB is visible in the beginning
        closeSubMenusFab();

        // Fabs

        insurance = (CheckBox)findViewById(R.id.insuranceCheckbox);
        if(amount.getText().toString().equals(" ")){

        }else{

            insurance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                    android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.insurance_layout, null);
                    mBuilder.setView(mView);
                    final android.app.AlertDialog dialog = mBuilder.create();
                    TextView txtInsur = (TextView)mView.findViewById(R.id.txtinsur);
                    TextView txtnewamt = (TextView)mView.findViewById(R.id.txtnwamt);

                    double insurance = Double.parseDouble(amount.getText().toString()) * 0.05;
                    try {

                        if (insurance < 20) {
                            insurance = 20;
                        }
                    }catch (Exception e){

                    }
                    newbidamt = Double.parseDouble(amount.getText().toString()) + insurance;
                    txtInsur.setText("R"+insurance);
                    txtnewamt.setText("R"+newbidamt);

                    if(buttonView.isChecked()){

                        Button Done = (Button)mView.findViewById(R.id.btnDoneInsu);

                        Done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }else{

                    }
                }

            });
        }


    }
    private void setDateTimeField() {
        date.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View view) {
        if(view == date) {
            fromDatePickerDialog.show();
        }
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Document"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            file.setText(filePath.toString());
        }
    }
    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {

          uniqkey= uniqkeyz.getText().toString().trim();
        //checking if file is available
        if (filePath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Creating a Package...");
            progressDialog.show();

            //getting the storage reference
            StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_DEVICES + System.currentTimeMillis() + "." + getFileExtension(filePath));

            //adding the file to reference
            sRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();

                            //displaying success toast

                            Toast.makeText(getApplicationContext(), "Package Created ", Toast.LENGTH_LONG).show();

                            //Firebase ref = new Firebase("https://i-courier-18c0e.firebaseio.com/I-Courier/Poster");
                            //creating the upload object to store uploaded image details
                           // Toast.makeText(getApplicationContext(),  ref.child("Poster").toString(), Toast.LENGTH_LONG).show();
                           /* Toast.makeText(getApplicationContext(), u.getPuniqkey()+"\n"+uniqkey, Toast.LENGTH_LONG).show();
                            try {
                                if(u.getPuniqkey().equals(uniqkey)){

                                    uniqkeyz.setError("Key Taken");
                                     }else {

                                    Courier upload = new Courier("True",username,name.getText().toString().trim(),phone,street,city,code,destination.getText().toString().trim(),description.getText().toString().trim(),date.getText().toString().trim(),amount.getText().toString().trim(),pname.getText().toString().trim(),email,newbidamt,taskSnapshot.getDownloadUrl().toString(),uniqkey);
                                    mDatabase.child(uniqkey).setValue(upload);

                                    Toast.makeText(getApplicationContext(), "Package Created ", Toast.LENGTH_LONG).show();

                                }
                            }catch (Exception e){
                                uniqkeyz.setError("Key Taken");
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }
*/
                            Courier upload = new Courier("True",username,name.getText().toString().trim(),phone,street,city,code,destination.getText().toString().trim(),description.getText().toString().trim(),date.getText().toString().trim(),amount.getText().toString().trim(),pname.getText().toString().trim(),email,newbidamt,taskSnapshot.getDownloadUrl().toString(),uniqkey);
                            mDatabase.child(uniqkey).setValue(upload);

                            //adding an upload to firebase database
                           // String uploadId = mDatabase.push().getKey();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        } else {
            //display an error if no file is selected
        }
    }

    //closes FAB submenus
    private void closeSubMenusFab(){
        layoutFabSave.setVisibility(View.INVISIBLE);
        layoutFabEdit.setVisibility(View.INVISIBLE);
        fabSettings.setImageResource(R.drawable.ic_settings_black_24dp);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        layoutFabSave.setVisibility(View.VISIBLE);
        layoutFabEdit.setVisibility(View.VISIBLE);
        //Change settings icon to 'X' icon
        fabSettings.setImageResource(R.drawable.ic_close_black_24dp);
        fabExpanded = true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_nav, menu);
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
        if(id == R.id.action_key){

            Intent i = new Intent(this,Key.class);
            i.putExtra("username",username);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }
}
