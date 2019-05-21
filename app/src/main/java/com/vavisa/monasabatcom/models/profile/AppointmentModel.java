package com.vavisa.monasabatcom.models.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jesus on 11/21/2018.
 */

public class AppointmentModel {

    @SerializedName("Id")
    @Expose
    int id;
    @SerializedName("StatusNameAR")
    @Expose
    String statusNameAR;
    @SerializedName("StatusNameEN")
    @Expose
    String statusNameEN;
    @SerializedName("CompanyNameAR")
    @Expose
    String companyNameAR;
    @SerializedName("CompanyNameEN")
    @Expose
    String CompanyNameEN;
    @SerializedName("PaidAmount")
    @Expose
    String paidAmount;
    @SerializedName("DueAmount")
    @Expose
    String dueAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatusNameAR() {
        return statusNameAR;
    }

    public void setStatusNameAR(String statusNameAR) {
        this.statusNameAR = statusNameAR;
    }

    public String getStatusNameEN() {
        return statusNameEN;
    }

    public void setStatusNameEN(String statusNameEN) {
        this.statusNameEN = statusNameEN;
    }

    public String getCompanyNameAR() {
        return companyNameAR;
    }

    public void setCompanyNameAR(String companyNameAR) {
        this.companyNameAR = companyNameAR;
    }

    public String getCompanyNameEN() {
        return CompanyNameEN;
    }

    public void setCompanyNameEN(String companyNameEN) {
        CompanyNameEN = companyNameEN;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }
}
