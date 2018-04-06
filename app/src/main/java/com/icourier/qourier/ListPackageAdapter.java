package com.icourier.qourier;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class ListPackageAdapter extends RecyclerView.Adapter<ListPackageAdapter.ViewHolder> {

    String name ,amount,date,packagename,destination,details,picture,packkey;
    private Context context, c;
    private List<Courier> uploads;

    SharedPreferences prefs ;

    public ListPackageAdapter(Context context, List<Courier> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        c = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_package_cardview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @SuppressLint("Range")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Courier courier = uploads.get(position);
        Firebase.setAndroidContext(c);

        prefs = c.getSharedPreferences("Packages",Context.MODE_PRIVATE);

       holder.layout.getBackground().setAlpha(100);

        holder.textViewName.setText(courier.getpName());
        holder.textViewDate.setText(courier.getDate());
        holder.textViewAmount.setText("R"+courier.getAmount());
       // holder.textViewAddresses.setText(courier.getCity()+"   To   "+courier.getDetination());
        Glide.with(context).load(courier.getUrl()).into(holder.image);

        holder.layoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = courier.getName();
                packagename =courier.getName().toString();
                amount = courier.getAmount();
                date = courier.getDate();
                destination = courier.getDetination();
                details = courier.getDescription();
                picture = courier.getUrl();
                packkey = courier.getKey();

                Intent preview = new Intent(c,PackageEditor.class);

                preview.putExtra("name",name);
                preview.putExtra("packagename",packagename);
                preview.putExtra("amount",amount);
                preview.putExtra("date",date);
                preview.putExtra("destination",destination);
                preview.putExtra("details",details);
                preview.putExtra("image",picture);
                preview.putExtra("packkey",packkey);

                c.startActivity(preview);

            }
        });

        holder.layoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage(Html.fromHtml("<font color='#627984'>Are you sure you want to logout?</font>"))
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Firebase ref = new Firebase("https://i-courier-18c0e.firebaseio.com/I-Courier/Poster");
                                ref.child(courier.getKey()).child("delete").setValue("deleted");

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
    public LinearLayout layoutDelete;
    public LinearLayout layoutEdit;
    RelativeLayout layout;

    public ImageView image;

    public ViewHolder(View itemView) {
        super(itemView);

        layoutDelete = (LinearLayout) itemView.findViewById(R.id.layout_delete);
        layoutEdit = (LinearLayout) itemView.findViewById(R.id.layout_edit);
        textViewName = (TextView) itemView.findViewById(R.id.courier_name_output);
        textViewDate = (TextView) itemView.findViewById(R.id.courier_date_output);
        textViewAmount = (TextView) itemView.findViewById(R.id.courier_amount_output);
        textViewAddresses = (TextView) itemView.findViewById(R.id.courier_addresses_output);
        image = (ImageView) itemView.findViewById(R.id.courier_image_output);
        layout = (RelativeLayout) itemView.findViewById(R.id.trasp_layout);

        }
    }

}










