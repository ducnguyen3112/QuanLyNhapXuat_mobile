package com.example.quanlynhapxuat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.model.DeliveryDocket;
import com.example.quanlynhapxuat.model.DeliveryDocketDetail;
import com.example.quanlynhapxuat.utils.Convert;

import java.util.List;

public class ChiTietPXAdapter extends RecyclerView.Adapter<ChiTietPXAdapter.ChiTietPXViewHolder> {

    private List<DeliveryDocketDetail> list;


    public void setData(List<DeliveryDocketDetail> list ) {
        this.list = list;
    }

    @NonNull
    @Override
    public ChiTietPXViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ctpx,
                parent,false);
        return new ChiTietPXViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietPXViewHolder holder, int position) {
        DeliveryDocketDetail item= list.get(position);
        if (item==null){
            return;
        }
        holder.tv_tensppx.setText("product"+item.getId());
        holder.tv_slsppx.setText(String.valueOf(item.getQuantity()));
        holder.tv_dongiasppx.setText(Convert.currencyFormat(item.getPrice()));
        holder.tv_tonggiasppx.setText(Convert.currencyFormat(item.getPrice()*item.getQuantity()));

    }

    @Override
    public int getItemCount() {
        if (list !=null){
            return list.size();
        }
        return 0;
    }

    public class ChiTietPXViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_tensppx,tv_tonggiasppx,tv_dongiasppx,tv_slsppx;
        public ChiTietPXViewHolder(@NonNull View itemView) {
            super(itemView);
             tv_tensppx=itemView.findViewById(R.id.tv_tensppx);
             tv_tonggiasppx=itemView.findViewById(R.id.tv_tonggiasppx);
             tv_dongiasppx=itemView.findViewById(R.id.tv_dongiasppx);
             tv_slsppx=itemView.findViewById(R.id.tv_sl_sp_px);
        }

    }
}
