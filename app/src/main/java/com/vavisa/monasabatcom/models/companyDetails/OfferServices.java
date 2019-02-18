package com.vavisa.monasabatcom.models.companyDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jesus on 11/25/2018.
 */

public class OfferServices {

    //for show in appointment details
    private boolean isSelected = false;
    String _fsNameAR;
    String _fsNameEN;

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("NameAR")
    @Expose
    private String NameAR;
    @SerializedName("NameEN")
    @Expose
    private String NameEN;
    @SerializedName("Price")
    @Expose
    private Float price;

    public OfferServices() {
    }

    public OfferServices(Integer id, String nameAR, String nameEN, Float price) {
        this.id = id;
        NameAR = nameAR;
        NameEN = nameEN;
        this.price = price;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String get_fsNameAR() {
        return _fsNameAR;
    }

    public void set_fsNameAR(String _fsNameAR) {
        this._fsNameAR = _fsNameAR;
    }

    public String get_fsNameEN() {
        return _fsNameEN;
    }

    public void set_fsNameEN(String _fsNameEN) {
        this._fsNameEN = _fsNameEN;
    }
}
