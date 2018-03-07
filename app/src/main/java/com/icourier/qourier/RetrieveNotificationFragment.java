package com.icourier.qourier;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icourier.qourier.Notification.noticeItems;
import com.icourier.qourier.Notification.noticeViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RetrieveNotificationFragment extends Fragment {

    private String JSON_STRING;
    String message,time,subject,name,gender;

    LinearLayoutManager linearlayout;
    RecyclerView recyclerView;
    noticeViewAdapter noticeViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_retrieve_notification, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.listMessage);
        linearlayout = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);

        getJSON();

        return view;
    }

    private void showMessages(){

        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        List<noticeItems> arrList = new ArrayList<noticeItems>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                name = jo.getString(Config.TAG_MESSAGE_NAME);
                subject = jo.getString(Config.TAG_MESSAGE_SUBJECT);
                message = jo.getString(Config.TAG_MESSAGE_DISPLAY);
                time = jo.getString(Config.TAG_MESSAGE_TIME);
                gender = jo.getString(Config.TAG_MESSAGE_GENDER);

                arrList.add(new noticeItems(message,time,subject,name,gender));

            }
            noticeViewAdapter = new noticeViewAdapter(getActivity().getApplicationContext(),arrList);
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

                loading = new ProgressDialog(getActivity());
                loading.setMessage("Fetching Messages...");
                loading.setIndeterminate(false);
                loading.setCancelable(true);
                loading.show();

                 }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showMessages();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL_MESSAGES);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

}
