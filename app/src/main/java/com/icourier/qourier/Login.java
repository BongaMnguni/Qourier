package com.icourier.qourier;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    EditText username, password;
    Button loginButton;
    String user, pass;
    TextView signup;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedpreferences = getSharedPreferences("Packages", Context.MODE_PRIVATE);

        signup = (TextView) findViewById(R.id.signup);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.signin);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Registration.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                pass = password.getText().toString();


                if(user.equals("")){
                    username.setError("can't be blank");
                }
                else if(pass.equals("")){
                    password.setError("can't be blank");
                }
                else  if(user.equalsIgnoreCase("admin@icourier") && pass.equalsIgnoreCase("adminjdpvs$#")){
                    startActivity(new Intent(getApplicationContext(),SendNotification.class));
                }
                else{

                    String url = "https://i-courier-18c0e.firebaseio.com/users.json";
                    final ProgressDialog pd = new ProgressDialog(Login.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            if(s.equals("null")){
                                Toast.makeText(Login.this, "user not found", Toast.LENGTH_LONG).show();
                            }
                            else{
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if(!obj.has(user)){
                                        Toast.makeText(Login.this, "user not found", Toast.LENGTH_LONG).show();
                                    }
                                    else if(obj.getJSONObject(user).getString("password").equals(pass)   ){
                                        SharedPreferences.Editor editor = sharedpreferences.edit();

                                        String categoty = obj.getJSONObject(user).getString("category");
                                        String fullname = obj.getJSONObject(user).getString("fullname");
                                        String phone = obj.getJSONObject(user).getString("phone");
                                        String email = obj.getJSONObject(user).getString("email");
                                        String street = obj.getJSONObject(user).getString("street");
                                        String city = obj.getJSONObject(user).getString("city");
                                        String code = obj.getJSONObject(user).getString("code");



                                       if(categoty.equals("Service")){
                                            Intent i = new Intent(Login.this,MakeDelivery.class);

                                           String bnam = obj.getJSONObject(user).getString("bankname");
                                           String baccount = obj.getJSONObject(user).getString("bankaccount");
                                           String bbranch = obj.getJSONObject(user).getString("branchcode");

                                            editor.putString("username",user);
                                            editor.putString("fullname",fullname);
                                            editor.putString("phone",phone);
                                            editor.putString("email",email);
                                            editor.putString("street",street);
                                            editor.putString("city",city);
                                            editor.putString("code",code);
                                           editor.putString("bankname",bnam);
                                           editor.putString("bankaccount",baccount);
                                           editor.putString("branch",bbranch);
                                            editor.commit();

                                            i.putExtra("fullname",fullname);
                                            i.putExtra("username",user);
                                            startActivity(i);
                                            finish();

                                        }else{
                                                Intent i = new Intent(Login.this,MainActivity.class);
                                                i.putExtra("fullname",fullname);
                                                i.putExtra("username",user);

                                                i.putExtra("street",street);
                                                i.putExtra("city",city);
                                                i.putExtra("code",code);
                                                i.putExtra("phone",phone);
                                                i.putExtra("email",email);

                                                startActivity(i);
                                                finish();
                                            }
                                    }
                                    else {
                                        Toast.makeText(Login.this, "incorrect password", Toast.LENGTH_LONG).show();
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
                            System.out.println("" + volleyError);
                            pd.dismiss();
                        }
                    });
                    RequestQueue rQueue = Volley.newRequestQueue(Login.this);
                    rQueue.add(request);
                }

            }
        });
    }


}
