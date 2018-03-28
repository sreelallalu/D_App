package com.dapp.dapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sreelal on 27/3/18.
 */




public class BatchModel {

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

        @SerializedName("br_id")
        @Expose
        private Integer brId;
        @SerializedName("br_name")
        @Expose
        private String brName;

        public Integer getBrId() {
            return brId;
        }

        public void setBrId(Integer brId) {
            this.brId = brId;
        }

        public String getBrName() {
            return brName;
        }

        public void setBrName(String brName) {
            this.brName = brName;
        }

    }

}