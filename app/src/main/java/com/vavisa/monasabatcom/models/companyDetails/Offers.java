package com.vavisa.monasabatcom.models.companyDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vavisa.monasabatcom.models.ServiceExtras;

import java.util.ArrayList;

/**
 * Created by jesus on 11/25/2018.
 */

public class Offers {

    //for show in appointment details
    private boolean isSelected = false;

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
    @SerializedName("ServiceAndPresentationMethodAR")
    @Expose
    private String serviceAndPresentationMethodAR;
    @SerializedName("ServiceAndPresentationMethodEN")
    @Expose
    private String serviceAndPresentationMethodEN;
    @SerializedName("PreparationTimeAR")
    @Expose
    private String preparationTimeAR;
    @SerializedName("PreparationTimeEN")
    @Expose
    private String preparationTimeEN;
    @SerializedName("RequirementsAR")
    @Expose
    private String requirementsAR;
    @SerializedName("RequirementsEN")
    @Expose
    private String requirementsEN;
    @SerializedName("StayDurationAR")
    @Expose
    private String stayDurationAR;
    @SerializedName("StayDurationEN")
    @Expose
    private String stayDurationEN;
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
    private Float DepositPercentage;
    @SerializedName("Price")
    @Expose
    private Float price;
    @SerializedName("Photos")
    @Expose
    private ArrayList<Photos> photo;
    @SerializedName("WorkingDaysAndHours")
    @Expose
    private ArrayList<WorkingDaysAndHours> WorkingDaysAndHours;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

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

    public String getServiceAndPresentationMethodAR() {
        return serviceAndPresentationMethodAR;
    }

    public void setServiceAndPresentationMethodAR(String serviceAndPresentationMethodAR) {
        this.serviceAndPresentationMethodAR = serviceAndPresentationMethodAR;
    }

    public String getServiceAndPresentationMethodEN() {
        return serviceAndPresentationMethodEN;
    }

    public void setServiceAndPresentationMethodEN(String serviceAndPresentationMethodEN) {
        this.serviceAndPresentationMethodEN = serviceAndPresentationMethodEN;
    }

    public String getPreparationTimeAR() {
        return preparationTimeAR;
    }

    public void setPreparationTimeAR(String preparationTimeAR) {
        this.preparationTimeAR = preparationTimeAR;
    }

    public String getPreparationTimeEN() {
        return preparationTimeEN;
    }

    public void setPreparationTimeEN(String preparationTimeEN) {
        this.preparationTimeEN = preparationTimeEN;
    }

    public String getRequirementsAR() {
        return requirementsAR;
    }

    public void setRequirementsAR(String requirementsAR) {
        this.requirementsAR = requirementsAR;
    }

    public String getRequirementsEN() {
        return requirementsEN;
    }

    public void setRequirementsEN(String requirementsEN) {
        this.requirementsEN = requirementsEN;
    }

    public String getStayDurationAR() {
        return stayDurationAR;
    }

    public void setStayDurationAR(String stayDurationAR) {
        this.stayDurationAR = stayDurationAR;
    }

    public String getStayDurationEN() {
        return stayDurationEN;
    }

    public void setStayDurationEN(String stayDurationEN) {
        this.stayDurationEN = stayDurationEN;
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

    public Float getDepositPercentage() {
        return DepositPercentage;
    }

    public void setDepositPercentage(Float depositPercentage) {
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

    public ArrayList<com.vavisa.monasabatcom.models.companyDetails.WorkingDaysAndHours> getWorkingDaysAndHours() {
        return WorkingDaysAndHours;
    }

    public void setWorkingDaysAndHours(ArrayList<com.vavisa.monasabatcom.models.companyDetails.WorkingDaysAndHours> workingDaysAndHours) {
        WorkingDaysAndHours = workingDaysAndHours;
    }
}
