package com.amazon.portaldevelopment.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Generated("jsonschema2pojo")
public class ProjectResponse {

    @SerializedName("data")
    @Expose
    private List<Project> data;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProjectResponse() {
    }

    /**
     *
     * @param data
     */
    public ProjectResponse(List<Project> data) {
        super();
        this.data = data;
    }

    public List<Project> getData() {
        return data;
    }

    public void setData(List<Project> data) {
        this.data = data;
    }

}