package com.example.quanlynhapxuat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.model.ReceivedDocket;
import com.example.quanlynhapxuat.model.ReceivedDocketDetail;

import java.util.ArrayList;

public class ReceivedDocketAdapter extends RecyclerView.Adapter<ReceivedDocketAdapter.ReceivedDocketViewHolder> {
    private Context context;
    private ArrayList<ReceivedDocket> receivedDocketList;

    public ReceivedDocketAdapter(Context context, ArrayList<ReceivedDocket> receivedDocketList) {
        this.context = context;
        this.receivedDocketList = receivedDocketList;
    }

    @NonNull
    @Override
    public ReceivedDocketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_received_docket,parent,false);
        return new ReceivedDocketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceivedDocketViewHolder holder, int position) {
        ReceivedDocket receivedDocket = receivedDocketList.get(position);
        if(receivedDocket==null) {
            return;
        }

        holder.tvID.setText("PN"+receivedDocket.getId());
        holder.tvCreatedAt.setText(receivedDocket.getCreatedAt());
        holder.tvSupplierName.setText(receivedDocket.getSupplier_name());
        holder.tvTotalDocket.setText(getTotalDocket(receivedDocket.receivedDocketDetails)+"");
    }

    @Override
    public int getItemCount() {
        if(receivedDocketList==null) {
            return 0;
        }
        return receivedDocketList.size();
    }

    public class ReceivedDocketViewHolder extends RecyclerView.ViewHolder {
        private TextView tvID;
        private TextView tvCreatedAt;
        private TextView tvSupplierName;
        private TextView tvTotalDocket;

        public ReceivedDocketViewHolder(@NonNull View itemView) {
            super(itemView);

            tvID = itemView.findViewById(R.id.tvID_itemReceivedDocket);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt_itemReceivedDocket);
            tvSupplierName = itemView.findViewById(R.id.tvSupplierName_itemReceivedDocket);
            tvTotalDocket = itemView.findViewById(R.id.tvTotalDocket_itemReceivedDocket);
        }
    }

    private int getTotalDocket(ArrayList<ReceivedDocketDetail> receivedDocketDetailList) {
        if(receivedDocketDetailList==null) {
            return 0;
        }
        int sum = 0;
        for(ReceivedDocketDetail receivedDocketDetail : receivedDocketDetailList) {
            sum += receivedDocketDetail.getPrice()*receivedDocketDetail.getQuantity();
        }
        return sum;
    }

    public int getTotalList() {
        int sum = 0;
        if(receivedDocketList!=null) {
            for(ReceivedDocket receivedDocket : receivedDocketList) {
                sum += getTotalDocket(receivedDocket.receivedDocketDetails);
            }
        }
        return sum;
    }
}
