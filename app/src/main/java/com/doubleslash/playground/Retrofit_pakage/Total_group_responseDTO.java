package com.doubleslash.playground.Retrofit_pakage;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Total_group_responseDTO {

    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("data")
    @Expose
    private List<Total_group_dataDTO> data = null;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<Total_group_dataDTO> getData() {
        return data;
    }

    public void setData(List<Total_group_dataDTO> data) {
        this.data = data;
    }

}
