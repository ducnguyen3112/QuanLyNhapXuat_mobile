package com.example.quanlynhapxuat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.activity.EmployeesActivity;
import com.example.quanlynhapxuat.activity.KhachHang.ListKHActivity;
import com.example.quanlynhapxuat.activity.main.LoginActivity;
import com.example.quanlynhapxuat.activity.main.MainActivity;

public class MoreFragment extends Fragment {

    Button btn_nv, btn_kh,  btn_dx;
    TextView tvName;
    ImageView ivAvatar;
    MainActivity mainActivity;
    private View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_more, container, false);
        mainActivity= (MainActivity) getActivity();
            btn_nv = mView.findViewById(R.id.btn_nv);
            btn_kh = mView.findViewById(R.id.btn_kh);
            tvName=mView.findViewById(R.id.tv_nameNV);
            btn_dx = mView.findViewById(R.id.btn_dx);
            ivAvatar = mView.findViewById(R.id.ivAvatar);
            Log.e("more", LoginActivity.nameLogin );
            tvName.setText(LoginActivity.nameLogin);
            if (LoginActivity.linkAvatar!=null) {
                Glide.with(mainActivity).load(LoginActivity.linkAvatar).into(ivAvatar);
                // Inflate the layout for this fragment
            }
        setEvent();
        return mView;


    }

    private void setEvent() {
        btn_nv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EmployeesActivity.class);
                startActivity(intent);
            }
        });

        btn_kh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListKHActivity.class);
                startActivity(intent);
            }
        });
        btn_dx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

    }
}