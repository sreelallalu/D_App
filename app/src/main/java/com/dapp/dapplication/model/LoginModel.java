package com.dapp.dapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sreelal on 27/3/18.
 */


public class LoginModel {

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

        @SerializedName("ad_id")
        @Expose
        private Integer adId;
        @SerializedName("ad_name")
        @Expose
        private String adName;
        @SerializedName("ad_password")
        @Expose
        private String adPassword;

        public Integer getAdId() {
            return adId;
        }

        public void setAdId(Integer adId) {
            this.adId = adId;
        }

        public String getAdName() {
            return adName;
        }

        public void setAdName(String adName) {
            this.adName = adName;
        }

        public String getAdPassword() {
            return adPassword;
        }

        public void setAdPassword(String adPassword) {
            this.adPassword = adPassword;
        }

    }
}