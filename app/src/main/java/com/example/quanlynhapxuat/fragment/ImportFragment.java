package com.example.quanlynhapxuat.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.adapter.ReceivedDocketAdapter;
import com.example.quanlynhapxuat.api.ApiUtils;
import com.example.quanlynhapxuat.model.ReceivedDocket;
import com.example.quanlynhapxuat.model.ReceivedDocketDetail;
import com.example.quanlynhapxuat.model.RestErrorResponse;
import com.example.quanlynhapxuat.service.ReceivedDocketService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImportFragment extends Fragment {
    //
    private ReceivedDocketAdapter receivedDocketAdapter;
    private ArrayList<ReceivedDocket> receivedDocketList;
    private ArrayList<ReceivedDocketDetail> receivedDocketDetailList;

    //
    private RecyclerView rcvListPhieuNhap;
    private TextView tvSLPhieuNhap;
    private TextView tvTotal;
    private FloatingActionButton flbThemPhieuNhap;

    public ImportFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_import, container, false);
        setControl(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        capNhatDuLieu();
    }

    private void setControl(View view) {
        rcvListPhieuNhap = view.findViewById(R.id.rcvListPhieuNhap_fragmentImport);
        rcvListPhieuNhap.setLayoutManager(new LinearLayoutManager(getContext()));

        tvSLPhieuNhap = view.findViewById(R.id.tvSLPhieuNhap_fragmentImport);
        tvTotal = view.findViewById(R.id.tvTotal_fragmentImport );
        flbThemPhieuNhap = view.findViewById(R.id.flbThemPhieuNhap_fragmentImport);
    }

    private void capNhatDuLieu() {
        getReceivedDocketList();
        //getReceivedDocketDetailList();
        receivedDocketAdapter = new ReceivedDocketAdapter(getContext(),receivedDocketList);
        rcvListPhieuNhap.setAdapter(receivedDocketAdapter);
        //
        tvSLPhieuNhap.setText(receivedDocketAdapter.getItemCount()+"");
        tvTotal.setText(receivedDocketAdapter.getTotalList()+"");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("   QU???N L?? PHI???U NH???P");
        capNhatDuLieu();
    }

    private void getReceivedDocketList() {
        ApiUtils.getReceivedDocketService().getReceivedDocketList().enqueue(new Callback<ArrayList<ReceivedDocket>>() {
            @Override
            public void onResponse(Call<ArrayList<ReceivedDocket>> call, Response<ArrayList<ReceivedDocket>> response) {
                if(response.isSuccessful()) {
                    //Toast.makeText(getContext(),"Call API Success!!!",Toast.LENGTH_SHORT).show();
                    receivedDocketList = response.body();
                    Log.e("receivedDocketList",receivedDocketList.get(0).toString());
                }
                else {
                    try {
                        Gson g = new Gson();
                        RestErrorResponse errorResponse = g.fromJson(response.errorBody().string(),RestErrorResponse.class);
                        Log.e("errorResponseGetMessage",errorResponse.getMessage());
                    }
                    catch (Exception e) {
                        Log.e("e.getMessage()",e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ReceivedDocket>> call, Throwable t) {
                Toast.makeText(getContext(),"Unknown Error!!!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getReceivedDocketDetailList() {
        ApiUtils.getReceivedDocketService().getReceivedDocketDetailList().enqueue(new Callback<ArrayList<ReceivedDocketDetail>>() {
            @Override
            public void onResponse(Call<ArrayList<ReceivedDocketDetail>> call, Response<ArrayList<ReceivedDocketDetail>> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getContext(),"Call API Success!!!",Toast.LENGTH_SHORT).show();
                    receivedDocketDetailList = response.body();
                    Log.e("response.body()",response.toString());
                }
                else {
                    try {
                        Gson g = new Gson();
                        RestErrorResponse errorResponse = g.fromJson(response.errorBody().string(),RestErrorResponse.class);
                        Log.e("errorResponseGetMessage",errorResponse.getMessage());
                    }
                    catch (Exception e) {
                        Log.e("e.getMessage()",e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ReceivedDocketDetail>> call, Throwable t) {
                Toast.makeText(getContext(),"Unknown Error!!!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}