package com.dapp.dapplication.admin_module;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dapp.dapplication.BaseActivity;
import com.dapp.dapplication.Helper.TimeChange;
import com.dapp.dapplication.R;
import com.dapp.dapplication.adapter.ViewAdapter;
import com.dapp.dapplication.databinding.StudentViewSyllabusBinding;
import com.dapp.dapplication.model.ViewModel;
import com.dapp.dapplication.service.RestBuilderPro;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackView extends BaseActivity {

    private StudentViewSyllabusBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.student_view_syllabus);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.studentRecycler.setLayoutManager(layoutManager);
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.show();


        RestBuilderPro.getService().feddbackview().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                dialog.dismiss();
                if (response.isSuccessful()) {

                    try {

                        ResponseBody respo = response.body();
                        String res=respo.string();
                        JSONObject jsonObject = new JSONObject(res);
                        Log.e("response", res.toString());

                        int succ = jsonObject.getInt("success");
                        if (succ == 1) {
                            List<ViewModel> viewlist=new ArrayList<>();
                            JSONArray jsonArray=jsonObject.getJSONArray("data");
                            if(jsonArray.length()!=0) {
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object=jsonArray.getJSONObject(i);
                                    String name=object.getString("fe_content");
                                    //  String content=object.getString("");
                                   // String link=object.getString("sy_data");
                                     String date=object.getString("fe_date");

                                    viewlist.add(new ViewModel(name,"", "", TimeChange.parseDateToddMMyyyy(date)));

                                }
                                ViewAdapter adapter=new ViewAdapter(viewlist,FeedbackView.this,3) ;
                                binding.studentRecycler.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }else{
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
                    dialog.dismiss();

            }
        });




    }
}
