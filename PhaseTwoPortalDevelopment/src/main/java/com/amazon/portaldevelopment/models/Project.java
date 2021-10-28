package com.amazon.portaldevelopment.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Project {

    @SerializedName("ID")
    @Expose
    private String ID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("objCode")
    @Expose
    private String objCode;
    @SerializedName("projectedStartDate")
    @Expose
    private String projectedStartDate;
    @SerializedName("projectedCompletionDate")
    @Expose
    private String projectedCompletionDate;
    @SerializedName("percentComplete")
    @Expose
    private Double percentComplete;
    @SerializedName("priority")
    @Expose
    private Integer priority;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * No args constructor for use in serialization
     *
     */
    public Project() {
    }

    /**
     *
     * @param objCode
     * @param name
     * @param projectedCompletionDate
     * @param id
     * @param percentComplete
     * @param priority
     * @param projectedStartDate
     * @param status
     */
    public Project(String id, String name, String objCode, String projectedStartDate, String projectedCompletionDate, Double percentComplete, Integer priority, String status) {
        super();
        this.ID = id;
        this.name = name;
        this.objCode = objCode;
        this.projectedStartDate = projectedStartDate;
        this.projectedCompletionDate = projectedCompletionDate;
        this.percentComplete = percentComplete;
        this.priority = priority;
        this.status = status;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjCode() {
        return objCode;
    }

    public void setObjCode(String objCode) {
        this.objCode = objCode;
    }

    public String getProjectedStartDate() {
        return projectedStartDate;
    }

    public void setProjectedStartDate(String projectedStartDate) {
        this.projectedStartDate = projectedStartDate;
    }

    public String getProjectedCompletionDate() {
        return projectedCompletionDate;
    }

    public void setProjectedCompletionDate(String projectedCompletionDate) {
        this.projectedCompletionDate = projectedCompletionDate;
    }

    public Double getPercentComplete() {
        return percentComplete;
    }

    public void setPercentComplete(Double percentComplete) {
        this.percentComplete = percentComplete;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}