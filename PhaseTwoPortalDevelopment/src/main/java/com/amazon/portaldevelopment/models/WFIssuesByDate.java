package com.amazon.portaldevelopment.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WFIssuesByDate {
	
	@JsonProperty("ID")
	private String ID;
	
	private String objCode;
	
	private String name;
	
	private String description;
	
	private String plannedStartDate;

	private String plannedCompletionDate;
	
	private Double percentComplete;
	
	private String status;
	
	private String ownerID;
	
	private String managerName;
	
	private String taskName;
	
	private String assetTitle;
	
	private String dueDate;
	
	public WFIssuesByDate() {
		
	}

	public WFIssuesByDate(String objCode, String name, String description, String plannedStartDate,
			String plannedCompletionDate, String iD, Double percentComplete, String status) {
		super();
		this.objCode = objCode;
		this.name = name;
		this.description = description;
		this.plannedStartDate = plannedStartDate;
		this.plannedCompletionDate = plannedCompletionDate;
		ID = iD;
		this.percentComplete = percentComplete;
		this.status = status;
	}

	public String getObjCode() {
		return objCode;
	}

	public void setObjCode(String objCode) {
		this.objCode = objCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlannedCompletionDate() {
		return plannedCompletionDate;
	}

	public void setPlannedCompletionDate(String plannedCompletionDate) {
		this.plannedCompletionDate = plannedCompletionDate;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public Double getPercentComplete() {
		return percentComplete;
	}

	public void setPercentComplete(Double percentComplete) {
		this.percentComplete = percentComplete;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlannedStartDate() {
		return plannedStartDate;
	}

	public void setPlannedStartDate(String plannedStartDate) {
		this.plannedStartDate = plannedStartDate;
	}

	public String getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getAssetTitle() {
		return assetTitle;
	}

	public void setAssetTitle(String assetTitle) {
		this.assetTitle = assetTitle;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public String toString() {
		return "WFIssuesByDate [ID=" + ID + ", objCode=" + objCode + ", name=" + name + ", description=" + description
				+ ", plannedStartDate=" + plannedStartDate + ", plannedCompletionDate=" + plannedCompletionDate
				+ ", percentComplete=" + percentComplete + ", status=" + status + ", ownerID=" + ownerID
				+ ", managerName=" + managerName + ", taskName=" + taskName + ", assetTitle=" + assetTitle
				+ ", dueDate=" + dueDate + "]";
	}

}
	