package com.vavisa.monasabatcom.models.companyDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkingHours {

    @SerializedName("FromHour")
    @Expose
    private String FromHour;
    @SerializedName("ToHour")
    @Expose
    private String ToHour;

    public String getFromHour() {
        return FromHour;
    }

    public void setFromHour(String fromHour) {
        FromHour = fromHour;
    }

    public String getToHour() {
        return ToHour;
    }

    public void setToHour(String toHour) {
        ToHour = toHour;
    }
}
