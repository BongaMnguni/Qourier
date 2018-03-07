package com.icourier.qourier.Notification;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.icourier.qourier.R;


/**
 * Created by EDU01 on 5/3/2017.
 */

public class noticeViewHolder extends RecyclerView.ViewHolder{
    public TextView time,message,subject,sender;
            ImageView gender,urgent;

    public  noticeViewHolder(View v){
       super(v);

        message = (TextView) v.findViewById(R.id.displayMessage);
        time = (TextView) v.findViewById(R.id.messageTime);
        subject = (TextView) v.findViewById(R.id.messagesubject);
        sender = (TextView) v.findViewById(R.id.msender);
        gender = (ImageView) v.findViewById(R.id.mssgIMGV);
        urgent = (ImageView) v.findViewById(R.id.alert);

    }

}
