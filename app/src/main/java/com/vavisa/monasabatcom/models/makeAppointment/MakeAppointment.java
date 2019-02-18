package com.vavisa.monasabatcom.models.makeAppointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MakeAppointment {

    @SerializedName("CompanyId")
    @Expose
    Integer companyId;
    @SerializedName("AppointmentDate")
    @Expose
    String appointmentDate;
    @SerializedName("UserId")
    @Expose
    Integer userId;
    @SerializedName("AddressId")
    @Expose
    int addressId;
    @SerializedName("PaymentMethodId")
    @Expose
    int paymentMethodId;
    @SerializedName("Services")
    @Expose
    ArrayList<ServicesOrder> servicesOrder;
    @SerializedName("Offers")
    @Expose
    ArrayList<OfferOrder> offerOrder;
    int total;

    public MakeAppointment() {
    }

    public MakeAppointment(Integer companyId, String appointmentDate, Integer userId, int addressId, int paymentMethodId, ArrayList<ServicesOrder> servicesOrder, ArrayList<OfferOrder> offerOrder) {
        this.companyId = companyId;
        this.appointmentDate = appointmentDate;
        this.userId = userId;
        this.addressId = addressId;
        this.paymentMethodId = paymentMethodId;
        this.servicesOrder = servicesOrder;
        this.offerOrder = offerOrder;
    }

    public MakeAppointment(Integer companyId, String appointmentDate, Integer userId, Integer addressId, Integer paymentMethodId, ArrayList<ServicesOrder> servicesOrder) {
        this.companyId = companyId;
        this.appointmentDate = appointmentDate;
        this.userId = userId;
        this.addressId = addressId;
        this.paymentMethodId = paymentMethodId;
        this.servicesOrder = servicesOrder;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public ArrayList<ServicesOrder> getServicesOrder() {
        return servicesOrder;
    }

    public void setServicesOrder(ArrayList<ServicesOrder> servicesOrder) {
        this.servicesOrder = servicesOrder;
    }

    public ArrayList<OfferOrder> getOfferOrder() {
        return offerOrder;
    }

    public void setOfferOrder(ArrayList<OfferOrder> offerOrder) {
        this.offerOrder = offerOrder;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}


