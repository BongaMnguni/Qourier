package com.icourier.qourier;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SendNotification extends AppCompatActivity {

    RequestQueue requestQueue;
    EditText namez,subject,message;
    Spinner gender;
    private String Mgender;
    Button SendMessage;
    String insertUrlMessage = "http://mydm.co.za/Icourier/InsertMessage.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_send_notification);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        namez = (EditText) findViewById(R.id.notname);
        subject = (EditText) findViewById(R.id.notsubject);
        message = (EditText) findViewById(R.id.notmessage);

        gender = (Spinner) findViewById(R.id.notgender);

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Mgender = gender.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        SendMessage = (Button)findViewById(R.id.notbtncont);
        SendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    public void sendMessage(){

        if(!message.getText().toString().isEmpty() || !namez.getText().toString().isEmpty() || !subject.getText().toString().isEmpty()){
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

                    parameters.put("name", namez.getText().toString());
                    parameters.put("subject", subject.getText().toString());
                    parameters.put("message", message.getText().toString());
                    parameters.put("gender", Mgender);

                    return parameters;
                }
            };
            requestQueue.add(request);
            Toast.makeText(getApplicationContext(), " message sent...", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(), "All Fields are Required...", Toast.LENGTH_LONG).show();
        }

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
                            startActivity(new Intent(getApplicationContext(),Login.class));
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
