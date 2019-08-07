package com.nikvay.saipoojadoctor.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikvay.saipoojadoctor.R;
import com.nikvay.saipoojadoctor.model.DrawerItem;

import java.util.ArrayList;

public class DrawerItemAdapter extends RecyclerView.Adapter<DrawerItemAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<DrawerItem> drawerItemArrayList;
    public DrawerItemAdapter(Context mContext, ArrayList<DrawerItem> drawerItemArrayList) {
        this.mContext=mContext;
        this.drawerItemArrayList=drawerItemArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_drawer_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DrawerItem drawerItem=drawerItemArrayList.get(position);
        holder.img_icon.setImageResource(drawerItem.getLogoName());
        holder.txt_name.setText(drawerItem.getCategoryType());

    }

    @Override
    public int getItemCount() {
        return drawerItemArrayList==null?0:drawerItemArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private  ImageView img_icon;
        private TextView txt_name;
        public MyViewHolder(View itemView) {
            super(itemView);
            img_icon=itemView.findViewById(R.id.img_icon);
            txt_name=itemView.findViewById(R.id.txt_name);

        }
    }
}
