package com.icourier.qourier.Notification;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.icourier.qourier.R;

import java.util.List;

public class noticeViewAdapter extends RecyclerView.Adapter<noticeViewHolder>{

   public List<noticeItems> list ;
    private Context context;


    public noticeViewAdapter(Context context, List<noticeItems> list) {
        this.list = list;
        this.context = context;

    }
    @Override
    public noticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_messages,null);
        noticeViewHolder viewHolder = new noticeViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final noticeViewHolder holder, int position) {
        holder.time.setText(list.get(position).getTime());
        holder.message.setText(list.get(position).getMessage());
        holder.subject.setText(list.get(position).getSubject());
        holder.sender.setText(list.get(position).getSender());


        if(list.get(position).getGender().toString().equals("Male"))
        {
            holder.gender.setBackgroundResource(R.drawable.malesender);
        }else{
            holder.gender.setBackgroundResource(R.drawable.femalesender);
        }


        holder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.message.setSingleLine(false);
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
}
