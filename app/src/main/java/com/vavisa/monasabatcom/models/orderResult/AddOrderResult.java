package com.vavisa.monasabatcom.models.orderResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vavisa.monasabatcom.models.orderModels.ServicesOrder;

import java.util.ArrayList;

public class AddOrderResult {

    @SerializedName("OrderId")
    @Expose
    Integer OrderId;
    @SerializedName("ErrorId")
    @Expose
    Integer ErrorId;
    @SerializedName("NotAvailableServices")
    @Expose
    ArrayList<ServiceResultModel> notAvailableServices;
    @SerializedName("NotAvailableOffers")
    @Expose
    ArrayList<OfferOrderResult> notAvailableOffers;

    public Integer getOrderId() {
        return OrderId;
    }

    public void setOrderId(Integer orderId) {
        OrderId = orderId;
    }

    public Integer getErrorId() {
        return ErrorId;
    }

    public void setErrorId(Integer errorId) {
        ErrorId = errorId;
    }

    public ArrayList<ServiceResultModel> getNotAvailableServices() {
        return notAvailableServices;
    }

    public void setNotAvailableServices(ArrayList<ServiceResultModel> notAvailableServices) {
        this.notAvailableServices = notAvailableServices;
    }

    public ArrayList<OfferOrderResult> getNotAvailableOffers() {
        return notAvailableOffers;
    }

    public void setNotAvailableOffers(ArrayList<OfferOrderResult> notAvailableOffers) {
        this.notAvailableOffers = notAvailableOffers;
    }
}
