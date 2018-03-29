package com.dapp.dapplication.admin_module;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.dapp.dapplication.BaseActivity;
import com.dapp.dapplication.Helper.SelectedFilePath;
import com.dapp.dapplication.Helper.SharedHelper;
import com.dapp.dapplication.R;
import com.dapp.dapplication.adapter.BranchAdapter;
import com.dapp.dapplication.adapter.SemAdapter;
import com.dapp.dapplication.adapter.SubjectAdapter;
import com.dapp.dapplication.databinding.AdminTimetableBinding;
import com.dapp.dapplication.model.AddSuccess;
import com.dapp.dapplication.model.BatchModel;
import com.dapp.dapplication.model.SemModel;
import com.dapp.dapplication.model.SubjectModel;
import com.dapp.dapplication.service.RestBuilderPro;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTimetable extends BaseActivity {

    private static final int GALLERYPICK = 103;
    private AdminTimetableBinding binding;
    private BranchAdapter brachadapter;
    private List<BatchModel.Datum> batch_list;
    private List<SemModel.Datum> sem_list=new ArrayList<>();
    private SemAdapter semAdapter;
    private String batchId;
    private String semtId;
    private String regType;

    private List<SubjectModel.Datum> subjest_list=new ArrayList<>();
    private SubjectAdapter subjectAdapter;
    private String subjectId;
    private String subjectId1;
    private String filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.admin_timetable);


        SharedHelper sharedHelper = new SharedHelper(this);
        final String regType = sharedHelper.getRegType();

        getBranches();
        binding.filePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent();
                    intent.setType("application/pdf");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERYPICK);
                } catch (Exception e) {
                    // showsnackbar("Something went wrong");
                    e.printStackTrace();
                }

            }

        });
        binding.uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean check = true;

                String notes = binding.title.getText().toString().trim();
                if (notes.isEmpty()) {
                    check = false;
                    binding.title.setError("Invalid title");

                }
                if (check) {

                    LoadingOn(binding.uploadbutton);
                    String encodedImage = "";
                    if (filepath != null) {
                        encodedImage = convertFileToByteArray(new File(filepath));


                    }


                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("data", encodedImage);
                    hashMap.put("br_id", batchId);
                    hashMap.put("se_id", semtId);
                    hashMap.put("title", notes);
                    hashMap.put("reg_type", regType);


                    RestBuilderPro.getService().addtimetable(hashMap).enqueue(new Callback<AddSuccess>() {
                        @Override
                        public void onResponse(Call<AddSuccess> call, Response<AddSuccess> response) {
                            LoadingOff(binding.uploadbutton);

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
                            LoadingOff(binding.uploadbutton);


                            SnakBar("Server could not connect");

                        }
                    });


                }


            }
        });

    }

    public static String convertFileToByteArray(File f) {
        byte[] byteArray = null;
        try {
            InputStream inputStream = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 11];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();

            Log.e("Byte array", ">" + byteArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
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

                    brachadapter = new BranchAdapter(AddTimetable.this, batch_list);
                    binding.branchSpinner.setAdapter(brachadapter);
                    brachadapter.notifyDataSetChanged();
                    binding.branchSpinner.setOnItemSelectedListener(new AddTimetable.BatChlistClick());

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
        semAdapter = new SemAdapter(AddTimetable.this, sem_list);
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

                    semAdapter = new SemAdapter(AddTimetable.this, sem_list);
                    binding.semesterSpinner.setAdapter(semAdapter);
                    semAdapter.notifyDataSetChanged();
                    binding.semesterSpinner.setOnItemSelectedListener(new AddTimetable.SemlistClick());

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
               semtId=data.getSeId()+"";
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERYPICK && resultCode == RESULT_OK) {
            Uri pickedImage = data.getData();

            if (null != pickedImage) {
                // Get the path from the Uri
             try {
                 filepath = SelectedFilePath.getPath(AddTimetable.this, pickedImage);
                 binding.fileText.setText(new File(filepath).getName());
             }catch (Exception e){e.printStackTrace();}
            }
        }
    }
}
