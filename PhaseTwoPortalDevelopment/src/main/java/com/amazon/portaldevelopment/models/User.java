package com.amazon.portaldevelopment.models;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class User {

    @SerializedName("ID")
    @Expose
    private String ID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("objCode")
    @Expose
    private String objCode;
    @SerializedName("emailAddr")
    @Expose
    private String emailAddr;
    @SerializedName("licenseType")
    @Expose
    private Object licenseType;
    @SerializedName("myInfo")
    @Expose
    private String myInfo;
    @SerializedName("phoneNumber")
    @Expose
    private Object phoneNumber;
    @SerializedName("title")
    @Expose
    private Object title;
    @SerializedName("workHoursPerDay")
    @Expose
    private Double workHoursPerDay;


    public User() {
    }

    /**
     *
     * @param objCode
     * @param workHoursPerDay
     * @param licenseType
     * @param phoneNumber
     * @param myInfo
     * @param emailAddr
     * @param name
     * @param ID
     * @param title
     */
    public User(String ID, String name, String objCode, String emailAddr, Object licenseType, String myInfo, Object phoneNumber, Object title, Double workHoursPerDay) {
        super();
        this.ID = ID;
        this.name = name;
        this.objCode = objCode;
        this.emailAddr = emailAddr;
        this.licenseType = licenseType;
        this.myInfo = myInfo;
        this.phoneNumber = phoneNumber;
        this.title = title;
        this.workHoursPerDay = workHoursPerDay;
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

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public Object getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(Object licenseType) {
        this.licenseType = licenseType;
    }

    public String getMyInfo() {
        return myInfo;
    }

    public void setMyInfo(String myInfo) {
        this.myInfo = myInfo;
    }

    public Object getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Object phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public Double getWorkHoursPerDay() {
        return workHoursPerDay;
    }

    public void setWorkHoursPerDay(Double workHoursPerDay) {
        this.workHoursPerDay = workHoursPerDay;
    }

}
