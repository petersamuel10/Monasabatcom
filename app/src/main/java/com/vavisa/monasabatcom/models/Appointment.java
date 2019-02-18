package com.vavisa.monasabatcom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jesus on 11/21/2018.
 */

public class Appointment {

    @SerializedName("Id")
    @Expose
    int Id;
    @SerializedName("StatusNameAR")
    @Expose
    String StatusNameAR;
    @SerializedName("StatusNameEN")
    @Expose
    String StatusNameEN;
    @SerializedName("CompanyNameAR")
    @Expose
    String CompanyNameAR;
    @SerializedName("CompanyNameEN")
    @Expose
    String CompanyNameEN;
    @SerializedName("AppointmentDate")
    @Expose
    String AppointmentDate;
    @SerializedName("StartTime")
    @Expose
    String StartTime;
    @SerializedName("EndTime")
    @Expose
    String EndTime;
    @SerializedName("PaidAmount")
    @Expose
    String PaidAmount;
    @SerializedName("TotalAmount")
    @Expose
    String TotalAmount;

    public Appointment(int id, String statusNameAR, String statusNameEN, String companyNameAR,
                       String companyNameEN, String appointmentDate, String startTime, String endTime,
                       String paidAmount, String totalAmount) {
        Id = id;
        StatusNameAR = statusNameAR;
        StatusNameEN = statusNameEN;
        CompanyNameAR = companyNameAR;
        CompanyNameEN = companyNameEN;
        AppointmentDate = appointmentDate;
        StartTime = startTime;
        EndTime = endTime;
        PaidAmount = paidAmount;
        TotalAmount = totalAmount;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getStatusNameAR() {
        return StatusNameAR;
    }

    public void setStatusNameAR(String statusNameAR) {
        StatusNameAR = statusNameAR;
    }

    public String getStatusNameEN() {
        return StatusNameEN;
    }

    public void setStatusNameEN(String statusNameEN) {
        StatusNameEN = statusNameEN;
    }

    public String getCompanyNameAR() {
        return CompanyNameAR;
    }

    public void setCompanyNameAR(String companyNameAR) {
        CompanyNameAR = companyNameAR;
    }

    public String getCompanyNameEN() {
        return CompanyNameEN;
    }

    public void setCompanyNameEN(String companyNameEN) {
        CompanyNameEN = companyNameEN;
    }

    public String getAppointmentDate() {
        return AppointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        AppointmentDate = appointmentDate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        PaidAmount = paidAmount;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }
}
