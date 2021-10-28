package com.amazon.portaldevelopment.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="manager")
public class ManagerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer roleId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "mappingWith")
	private String mappingWith;
	
	@Column(name = "createdDate")
	private Date createdDate;
	
	@Column(name = "createdBy")
	private String createdBy;
	
	@Column(name = "updatedDate")
	private String updatedDate;
	
	@Column(name = "updatedBy")
	private String updatedBy;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMappingWith() {
		return mappingWith;
	}

	public void setMappingWith(String mappingWith) {
		this.mappingWith = mappingWith;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "ManagerEntity [roleId=" + roleId + ", name=" + name + ", mappingWith=" + mappingWith + ", createdDate="
				+ createdDate + ", createdBy=" + createdBy + ", updatedDate=" + updatedDate + ", updatedBy=" + updatedBy
				+ "]";
	}
	
}
