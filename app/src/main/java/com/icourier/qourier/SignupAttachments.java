package com.icourier.qourier;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignupAttachments extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 234;
    private TextView file_id,file_licence,file_picture,file_vehicle;
    private Uri filePathid,filePathlicence,filePathvehicle,filePathpicture;

    Button btnID,btnLicence,btnVehicle,btnRegPicture,Signup;
    CheckBox terms;
    int count=0;
    EditText password,confirmpassword;
    private String user,pass,category,fullname,phone,email,address,street,city,code,bankname,bankaccount,branchcode;
    //firebase objects
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_attachments);
        getSupportActionBar().setTitle("Registration");
        Bundle b = getIntent().getExtras();
        user = b.getString("userna");
        category = b.getString("cate");
        fullname = b.getString("fullna");
        phone = b.getString("phon");
        email = b.getString("email");
        address = b.getString("adres");
        code = b.getString("code");
        city = b.getString("city");
        street = b.getString("street");
        bankname = b.getString("bankname");
        bankaccount = b.getString("bankaccount");
        branchcode = b.getString("branchcode");

        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_USERS);

        btnID = (Button)findViewById(R.id.btnReg_id);
        btnLicence = (Button)findViewById(R.id.btnReg_licence);
        btnVehicle = (Button)findViewById(R.id.btnReg_vehicle);
        btnRegPicture = (Button)findViewById(R.id.btnReg_picture);
        Signup = (Button)findViewById(R.id.btn_signups);

        password = (EditText) findViewById(R.id.edtpasswords);
        confirmpassword = (EditText) findViewById(R.id.edtconf_passwords);

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = password.getText().toString();

                 if(terms.isChecked()==false){
                    terms.setError("Accept Terms and Conditions");
                } else if(pass.equals("")){
                     password.setError("can't be blank");
                 }else if (!pass.equalsIgnoreCase(confirmpassword.getText().toString())){
                     password.setError("password don't match");
                 }else if(pass.length()<5){
                     password.setError("at least 5 characters long");
                 }else {
                     uploadFile();
                 }

            }
        });

        terms = (CheckBox)findViewById(R.id.termsCheckbox);

        terms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                if(buttonView.isChecked()){
                    android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(SignupAttachments.this);
                    View mView = getLayoutInflater().inflate(R.layout.terms_and_conditions_layout, null);
                    mBuilder.setView(mView);
                    final android.app.AlertDialog dialog = mBuilder.create();
                    Button Done = (Button)mView.findViewById(R.id.acceptTC);
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

        file_id = (TextView)findViewById(R.id.tvfile_id);
        file_licence = (TextView)findViewById(R.id.tvfile_licence);
        file_picture = (TextView)findViewById(R.id.tvfile_picture);
        file_vehicle = (TextView)findViewById(R.id.tvfile_vehicle);

        btnID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
                count=1;
            }
        });
        btnLicence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
                count=2;
            }
        });
        btnVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
                count=3;
            }
        });

        btnRegPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
                count=4;
            }
        });
    }
    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePathid = data.getData();
            filePathlicence = data.getData();
            filePathvehicle = data.getData();
            filePathpicture = data.getData();
            if(count==1){
                file_id.setText(filePathid.toString());
            }if(count==2){
                file_licence.setText(filePathlicence.toString());
            }if(count==3){
                file_vehicle.setText(filePathvehicle.toString());
            }if(count==4){
                file_picture.setText(filePathpicture.toString());
            }

        }
    }
    public String getFileExtension(Uri uri,Uri a,Uri e,Uri i) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {
        //checking if file is available
        if (filePathid != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_USERS+ System.currentTimeMillis() + "." + getFileExtension(filePathpicture,filePathid,filePathlicence,filePathvehicle));

            //adding the file to reference
            sRef.putFile(filePathid)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();

                            //displaying success toast
                            Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),Login.class));

                            //creating the upload object to store uploaded image details

                            Reg upload = new Reg(category,fullname,phone,email,street,city,code,bankname,bankaccount,branchcode,taskSnapshot.getDownloadUrl().toString(),taskSnapshot.getDownloadUrl().toString(),taskSnapshot.getDownloadUrl().toString(),taskSnapshot.getDownloadUrl().toString(),"Accepted",password.getText().toString().trim());
                            //adding an upload to firebase database
                            String uploadId = mDatabase.push().getKey();
                            mDatabase.child(user).setValue(upload);
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
            Intent i = new Intent(SignupAttachments.this,TabActivity.class);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
