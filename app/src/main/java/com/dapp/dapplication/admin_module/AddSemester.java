package com.dapp.dapplication.admin_module;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.dapp.dapplication.BaseActivity;
import com.dapp.dapplication.Helper.SharedHelper;
import com.dapp.dapplication.R;
import com.dapp.dapplication.adapter.BranchAdapter;
import com.dapp.dapplication.databinding.AdminAddSemesterBinding;
import com.dapp.dapplication.model.AddSuccess;
import com.dapp.dapplication.model.BatchModel;
import com.dapp.dapplication.service.RestBuilderPro;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSemester extends BaseActivity {

    private AdminAddSemesterBinding binding;
    private List<BatchModel.Datum> batch_list;
    private String batchId;
    private BranchAdapter brachadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.admin_add_semester);
        SharedHelper sharedHelper = new SharedHelper(this);
        final String regType = sharedHelper.getRegType();


        getBranches();
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semname = binding.addSemedit.getText().toString().trim();
                if (semname.isEmpty()) {
                    binding.addSemedit.setError("Invalid semester");
                } else {

                    final ProgressDialog dialog = new ProgressDialog(AddSemester.this);
                    dialog.setMessage("Loading..");
                    dialog.show();
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("br_id", batchId);
                    hashMap.put("se_name", semname);
                    hashMap.put("reg_type", regType);

                    RestBuilderPro.getService().addsemester(hashMap).enqueue(new Callback<AddSuccess>() {
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
                            SnakBar("Server could not connect");

                        }
                    });

                }
            }
        });

    }

    private void getBranches() {

        binding.branchLoading.setVisibility(View.VISIBLE);

        RestBuilderPro.getService().brachlist().enqueue(new Callback<BatchModel>() {
            @Override
            public void onResponse(Call<BatchModel> call, Response<BatchModel> response) {
                binding.branchLoading.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    BatchModel model = response.body();

                    batch_list = model.getData();

                    brachadapter = new BranchAdapter(AddSemester.this, batch_list);
                    binding.branchSpinner.setAdapter(brachadapter);
                    brachadapter.notifyDataSetChanged();
                    binding.branchSpinner.setOnItemSelectedListener(new AddSemester.BatChlistClick());

                } else {
                    SnakBar("Batch list is empty");
                }
            }

            @Override
            public void onFailure(Call<BatchModel> call, Throwable t) {
                binding.branchLoading.setVisibility(View.VISIBLE);
                SnakBar("Server could not connect");

            }
        });


    }

    private class BatChlistClick implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            BatchModel.Datum data = batch_list.get(position);
            if (data.getBrName() != "") {


                batchId = data.getBrId() + "";
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}
