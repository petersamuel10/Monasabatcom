package com.vavisa.monasabatcom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vavisa.monasabatcom.models.companyDetails.Offers;
import com.vavisa.monasabatcom.models.companyDetails.Services;

import java.util.ArrayList;

public class OrderDetails {

    @SerializedName("CompanyNameAR")
    @Expose
    String CompanyNameAR;
    @SerializedName("CompanyNameEN")
    @Expose
    String CompanyNameEN;
    @SerializedName("StatusNameAR")
    @Expose
    String StatusNameAR;
    @SerializedName("StatusNameEN")
    @Expose
    String StatusNameEN;
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
    @SerializedName("PaymentMethodNameAR")
    @Expose
    String PaymentMethodNameAR;
    @SerializedName("PaymentMethodNameEN")
    @Expose
    String PaymentMethodNameEN;
    @SerializedName("Address")
    @Expose
    Address address;
    @SerializedName("Services")
    @Expose
    ArrayList<Services> services;
    @SerializedName("Offers")
    @Expose
    ArrayList<Offers> offers;

    public OrderDetails() {
    }

    public OrderDetails(String companyNameAR, String companyNameEN, String statusNameAR, String statusNameEN, String appointmentDate, String startTime, String endTime, String paidAmount, String totalAmount, String paymentMethodNameAR, String paymentMethodNameEN, Address address, ArrayList<Services> services, ArrayList<Offers> offers) {
        CompanyNameAR = companyNameAR;
        CompanyNameEN = companyNameEN;
        StatusNameAR = statusNameAR;
        StatusNameEN = statusNameEN;
        AppointmentDate = appointmentDate;
        StartTime = startTime;
        EndTime = endTime;
        PaidAmount = paidAmount;
        TotalAmount = totalAmount;
        PaymentMethodNameAR = paymentMethodNameAR;
        PaymentMethodNameEN = paymentMethodNameEN;
        this.address = address;
        this.services = services;
        this.offers = offers;
    }

    public OrderDetails(String companyNameAR, String companyNameEN, String statusNameAR, String statusNameEN, String appointmentDate, String startTime, String endTime, String paidAmount, String totalAmount, String paymentMethodNameAR, String paymentMethodNameEN, Address address, ArrayList<Services> services) {
        CompanyNameAR = companyNameAR;
        CompanyNameEN = companyNameEN;
        StatusNameAR = statusNameAR;
        StatusNameEN = statusNameEN;
        AppointmentDate = appointmentDate;
        StartTime = startTime;
        EndTime = endTime;
        PaidAmount = paidAmount;
        TotalAmount = totalAmount;
        PaymentMethodNameAR = paymentMethodNameAR;
        PaymentMethodNameEN = paymentMethodNameEN;
        this.address = address;
        this.services = services;
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

    public String getPaymentMethodNameAR() {
        return PaymentMethodNameAR;
    }

    public void setPaymentMethodNameAR(String paymentMethodNameAR) {
        PaymentMethodNameAR = paymentMethodNameAR;
    }

    public String getPaymentMethodNameEN() {
        return PaymentMethodNameEN;
    }

    public void setPaymentMethodNameEN(String paymentMethodNameEN) {
        PaymentMethodNameEN = paymentMethodNameEN;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ArrayList<Services> getServices() {
        return services;
    }

    public void setServices(ArrayList<Services> services) {
        this.services = services;
    }

    public ArrayList<Offers> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<Offers> offers) {
        this.offers = offers;
    }
}
