package com.vavisa.monasabatcom.models.orderResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferOrderResult {

    @SerializedName("OfferId")
    @Expose
    Integer OfferId;
    @SerializedName("OfferNameAR")
    @Expose
    String OfferNameAR;
    @SerializedName("OfferNameEN")
    @Expose
    String ServiceNameEN;
    @SerializedName("ErrorMessage")
    @Expose
    String ErrorMessage;

    public Integer getOfferId() {
        return OfferId;
    }

    public void setOfferId(Integer offerId) {
        OfferId = offerId;
    }

    public String getOfferNameAR() {
        return OfferNameAR;
    }

    public void setOfferNameAR(String offerNameAR) {
        OfferNameAR = offerNameAR;
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
