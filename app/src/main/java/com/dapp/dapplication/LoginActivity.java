package com.dapp.dapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.dapp.dapplication.Helper.SharedHelper;
import com.dapp.dapplication.databinding.ActivityLoginBinding;
import com.dapp.dapplication.model.LoginModel;
import com.dapp.dapplication.service.RestBuilderPro;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;
    private String reg_type;
    private boolean stud_checked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.student.setChecked(true);
        stud_checked = true;
        SharedHelper sharedHelper=new SharedHelper(LoginActivity.this);
        if(sharedHelper.getLoginCheck())
        {
            startActivity(new Intent(LoginActivity.this,AdminDashBoard.class));
            finish();
        }

        binding.logincheck.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == binding.student.getId()) {
                    binding.register.setVisibility(View.VISIBLE);
                    stud_checked = true;

                } else {
                    binding.register.setVisibility(View.INVISIBLE);
                    reg_type = "admin";
                    stud_checked = false;

                }
            }
        });
        binding.loginsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _username = binding.loginid.getText().toString();
                String password = binding.loginpass.getText().toString();
                boolean check=true;


             if(_username.isEmpty())
             {
                 binding.loginid.setError("invalid id");
                  check=false;
             }
             if(password.isEmpty())
             {

                 binding.loginid.setError("invalid password");
                 check=false;

             }


             if(check)
             {
                 if (stud_checked) {
                     reg_type = "student";

                 } else {
                     if (_username.equalsIgnoreCase("admin")) {
                         reg_type = "admin";
                     } else {

                         reg_type = "teacher";
                     }
                 }

                 HashMap<String,String> hashMap=new HashMap<>();
                 hashMap.put("user_type",reg_type);
                 hashMap.put("user_password",password);
                 hashMap.put("user_name",_username);
                final ProgressDialog dialog=new ProgressDialog(LoginActivity.this);
                 dialog.setMessage("Loading...");
                 dialog.show();

                 RestBuilderPro.getService().login(hashMap).enqueue(new Callback<LoginModel>() {
                     @Override
                     public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                         dialog.dismiss();

                         if (response.isSuccessful()) {
                             try {

                                LoginModel data=response.body();
                                if(data.getSuccess()==1)
                                {

                                    SnakBarCallback("Login success", new CallbackSnak() {
                                        @Override
                                        public void back() {
                                            SharedHelper sharedHelper=new SharedHelper(LoginActivity.this);
                                            sharedHelper.setRegType(reg_type);
                                            sharedHelper.setLoginCheck(true);
                                            startActivity(new Intent(LoginActivity.this,AdminDashBoard.class));
                                            finish();
                                        }
                                    });



                                }else{
                                    SnakBar("check user credentials");
                                }

                             } catch (Exception e) {
                                 e.printStackTrace();

                             }
                         }

                     }

                     @Override
                     public void onFailure(Call<LoginModel> call, Throwable t) {
                         dialog.dismiss();
                         SnakBar("Server could not connect");

                     }
                 });





             }

            }
        });


    }
}
