package com.dapp.dapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.dapp.dapplication.Helper.PERMISSION;
import com.dapp.dapplication.Helper.PermissionHelper;
import com.dapp.dapplication.Helper.SharedHelper;
import com.dapp.dapplication.admin_module.AdminDashBoard;
import com.dapp.dapplication.databinding.ActivityLoginBinding;
import com.dapp.dapplication.service.RestBuilderPro;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;
    private String reg_type;
    private boolean stud_checked;
    private PermissionHelper permissionHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.student.setChecked(true);
        stud_checked = true;

        permission();

        binding.logincheck.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == binding.student.getId()) {
                    binding.register.setVisibility(View.VISIBLE);
                    stud_checked = true;
                    binding.loginid.setHint("Register Id");

                } else {
                    binding.register.setVisibility(View.INVISIBLE);
                    reg_type = "admin";
                    binding.loginid.setHint("Department Id");


                    stud_checked = false;

                }
            }
        });
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,StudentRegister.class));
            }
        });
        binding.loginsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _username = binding.loginid.getText().toString();
                String password = binding.loginpass.getText().toString();
                boolean check = true;


                if (_username.isEmpty()) {
                    binding.loginid.setError("invalid id");
                    check = false;
                }
                if (password.isEmpty()) {

                    binding.loginid.setError("invalid password");
                    check = false;

                }


                if (check) {
                    if (stud_checked) {
                        reg_type = "student";

                    } else {
                        if (_username.equalsIgnoreCase("admin")) {
                            reg_type = "admin";
                        } else {

                            reg_type = "teacher";
                        }
                    }

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("user_type", reg_type);
                    hashMap.put("user_password", password);
                    hashMap.put("user_name", _username);
                    final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
                    dialog.setMessage("Loading...");
                    dialog.show();

                    RestBuilderPro.getService().login(hashMap).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            dialog.dismiss();

                            if (response.isSuccessful()) {
                                try {
                                    String respo=response.body().string();
                                    Log.e("value",respo.toString());

                                    JSONObject jsonObject = new JSONObject(respo);
                                    Log.e("value",jsonObject.toString());
                                    int succ = jsonObject.getInt("success");

                                    if (succ == 1) {
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                                        final JSONObject jsonObject1 = jsonArray.getJSONObject(0);


                                        SnakBarCallback("Login success", new CallbackSnak() {
                                            @Override
                                            public void back() {

                                                if (reg_type.equalsIgnoreCase("student")) {

                                                    SharedHelper sharedHelper = new SharedHelper(LoginActivity.this);
                                                    sharedHelper.setRegType(reg_type);
                                                    sharedHelper.setLoginCheck(true);
                                                    sharedHelper.setStudent(jsonObject1.toString());
                                                    startActivity(new Intent(LoginActivity.this, StudentDashBoard.class));



                                                } else if (reg_type.equalsIgnoreCase("admin")) {
                                                    SharedHelper sharedHelper = new SharedHelper(LoginActivity.this);
                                                    sharedHelper.setRegType(reg_type);
                                                    sharedHelper.setLoginCheck(true);
                                                    startActivity(new Intent(LoginActivity.this, AdminDashBoard.class));

                                                } else if (reg_type.equalsIgnoreCase("teacher")) {

                                                    SharedHelper sharedHelper = new SharedHelper(LoginActivity.this);
                                                    sharedHelper.setRegType(reg_type);
                                                    sharedHelper.setLoginCheck(true);
                                                    startActivity(new Intent(LoginActivity.this, AdminDashBoard.class));

                                                }




                                                finish();
                                            }
                                        });


                                    } else {
                                        SnakBar("check user credentials");
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();

                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            dialog.dismiss();
                            SnakBar("Server could not connect");

                        }
                    });


                }

            }
        });


    }

    private void permission() {
        permissionHelper = new PermissionHelper(this, PERMISSION.ALL, 123);
        permissionHelper.request(new PermissionHelper.PermissionCallback() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied() {

            }

            @Override
            public void onPermissionDeniedBySystem() {

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (permissionHelper != null) {
            permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

}
