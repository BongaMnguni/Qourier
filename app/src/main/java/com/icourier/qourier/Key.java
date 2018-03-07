package com.icourier.qourier;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.icourier.qourier.Keys.KeyAdapter;
import com.icourier.qourier.Keys.KeyItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Key extends AppCompatActivity {

    private String JSON_STRING;
    String username,time,name,keys;
    String user ;

    LinearLayoutManager linearlayout;
    RecyclerView recyclerView;
    KeyAdapter noticeViewAdapter;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key);
        getSupportActionBar().setTitle("Key");

        textView = (TextView) findViewById(R.id.texts);

        Bundle b = getIntent().getExtras();
        user =  b.getString("username");

        recyclerView = (RecyclerView)findViewById(R.id.listkeys);
        linearlayout = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);

        getJSON();
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
           // startActivity(new Intent(Key.this,MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void showMessages(){

        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        List<KeyItems> arrList = new ArrayList<KeyItems>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                name = jo.getString(Config.TAG_KEY_NAME);
                username = jo.getString(Config.TAG_KEY_USERNAME);
                keys = jo.getString(Config.TAG_KEY_KEYS);
                time = jo.getString(Config.TAG_KEY_TIME);

                if(username.equalsIgnoreCase(user)) {
                    arrList.add(new KeyItems(keys, name, username, time));
                }

            }
            noticeViewAdapter = new KeyAdapter(getApplicationContext(),arrList);
            recyclerView.setAdapter(noticeViewAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Key.this,"Fetching Messages"," Please Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                textView.setVisibility(View.VISIBLE);
                JSON_STRING = s;
                showMessages();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL_KEYS);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
