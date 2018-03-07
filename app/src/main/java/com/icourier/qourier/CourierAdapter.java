package com.icourier.qourier;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourierAdapter extends RecyclerView.Adapter<CourierAdapter.ViewHolder> {

    String testin ,amount,date,fulnam,phne,bankname,bankaccount,branch;
    private Context context, c;
    RequestQueue requestQueue;
    String insertUrlMessage = "http://mydm.co.za/Icourier/InsertKey.php";
    private List<Courier> uploads;

    SharedPreferences prefs ;

    public CourierAdapter(Context context, List<Courier> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        c = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.courier_cardview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Courier courier = uploads.get(position);
        Firebase.setAndroidContext(c);

        requestQueue = Volley.newRequestQueue(c);

        prefs = c.getSharedPreferences("Packages",Context.MODE_PRIVATE);

        holder.textViewName.setText(courier.getName());
        holder.textViewDate.setText(courier.getDate());
        holder.textViewAmount.setText("R"+courier.getAmount());
        holder.textViewAddresses.setText(courier.getCity()+"\tTo\t"+courier.getDetination());
        Glide.with(context).load(courier.getUrl()).into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  fulnam = prefs.getString("fullname", null);
                   phne = prefs.getString("phone", null);
                  bankname = prefs.getString("bankname", null);
                  bankaccount = prefs.getString("bankaccount", null);
                  branch = prefs.getString("branch", null);

                final Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_package_details);
                dialog.setCancelable(false);
/////
                final Dialog dialog1 = new Dialog(view.getContext());
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setContentView(R.layout.action_layout);
                dialog1.setCancelable(false);

                ImageView Dcall = (ImageView) dialog1.findViewById(R.id.dcall);
                ImageView Dmessage = (ImageView) dialog1.findViewById(R.id.dmessage);
                ImageView Demail = (ImageView) dialog1.findViewById(R.id.demail);
                ImageView close = (ImageView) dialog1.findViewById(R.id.cancel);
                //////
                ImageView DCancel = (ImageView) dialog.findViewById(R.id.dcancel);
                ImageView DApprove = (ImageView) dialog.findViewById(R.id.dapprove);

                ImageView imageb = (ImageView) dialog.findViewById(R.id.dImage);
                TextView nam = (TextView) dialog.findViewById(R.id.dname);

                TextView street = (TextView) dialog.findViewById(R.id.dstreet);
                TextView city = (TextView) dialog.findViewById(R.id.dcity);
                TextView code = (TextView) dialog.findViewById(R.id.dcode);

                TextView phon = (TextView) dialog.findViewById(R.id.dphone);
                final TextView pNam = (TextView) dialog.findViewById(R.id.dpname);
                TextView pDescription = (TextView) dialog.findViewById(R.id.dpDescription);
                TextView amountBid = (TextView) dialog.findViewById(R.id.dAmount);
                TextView dateS = (TextView) dialog.findViewById(R.id.dDate);

                testin =courier.getName().toString();
                amount = "R" + courier.getAmount();
                date = courier.getDate();
                nam.setText(testin);
                street.setText(courier.getStreet());
                city.setText(courier.getCity());
                code.setText(courier.getCode());
                phon.setText(courier.getPhone());
                pNam.setText(courier.getpName());
                pDescription.setText(courier.getDescription());
                amountBid.setText(amount);
                dateS.setText(date);

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });

                DCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                Dcall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+courier.getPhone()));
                        c.startActivity(intent);
                        dialog1.dismiss();
                        dialog.dismiss();

                    }
                });

                Dmessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",courier.getPhone().toString(), null)));
                        dialog1.dismiss();
                        dialog.dismiss();
                    }
                });

                Demail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",courier.getEmail().toString(), null));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Accept Delivery");
                        c.startActivity(Intent.createChooser(emailIntent, "Send email..."));
                        dialog1.dismiss();
                        dialog.dismiss();

                    }
                });

                final String username = prefs.getString("username", null);

                DApprove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Firebase reference = new Firebase("https://i-courier-18c0e.firebaseio.com/");
                        String key = reference.child("I-Courier").child("Packages").push().getKey();
                        String uniqueID = courier.getUsername()+"*"+username;

                        Courier pack = new Courier(courier.getPuniqkey(),date,"false",amount,courier.getpName(),courier.getDescription(), courier.getName(), courier.getPhone(),courier.getStreet(),courier.getCity(),courier.getCode(), courier.getEmail(), courier.getAccount(),uniqueID.toString().trim() ,courier.getUrl(),fulnam,phne,bankname, bankaccount,branch);


                        reference.child("I-Courier").child("Packages").child(key).setValue(pack);

                        dialog1.show();

                        Firebase ref = new Firebase("https://i-courier-18c0e.firebaseio.com/I-Courier/Poster");
                        ref.child(courier.getKey().toString().trim()).child("check").setValue("delivered");

                        try {

                            sendKey(username,courier.getPuniqkey(),courier.getUsername());


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                Glide.with(context).load(courier.getUrl()).into(imageb);
                dialog.show();
            }
        });

    }
    @Override
    public int getItemCount() {
        return uploads.size();
    }

class ViewHolder extends RecyclerView.ViewHolder {

    public TextView textViewName;
    public TextView textViewDate;
    public TextView textViewAmount;
    public TextView textViewAddresses;

    public ImageView image;

    public ViewHolder(View itemView) {
        super(itemView);

        textViewName = (TextView) itemView.findViewById(R.id.courier_name_output);
        textViewDate = (TextView) itemView.findViewById(R.id.courier_date_output);
        textViewAmount = (TextView) itemView.findViewById(R.id.courier_amount_output);
        textViewAddresses = (TextView) itemView.findViewById(R.id.courier_addresses_output);
        image = (ImageView) itemView.findViewById(R.id.courier_image_output);

        }
    }
    public void sendKey(final String name, final String mykey, final String user){

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

                    parameters.put("name", name);
                    parameters.put("mykey", mykey);
                    parameters.put("username", user);


                    return parameters;
                }
            };
            requestQueue.add(request);
           // Toast.makeText(c, " Key sent...", Toast.LENGTH_LONG).show();


    }

}










