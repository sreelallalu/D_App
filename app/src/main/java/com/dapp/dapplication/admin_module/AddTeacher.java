package com.dapp.dapplication.admin_module;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.RadioButton;

import com.dapp.dapplication.BaseActivity;
import com.dapp.dapplication.Helper.SharedHelper;
import com.dapp.dapplication.R;
import com.dapp.dapplication.adapter.BranchAdapter;
import com.dapp.dapplication.adapter.SemAdapter;
import com.dapp.dapplication.databinding.AdminAddTeacherBinding;
import com.dapp.dapplication.model.AddSuccess;
import com.dapp.dapplication.model.BatchModel;
import com.dapp.dapplication.model.SemModel;
import com.dapp.dapplication.service.RestBuilderPro;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTeacher extends BaseActivity {

    private AdminAddTeacherBinding binding;
    private BranchAdapter brachadapter;
    private List<BatchModel.Datum> batch_list;
    private List<SemModel.Datum> sem_list = new ArrayList<>();
    private SemAdapter semAdapter;
    private String batchId;
    private String semtId;
    private String regType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.admin_add_teacher);
        SharedHelper sharedHelper=new SharedHelper(this);
        final String  regType = sharedHelper.getRegType();
        getBranches();
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=binding.name.getText().toString().trim();
                String dob=binding.dob.getText().toString().trim();
                String address=binding.address.getText().toString().trim();
                String id=binding.id.getText().toString().trim();
                String password=binding.password.getText().toString().trim();
                String gender = ((RadioButton) findViewById(binding.gender.getCheckedRadioButtonId())).getText().toString();

                boolean check=true;
                if(name.isEmpty())
                {
                    check=false;
                    binding.name.setError("Invalid name");

                }if(dob.isEmpty())
                {
                    check=false;
                    binding.dob.setError("Invalid Dob");
                }if(address.isEmpty())
                {

                    check=false;
                    binding.address.setError("Invalid Address");
                }if(id.isEmpty())
                {
                    check=false;
                    binding.id.setError("Invalid id");

                }if(password.isEmpty())
                {
                    check=false;
                    binding.password.setError("Invalid Password");
                }

                if(check)
                {

                   final ProgressDialog dialog=new ProgressDialog(AddTeacher.this);
                    dialog.setMessage("Loading...");
                     dialog.show();

                    HashMap<String,String> hashMap=new HashMap<>();


                    hashMap.put("br_id",batchId);
                    hashMap.put("reg_type",regType);
                    hashMap.put("se_id",semtId);
                    hashMap.put("te_name",name);
                    hashMap.put("te_dob",dob);
                    hashMap.put("te_gender",gender);
                    hashMap.put("te_address",address);
                    hashMap.put("te_password",password);
                    hashMap.put("te_teacherid",id);

                    RestBuilderPro.getService().addteacher(hashMap).enqueue(new Callback<AddSuccess>() {
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
        binding.dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateOfBirth();
            }
        });
    }
    private void DateOfBirth() {
        try {

            Calendar myCalendar = Calendar.getInstance();
            DatePickerDialog pick = new DatePickerDialog(this, date, (myCalendar.get(Calendar.YEAR)), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            pick.show();
            pick.setCancelable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
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

                    brachadapter = new BranchAdapter(AddTeacher.this, batch_list);
                    binding.branchSpinner.setAdapter(brachadapter);
                    brachadapter.notifyDataSetChanged();
                    binding.branchSpinner.setOnItemSelectedListener(new AddTeacher.BatChlistClick());

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
        semAdapter = new SemAdapter(AddTeacher.this, sem_list);
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

                    semAdapter = new SemAdapter(AddTeacher.this, sem_list);
                    binding.semesterSpinner.setAdapter(semAdapter);
                    semAdapter.notifyDataSetChanged();
                    binding.semesterSpinner.setOnItemSelectedListener(new AddTeacher.SemlistClick());

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
                semtId = data.getSeId() + "";
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {


            try {
                // view.updateDate(minYear,monthOfYear,dayOfMonth);


                Calendar myCalendar = Calendar.getInstance();
                int Currentyear = myCalendar.get(Calendar.YEAR);

                if (Currentyear < year) {


                    SnakBar("invalid date of birth");

                } else {
                    myCalendar.set(Calendar.YEAR, year);

                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "dd-MM-yyyy"; //In which you need put here
                    SimpleDateFormat sdf = null;

                    sdf = new SimpleDateFormat(myFormat, Locale.US);

                    binding.dob.setError(null);
                    binding.dob.setText(sdf.format(myCalendar.getTime()));
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    };

}
