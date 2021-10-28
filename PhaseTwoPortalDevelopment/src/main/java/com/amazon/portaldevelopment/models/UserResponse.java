package com.amazon.portaldevelopment.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class UserResponse {

    @SerializedName("data")
    @Expose
    private List<User> data;

    public UserResponse() {
    }

    /**
     *
     * @param data
     */
    public UserResponse(List<User> data) {
        super();
        this.data = data;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

}