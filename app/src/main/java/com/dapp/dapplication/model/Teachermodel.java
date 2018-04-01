package com.dapp.dapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sreelal on 1/4/18.
 */

public class Teachermodel {

    @SerializedName("te_id")
    @Expose
    private Integer teId;
    @SerializedName("te_name")
    @Expose
    private String teName;
    @SerializedName("te_gender")
    @Expose
    private String teGender;
    @SerializedName("te_dob")
    @Expose
    private Integer teDob;
    @SerializedName("te_address")
    @Expose
    private Integer teAddress;
    @SerializedName("te_sem")
    @Expose
    private Integer teSem;
    @SerializedName("te_branch")
    @Expose
    private Integer teBranch;
    @SerializedName("te_password")
    @Expose
    private String tePassword;
    @SerializedName("te_teacherid")
    @Expose
    private String teTeacherid;

    public Integer getTeId() {
        return teId;
    }

    public void setTeId(Integer teId) {
        this.teId = teId;
    }

    public String getTeName() {
        return teName;
    }

    public void setTeName(String teName) {
        this.teName = teName;
    }

    public String getTeGender() {
        return teGender;
    }

    public void setTeGender(String teGender) {
        this.teGender = teGender;
    }

    public Integer getTeDob() {
        return teDob;
    }

    public void setTeDob(Integer teDob) {
        this.teDob = teDob;
    }

    public Integer getTeAddress() {
        return teAddress;
    }

    public void setTeAddress(Integer teAddress) {
        this.teAddress = teAddress;
    }

    public Integer getTeSem() {
        return teSem;
    }

    public void setTeSem(Integer teSem) {
        this.teSem = teSem;
    }

    public Integer getTeBranch() {
        return teBranch;
    }

    public void setTeBranch(Integer teBranch) {
        this.teBranch = teBranch;
    }

    public String getTePassword() {
        return tePassword;
    }

    public void setTePassword(String tePassword) {
        this.tePassword = tePassword;
    }

    public String getTeTeacherid() {
        return teTeacherid;
    }

    public void setTeTeacherid(String teTeacherid) {
        this.teTeacherid = teTeacherid;
    }

}


