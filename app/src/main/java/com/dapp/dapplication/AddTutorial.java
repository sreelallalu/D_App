package com.dapp.dapplication;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.dapp.dapplication.Helper.SharedHelper;
import com.dapp.dapplication.adapter.BranchAdapter;
import com.dapp.dapplication.adapter.SemAdapter;
import com.dapp.dapplication.adapter.SubjectAdapter;
import com.dapp.dapplication.databinding.AdminTutorialBinding;
import com.dapp.dapplication.model.AddSuccess;
import com.dapp.dapplication.model.BatchModel;
import com.dapp.dapplication.model.SemModel;
import com.dapp.dapplication.model.SubjectModel;
import com.dapp.dapplication.service.RestBuilderPro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTutorial extends BaseActivity {
    private BranchAdapter brachadapter;
    private List<BatchModel.Datum> batch_list;
    private List<SemModel.Datum> sem_list = new ArrayList<>();
    private SemAdapter semAdapter;
    private String batchId;
    private List<SubjectModel.Datum> subjest_list = new ArrayList<>();
    private SubjectAdapter subjectAdapter;
    private String subjectId;
    private String semtId;
    private String regType;
    private AdminTutorialBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_tutorial);
        binding = DataBindingUtil.setContentView(this, R.layout.admin_tutorial);
        getBranches();

        SharedHelper sharedHelper = new SharedHelper(this);
        regType = sharedHelper.getRegType();
        binding.uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videolink = binding.videoLink.getText().toString().trim();
                String notes = binding.notes.getText().toString().trim();
                boolean check = true;

                if (videolink.isEmpty()) {
                    binding.videoLink.setError("Invalid video link");
                }
                if (notes.isEmpty()) {
                    binding.notes.setError("Invalid video link");


                }
                if (check) {

                    final ProgressDialog dialog = new ProgressDialog(AddTutorial.this);
                    dialog.setMessage("Loading..");
                    dialog.show();

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("br_id", batchId);
                    hashMap.put("se_id", semtId);
                    hashMap.put("su_id", subjectId);
                    hashMap.put("reg_type", regType);
                    hashMap.put("videolink", videolink);
                    hashMap.put("notes", notes);

                    RestBuilderPro.getService().addtutorial(hashMap).enqueue(new Callback<AddSuccess>() {
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

                    brachadapter = new BranchAdapter(AddTutorial.this, batch_list);
                    binding.branchSpinner.setAdapter(brachadapter);
                    brachadapter.notifyDataSetChanged();
                    binding.branchSpinner.setOnItemSelectedListener(new AddTutorial.BatChlistClick());

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


                SemesterApi(data.getBrId() + "");
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private void SemesterApi(final String brId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("br_id", brId);

        binding.semLoading.setVisibility(View.VISIBLE);
        sem_list.clear();
        semAdapter = new SemAdapter(AddTutorial.this, sem_list);
        binding.semesterSpinner.setAdapter(semAdapter);
        semAdapter.notifyDataSetChanged();


        RestBuilderPro.getService().semesterlist(hashMap).enqueue(new Callback<SemModel>() {
            @Override
            public void onResponse(Call<SemModel> call, Response<SemModel> response) {
                binding.semLoading.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    SemModel model = response.body();
                    sem_list = model.getData();

                    batchId = brId;

                    semAdapter = new SemAdapter(AddTutorial.this, sem_list);
                    binding.semesterSpinner.setAdapter(semAdapter);
                    semAdapter.notifyDataSetChanged();
                    binding.semesterSpinner.setOnItemSelectedListener(new AddTutorial.SemlistClick());

                } else {
                    SnakBar("Semester list is empty");
                }
            }

            @Override
            public void onFailure(Call<SemModel> call, Throwable t) {
                binding.semLoading.setVisibility(View.GONE);
                SnakBar("Server could not connect");
                Log.e("error", t.getMessage());

            }
        });


    }

    private class SemlistClick implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SemModel.Datum data = sem_list.get(position);
            if (data.getSeName() != "") {
                SubjectApi(data.getSeId() + "");
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private void SubjectApi(final String s) {

        if (batchId == null) {
            return;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("br_id", batchId);
        hashMap.put("se_id", s);

        binding.subjLoading.setVisibility(View.VISIBLE);
        subjest_list.clear();
        subjectAdapter = new SubjectAdapter(AddTutorial.this, subjest_list);
        binding.subjctSpinner.setAdapter(subjectAdapter);
        subjectAdapter.notifyDataSetChanged();
        RestBuilderPro.getService().subjectlist(hashMap).enqueue(new Callback<SubjectModel>() {
            @Override
            public void onResponse(Call<SubjectModel> call, Response<SubjectModel> response) {
                binding.subjLoading.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    SubjectModel model = response.body();
                    subjest_list = model.getData();
                    semtId = s;

                    subjectAdapter = new SubjectAdapter(AddTutorial.this, subjest_list);
                    binding.subjctSpinner.setAdapter(subjectAdapter);
                    subjectAdapter.notifyDataSetChanged();
                    binding.subjctSpinner.setOnItemSelectedListener(new AddTutorial.SublistClick());

                } else {
                    SnakBar("Semester list is empty");
                }
            }

            @Override
            public void onFailure(Call<SubjectModel> call, Throwable t) {
                binding.subjLoading.setVisibility(View.GONE);
                SnakBar("Server could not connect");
                Log.e("error", t.getMessage());
            }
        });
    }

    private class SublistClick implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SubjectModel.Datum data = subjest_list.get(position);
            if (data.getSuName() != "") {
                subjectId = String.valueOf(data.getSuId());
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
