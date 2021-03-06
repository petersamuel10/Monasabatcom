package com.vavisa.monasabatcom.models.companyDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jesus on 11/24/2018.
 */

public class CompanyDetailsModel {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("NameAR")
    @Expose
    private String nameAR;
    @SerializedName("NameEN")
    @Expose
    private String nameEN;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("WorkingFromTime")
    @Expose
    private String workingFromTime;
    @SerializedName("WorkingToTime")
    @Expose
    private String workingToTime;
    @SerializedName("AppointmentDuration")
    @Expose
    private Integer appointmentDuration;
    @SerializedName("AddressAR")
    @Expose
    private String addressAR;
    @SerializedName("AddressEN")
    @Expose
    private String addressEN;
    @SerializedName("AboutAR")
    @Expose
    private String aboutAR;
    @SerializedName("AboutEN")
    @Expose
    private String aboutEN;
    @SerializedName("TermsAndConditionsAR")
    @Expose
    private String TermsAndConditionsAR;
    @SerializedName("TermsAndConditionsEn")
    @Expose
    private String TermsAndConditionsEn;
    @SerializedName("Rating")
    @Expose
    private Float rating;
    @SerializedName("RatingCount")
    @Expose
    private Integer ratingCount;
    @SerializedName("Logo")
    @Expose
    private String logo;
    @SerializedName("IsOpen")
    @Expose
    private Boolean isOpen;
    @SerializedName("PaymentMethods")
    @Expose
    private ArrayList<PaymentMethod> paymentMethod;
    @SerializedName("Photos")
    @Expose
    private ArrayList<Photos> photo;
    @SerializedName("IsFavorite")
    @Expose
    private Boolean isFavourite;
    @SerializedName("Services")
    @Expose
    private ArrayList<Services> services;
    @SerializedName("Offers")
    @Expose
    private ArrayList<Offers> offers;

    public CompanyDetailsModel() {
    }

    public CompanyDetailsModel(Integer id, String nameAR, String nameEN, String email, String phone, String mobile, String workingFromTime, String workingToTime, Integer appointmentDuration, String addressAR, String addressEN, String aboutAR, String aboutEN, Float rating, Integer ratingCount, String logo, Boolean isOpen, ArrayList<PaymentMethod> paymentMethod, ArrayList<Photos> photo, Boolean isFavourite, ArrayList<Services> services, ArrayList<Offers> offers) {
        this.id = id;
        this.nameAR = nameAR;
        this.nameEN = nameEN;
        this.email = email;
        this.phone = phone;
        this.mobile = mobile;
        this.workingFromTime = workingFromTime;
        this.workingToTime = workingToTime;
        this.appointmentDuration = appointmentDuration;
        this.addressAR = addressAR;
        this.addressEN = addressEN;
        this.aboutAR = aboutAR;
        this.aboutEN = aboutEN;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.logo = logo;
        this.isOpen = isOpen;
        this.paymentMethod = paymentMethod;
        this.photo = photo;
        this.isFavourite = isFavourite;
        this.services = services;
        this.offers = offers;
    }

    public CompanyDetailsModel(Integer id, String nameAR, String nameEN, String email, String phone, String mobile, String workingFromTime, String workingToTime, Integer appointmentDuration, String addressAR, String addressEN, String aboutAR, String aboutEN, Float rating, Integer ratingCount, String logo, Boolean isOpen, ArrayList<PaymentMethod> paymentMethod, ArrayList<Photos> photo, Boolean isFavourite, ArrayList<Services> services) {
        this.id = id;
        this.nameAR = nameAR;
        this.nameEN = nameEN;
        this.email = email;
        this.phone = phone;
        this.mobile = mobile;
        this.workingFromTime = workingFromTime;
        this.workingToTime = workingToTime;
        this.appointmentDuration = appointmentDuration;
        this.addressAR = addressAR;
        this.addressEN = addressEN;
        this.aboutAR = aboutAR;
        this.aboutEN = aboutEN;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.logo = logo;
        this.isOpen = isOpen;
        this.paymentMethod = paymentMethod;
        this.photo = photo;
        this.isFavourite = isFavourite;
        this.services = services;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameAR() {
        return nameAR;
    }

    public void setNameAR(String nameAR) {
        this.nameAR = nameAR;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWorkingFromTime() {
        return workingFromTime;
    }

    public void setWorkingFromTime(String workingFromTime) {
        this.workingFromTime = workingFromTime;
    }

    public String getWorkingToTime() {
        return workingToTime;
    }

    public void setWorkingToTime(String workingToTime) {
        this.workingToTime = workingToTime;
    }

    public Integer getAppointmentDuration() {
        return appointmentDuration;
    }

    public void setAppointmentDuration(Integer appointmentDuration) {
        this.appointmentDuration = appointmentDuration;
    }

    public String getAddressAR() {
        return addressAR;
    }

    public void setAddressAR(String addressAR) {
        this.addressAR = addressAR;
    }

    public String getAddressEN() {
        return addressEN;
    }

    public void setAddressEN(String addressEN) {
        this.addressEN = addressEN;
    }

    public String getAboutAR() {
        return aboutAR;
    }

    public void setAboutAR(String aboutAR) {
        this.aboutAR = aboutAR;
    }

    public String getAboutEN() {
        return aboutEN;
    }

    public void setAboutEN(String aboutEN) {
        this.aboutEN = aboutEN;
    }

    public String getTermsAndConditionsAR() {
        return TermsAndConditionsAR;
    }

    public void setTermsAndConditionsAR(String termsAndConditionsAR) {
        TermsAndConditionsAR = termsAndConditionsAR;
    }

    public String getTermsAndConditionsEn() {
        return TermsAndConditionsEn;
    }

    public void setTermsAndConditionsEn(String termsAndConditionsEn) {
        TermsAndConditionsEn = termsAndConditionsEn;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public ArrayList<PaymentMethod> getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(ArrayList<PaymentMethod> paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public ArrayList<Photos> getPhoto() {
        return photo;
    }

    public void setPhoto(ArrayList<Photos> photo) {
        this.photo = photo;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
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







