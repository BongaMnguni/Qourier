package com.icourier.qourier;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import java.util.List;

/**
 * Created by Payghost on 8/16/2017.
 */

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.PackageViewHolder> {


    private Context context, c;
    private List<Courier> uploads;

    public PackageAdapter(Context context, List<Courier> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public PackageAdapter.PackageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        c = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.packages_cardview, parent, false);

        PackageAdapter.PackageViewHolder viewHolder = new PackageAdapter.PackageViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PackageAdapter.PackageViewHolder holder, final int position) {
        final Courier courier = uploads.get(position);
        Firebase.setAndroidContext(c);

        holder.textViewName.setText(courier.getpName());
        holder.textViewStreet.setText(courier.getpStreet());
        holder.textViewCity.setText(courier.getpCity());
        holder.textViewCode.setText(courier.getpCode());
        holder.textViewAmount.setText(courier.getAmount());
        holder.textViewDescription.setText(courier.getpDescription());
        holder.textViewFullname.setText(courier.getpFullname());
        holder.textViewPhone.setText(courier.getpPhone());
        holder.textViewEmail.setText(courier.getpEmail());
        holder.textViewDate.setText(courier.getDate());


        holder.textViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(c);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage(Html.fromHtml("<font color='#d71313'>Are you sure you want to delete ?</font>"))
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                              try{
                                  Firebase ref = new Firebase("https://i-courier-18c0e.firebaseio.com/I-Courier/Packages");


                                  ref.child(courier.getKey()).child("delete").setValue("deleted");

                                  Toast.makeText(c,""+courier.getKey().toString(),Toast.LENGTH_LONG).show();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                android.support.v7.app.AlertDialog alert = builder.create();
                alert.show();



            }
        });


        Glide.with(context).load(courier.getUpUrl()).into(holder.image);
}

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class PackageViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewDate,textViewDelete,textViewName,textViewStreet,textViewCity,textViewCode,textViewAmount,textViewDescription,textViewEmail,textViewFullname,textViewPhone;
        public ImageView image;

        public PackageViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.package_name_output);
            textViewStreet = (TextView) itemView.findViewById(R.id.package_street);
            textViewCity = (TextView) itemView.findViewById(R.id.package_city);
            textViewCode = (TextView) itemView.findViewById(R.id.package_code);
            textViewAmount = (TextView) itemView.findViewById(R.id.package_amount);
            textViewDescription = (TextView) itemView.findViewById(R.id.package_description);
            textViewFullname = (TextView) itemView.findViewById(R.id.package_fullname);
            textViewPhone = (TextView) itemView.findViewById(R.id.package_phone);
            textViewEmail = (TextView) itemView.findViewById(R.id.package_email);
            textViewDelete = (TextView) itemView.findViewById(R.id.package_delete);
            textViewDate = (TextView) itemView.findViewById(R.id.package_date);
            image = (ImageView) itemView.findViewById(R.id.package_image_output);

        }
    }
}