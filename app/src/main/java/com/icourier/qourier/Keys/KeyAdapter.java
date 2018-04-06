package com.icourier.qourier.Keys;

/**
 * Created by Payghost on 2/8/2018.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.icourier.qourier.Config;
import com.icourier.qourier.Constants;
import com.icourier.qourier.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class KeyAdapter extends RecyclerView.Adapter<KeyAdapter.ViewHolder>{

    public List<KeyItems> list ;
    private Context context;
    RequestQueue requestQueue;


    public KeyAdapter(Context context, List<KeyItems> list) {
        this.list = list;
        this.context = context;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.key_cardview_layout,null);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        holder.textViewDate.setText(list.get(position).getTime());
        holder.textViewKey.setText(list.get(position).getKeys());
        holder.textViewName.setText(list.get(position).getName());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage(Html.fromHtml("<font color='#1b5e20'>Proceed with deleting ?</font>"))
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                sendValues("keyTable",list.get(position).getId());


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        Animation upAnim = AnimationUtils.loadAnimation(context,R.anim.translate);
        upAnim.reset();
        holder.itemView.clearAnimation();
        holder.itemView.setAnimation(upAnim);

    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    ///////////////// view Holder  ///////////////////////////


    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewDate;
        public TextView textViewKey;

        public ImageView delete;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.kname);
            textViewDate = (TextView) itemView.findViewById(R.id.keyTime);
            textViewKey = (TextView) itemView.findViewById(R.id.tvkeys);
            delete = (ImageView)itemView.findViewById(R.id.imgtrash);
        }
    }

    public void sendValues(final String table, final String id){

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_DELETE, new Response.Listener<String>() {
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

                parameters.put("table", table);
                parameters.put("id", id);

                return parameters;
            }
        };
        requestQueue.add(request);
        Toast.makeText(context, " deleted...", Toast.LENGTH_LONG).show();
    }
}
