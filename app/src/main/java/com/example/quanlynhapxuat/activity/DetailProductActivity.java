package com.example.quanlynhapxuat.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.api.ApiUtils;
import com.example.quanlynhapxuat.model.Product;
import com.example.quanlynhapxuat.utils.CustomToast;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends AppCompatActivity {
    TextInputLayout name;
    Switch status;
    Toolbar toolbar;
    boolean is_update = false;
    int check;
    Button add, cancel;
    int id;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        setControl();
        id = getIntent().getIntExtra("id", 0);
        if (id != 0) {
            toolbar.setTitle("Sửa thông tin");
            is_update = true;
            setDisplayInfoProduct(id);
            add.setText("Sửa");
        } else {
            check = 1;
            status.setChecked(true);
        }
        handleClickButtonAdd();
        handleClickButtonCancel();
        handleSwitch();
        setActionBar();
    }

    private void setActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void handleSwitch() {
        status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check = 1;
                } else {
                    check = 0;
                }
            }
        });
    }

    public void setDisplayInfoProduct(int id) {
        ApiUtils.productRetrofit().getProduct(id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                product = response.body();
                name.getEditText().setText(product.getName());
                if (product.getStatus() == 1) {
                    status.setChecked(true);
                } else {
                    status.setChecked(false);
                }

            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                CustomToast.makeText(DetailProductActivity.this, t.toString(),
                        CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });
    }

    private void setControl() {
        name = findViewById(R.id.name);
        status = findViewById(R.id.status);
        toolbar = findViewById(R.id.toolbar);
        add = findViewById(R.id.add);
        cancel = findViewById(R.id.cancel);
    }

    private void handleClickButtonAdd() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameProduct = name.getEditText().getText().toString().trim();
                Date date = new Date();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = df.format(date);
                String imageStr = "";
                if (checkInput(nameProduct)){
                    Product product = new Product(nameProduct, check, imageStr, dateString);
                    if (is_update == false) {
                        addProduct(product);
                        finish();
                    } else {
                        updateProduct(id, product);
                        finish();
                    }
                }
            }
        });
    }

    private void handleClickButtonCancel() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addProduct(Product product) {
        ApiUtils.productRetrofit().createNewProduct(product).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    CustomToast.makeText(DetailProductActivity.this, "Thêm thành công!!",
                            CustomToast.LENGTH_LONG, CustomToast.SUCCESS).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                CustomToast.makeText(DetailProductActivity.this, "Vui lòng thử lại!!",
                        CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });
    }

    private void updateProduct(int id, Product product) {
        ApiUtils.productRetrofit().updateProduct(id, product).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    CustomToast.makeText(DetailProductActivity.this, "Sửa thành công!!",
                            CustomToast.LENGTH_LONG, CustomToast.SUCCESS).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                CustomToast.makeText(DetailProductActivity.this, t.toString(),
                        CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });
    }

    boolean checkInput(String nameProduct) {
        if (nameProduct.isEmpty()) {
            CustomToast.makeText(DetailProductActivity.this, "Vui lòng nhập tên sản phẩm!!",
                    CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            return false;
        }
        return true;
    }
}
