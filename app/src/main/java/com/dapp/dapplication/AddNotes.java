package com.dapp.dapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.dapp.dapplication.Helper.SelectedFilePath;
import com.dapp.dapplication.Helper.SharedHelper;
import com.dapp.dapplication.adapter.BranchAdapter;
import com.dapp.dapplication.adapter.SemAdapter;
import com.dapp.dapplication.adapter.SubjectAdapter;
import com.dapp.dapplication.databinding.AdminNotesBinding;
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

public class AddNotes extends BaseActivity {

    private static final int GALLERYPICK = 103;
    private AdminNotesBinding binding;
    private String filepath;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.admin_notes);


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

                    final ProgressDialog dialog = new ProgressDialog(AddNotes.this);
                    dialog.setMessage("Loading...");
                    dialog.show();
                    String encodedImage = "";
                    if (filepath != null) {
                        encodedImage = convertFileToByteArray(new File(filepath));


                    }


                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("data", encodedImage);
                    hashMap.put("br_id", batchId);
                    hashMap.put("se_id", semtId);
                    hashMap.put("su_id", subjectId);
                    hashMap.put("notes", notes);
                    hashMap.put("reg_type", regType);


                    RestBuilderPro.getService().addnotes(hashMap).enqueue(new Callback<AddSuccess>() {
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

                    brachadapter = new BranchAdapter(AddNotes.this, batch_list);
                    binding.branchSpinner.setAdapter(brachadapter);
                    brachadapter.notifyDataSetChanged();
                    binding.branchSpinner.setOnItemSelectedListener(new BatChlistClick());

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
        semAdapter = new SemAdapter(AddNotes.this, sem_list);
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

                    semAdapter = new SemAdapter(AddNotes.this, sem_list);
                    binding.semesterSpinner.setAdapter(semAdapter);
                    semAdapter.notifyDataSetChanged();
                    binding.semesterSpinner.setOnItemSelectedListener(new SemlistClick());

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
        subjectAdapter = new SubjectAdapter(AddNotes.this, subjest_list);
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

                    subjectAdapter = new SubjectAdapter(AddNotes.this, subjest_list);
                    binding.subjctSpinner.setAdapter(subjectAdapter);
                    subjectAdapter.notifyDataSetChanged();
                    binding.subjctSpinner.setOnItemSelectedListener(new SublistClick());

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERYPICK && resultCode == RESULT_OK) {
            Uri pickedImage = data.getData();

            if (null != pickedImage) {
                // Get the path from the Uri
                filepath = SelectedFilePath.getPath(AddNotes.this, pickedImage);
                binding.fileText.setText(new File(filepath).getName());

            }
        }
    }

}
