package com.dapp.dapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sreelal on 27/3/18.
 */



public class SubjectModel {

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
    public class Datum {

        @SerializedName("su_id")
        @Expose
        private Integer suId;
        @SerializedName("su_branchid")
        @Expose
        private Integer suBranchid;
        @SerializedName("su_semid")
        @Expose
        private Integer suSemId;
        @SerializedName("su_name")
        @Expose
        private String suName;

        public Integer getSuId() {
            return suId;
        }

        public void setSuId(Integer suId) {
            this.suId = suId;
        }

        public Integer getSuBranchid() {
            return suBranchid;
        }

        public void setSuBranchid(Integer suBranchid) {
            this.suBranchid = suBranchid;
        }

        public Integer getSuSemId() {
            return suSemId;
        }

        public void setSuSemId(Integer suSemId) {
            this.suSemId = suSemId;
        }

        public String getSuName() {
            return suName;
        }

        public void setSuName(String suName) {
            this.suName = suName;
        }
    }
}