package com.example.quanlynhapxuat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.model.KhachHang;
import com.example.quanlynhapxuat.model.Product;

import java.util.List;

public class SPSpinnerAdapter extends BaseAdapter {
    private Context context;
    public List<Product> products;

    public SPSpinnerAdapter(Context context,List<Product> products){
        this.context=context;
        this.products=products;
    }
    @Override
    public int getCount() {
        return products!=null ? products.size():0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product=products.get(position);
        View rootView  = LayoutInflater.from(context).inflate(R.layout.item_khachhang_spinner,
                parent,false);
        TextView tvTen=rootView.findViewById(R.id.tv_TenKHSpinner);
        ImageView ivAvatar=rootView.findViewById(R.id.iv_KHSpinner);

        tvTen.setText( product.getName());
        if(products.get(position).getImage()!=null){
            Glide.with(context).load(product.getImage()).into(ivAvatar);
        }else {
            ivAvatar.setImageResource(R.drawable.ic_shoes_svgrepo_com);
        }
        return rootView;
    }
}
