package com.dapp.dapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sreelal on 27/3/18.
 */

public class StudentModel {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("data")
    @Expose
    private List<Datum> data = new ArrayList<Datum>();
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public class Datum  {

        @SerializedName("st_id")
        @Expose
        private Integer stId;
        @SerializedName("st_name")
        @Expose
        private String stName;
        @SerializedName("st_regid")
        @Expose
        private String stRegid;
        @SerializedName("st_semid")
        @Expose
        private Integer stSemid;
        @SerializedName("st_branchid")
        @Expose
        private Integer stBranchid;
        @SerializedName("st_address")
        @Expose
        private String stAddress;
        @SerializedName("st_mobile")
        @Expose
        private String stMobile;
        @SerializedName("st_gender")
        @Expose
        private String stGender;
        @SerializedName("st_dob")
        @Expose
        private String stDob;
        @SerializedName("st_password")
        @Expose
        private String stPassword;
        @SerializedName("st_email")
        @Expose
        private String stEmail;
        @SerializedName("st_photo")
        @Expose
        private String stPhoto;
        @SerializedName("st_fathername")
        @Expose
        private String stFathername;

        public Integer getStId() {
            return stId;
        }

        public void setStId(Integer stId) {
            this.stId = stId;
        }

        public String getStName() {
            return stName;
        }

        public void setStName(String stName) {
            this.stName = stName;
        }

        public String getStRegid() {
            return stRegid;
        }

        public void setStRegid(String stRegid) {
            this.stRegid = stRegid;
        }

        public Integer getStSemid() {
            return stSemid;
        }

        public void setStSemid(Integer stSemid) {
            this.stSemid = stSemid;
        }

        public Integer getStBranchid() {
            return stBranchid;
        }

        public void setStBranchid(Integer stBranchid) {
            this.stBranchid = stBranchid;
        }

        public String getStAddress() {
            return stAddress;
        }

        public void setStAddress(String stAddress) {
            this.stAddress = stAddress;
        }

        public String getStMobile() {
            return stMobile;
        }

        public void setStMobile(String stMobile) {
            this.stMobile = stMobile;
        }

        public String getStGender() {
            return stGender;
        }

        public void setStGender(String stGender) {
            this.stGender = stGender;
        }

        public String getStDob() {
            return stDob;
        }

        public void setStDob(String stDob) {
            this.stDob = stDob;
        }

        public String getStPassword() {
            return stPassword;
        }

        public void setStPassword(String stPassword) {
            this.stPassword = stPassword;
        }

        public String getStEmail() {
            return stEmail;
        }

        public void setStEmail(String stEmail) {
            this.stEmail = stEmail;
        }

        public String getStPhoto() {
            return stPhoto;
        }

        public void setStPhoto(String stPhoto) {
            this.stPhoto = stPhoto;
        }

        public String getStFathername() {
            return stFathername;
        }

        public void setStFathername(String stFathername) {
            this.stFathername = stFathername;
        }


    }

}