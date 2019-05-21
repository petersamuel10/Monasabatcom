package com.vavisa.monasabatcom.models.orderModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderModel {

    @SerializedName("CompanyId")
    @Expose
    Integer companyId;
    @SerializedName("UserId")
    @Expose
    Integer userId;
    @SerializedName("AddressId")
    @Expose
    Integer addressId;
    @SerializedName("PaymentMethodId")
    @Expose
    Integer paymentMethodId;
    @SerializedName("DeliveryCost")
    @Expose
    Float DeliveryCost;
    @SerializedName("Services")
    @Expose
    ArrayList<ServicesOrder> servicesOrder;
    @SerializedName("Offers")
    @Expose
    ArrayList<OfferOrder> offerOrder;

    public OrderModel(Integer companyId, Integer userId, Integer addressId, Integer paymentMethodId,
                      Float deliveryCost, ArrayList<ServicesOrder> servicesOrder, ArrayList<OfferOrder> offerOrder) {
        this.companyId = companyId;
        this.userId = userId;
        this.addressId = addressId;
        this.paymentMethodId = paymentMethodId;
        DeliveryCost = deliveryCost;
        this.servicesOrder = servicesOrder;
        this.offerOrder = offerOrder;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    public Float getDeliveryCost() {
        return DeliveryCost;
    }

    public void setDeliveryCost(Float deliveryCost) {
        DeliveryCost = deliveryCost;
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
}


