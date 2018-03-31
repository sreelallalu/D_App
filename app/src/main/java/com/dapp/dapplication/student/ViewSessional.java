package com.dapp.dapplication.student;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dapp.dapplication.Helper.SharedHelper;
import com.dapp.dapplication.R;
import com.dapp.dapplication.databinding.StudentViewSyllabusBinding;
import com.dapp.dapplication.model.StudentModel;
import com.dapp.dapplication.service.RestBuilderPro;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewSessional extends AppCompatActivity {

    private StudentViewSyllabusBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.student_view_syllabus);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        binding.studentRecycler.setLayoutManager(layoutManager);

        SharedHelper sharedHelper=new SharedHelper(this);
        Gson gson = new Gson();
        String jsonInString =sharedHelper.getStudentDetails() ;
        StudentModel.Datum user= gson.fromJson(jsonInString, StudentModel.Datum.class);
        getDetails(user);



    }

    private void getDetails(StudentModel.Datum user) {
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.show();

        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("br_id",user.getStBranchid()+"");
        hashMap.put("se_id",user.getStSemid()+"");
        hashMap.put("st_id",user.getStId()+"");

        RestBuilderPro.getService().viewsessional(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dialog.dismiss();

                if(response.isSuccessful())
                {
                    try {
                        Log.e("response",response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error",t.getMessage());
                dialog.dismiss();

            }
        });



    }
}
