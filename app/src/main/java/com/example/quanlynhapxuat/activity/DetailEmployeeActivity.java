package com.example.quanlynhapxuat.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;

import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.api.ApiUtils;
import com.example.quanlynhapxuat.model.Employee;
import com.example.quanlynhapxuat.model.Product;
import com.example.quanlynhapxuat.utils.Constants;
import com.example.quanlynhapxuat.utils.CustomToast;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEmployeeActivity extends AppCompatActivity {

    TextInputLayout name, sdt, address, pass;
    DatePicker datePicker;
    Toolbar toolbar;
    ImageView image;
    Switch status;
    Button add, cancel;

    boolean is_update = false;
    int check;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_employee);
        setControl();
        id = getIntent().getIntExtra("id", 0);
        if (id > 0) {
            displayInfo(id);
            toolbar.setTitle("Sửa thông tin");
            is_update = true;
            add.setText("Sửa");
        } else {
            check = 1;
            status.setChecked(true);
        }
        handleClickButtonAdd();
        handleClickButtonCancel();
        handleSwitch();
        handleClickDatePicker();
    }

    private void setControl() {
        name = findViewById(R.id.name);
        sdt = findViewById(R.id.sdt);
        address = findViewById(R.id.address);
        pass = findViewById(R.id.pass);
        add = findViewById(R.id.add);
        cancel = findViewById(R.id.cancel);

        datePicker = findViewById(R.id.datePicker);
        toolbar = findViewById(R.id.toolbar);
        image = findViewById(R.id.image);
        status = findViewById(R.id.status);
    }

    public void displayInfo(int id) {
        ApiUtils.employeeRetrofit().getEmployee(id).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful()) {
                    Employee employee = response.body();
                    name.getEditText().setText(employee.getFullName());
                    if (employee.getStatus() == 1) {
                        status.setChecked(true);
                    } else {
                        status.setChecked(false);
                    }
                    sdt.getEditText().setText(employee.getPhoneNumber());
                    address.getEditText().setText(employee.getAddress());
                    pass.getEditText().setText(employee.getPassword());
                    try {
                        String str[] = employee.getDateOfBirth().split(" ");
                        String strB[] = str[0].split("-");
                        int year = Integer.parseInt(strB[0]);

                        int month = Integer.parseInt(strB[1]);
                        int dayOfMonth = Integer.parseInt(strB[2]);

                        datePicker.updateDate(year, (month - 1), dayOfMonth);

                    } catch (Exception e) {
                        CustomToast.makeText(DetailEmployeeActivity.this, e.toString(),
                                CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                CustomToast.makeText(DetailEmployeeActivity.this, t.toString(),
                        CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });
    }

    public void handleClickDatePicker() {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int dayOfMonth = datePicker.getDayOfMonth();
        datePicker.init(year, (month - 1), dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                datePicker.updateDate(year, (month - 1), dayOfMonth);
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

    public void handleClickButtonAdd() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStr = name.getEditText().getText().toString().trim();
                String phoneStr = sdt.getEditText().getText().toString().trim();
                String addressStr = address.getEditText().getText().toString().trim();
                String passStr = pass.getEditText().getText().toString().trim();
                String dayStr = datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth() + " 00:00:00";
                Employee e = new Employee(nameStr, addressStr, dayStr, phoneStr, 1, passStr, check, null);

                if (is_update == false) {
                    addEmployee(e);
                    Intent intent = new Intent(DetailEmployeeActivity.this, EmployeesActivity.class);
                    startActivityForResult(intent, Constants.RESULT_PRODUCT_ACTIVITY);
                } else {
                    updateEmployee(id, e);
                    Intent intent = new Intent(DetailEmployeeActivity.this, EmployeesActivity.class);
                    startActivityForResult(intent, Constants.RESULT_PRODUCT_ACTIVITY);
                }
            }
        });
    }

    private void addEmployee(Employee e) {
        ApiUtils.employeeRetrofit().createEmployee(e).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful()) {
                    CustomToast.makeText(DetailEmployeeActivity.this, "Thêm thành công!!",
                            CustomToast.LENGTH_LONG, CustomToast.SUCCESS).show();
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                CustomToast.makeText(DetailEmployeeActivity.this, t.toString(),
                        CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });
    }

    private void updateEmployee(int id, Employee e) {
        ApiUtils.employeeRetrofit().updateEmployee(id, e).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful()) {
                    CustomToast.makeText(DetailEmployeeActivity.this, "Sửa thông tin thành công!!",
                            CustomToast.LENGTH_LONG, CustomToast.SUCCESS).show();
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                CustomToast.makeText(DetailEmployeeActivity.this, t.toString(),
                        CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });
    }

    private void handleClickButtonCancel() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailEmployeeActivity.this, EmployeesActivity.class);
                startActivityForResult(intent, Constants.RESULT_PRODUCT_ACTIVITY);
            }
        });
    }
}