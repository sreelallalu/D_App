package com.dapp.dapplication;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.RadioButton;

import com.dapp.dapplication.Helper.SharedHelper;
import com.dapp.dapplication.adapter.BranchAdapter;
import com.dapp.dapplication.adapter.SemAdapter;
import com.dapp.dapplication.databinding.ActivityStudentRegisterBinding;
import com.dapp.dapplication.model.BatchModel;
import com.dapp.dapplication.model.SemModel;
import com.dapp.dapplication.service.RestBuilderPro;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentRegister extends BaseActivity {

    private ActivityStudentRegisterBinding binding;
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_student_register);
        getBranches();
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.name.getText().toString();
                String mobile = binding.Mobile.getText().toString();
                String fathername = binding.fathername.getText().toString();
                String regId = binding.regid.getText().toString();
                String pass = binding.password.getText().toString();
                String email = binding.email.getText().toString();
                String dob = binding.dob.getText().toString();
                String address = binding.address.getText().toString();
                String gender = ((RadioButton) findViewById(binding.gender.getCheckedRadioButtonId())).getText().toString();

                boolean check = true;
                if (name.isEmpty()) {

                    check = false;
                    binding.name.setError("Invalid name");
                }
                if (mobile.isEmpty() || mobile.length() < 10) {
                    check = false;
                    binding.Mobile.setError("Invalid mobile");

                }
                if (fathername.isEmpty()) {
                    check = false;
                    binding.fathername.setError("Invalid father name");


                }
                if (regId.isEmpty()) {
                    check = false;
                    binding.regid.setError("Invalid register id");

                }
                if (pass.isEmpty()) {
                    check = false;
                    binding.password.setError("Invalid password");


                }
                if (dob.isEmpty()) {
                    check = false;
                    binding.fathername.setError("Invalid dob");

                }if (address.isEmpty()) {
                    check = false;
                    binding.address.setError("Invalid address");

                }
                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    check = false;
                    binding.fathername.setError("Invalid email");

                }
                if(check)
                {
                    final ProgressDialog dialog = new ProgressDialog(StudentRegister.this);
                    dialog.setMessage("Loading...");
                    dialog.show();

                    HashMap<String,String > hashMap=new HashMap<>();
                    hashMap.put("st_name",name);
                    hashMap.put("st_mobile",mobile);
                    hashMap.put("st_fathername",fathername);
                    hashMap.put("st_regid",regId);
                    hashMap.put("st_dob",dob);
                    hashMap.put("st_password",pass);
                    hashMap.put("st_email",email);
                    hashMap.put("st_branchid",address);
                    hashMap.put("st_gender",gender);
                    hashMap.put("st_branchid",batchId);
                    hashMap.put("st_semid",semtId);

                 RestBuilderPro.getService().stud_register(hashMap).enqueue(new Callback<ResponseBody>() {
                     @Override
                     public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                         dialog.dismiss();

                         if (response.isSuccessful()) {
                             try {

                                 JSONObject jsonObject = new JSONObject(response.body().string());
                                 int succ = jsonObject.getInt("success");

                                 if (succ == 1) {
                                     JSONArray jsonArray = jsonObject.getJSONArray("data");
                                     final JSONObject jsonObject1 = jsonArray.getJSONObject(0);


                                     SnakBarCallback("Login success", new CallbackSnak() {
                                         @Override
                                         public void back() {



                                                 SharedHelper sharedHelper = new SharedHelper(StudentRegister.this);
                                                 sharedHelper.setRegType("student");
                                                 sharedHelper.setLoginCheck(true);
                                                 sharedHelper.setStudent(jsonObject1.toString());
                                                 startActivity(new Intent(StudentRegister.this, StudentDashBoard.class));

                                                 finish();

                                             }






                                     });


                                 } else {
                                     SnakBar("check user credentials");
                                 }

                             } catch (Exception e) {
                                 e.printStackTrace();

                             }
                         }
                     }

                     @Override
                     public void onFailure(Call<ResponseBody> call, Throwable t) {
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

    private void getBranches() {

        binding.branchLoading.setVisibility(View.VISIBLE);

        RestBuilderPro.getService().brachlist().enqueue(new Callback<BatchModel>() {
            @Override
            public void onResponse(Call<BatchModel> call, Response<BatchModel> response) {
                binding.branchLoading.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    BatchModel model = response.body();

                    batch_list = model.getData();

                    brachadapter = new BranchAdapter(StudentRegister.this, batch_list);
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
        semAdapter = new SemAdapter(StudentRegister.this, sem_list);
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

                    semAdapter = new SemAdapter(StudentRegister.this, sem_list);
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
                semtId = data.getSeId() + "";
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
