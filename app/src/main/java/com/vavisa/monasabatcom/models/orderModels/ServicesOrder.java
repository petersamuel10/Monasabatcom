package com.vavisa.monasabatcom.models.orderModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ServicesOrder implements Parcelable {

    //to show in cart activity
    String service_name_ar;
    String service_name_en;
    Integer payType;
    Float depositPercentage;


    @SerializedName("ServiceId")
    @Expose
    Integer serviceId;
    @SerializedName("Date")
    @Expose
    String date;
    @SerializedName("Time")
    @Expose
    String time;
    @SerializedName("WomenService")
    @Expose
    Boolean WomenService;
    @SerializedName("Quantity")
    @Expose
    Integer quantity;
    @SerializedName("Note")
    @Expose
    String note;
    @SerializedName("Price")
    @Expose
    Float price;
    @SerializedName("Extras")
    @Expose
    ArrayList<ExtrasOrder> extrasOrder;

    public ServicesOrder(String service_name_ar, String service_name_en, Integer payType, Float depositPercentage,
                         Integer serviceId, String date, String time, Boolean womenService, Integer quantity,
                         String note, Float price, ArrayList<ExtrasOrder> extrasOrder) {
        this.service_name_ar = service_name_ar;
        this.service_name_en = service_name_en;
        this.payType = payType;
        this.depositPercentage = depositPercentage;
        this.serviceId = serviceId;
        this.date = date;
        this.time = time;
        WomenService = womenService;
        this.quantity = quantity;
        this.note = note;
        this.price = price;
        this.extrasOrder = extrasOrder;
    }

    protected ServicesOrder(Parcel in) {
        service_name_ar = in.readString();
        service_name_en = in.readString();
        if (in.readByte() == 0) {
            payType = null;
        } else {
            payType = in.readInt();
        }
        if (in.readByte() == 0) {
            depositPercentage = null;
        } else {
            depositPercentage = in.readFloat();
        }
        if (in.readByte() == 0) {
            serviceId = null;
        } else {
            serviceId = in.readInt();
        }
        date = in.readString();
        time = in.readString();
        byte tmpWomenService = in.readByte();
        WomenService = tmpWomenService == 0 ? null : tmpWomenService == 1;
        if (in.readByte() == 0) {
            quantity = null;
        } else {
            quantity = in.readInt();
        }
        note = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readFloat();
        }
        extrasOrder = in.createTypedArrayList(ExtrasOrder.CREATOR);
    }

    public static final Creator<ServicesOrder> CREATOR = new Creator<ServicesOrder>() {
        @Override
        public ServicesOrder createFromParcel(Parcel in) {
            return new ServicesOrder(in);
        }

        @Override
        public ServicesOrder[] newArray(int size) {
            return new ServicesOrder[size];
        }
    };

    public String getService_name_ar() {
        return service_name_ar;
    }

    public void setService_name_ar(String service_name_ar) {
        this.service_name_ar = service_name_ar;
    }

    public String getService_name_en() {
        return service_name_en;
    }

    public void setService_name_en(String service_name_en) {
        this.service_name_en = service_name_en;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Float getDepositPercentage() {
        return depositPercentage;
    }

    public void setDepositPercentage(Float depositPercentage) {
        this.depositPercentage = depositPercentage;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getWomenService() {
        return WomenService;
    }

    public void setWomenService(Boolean womenService) {
        WomenService = womenService;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public ArrayList<ExtrasOrder> getExtrasOrder() {
        return extrasOrder;
    }

    public void setExtrasOrder(ArrayList<ExtrasOrder> extrasOrder) {
        this.extrasOrder = extrasOrder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(service_name_ar);
        dest.writeString(service_name_en);
        if (payType == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(payType);
        }
        if (depositPercentage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(depositPercentage);
        }
        if (serviceId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(serviceId);
        }
        dest.writeString(date);
        dest.writeString(time);
        dest.writeByte((byte) (WomenService == null ? 0 : WomenService ? 1 : 2));
        if (quantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantity);
        }
        dest.writeString(note);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(price);
        }
        dest.writeTypedList(extrasOrder);
    }
}
