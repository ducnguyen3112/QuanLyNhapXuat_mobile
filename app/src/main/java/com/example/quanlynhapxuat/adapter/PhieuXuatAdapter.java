package com.example.quanlynhapxuat.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.api.ApiUtils;
import com.example.quanlynhapxuat.model.DeliveryDocket;
import com.example.quanlynhapxuat.model.DeliveryDocketDetail;
import com.example.quanlynhapxuat.model.KhachHang;
import com.example.quanlynhapxuat.utils.Convert;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhieuXuatAdapter extends RecyclerView.Adapter<PhieuXuatAdapter.PXViewHolder>{


    private List<DeliveryDocket> deliveryDockets;
    private List<DeliveryDocket> deliveryDocketsOld;


    public void setData(List<DeliveryDocket> list) {
        this.deliveryDockets = list;
        notifyDataSetChanged();
    }
    public class PXViewHolder extends RecyclerView.ViewHolder{
        private TextView tvGiaPX,tvMaPX,tvTenKH,tvNgay,tvStatus;

        public PXViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGiaPX=itemView.findViewById(R.id.tv_giatripx);
            tvMaPX=itemView.findViewById(R.id.tv_mapx);
            tvTenKH=itemView.findViewById(R.id.tv_tenkhpx);
            tvNgay=itemView.findViewById(R.id.tv_ngaypx);
            tvStatus=itemView.findViewById(R.id.tv_trangthai);
        }
    }
    @NonNull
    @Override
    public PXViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_phieuxuat,parent,false);
        return new PXViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PXViewHolder holder, int position) {
        DeliveryDocket deliveryDocket=deliveryDockets.get(position);
        if (deliveryDocket==null){
            return;
        }
        int id =deliveryDocket.getId();
        holder.tvMaPX.setText(String.valueOf(id));
        holder.tvNgay.setText(deliveryDocket.getCreatedAt());
        if(deliveryDocket.getStatus()==1){
            holder.tvStatus.setText("Hoàn thành");
        }else{
            holder.tvStatus.setText("Chưa hoàn thành");
        }
       holder.tvTenKH.setText(String.valueOf(deliveryDocket.getId()));
        holder.tvNgay.setText("31/12/2000 31:12");
        int tong=0;
        if (deliveryDockets.get(position).getDeliveryDocketDetails()!=null){
            for (DeliveryDocketDetail item: deliveryDockets.get(position).getDeliveryDocketDetails()) {
                tong+=item.getPrice();
            }
            holder.tvGiaPX.setText(String.valueOf(tong));
        }

    }

    @Override
    public int getItemCount() {
        if (deliveryDockets !=null){
            return deliveryDockets.size();
        }
        return 0;
    }

}
