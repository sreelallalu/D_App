package com.dapp.dapplication.admin_module;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.dapp.dapplication.BaseActivity;
import com.dapp.dapplication.Helper.Position;
import com.dapp.dapplication.Helper.SharedHelper;
import com.dapp.dapplication.R;
import com.dapp.dapplication.adapter.BranchAdapter;
import com.dapp.dapplication.adapter.SemAdapter;
import com.dapp.dapplication.adapter.StudentAdapter;
import com.dapp.dapplication.databinding.AdminAttendanceBinding;
import com.dapp.dapplication.model.BatchModel;
import com.dapp.dapplication.model.SemModel;
import com.dapp.dapplication.model.StudentModel;
import com.dapp.dapplication.model.Teachermodel;
import com.dapp.dapplication.service.RestBuilderPro;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAttendance extends BaseActivity {

    private AdminAttendanceBinding binding;

    private BranchAdapter brachadapter;
    private List<BatchModel.Datum> batch_list;
    private List<SemModel.Datum> sem_list = new ArrayList<>();
    private SemAdapter semAdapter;
    private String batchId;
    private String subjectId;
    private String subjectId1;
    private String semtId;
    private String regType;
    private List<StudentModel.Datum> studentlist=new ArrayList<>();
    private StudentAdapter studentAdapter;
    private String tempBrachID;
    private String tempsemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.admin_attendance);
        SharedHelper sharedHelper = new SharedHelper(this);
        regType = sharedHelper.getRegType();

        regType = sharedHelper.getRegType();
        if(regType.equals("teacher")) {
            Gson gson = new Gson();
            String jsonInString = sharedHelper.getTeacherDetails();
            Teachermodel user = gson.fromJson(jsonInString, Teachermodel.class);

            tempBrachID = user.getTeBranch() + "";
            tempsemId = user.getTeSem() + "";
            Log.e("json_type",jsonInString);
        }
        getBranches();


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.studentRecycler.setLayoutManager(mLayoutManager);
        binding.studentRecycler.setItemAnimator(new DefaultItemAnimator());
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

                    brachadapter = new BranchAdapter(AddAttendance.this, batch_list);
                    binding.branchSpinner.setAdapter(brachadapter);
                    brachadapter.notifyDataSetChanged();
                    binding.branchSpinner.setOnItemSelectedListener(new BatChlistClick());
                    if(regType.equals("teacher"))
                    {
                        binding.branchSpinner.setSelection(Position.getPosition(tempBrachID,batch_list));
                        binding.branchSpinner.setClickable(false);
                        binding.branchSpinner.setEnabled(false);
                    }

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
        semAdapter = new SemAdapter(AddAttendance.this, sem_list);
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

                    semAdapter = new SemAdapter(AddAttendance.this, sem_list);
                    binding.semesterSpinner.setAdapter(semAdapter);
                    semAdapter.notifyDataSetChanged();
                    binding.semesterSpinner.setOnItemSelectedListener(new SemlistClick());
                    if(regType.equals("teacher"))
                    {
                        binding.semesterSpinner.setSelection(Position.getPositionSem(tempsemId,sem_list));
                        binding.semesterSpinner.setClickable(false);
                        binding.semesterSpinner.setEnabled(false);
                    }
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
                semtId = String.valueOf(data.getSeId());

                StudentListApi(semtId);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private void StudentListApi(String semtId) {


        if (semtId == null) {
            return;
        }
        studentlist.clear();
        studentAdapter = new StudentAdapter( studentlist,AddAttendance.this);
        binding.studentRecycler.setAdapter(studentAdapter);
        studentAdapter.notifyDataSetChanged();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("br_id", batchId);
        hashMap.put("se_id", semtId);
        hashMap.put("reg_type", regType);
        final ProgressDialog dialog=new ProgressDialog(AddAttendance.this);
        dialog.setMessage("Loading...");
        dialog.show();
        RestBuilderPro.getService().studentlist(hashMap).enqueue(new Callback<StudentModel>() {
            @Override
            public void onResponse(Call<StudentModel> call, Response<StudentModel> response) {
                dialog.dismiss();

                if(response.isSuccessful())
                {
                    StudentModel data=response.body();
                    studentlist = data.getData();
                    studentAdapter = new StudentAdapter( studentlist,AddAttendance.this);
                    binding.studentRecycler.setAdapter(studentAdapter);
                    studentAdapter.notifyDataSetChanged();
                }else {
                    SnakBar("Something went wrong");

                }
            }

            @Override
            public void onFailure(Call<StudentModel> call, Throwable t) {
                dialog.dismiss();
                SnakBar("list not available");
                Log.e("error", t.getMessage());

            }
        });
    }
}
