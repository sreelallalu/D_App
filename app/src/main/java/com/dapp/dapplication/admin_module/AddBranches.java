package com.dapp.dapplication.admin_module;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.dapp.dapplication.BaseActivity;
import com.dapp.dapplication.Helper.SharedHelper;
import com.dapp.dapplication.R;
import com.dapp.dapplication.databinding.AdminAddBranchesBinding;
import com.dapp.dapplication.model.AddSuccess;
import com.dapp.dapplication.service.RestBuilderPro;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBranches extends BaseActivity {

    private AdminAddBranchesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.admin_add_branches);
        SharedHelper sharedHelper=new SharedHelper(this);
      final String  regType = sharedHelper.getRegType();
           binding.button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   String brach=binding.addBranchedit.getText().toString().trim();
                   if(brach.isEmpty())
                   {
                       binding.addBranchedit.setError("Invalid branch name");

                   }else{
                      final ProgressDialog dialog=new ProgressDialog(AddBranches.this);
                       dialog.setMessage("Loading...");
                       dialog.show();
                       HashMap<String ,String > hashMap=new HashMap<>();
                       hashMap.put("reg_type",regType);
                       hashMap.put("br_name",brach);
                       RestBuilderPro.getService().addbrach(hashMap).enqueue(new Callback<AddSuccess>() {
                           @Override
                           public void onResponse(Call<AddSuccess> call, Response<AddSuccess> response) {
                               dialog.dismiss();
                               if (response.isSuccessful()) {
                                   try {

                                       AddSuccess data = response.body();

                                       if (data.getSuccess() == 1) {

                                           SnakBarCallback("Success", new CallbackSnak() {
                                               @Override
                                               public void back() {

                                                   finish();
                                               }
                                           });


                                       } else {
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

                           }
                       });
                   }
               }
           });
    }
}
