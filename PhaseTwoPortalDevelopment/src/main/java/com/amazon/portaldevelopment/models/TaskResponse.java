package com.amazon.portaldevelopment.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class TaskResponse {

    @SerializedName("data")
    @Expose
    private List<Task> data;

    public TaskResponse() {
    }

    public TaskResponse(List<Task> data) {
        super();
        this.data = data;
    }

    public List<Task> getData() {
        return data;
    }

    public void setData(List<Task> data) {
        this.data = data;
    }

}