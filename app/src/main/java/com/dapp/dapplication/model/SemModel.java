package com.dapp.dapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sreelal on 27/3/18.
 */
public class SemModel {

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

@SerializedName("se_id")
@Expose
private Integer seId;
@SerializedName("se_branchid")
@Expose
private Integer seBranchid;
@SerializedName("se_name")
@Expose
private String seName;

public Integer getSeId() {
return seId;
}

public void setSeId(Integer seId) {
this.seId = seId;
}

public Integer getSeBranchid() {
return seBranchid;
}

public void setSeBranchid(Integer seBranchid) {
this.seBranchid = seBranchid;
}

public String getSeName() {
return seName;
}

public void setSeName(String seName) {
this.seName = seName;
}

}
}