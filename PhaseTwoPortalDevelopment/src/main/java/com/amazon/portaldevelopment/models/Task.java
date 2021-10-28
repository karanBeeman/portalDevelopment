package com.amazon.portaldevelopment.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Task {

    @SerializedName("ID")
    @Expose
    private String ID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("objCode")
    @Expose
    private String objCode;
    @SerializedName("percentComplete")
    @Expose
    private Double percentComplete;
    @SerializedName("plannedCompletionDate")
    @Expose
    private String plannedCompletionDate;
    @SerializedName("plannedStartDate")
    @Expose
    private String plannedStartDate;
    @SerializedName("priority")
    @Expose
    private Integer priority;
    @SerializedName("progressStatus")
    @Expose
    private String progressStatus;
    @SerializedName("projectedCompletionDate")
    @Expose
    private String projectedCompletionDate;
    @SerializedName("projectedStartDate")
    @Expose
    private String projectedStartDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("taskNumber")
    @Expose
    private Integer taskNumber;
    @SerializedName("wbs")
    @Expose
    private String wbs;

    /**
     * No args constructor for use in serialization
     *
     */
    public Task() {
    }

    /**
     *
     * @param objCode
     * @param projectedCompletionDate
     * @param plannedCompletionDate
     * @param wbs
     * @param percentComplete
     * @param priority
     * @param plannedStartDate
     * @param name
     * @param progressStatus
     * @param ID
     * @param taskNumber
     * @param projectedStartDate
     * @param status
     */
    public Task(String ID, String name, String objCode, Double percentComplete, String plannedCompletionDate, String plannedStartDate, Integer priority, String progressStatus, String projectedCompletionDate, String projectedStartDate, String status, Integer taskNumber, String wbs) {
        super();
        this.ID = ID;
        this.name = name;
        this.objCode = objCode;
        this.percentComplete = percentComplete;
        this.plannedCompletionDate = plannedCompletionDate;
        this.plannedStartDate = plannedStartDate;
        this.priority = priority;
        this.progressStatus = progressStatus;
        this.projectedCompletionDate = projectedCompletionDate;
        this.projectedStartDate = projectedStartDate;
        this.status = status;
        this.taskNumber = taskNumber;
        this.wbs = wbs;
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

    public Double getPercentComplete() {
        return percentComplete;
    }

    public void setPercentComplete(Double percentComplete) {
        this.percentComplete = percentComplete;
    }

    public String getPlannedCompletionDate() {
        return plannedCompletionDate;
    }

    public void setPlannedCompletionDate(String plannedCompletionDate) {
        this.plannedCompletionDate = plannedCompletionDate;
    }

    public String getPlannedStartDate() {
        return plannedStartDate;
    }

    public void setPlannedStartDate(String plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public String getProjectedCompletionDate() {
        return projectedCompletionDate;
    }

    public void setProjectedCompletionDate(String projectedCompletionDate) {
        this.projectedCompletionDate = projectedCompletionDate;
    }

    public String getProjectedStartDate() {
        return projectedStartDate;
    }

    public void setProjectedStartDate(String projectedStartDate) {
        this.projectedStartDate = projectedStartDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(Integer taskNumber) {
        this.taskNumber = taskNumber;
    }

    public String getWbs() {
        return wbs;
    }

    public void setWbs(String wbs) {
        this.wbs = wbs;
    }

}
