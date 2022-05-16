package com.example.quanlynhapxuat.fragment.phieuxuat;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.activity.main.LoginActivity;
import com.example.quanlynhapxuat.activity.main.MainActivity;
import com.example.quanlynhapxuat.adapter.ChiTietPXAdapter;
import com.example.quanlynhapxuat.adapter.KHSpinnerAdapter;
import com.example.quanlynhapxuat.api.ApiUtils;
import com.example.quanlynhapxuat.model.DeliveryDocket;
import com.example.quanlynhapxuat.model.DeliveryDocketDetail;
import com.example.quanlynhapxuat.model.KhachHang;
import com.example.quanlynhapxuat.model.RestErrorResponse;
import com.example.quanlynhapxuat.service.DeliveryDocketService;
import com.example.quanlynhapxuat.utils.Convert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddExportFragment extends Fragment {

    private ImageButton btnThoat,btnLuu,btnThemSP;
    private Spinner spKhangHang;
    private View mView;
    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private TextView tvTenNV;
    private List<KhachHang> khachHangList=new ArrayList<>();
    private int maSKH;
    public static final String TAG = AddExportFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ApiUtils.getKhachHangService().getAllKH().enqueue(new Callback<List<KhachHang>>() {
            @Override
            public void onResponse(Call<List<KhachHang>> call, Response<List<KhachHang>> response) {
                if (response.isSuccessful()){
                    khachHangList=response.body();
                    KHSpinnerAdapter khSpinnerAdapter=new KHSpinnerAdapter(mainActivity,khachHangList);
                    spKhangHang.setAdapter(khSpinnerAdapter);
                    spKhangHang.setDropDownVerticalOffset(150);
                    spKhangHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            maSKH=khachHangList.get(position).getId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<KhachHang>> call, Throwable t) {
                Log.e("addpx",t.getMessage() );
            }
        });
        mView= inflater.inflate(R.layout.fragment_add_export, container, false);
        mainActivity= (MainActivity) getActivity();
        recyclerView=mView.findViewById(R.id.rcv_tpx);
        btnThoat=mView.findViewById(R.id.btn_thoattaopx);
        btnLuu=mView.findViewById(R.id.btn_luuaddpx);
        btnThemSP=mView.findViewById(R.id.btn_themsppx);
        spKhangHang=mView.findViewById(R.id.sp_khpx);
        tvTenNV=mView.findViewById(R.id.tv_name_employee_px);
        tvTenNV.setText(LoginActivity.nameLogin);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryDocketService.deliveryDocketService.addDeliveryDocket(new DeliveryDocket(LoginActivity.idLogin,maSKH,1,Convert.dateToString(new Date())))
                        .enqueue(new Callback<DeliveryDocket>() {
                    @Override
                    public void onResponse(Call<DeliveryDocket> call, Response<DeliveryDocket> response) {
                        if (response.isSuccessful()){
                            Log.e("addpx", "thêm thành công"+maSKH );
                        }else{
                            Log.e("addpx", "không thành công!!!!");
                        }
                    }

                    @Override
                    public void onFailure(Call<DeliveryDocket> call, Throwable t) {
                    }
                });
            }
        });
        btnThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=getDialogDDH(mainActivity);
                dialog.show();
                dialog.getWindow().setLayout((6*MainActivity.width)/7, WindowManager.LayoutParams.WRAP_CONTENT);
            }
        });
        List<DeliveryDocketDetail> deliveryDocketDetails=new ArrayList<>();
        deliveryDocketDetails.add(new DeliveryDocketDetail(10,1000,1,1));
        deliveryDocketDetails.add(new DeliveryDocketDetail(10,1000,4,1));
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(mainActivity,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        ChiTietPXAdapter chiTietPXAdapter=new ChiTietPXAdapter();
        chiTietPXAdapter.setData(deliveryDocketDetails,mainActivity);
        recyclerView.setAdapter(chiTietPXAdapter);
        return mView;
    }
    public Dialog getDialogDDH(Context context){
        Dialog dialog=new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_them_product_ctpx);
        Spinner spSP=dialog.findViewById(R.id.sp_sppx);
        EditText etSLSP=dialog.findViewById(R.id.et_slsanphampx);
        Button btnThem=dialog.findViewById(R.id.btn_dialogthem);
        Button btnHuy=dialog.findViewById(R.id.btn_dialoghuy);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        return dialog;
    }
}