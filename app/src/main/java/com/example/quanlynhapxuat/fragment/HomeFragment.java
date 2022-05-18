package com.example.quanlynhapxuat.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.api.ApiUtils;
import com.example.quanlynhapxuat.model.DeliveryDocketDetail;
import com.example.quanlynhapxuat.model.Product;
import com.example.quanlynhapxuat.service.DeliveryDocketDetailService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    public static HomeFragment newInstance(){
        HomeFragment fragment=new HomeFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnyChartView chartView=view.findViewById(R.id.chart_home);
        Cartesian cartesian = AnyChart.column();
        ApiUtils.getProductService().getAllProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> products = response.body();
                DeliveryDocketDetailService.deliveryDocketService.getAllDeliveryDocketDetail().enqueue(new Callback<List<DeliveryDocketDetail>>() {
                    @Override
                    public void onResponse(Call<List<DeliveryDocketDetail>> call, Response<List<DeliveryDocketDetail>> response) {
                        List<DeliveryDocketDetail> list = response.body();
                        List<DataEntry> data = new ArrayList<>();
                        Map<String, Integer> hashMap = new HashMap<>();
                        int quantity = 0;
                        for (Product product : products) {
                            quantity = 0;
                            for (DeliveryDocketDetail item : list) {
                                if (product.getId() == item.getProductId()) {
                                    quantity += item.getQuantity();
                                }
                            }
                            hashMap.put(product.getName(), quantity);
                        }
                        List<Map.Entry<String, Integer>> ds = new ArrayList<Map.Entry<String, Integer>>();
                        ds.addAll(hashMap.entrySet());

                        Collections.sort(ds, new Comparator<Map.Entry<String, Integer>>() {
                            @Override
                            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                                return o1.getValue() > o2.getValue() ? -1 : 1;
                            }
                        });

                        data.add(new ValueDataEntry(ds.get(0).getKey(), ds.get(0).getValue()));
                        data.add(new ValueDataEntry(ds.get(1).getKey(), ds.get(1).getValue()));
                        data.add(new ValueDataEntry(ds.get(2).getKey(), ds.get(2).getValue()));
                        //data.add(new ValueDataEntry(ds.get(3).getKey(), ds.get(3).getValue()));
                        //data.add(new ValueDataEntry(ds.get(4).getKey(), ds.get(4).getValue()));
                        Column column = cartesian.column(data);

                        column.tooltip()
                                .titleFormat("{%X}")
                                .position(Position.CENTER_BOTTOM)
                                .anchor(Anchor.CENTER_BOTTOM)
                                .offsetX(0d)
                                .offsetY(5d)
                                .format("${%Value}{groupsSeparator: }");
                        cartesian.animation(true);
                        cartesian.title("Thống kê 3 sản phẩm bán chạy nhất");
                        cartesian.yScale().minimum(0d);
                        cartesian.yAxis(0).labels().format("${%(Value)}");
                        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                        cartesian.interactivity().hoverMode(HoverMode.BY_X);
                        //        cartesian.xAxis(0).title("Product");
                        //        cartesian.yAxis(0).title("Revenue");
                        chartView.setChart(cartesian);
                    }
                    @Override
                    public void onFailure(Call<List<DeliveryDocketDetail>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });



//        cartesian.xAxis(0).title("Product");
//        cartesian.yAxis(0).title("Revenue"
    }
}