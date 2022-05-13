package com.example.quanlynhapxuat.fragment.KhachHang;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlynhapxuat.R;


public class LichSuKHFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lich_su_k_h, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Lịch Sử");
    }
}