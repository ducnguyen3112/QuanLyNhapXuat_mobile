package com.example.quanlynhapxuat.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhapxuat.R;

import com.example.quanlynhapxuat.activity.KhachHang.ProfileKHActivity;
import com.example.quanlynhapxuat.model.KhachHang;

import java.util.List;


public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.KhachHangViewHolder> {

    private Activity activity;
    private Context context;
    private List<KhachHang> list;

    public KhachHangAdapter(Activity activity, Context context, List<KhachHang> list) {
        this.activity = activity;
        this.context = context;
        this.list = list;


    }

    @NonNull
    @Override
    public KhachHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kh, parent, false);
        return new KhachHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhachHangViewHolder holder, int position) {
        KhachHang kh = list.get(position);
        if (kh == null) {
            return;
        }


        holder.tv_tenKH.setText(kh.getName());
        holder.tv_tongKH.setText(kh.getAddress());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileKHActivity.class);
                intent.putExtra("KH", kh);
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class KhachHangViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_tenKH, tv_tongKH;
        private ImageView iv_KH;
        private CardView mainLayout;

        public KhachHangViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_KH = (ImageView) itemView.findViewById(R.id.iv_KH);
            tv_tenKH = itemView.findViewById(R.id.tv_tenKH);
            tv_tongKH = itemView.findViewById(R.id.tv_tongKH);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
