package com.example.quanlynhapxuat.fragment.KhachHang;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.model.KhachHang;


public class ThongTinKHFragment extends Fragment {

    private ImageView ivTTAvatar;
    private EditText edtTTName, edtTTAdress, edtTTPhone, edtTTEmail;
    private RadioButton rdoTTMale, rdoTTFemale;
    private Button btnEditKH, btnDeleteKH;
    private KhachHang dto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_tin_k_h, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        dto = (KhachHang) args.getSerializable("KH");
        setControl(view);
        edtTTName.setText(dto.getName());
        edtTTAdress.setText(dto.getName());
        edtTTPhone.setText(dto.getPhone());
        edtTTEmail.setText(dto.getEmail());
    }

    private void setControl(View view) {
        ivTTAvatar = view.findViewById(R.id.ivTTAvatar);
        rdoTTMale = view.findViewById(R.id.rdoTTMale);
        rdoTTFemale = view.findViewById(R.id.rdoTTFemale);
        edtTTName = view.findViewById(R.id.edtTTName);
        edtTTAdress = view.findViewById(R.id.edtTTAdress);
        edtTTPhone = view.findViewById(R.id.edtTTPhone);
        edtTTEmail = view.findViewById(R.id.edtTTEmail);
        btnEditKH = view.findViewById(R.id.btnEditKH);
        btnDeleteKH = view.findViewById(R.id.btnDeleteKH);
    }
}