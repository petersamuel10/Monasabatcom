package com.vavisa.monasabatcom.models.orderModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExtrasOrder implements Parcelable {

    //to show in cart activity
    String service_name_ar;
    String service_name_en;

    @SerializedName("ExtraId")
    @Expose
    Integer extraId;
    @SerializedName("Quantity")
    @Expose
    Integer quantity;
    @SerializedName("Price")
    @Expose
    Float price;

    public ExtrasOrder() {
    }

    public ExtrasOrder(String service_name_ar, String service_name_en, Integer extraId, Integer quantity, Float price) {
        this.service_name_ar = service_name_ar;
        this.service_name_en = service_name_en;
        this.extraId = extraId;
        this.quantity = quantity;
        this.price = price;
    }

    protected ExtrasOrder(Parcel in) {
        service_name_ar = in.readString();
        service_name_en = in.readString();
        if (in.readByte() == 0) {
            extraId = null;
        } else {
            extraId = in.readInt();
        }
        if (in.readByte() == 0) {
            quantity = null;
        } else {
            quantity = in.readInt();
        }
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readFloat();
        }
    }

    public static final Creator<ExtrasOrder> CREATOR = new Creator<ExtrasOrder>() {
        @Override
        public ExtrasOrder createFromParcel(Parcel in) {
            return new ExtrasOrder(in);
        }

        @Override
        public ExtrasOrder[] newArray(int size) {
            return new ExtrasOrder[size];
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

    public Integer getExtraId() {
        return extraId;
    }

    public void setExtraId(Integer extraId) {
        this.extraId = extraId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(service_name_ar);
        dest.writeString(service_name_en);
        if (extraId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(extraId);
        }
        if (quantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantity);
        }
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(price);
        }
    }
}
