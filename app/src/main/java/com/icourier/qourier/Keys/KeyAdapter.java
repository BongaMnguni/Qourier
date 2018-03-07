package com.icourier.qourier.Keys;

/**
 * Created by Payghost on 2/8/2018.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.icourier.qourier.R;

import java.util.List;


public class KeyAdapter extends RecyclerView.Adapter<KeyAdapter.ViewHolder>{

    public List<KeyItems> list ;
    private Context context;


    public KeyAdapter(Context context, List<KeyItems> list) {
        this.list = list;
        this.context = context;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.key_cardview_layout,null);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textViewDate.setText(list.get(position).getTime());
        holder.textViewKey.setText(list.get(position).getKeys());
        holder.textViewName.setText(list.get(position).getName());

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

        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.kname);
            textViewDate = (TextView) itemView.findViewById(R.id.keyTime);
            textViewKey = (TextView) itemView.findViewById(R.id.tvkeys);

        }
    }
}
