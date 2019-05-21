package com.vavisa.monasabatcom.models.orderResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceResultModel {

    @SerializedName("ServiceId")
    @Expose
    Integer ServiceId;
    @SerializedName("ServiceNameAR")
    @Expose
    String ServiceNameAR;
    @SerializedName("ServiceNameEN")
    @Expose
    String ServiceNameEN;
    @SerializedName("ErrorMessage")
    @Expose
    String ErrorMessage;

    public Integer getServiceId() {
        return ServiceId;
    }

    public void setServiceId(Integer serviceId) {
        ServiceId = serviceId;
    }

    public String getServiceNameAR() {
        return ServiceNameAR;
    }

    public void setServiceNameAR(String serviceNameAR) {
        ServiceNameAR = serviceNameAR;
    }

    public String getServiceNameEN() {
        return ServiceNameEN;
    }

    public void setServiceNameEN(String serviceNameEN) {
        ServiceNameEN = serviceNameEN;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}
