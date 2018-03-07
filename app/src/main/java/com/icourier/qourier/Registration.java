package com.icourier.qourier;

import android.app.ProgressDialog;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

public class Registration extends AppCompatActivity {

    Spinner category;
    LinearLayout userLayout,serviceproviderLayout;
    Button next,bank,S_Adress,U_Address,UserSignup;
    private String Bank_Name,Bank_Account,Branch_Code,Address_Street,Address_City,Address_Code,user,pass;
    CheckBox terms;
    EditText u_username,u_fullname,u_phone,u_email,u_password,u_confirmpass;
    EditText s_username,s_fullname,s_phone,s_email;

    private String cate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setTitle("Registration");
        Firebase.setAndroidContext(this);
        //////////////////////////////////////////////    user    ////////////////////////////////////////////////////

        u_username = (EditText)findViewById(R.id.edtusername);
        u_fullname = (EditText)findViewById(R.id.edtfullname);
        u_phone = (EditText)findViewById(R.id.edtphone);
        u_email = (EditText)findViewById(R.id.edtemail);
        u_password = (EditText)findViewById(R.id.edtpassword);
        u_confirmpass = (EditText)findViewById(R.id.edtconf_password);

        //////////////////////////////////////////////    user    ////////////////////////////////////////////////////
         //////////////////////////////////////////////    Service provider    ////////////////////////////////////////////////////

        s_username = (EditText)findViewById(R.id.edtServiceusername);
        s_fullname = (EditText)findViewById(R.id.edtServicefullname);
        s_phone = (EditText)findViewById(R.id.edtServicephone);
        s_email = (EditText)findViewById(R.id.edtServiceemail);

                //////////////////////////////////////////////    service provider    ////////////////////////////////////////////////////

        category = (Spinner)findViewById(R.id.spinner_category);
        userLayout = (LinearLayout)findViewById(R.id.userSignup);
        serviceproviderLayout = (LinearLayout)findViewById(R.id.ServiceProviderSignup);
        next = (Button)findViewById(R.id.btn_next);
        UserSignup = (Button)findViewById(R.id.btn_signup);

        bank = (Button) findViewById(R.id.btn_serviceBank);
        S_Adress = (Button) findViewById(R.id.btn_Addressservice);
        U_Address = (Button) findViewById(R.id.btnaddress);

        S_Adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(Registration.this);
                View mView = getLayoutInflater().inflate(R.layout.address_layout, null);
                mBuilder.setView(mView);
                final android.app.AlertDialog dialog = mBuilder.create();
                final EditText Street = (EditText)mView.findViewById(R.id.address_street);
                final EditText City = (EditText)mView.findViewById(R.id.address_city);
                final EditText Code = (EditText)mView.findViewById(R.id.address_code);
                Button Done = (Button)mView.findViewById(R.id.AddressDone);

                Done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Address_Street = Street.getText().toString().trim();
                        Address_City = City.getText().toString().trim();
                        Address_Code = Code.getText().toString().trim();
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

        U_Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(Registration.this);
                View mView = getLayoutInflater().inflate(R.layout.address_layout, null);
                mBuilder.setView(mView);
                final android.app.AlertDialog dialog = mBuilder.create();
                final EditText Street = (EditText)mView.findViewById(R.id.address_street);
                final EditText City = (EditText)mView.findViewById(R.id.address_city);
                final EditText Code = (EditText)mView.findViewById(R.id.address_code);
                Button Done = (Button)mView.findViewById(R.id.AddressDone);

                Done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Address_Street = Street.getText().toString().trim();
                        Address_City = City.getText().toString().trim();
                        Address_Code = Code.getText().toString().trim();
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(Registration.this);
                View mView = getLayoutInflater().inflate(R.layout.bank_layout, null);
                mBuilder.setView(mView);
                final android.app.AlertDialog dialog = mBuilder.create();
                final EditText BankName = (EditText)mView.findViewById(R.id.bank_name);
                final EditText BankAccount = (EditText)mView.findViewById(R.id.bank_account);
                final EditText BankCode = (EditText)mView.findViewById(R.id.bank_code);
                Button Done = (Button)mView.findViewById(R.id.btnDone);

                Done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Bank_Name = BankName.getText().toString().trim();
                        Bank_Account = BankAccount.getText().toString().trim();
                        Branch_Code = BankCode.getText().toString().trim();
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("User"))
                {
                    userLayout.setVisibility(View.VISIBLE);
                    serviceproviderLayout.setVisibility(View.GONE);
                    next.setVisibility(View.GONE);
                }else
                    if(selectedItem.equals("User and Service Provider"))
                    {
                        serviceproviderLayout.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        userLayout.setVisibility(View.GONE);
                    }

            }
            public void onNothingSelected(AdapterView<?> parent)
        {

        }
        });

        terms = (CheckBox)findViewById(R.id.termsCheckboxs);

        terms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                if(buttonView.isChecked()){
                    android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(Registration.this);
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

        UserSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = u_username.getText().toString();
                pass = u_password.getText().toString();

                if(user.equals("")){
                    u_username.setError("can't be blank");
                }
                else if(pass.equals("")){
                    u_password.setError("can't be blank");
                }else if (!pass.equalsIgnoreCase(u_confirmpass.getText().toString())){
                    u_password.setError("password don't match");
                }
                else if(!user.matches("[A-Za-z0-9]+")){
                    u_username.setError("only alphabet or number allowed");
                }
                else if(user.length()<5){
                    u_username.setError("at least 5 characters long");
                }
                else if(pass.length()<5){
                    u_password.setError("at least 5 characters long");
                }

                else if(terms.isChecked()==false){
                    terms.setError("Accept Terms and Conditions");
                }
                else {
                    final ProgressDialog pd = new ProgressDialog(Registration.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    String url = "https://i-courier-18c0e.firebaseio.com/users.json";

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            Firebase reference = new Firebase("https://i-courier-18c0e.firebaseio.com/users");

                            if(s.equals("null")) {
                                if(category.getSelectedItem().toString().equals("User")){
                                    reference.child(user).child("category").setValue("User");
                                }else
                                if(category.getSelectedItem().toString().equals("User and Service Provider")){
                                    reference.child(user).child("category").setValue("Service");
                                }
                                reference.child(user).child("fullname").setValue(u_fullname.getText().toString().trim());
                                reference.child(user).child("phone").setValue(u_phone.getText().toString().trim());
                                reference.child(user).child("email").setValue(u_email.getText().toString().trim());
                                reference.child(user).child("street").setValue(Address_Street);
                                reference.child(user).child("city").setValue(Address_City);
                                reference.child(user).child("code").setValue(Address_Code);
                                reference.child(user).child("T&C").setValue("Accepted");
                                reference.child(user).child("password").setValue(pass);

                                Toast.makeText(Registration.this, "Registration Successful!", Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(Registration.this,Login.class));

                            }
                            else {
                                try {
                                    JSONObject obj = new JSONObject(s);

                                        if (!obj.has(user)) {
                                        if(category.getSelectedItem().toString().equals("User")){
                                            reference.child(user).child("category").setValue("User");
                                        }else
                                            if(category.getSelectedItem().toString().equals("User and Service Provider")){
                                                reference.child(user).child("category").setValue("Service");
                                        }
                                        reference.child(user).child("fullname").setValue(u_fullname.getText().toString().trim());
                                        reference.child(user).child("phone").setValue(u_phone.getText().toString().trim());
                                        reference.child(user).child("email").setValue(u_email.getText().toString().trim());
                                        reference.child(user).child("street").setValue(Address_Street);
                                        reference.child(user).child("city").setValue(Address_City);
                                        reference.child(user).child("code").setValue(Address_Code);
                                        reference.child(user).child("terms").setValue("Accepted");
                                        reference.child(user).child("password").setValue(pass);

                                        Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_LONG).show();

                                            startActivity(new Intent(Registration.this,Login.class));

                                    } else {
                                        Toast.makeText(Registration.this, "username already exists", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            pd.dismiss();
                        }

                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError );
                            pd.dismiss();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(Registration.this);
                    rQueue.add(request);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(category.getSelectedItem().toString().equals("User")){
                   cate = "User";
                }else
                if(category.getSelectedItem().toString().equals("User and Service Provider")){
                    cate = "Service";
                }

                final String userna = s_username.getText().toString().trim();
                final String fullna = s_fullname.getText().toString().trim();
                final String phon = s_phone.getText().toString().trim();
                final String email = s_email.getText().toString().trim();
                final String code = Address_Code;
                final String city = Address_City;
                final String street = Address_Street;
                final String bankname = Bank_Name;
                final String bankaccount = Bank_Account;
                final String branchcode = Branch_Code;

                //Toast.makeText(getApplicationContext(),cate+"\n"+userna+"\n"+fullna+"\n"+phon+"\n"+email+"\n"+adres+"\n"+bank,Toast.LENGTH_LONG).show();

                user = s_username.getText().toString();

                if(user.equals("")){
                    s_username.setError("can't be blank");
                }
                else if(!user.matches("[A-Za-z0-9]+")){
                    s_username.setError("only alphabet or number allowed");
                }
                else if(user.length()<5){
                    s_username.setError("at least 5 characters long");
                } else {

                    String url = "https://i-courier-18c0e.firebaseio.com/users.json";

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if (!obj.has(user)) {

                                        Intent i = new Intent(Registration.this,SignupAttachments.class);
                                        i.putExtra("cate",cate);
                                        i.putExtra("userna",userna);
                                        i.putExtra("fullna",fullna);
                                        i.putExtra("phon",phon);
                                        i.putExtra("email",email);
                                        i.putExtra("street",street);
                                        i.putExtra("city",city);
                                        i.putExtra("code",code);
                                        i.putExtra("bankname",bankname);
                                        i.putExtra("bankaccount",bankaccount);
                                        i.putExtra("branchcode",branchcode);
                                        startActivity(i);

                                    } else {
                                        Toast.makeText(Registration.this, "username already exists", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                        }

                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError );

                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(Registration.this);
                    rQueue.add(request);

                }

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
            Intent i = new Intent(Registration.this,Login.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
