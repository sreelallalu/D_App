package com.dapp.dapplication.student;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dapp.dapplication.BaseActivity;
import com.dapp.dapplication.Helper.SharedHelper;
import com.dapp.dapplication.R;
import com.dapp.dapplication.model.AddSuccess;
import com.dapp.dapplication.model.StudentModel;
import com.dapp.dapplication.service.RestBuilderPro;
import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedBackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_feed_back);
        Button button=findViewById(R.id.submit);
        final EditText editText=findViewById(R.id.feedback);
        SharedHelper sharedHelper=new SharedHelper(this);
        Gson gson = new Gson();
        String jsonInString =sharedHelper.getStudentDetails() ;
        final StudentModel.Datum user= gson.fromJson(jsonInString, StudentModel.Datum.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback=editText.getText().toString();
                if(feedback.isEmpty())
                {
                    editText.setError("Invalid feedback");
                }else{


                    final ProgressDialog dialog=new ProgressDialog(FeedBackActivity.this);
                    dialog.setMessage("Loading..");
                    dialog.show();

                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("st_id",user.getStId()+"");
                    hashMap.put("se_id",user.getStSemid()+"");
                    hashMap.put("br_id",user.getStBranchid()+"");
                    hashMap.put("feedback",feedback);

                    RestBuilderPro.getService().feedbackpost(hashMap).enqueue(new Callback<AddSuccess>() {
                        @Override
                        public void onResponse(Call<AddSuccess> call, Response<AddSuccess> response) {
                            dialog.dismiss();

                            if (response.isSuccessful()) {
                                try {

                                    AddSuccess data=response.body();

                                    if(data.getSuccess()==1)
                                    {

                                        SnakBarCallback("Success", new CallbackSnak() {
                                            @Override
                                            public void back() {

                                                finish();
                                            }
                                        });



                                    }else{
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

                        }
                    });

                }
            }
        });
    }
}
