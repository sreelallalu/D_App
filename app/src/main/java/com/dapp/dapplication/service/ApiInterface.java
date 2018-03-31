package com.dapp.dapplication.service;

import com.dapp.dapplication.model.AddSuccess;
import com.dapp.dapplication.model.BatchModel;
import com.dapp.dapplication.model.SemModel;
import com.dapp.dapplication.model.StudentModel;
import com.dapp.dapplication.model.SubjectModel;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by sreelal on 6/12/17.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> login
            (@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("stud_register.php")
    Call<ResponseBody> stud_register(@FieldMap HashMap<String, String> hashMap);


    @FormUrlEncoded
    @POST("profile_updation.php")
    Call<ResponseBody> profileupdation(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("semlist.php")
    Call<SemModel> semesterlist(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("subjectlist.php")
    Call<SubjectModel> subjectlist(@FieldMap HashMap<String, String> hashMap);


    @GET("branchlist.php")
    Call<BatchModel> brachlist();

    @GET("feedback_view.php")
    Call<ResponseBody> feddbackview();


    @FormUrlEncoded
    @POST("attendance.php")
    Call<AddSuccess> attendance(@FieldMap HashMap<String, String> hashMap);


    @FormUrlEncoded
    @POST("addnotes.php")
    Call<AddSuccess> addnotes(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("addsyllabus.php")
    Call<AddSuccess> addsyllubus(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("addtimetable.php")
    Call<AddSuccess> addtimetable(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("addtutorial.php")
    Call<AddSuccess> addtutorial(@FieldMap HashMap<String, String> hashMap);


    @FormUrlEncoded
    @POST("assignment.php")
    Call<AddSuccess> assignment(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("notification.php")
    Call<AddSuccess> notification(@FieldMap HashMap<String, String> hashMap);

    @GET("category_list.php")
    Call<ResponseBody> catgerylist();

    @FormUrlEncoded
    @POST("pass_change.php")
    Call<ResponseBody> changepass(@Field("user_id") String id,
                                  @Field("user_password") String newpass
    );

    @FormUrlEncoded
    @POST("studentlist.php")
    Call<StudentModel> studentlist(@FieldMap HashMap<String, String> hashMap
    );

    @FormUrlEncoded
    @POST("forgotpass.php")
    Call<ResponseBody> forgotpass(@Field("user_email") String email
    );

    @FormUrlEncoded
    @POST("request.php")
    Call<ResponseBody> requestsend(@FieldMap HashMap<String, String> hashMap);


    @FormUrlEncoded
    @POST("appointment_result.php")
    Call<ResponseBody> request_send(@Field("result_id") String id);


    @FormUrlEncoded
    @POST("stud_syllobus.php")
    Call<ResponseBody> viewsyllabus(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("stud_timetable.php")
    Call<ResponseBody> viewtimetable(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("stud_attendance.php")
    Call<ResponseBody> viewattendance(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("stud_notes.php")
    Call<ResponseBody> viewnotes(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("stud_tutorial.php")
    Call<ResponseBody> viewtutorial(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("stud_assignment.php")
    Call<ResponseBody> viewassignment(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("stud_notification.php")
    Call<ResponseBody> viewnotification(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("stud_sessinal.php")
    Call<ResponseBody> viewsessional(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("stud_profile.php")
    Call<ResponseBody> viewprofile(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("stud_feedback.php")
    Call<AddSuccess> feedbackpost(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("stud_updateprofile.php")
    Call<ResponseBody> updateprofile(@FieldMap HashMap<String, String> hashMap);


}
