package com.dapp.dapplication;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.dapp.dapplication.Helper.SharedHelper;
import com.dapp.dapplication.adapter.SubjectAdapter;
import com.dapp.dapplication.databinding.AdminStudentViewBinding;
import com.dapp.dapplication.model.AddSuccess;
import com.dapp.dapplication.model.SubjectModel;
import com.dapp.dapplication.service.RestBuilderPro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentView extends BaseActivity {

    private AdminStudentViewBinding binding;
    private String regType;
    private List<SubjectModel.Datum> subjest_list = new ArrayList<>();
    private SubjectAdapter subjectAdapter;
    private String subjectId;
    private boolean attendance_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.admin_student_view);
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String registerId = getIntent().getStringExtra("regid");
        final String studenrId = getIntent().getStringExtra("studid");
        attendance_check = getIntent().getBooleanExtra("attendance", false);
        String branchId = getIntent().getStringExtra("br_id");
        String semhId = getIntent().getStringExtra("se_id");

        binding.name.setText(name);
        binding.regid.setText(registerId);
        binding.textView3.setText(email);

        SharedHelper sharedHelper = new SharedHelper(this);
        regType = sharedHelper.getRegType();
        if (attendance_check) {
            binding.editText.setHint("attendance");
        } else {

            binding.semView.setVisibility(View.VISIBLE);
            SubjectApi(semhId, branchId);

            binding.editText.setHint("sessional");

        }


        binding.uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = true;
                String attendance = binding.editText.getText().toString().trim();
                if (attendance.isEmpty()) {
                    check = false;
                    binding.editText.setError("Invalid");
                }
                if (check) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("attendance", attendance);
                    hashMap.put("reg_type", regType);
                    hashMap.put("st_id", studenrId);
                    hashMap.put("type", attendance_check?"attendance":"sessional");
                    if (!attendance_check) {
                        hashMap.put("su_id", subjectId != null ? subjectId : "");

                    }
                    final ProgressDialog dialog = new ProgressDialog(StudentView.this);
                    dialog.setMessage("Loading...");
                    dialog.show();
                    RestBuilderPro.getService().attendance(hashMap).enqueue(new Callback<AddSuccess>() {
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

    private void SubjectApi(final String s, String batchId) {

        if (batchId == null) {
            return;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("br_id", batchId);
        hashMap.put("se_id", s);

        binding.subjLoading.setVisibility(View.VISIBLE);
        subjest_list.clear();
        subjectAdapter = new SubjectAdapter(StudentView.this, subjest_list);
        binding.subjctSpinner.setAdapter(subjectAdapter);
        subjectAdapter.notifyDataSetChanged();
        RestBuilderPro.getService().subjectlist(hashMap).enqueue(new Callback<SubjectModel>() {
            @Override
            public void onResponse(Call<SubjectModel> call, Response<SubjectModel> response) {
                binding.subjLoading.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    SubjectModel model = response.body();
                    subjest_list = model.getData();
                    subjectAdapter = new SubjectAdapter(StudentView.this, subjest_list);
                    binding.subjctSpinner.setAdapter(subjectAdapter);
                    subjectAdapter.notifyDataSetChanged();
                    binding.subjctSpinner.setOnItemSelectedListener(new StudentView.SublistClick());

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
