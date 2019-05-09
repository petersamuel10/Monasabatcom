package com.vavisa.monasabatcom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vavisa.monasabatcom.models.companyDetails.Photos;
import com.vavisa.monasabatcom.models.companyDetails.WorkingDaysAndHours;
import com.vavisa.monasabatcom.models.companyDetails.WorkingHours;

import java.util.ArrayList;

/**
 * Created by jesus on 11/24/2018.
 */

public class Services {

    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("NameAR")
    @Expose
    private String NameAR;
    @SerializedName("NameEN")
    @Expose
    private String NameEN;
    @SerializedName("DescriptionAR")
    @Expose
    private String DescriptionAR;
    @SerializedName("DescriptionEN")
    @Expose
    private String DescriptionEN;
    @SerializedName("ServiceAndPresentationMethod")
    @Expose
    private String serviceAndPresentationMethod;
    @SerializedName("PreparationTime")
    @Expose
    private String PreparationTime;
    @SerializedName("Requirements")
    @Expose
    private String Requirements;
    @SerializedName("StayDuration")
    @Expose
    private String StayDuration;
    @SerializedName("DeliveryTime")
    @Expose
    private String DeliveryTime;
    @SerializedName("WomenService")
    @Expose
    private Boolean WomenService;
    @SerializedName("PaymentTypeId")
    @Expose
    private int PaymentTypeId;
    @SerializedName("DepositPercentage")
    @Expose
    private int DepositPercentage;
    @SerializedName("Price")
    @Expose
    private Float price;
    @SerializedName("Photos")
    @Expose
    private ArrayList<Photos> photo;
    @SerializedName("Extras")
    @Expose
    private ArrayList<ServiceExtras> serviceExtras;
    @SerializedName("WorkingDaysAndHours")
    @Expose
    private ArrayList<WorkingDaysAndHours> WorkingDaysAndHours;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameAR() {
        return NameAR;
    }

    public void setNameAR(String nameAR) {
        NameAR = nameAR;
    }

    public String getNameEN() {
        return NameEN;
    }

    public void setNameEN(String nameEN) {
        NameEN = nameEN;
    }

    public String getDescriptionAR() {
        return DescriptionAR;
    }

    public void setDescriptionAR(String descriptionAR) {
        DescriptionAR = descriptionAR;
    }

    public String getDescriptionEN() {
        return DescriptionEN;
    }

    public void setDescriptionEN(String descriptionEN) {
        DescriptionEN = descriptionEN;
    }

    public String getPreparationTime() {
        return PreparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        PreparationTime = preparationTime;
    }

    public String getRequirements() {
        return Requirements;
    }

    public void setRequirements(String requirements) {
        Requirements = requirements;
    }

    public String getStayDuration() {
        return StayDuration;
    }

    public void setStayDuration(String stayDuration) {
        StayDuration = stayDuration;
    }

    public String getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        DeliveryTime = deliveryTime;
    }

    public Boolean getWomenService() {
        return WomenService;
    }

    public void setWomenService(Boolean womenService) {
        WomenService = womenService;
    }

    public int getPaymentTypeId() {
        return PaymentTypeId;
    }

    public void setPaymentTypeId(int paymentTypeId) {
        PaymentTypeId = paymentTypeId;
    }

    public int getDepositPercentage() {
        return DepositPercentage;
    }

    public void setDepositPercentage(int depositPercentage) {
        DepositPercentage = depositPercentage;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public ArrayList<Photos> getPhoto() {
        return photo;
    }

    public void setPhoto(ArrayList<Photos> photo) {
        this.photo = photo;
    }

    public ArrayList<ServiceExtras> getServiceExtras() {
        return serviceExtras;
    }

    public void setServiceExtras(ArrayList<ServiceExtras> serviceExtras) {
        this.serviceExtras = serviceExtras;
    }

    public String getServiceAndPresentationMethod() {
        return serviceAndPresentationMethod;
    }

    public void setServiceAndPresentationMethod(String serviceAndPresentationMethod) {
        this.serviceAndPresentationMethod = serviceAndPresentationMethod;
    }

    public ArrayList<com.vavisa.monasabatcom.models.companyDetails.WorkingDaysAndHours> getWorkingDaysAndHours() {
        return WorkingDaysAndHours;
    }

    public void setWorkingDaysAndHours(ArrayList<com.vavisa.monasabatcom.models.companyDetails.WorkingDaysAndHours> workingDaysAndHours) {
        WorkingDaysAndHours = workingDaysAndHours;
    }
}
