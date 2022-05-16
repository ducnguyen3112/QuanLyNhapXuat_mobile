package com.example.quanlynhapxuat.fragment.phieuxuat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.activity.main.MainActivity;
import com.example.quanlynhapxuat.adapter.ChiTietPXAdapter;
import com.example.quanlynhapxuat.api.ApiUtils;
import com.example.quanlynhapxuat.model.DeliveryDocket;
import com.example.quanlynhapxuat.model.DeliveryDocketDetail;
import com.example.quanlynhapxuat.model.KhachHang;
import com.example.quanlynhapxuat.utils.Convert;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExportDetailFragment extends Fragment {

    public static final String TAG=ExportDetailFragment.class.getName();

    private RecyclerView recyclerView;
    private View mView;
    private MainActivity mainActivity;
    private TextView tvIdPX,tvTenKH,tvNgay,tvStatus,tvTongTien,tvGiamGia,tvCanTra;
    private ImageButton btnBack;



    public ExportDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_export_detail, container, false);
        mainActivity= (MainActivity) getActivity();
        recyclerView=mView.findViewById(R.id.rcv_ctpx);
        tvIdPX=mView.findViewById(R.id.tv_idpx_ctpx);
        tvTenKH=mView.findViewById(R.id.tv_tenKH_ctpx);
        tvNgay=mView.findViewById(R.id.tv_ngayctpx);
        tvStatus=mView.findViewById(R.id.tv_trangthai_ctpx);
        btnBack=mView.findViewById(R.id.btn_backexportdetail);
        tvTongTien=mView.findViewById(R.id.tv_tongtienpx);
        tvGiamGia=mView.findViewById(R.id.tv_giamgiapx);
        tvCanTra=mView.findViewById(R.id.tv_khachcantrapx);

        Bundle bundleReceive=getArguments();

        List<DeliveryDocketDetail> deliveryDocketDetails=mainActivity.getmDeliveryDocketDetails() ;
        if (bundleReceive!=null){
            DeliveryDocket deliveryDocket= (DeliveryDocket) bundleReceive.get("deliveryDocket");
            if (deliveryDocket!=null){
                tvIdPX.setText(String.valueOf(deliveryDocket.getId()));
                tvNgay.setText(deliveryDocket.getCreatedAt());
                if(deliveryDocket.getStatus()==1){
                    tvStatus.setText("Đang xử lí");
                }else if(deliveryDocket.getStatus()==2){
                    tvStatus.setText("Hoàn thành");
                }else if(deliveryDocket.getStatus()==0) {
                    tvStatus.setText("Đã hủy");
                }
                int tong=0;
                for (DeliveryDocketDetail item :
                        deliveryDocketDetails) {
                    tong+=item.getPrice();
                }
                tvTongTien.setText(Convert.currencyFormat(tong));
                tvGiamGia.setText(String.valueOf(0));
                tvCanTra.setText(Convert.currencyFormat(tong));
                ApiUtils.getKhachHangService().getKHById(deliveryDocket.getCustomerId()).enqueue(new Callback<KhachHang>() {
                    @Override
                    public void onResponse(Call<KhachHang> call, Response<KhachHang> response) {
                        KhachHang khachHang=response.body();
                        tvTenKH.setText(khachHang.getFullName());
                    }

                    @Override
                    public void onFailure(Call<KhachHang> call, Throwable t) {

                    }
                });
            }
        }
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(mainActivity,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        ChiTietPXAdapter chiTietPXAdapter=new ChiTietPXAdapter();
        chiTietPXAdapter.setData(deliveryDocketDetails);
        recyclerView.setAdapter(chiTietPXAdapter);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
        return mView;
    }
}