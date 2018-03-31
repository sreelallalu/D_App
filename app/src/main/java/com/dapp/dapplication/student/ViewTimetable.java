package com.dapp.dapplication.student;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dapp.dapplication.BaseActivity;
import com.dapp.dapplication.Helper.SharedHelper;
import com.dapp.dapplication.Helper.TimeChange;
import com.dapp.dapplication.R;
import com.dapp.dapplication.adapter.ViewAdapter;
import com.dapp.dapplication.databinding.StudentViewSyllabusBinding;
import com.dapp.dapplication.model.StudentModel;
import com.dapp.dapplication.model.ViewModel;
import com.dapp.dapplication.service.RestBuilderPro;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTimetable extends BaseActivity {

    private StudentViewSyllabusBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.student_view_syllabus);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.studentRecycler.setLayoutManager(layoutManager);

        SharedHelper sharedHelper = new SharedHelper(this);
        Gson gson = new Gson();
        String jsonInString = sharedHelper.getStudentDetails();
        StudentModel.Datum user = gson.fromJson(jsonInString, StudentModel.Datum.class);
        getDetails(user);


    }

    private void getDetails(StudentModel.Datum user) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.show();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("br_id", user.getStBranchid() + "");
        hashMap.put("se_id", user.getStSemid() + "");
        hashMap.put("st_id", user.getStId() + "");

        RestBuilderPro.getService().viewtimetable(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dialog.dismiss();

                if (response.isSuccessful()) {
                    try {

                        ResponseBody respo = response.body();
                        String res = respo.string();
                        JSONObject jsonObject = new JSONObject(res);
                        Log.e("response", res.toString());

                        int succ = jsonObject.getInt("success");
                        if (succ == 1) {
                            List<ViewModel> viewlist = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (jsonArray.length() != 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    //   "ti_id":1,"ti_branchid":1,"ti_semid":1,"ti_title":"uua","ti_data":0,

                                    String name = object.getString("ti_title");
                                    //  String content=object.getString("");
                                    String link = object.getString("ti_data");
                                    String date = object.getString("ti_date");


                                    viewlist.add(new ViewModel(name, "", link, TimeChange.parseDateToddMMyyyy(date)));

                                }
                                ViewAdapter adapter = new ViewAdapter(viewlist, ViewTimetable.this, 1);
                                binding.studentRecycler.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                SnakBar("Record not found");
                            }

                        } else {
                            SnakBar("Record not found");


                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error", t.getMessage());
                dialog.dismiss();

            }
        });


    }
}
