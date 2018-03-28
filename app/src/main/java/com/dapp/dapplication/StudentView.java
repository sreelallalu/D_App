package com.dapp.dapplication;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.dapp.dapplication.Helper.SharedHelper;
import com.dapp.dapplication.databinding.AdminStudentViewBinding;
import com.dapp.dapplication.model.AddSuccess;
import com.dapp.dapplication.service.RestBuilderPro;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentView extends BaseActivity {

    private AdminStudentViewBinding binding;
    private String regType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.admin_student_view);
      String name=getIntent().getStringExtra("name");
      String email=getIntent().getStringExtra("email");
      String registerId=getIntent().getStringExtra("regid");
      final String studenrId=getIntent().getStringExtra("studid");
      binding.name.setText(name);
      binding.regid.setText(registerId);
      binding.textView3.setText(email);
        SharedHelper sharedHelper = new SharedHelper(this);
        regType = sharedHelper.getRegType();
      binding.uploadbutton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              boolean check=true;
              String attendance=binding.editText.getText().toString().trim();
              if(attendance.isEmpty())
              {
                  check=false;
                  binding.editText.setError("Invalid");
              }
              if(check)
              {
                  HashMap<String,String> hashMap=new HashMap<>();
                  hashMap.put("attendance",attendance);
                  hashMap.put("reg_type",regType);
                  hashMap.put("st_id",studenrId);
                  final ProgressDialog dialog=new ProgressDialog(StudentView.this);
                  dialog.setMessage("Loading...");
                  dialog.show();
                  RestBuilderPro.getService().attendance(hashMap).enqueue(new Callback<AddSuccess>() {
                      @Override
                      public void onResponse(Call<AddSuccess> call, Response<AddSuccess> response) {
                          dialog.dismiss();

                          if (response.isSuccessful()) {
                              try {

                                  AddSuccess data=response.body();

                                  if(data.getSuccess()==1)
                                  {

                                      SnakBarCallback("Success", new CallbackSnak() {
                                          @Override
                                          public void back() {

                                              finish();
                                          }
                                      });



                                  }else{
                                      SnakBar("Failed");
                                  }

                              } catch (Exception e) {
                                  e.printStackTrace();

                              }
                          }

                      }

                      @Override
                      public void onFailure(Call<AddSuccess> call, Throwable t) {
                          dialog.dismiss();

                          SnakBar("Server could not connect");

                      }
                  });
              }

          }
      });
    }
}
