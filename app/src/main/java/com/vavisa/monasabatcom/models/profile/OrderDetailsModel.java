package com.vavisa.monasabatcom.models.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vavisa.monasabatcom.models.companyDetails.Offers;
import com.vavisa.monasabatcom.models.companyDetails.Services;
import com.vavisa.monasabatcom.models.profile.Address;

import java.util.ArrayList;

public class OrderDetailsModel {

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
    @SerializedName("TotalAmount")
    @Expose
    Float TotalAmount;
    @SerializedName("DeliveryCost")
    @Expose
    Float DeliveryCost;
    @SerializedName("DueAmount")
    @Expose
    Float DueAmount;
    @SerializedName("PaidAmount")
    @Expose
    Float PaidAmount;
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
    ArrayList<ServiceDetails> services;
    @SerializedName("Offers")
    @Expose
    ArrayList<OfferDetails> offers;

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

    public Float getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        TotalAmount = totalAmount;
    }

    public Float getDeliveryCost() {
        return DeliveryCost;
    }

    public void setDeliveryCost(Float deliveryCost) {
        DeliveryCost = deliveryCost;
    }

    public Float getDueAmount() {
        return DueAmount;
    }

    public void setDueAmount(Float dueAmount) {
        DueAmount = dueAmount;
    }

    public Float getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(Float paidAmount) {
        PaidAmount = paidAmount;
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

    public ArrayList<ServiceDetails> getServices() {
        return services;
    }

    public void setServices(ArrayList<ServiceDetails> services) {
        this.services = services;
    }

    public ArrayList<OfferDetails> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<OfferDetails> offers) {
        this.offers = offers;
    }
}
