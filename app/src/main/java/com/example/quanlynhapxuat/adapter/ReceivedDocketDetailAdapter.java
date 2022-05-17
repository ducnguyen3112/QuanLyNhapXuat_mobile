package com.example.quanlynhapxuat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.model.ReceivedDocketDetail;

import java.util.ArrayList;

public class ReceivedDocketDetailAdapter extends RecyclerView.Adapter<ReceivedDocketDetailAdapter.RDDViewHolder> {
    private Context context;
    private ArrayList<ReceivedDocketDetail> rddList;

    public ReceivedDocketDetailAdapter(Context context) {
        this.context = context;
    }

    public void setRddList(ArrayList<ReceivedDocketDetail> rddList) {
        this.rddList = rddList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RDDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_received_docket_detail,parent,false);
        return new ReceivedDocketDetailAdapter.RDDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RDDViewHolder holder, int position) {
        ReceivedDocketDetail rdd = rddList.get(position);
        if(rdd==null) {
            return;
        }

        holder.tvTenSP.setText("BASAS BUMPER GUM (SIZE 38)");
        holder.tvSL.setText("16");
        holder.tvDonGia.setText("1.200.000");

        holder.ibEditQuantity.setOnClickListener(view -> {
            //TODO -- sửa số lượng
        });

        holder.ibDelete.setOnClickListener(view -> {
            //TODO -- xóa sản phẩm
        });
    }

    @Override
    public int getItemCount() {
        return rddList==null ? 0 : rddList.size();
    }

    public class RDDViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivAnhSP;
        private TextView tvTenSP;
        private TextView tvSL;
        private TextView tvDonGia;
        private ImageButton ibEditQuantity;
        private ImageButton ibDelete;

        public RDDViewHolder(@NonNull View itemView) {
            super(itemView);

            ivAnhSP = itemView.findViewById(R.id.ivAnhSP_itemRDD);
            tvTenSP = itemView.findViewById(R.id.tvTenSP_itemRDD);
            tvSL = itemView.findViewById(R.id.tvSL_itemRDD);
            tvDonGia = itemView.findViewById(R.id.tvDonGia_itemRDD);
            ibEditQuantity = itemView.findViewById(R.id.ibEditQuantity_itemRDD);
            ibDelete = itemView.findViewById(R.id.ibDelete_itemRDD);
        }
    }

    public int getTotalList() {
        int sum = 0;
        if(rddList!=null) {
            for(ReceivedDocketDetail rdd : rddList) {
                sum += rdd.getQuantity()*rdd.getPrice();
            }
        }
        return sum;
    }
}