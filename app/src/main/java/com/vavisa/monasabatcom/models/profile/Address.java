package com.vavisa.monasabatcom.models.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("UserId")
    @Expose
    private int UserId;
    @SerializedName("AddressName")
    @Expose
    private String AddressName;
    @SerializedName("Phone")
    @Expose
    private String Phone;
    @SerializedName("CityId")
    @Expose
    private int CityId;
    @SerializedName("Block")
    @Expose
    private String Block;
    @SerializedName("Street")
    @Expose
    private String Street;
    @SerializedName("Building")
    @Expose
    private String Building;
    @SerializedName("Floor")
    @Expose
    private String Floor;
    @SerializedName("Apartment")
    @Expose
    private String Apartment;


    @SerializedName("Id")
    @Expose
    private int Id;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("GovernorateId")
    @Expose
    private int GovernorateId;
    @SerializedName("GovernorateNameAR")
    @Expose
    private String GovernorateNameAR;
    @SerializedName("GovernorateNameEN")
    @Expose
    private String GovernorateNameEN;
    @SerializedName("CityNameAR")
    @Expose
    private String CityNameAR;
    @SerializedName("CityNameEN")
    @Expose
    private String CityNameEN;
    @SerializedName("Notes")
    @Expose
    private String Notes;
    @SerializedName("FullAddressAR")
    @Expose
    private String FullAddressAR;
    @SerializedName("FullAddressEN")
    @Expose
    private String FullAddressEN;

    @SerializedName("AddressId")
    @Expose
    private int AddressId;

    public Address() {
    }

    public Address(int id, String name, int governorateId, String governorateNameAR, String governorateNameEN, int cityId, String cityNameAR, String cityNameEN,
                   String block, String street, String building, String floor, String apartment, String notes, String fullAddressAR, String fullAddressEN) {
        CityId = cityId;
        Block = block;
        Street = street;
        Building = building;
        Floor = floor;
        Apartment = apartment;
        Id = id;
        Name = name;
        GovernorateId = governorateId;
        GovernorateNameAR = governorateNameAR;
        GovernorateNameEN = governorateNameEN;
        CityNameAR = cityNameAR;
        CityNameEN = cityNameEN;
        Notes = notes;
        FullAddressAR = fullAddressAR;
        FullAddressEN = fullAddressEN;
    }
    public Address(int userId, String addressName, String phone, int cityId, String block, String street, String building, String floor, String apartment) {
        UserId = userId;
        AddressName = addressName;
        Phone = phone;
        CityId = cityId;
        Block = block;
        Street = street;
        Building = building;
        Floor = floor;
        Apartment = apartment;
    }

    public Address(int userId, String addressName, String phone, int cityId, String block, String street, String building) {
        UserId = userId;
        AddressName = addressName;
        Phone = phone;
        CityId = cityId;
        Block = block;
        Street = street;
        Building = building;
    }

    //for order details
    public Address(int id,String name, String phone, int governorateId, String governorateNameAR, String governorateNameEN, int cityId, String cityNameAR, String cityNameEN, String block, String street, String building, String floor, String apartment , String fullAddressAR, String fullAddressEN) {
        Name = name;
        Phone = phone;
        CityId = cityId;
        Block = block;
        Street = street;
        Building = building;
        Floor = floor;
        Apartment = apartment;
        Id = id;
        GovernorateId = governorateId;
        GovernorateNameAR = governorateNameAR;
        GovernorateNameEN = governorateNameEN;
        CityNameAR = cityNameAR;
        CityNameEN = cityNameEN;
        FullAddressAR = fullAddressAR;
        FullAddressEN = fullAddressEN;
    }

    public int getAddressId() {
        return AddressId;
    }

    public void setAddressId(int addressId) {
        AddressId = addressId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getAddressName() {
        return AddressName;
    }

    public void setAddressName(String addressName) {
        AddressName = addressName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public String getBlock() {
        return Block;
    }

    public void setBlock(String block) {
        Block = block;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getBuilding() {
        return Building;
    }

    public void setBuilding(String building) {
        Building = building;
    }

    public String getFloor() {
        return Floor;
    }

    public void setFloor(String floor) {
        Floor = floor;
    }

    public String getApartment() {
        return Apartment;
    }

    public void setApartment(String apartment) {
        Apartment = apartment;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getGovernorateId() {
        return GovernorateId;
    }

    public void setGovernorateId(int governorateId) {
        GovernorateId = governorateId;
    }

    public String getGovernorateNameAR() {
        return GovernorateNameAR;
    }

    public void setGovernorateNameAR(String governorateNameAR) {
        GovernorateNameAR = governorateNameAR;
    }

    public String getGovernorateNameEN() {
        return GovernorateNameEN;
    }

    public void setGovernorateNameEN(String governorateNameEN) {
        GovernorateNameEN = governorateNameEN;
    }

    public String getCityNameAR() {
        return CityNameAR;
    }

    public void setCityNameAR(String cityNameAR) {
        CityNameAR = cityNameAR;
    }

    public String getCityNameEN() {
        return CityNameEN;
    }

    public void setCityNameEN(String cityNameEN) {
        CityNameEN = cityNameEN;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getFullAddressAR() {
        return FullAddressAR;
    }

    public void setFullAddressAR(String fullAddressAR) {
        FullAddressAR = fullAddressAR;
    }

    public String getFullAddressEN() {
        return FullAddressEN;
    }

    public void setFullAddressEN(String fullAddressEN) {
        FullAddressEN = fullAddressEN;
    }
}
