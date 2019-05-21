package com.vavisa.monasabatcom.models.orderModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vavisa.monasabatcom.models.profile.Address;
import com.vavisa.monasabatcom.models.companyDetails.PaymentMethod;

import java.util.ArrayList;

public class CartModel {

    // for show in cart activity
    private String company_name_ar;
    private String company_name_en;
    private String terms_ar;
    private String terms_en;
    private Address address;
    private Float total;
    private Boolean isDeposit = false;
    private Boolean isAdvance = false;
    private ArrayList<PaymentMethod> paymentMethod;


    public CartModel(Integer company_id, Integer userId, Integer addressId,
                     Integer paymentMethodId, Float deliveryCost, ArrayList<ServicesOrder> services,
                     ArrayList<OfferOrder> offers,ArrayList<PaymentMethod> paymentMethod) {
        this.company_id = company_id;
        this.userId = userId;
        this.addressId = addressId;
        this.paymentMethodId = paymentMethodId;
        this.deliveryCost = deliveryCost;
        this.services = services;
        this.paymentMethod = paymentMethod;
        this.offers = offers;
    }

    @SerializedName("CompanyId")
    @Expose
    private Integer company_id;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("AddressId")
    @Expose
    private Integer addressId;
    @SerializedName("PaymentMethodId")
    @Expose
    private Integer paymentMethodId;
    @SerializedName("DeliveryCost")
    @Expose
    private Float deliveryCost;
    @SerializedName("Services")
    @Expose
    private ArrayList<ServicesOrder> services;
    @SerializedName("Offers")
    @Expose
    private ArrayList<OfferOrder> offers;

    public String getCompany_name_ar() {
        return company_name_ar;
    }

    public void setCompany_name_ar(String company_name_ar) {
        this.company_name_ar = company_name_ar;
    }

    public String getCompany_name_en() {
        return company_name_en;
    }

    public void setCompany_name_en(String company_name_en) {
        this.company_name_en = company_name_en;
    }

    public String getTerms_ar() {
        return terms_ar;
    }

    public void setTerms_ar(String terms_ar) {
        this.terms_ar = terms_ar;
    }

    public String getTerms_en() {
        return terms_en;
    }

    public void setTerms_en(String terms_en) {
        this.terms_en = terms_en;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Boolean getDeposit() {
        return isDeposit;
    }

    public void setDeposit(Boolean deposit) {
        isDeposit = deposit;
    }

    public Boolean getAdvance() {
        return isAdvance;
    }

    public void setAdvance(Boolean advance) {
        isAdvance = advance;
    }

    public ArrayList<PaymentMethod> getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(ArrayList<PaymentMethod> paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
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
        return deliveryCost;
    }

    public void setDeliveryCost(Float deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public ArrayList<ServicesOrder> getServices() {
        return services;
    }

    public void setServices(ArrayList<ServicesOrder> services) {
        this.services = services;
    }

    public ArrayList<OfferOrder> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<OfferOrder> offers) {
        this.offers = offers;
    }
}
