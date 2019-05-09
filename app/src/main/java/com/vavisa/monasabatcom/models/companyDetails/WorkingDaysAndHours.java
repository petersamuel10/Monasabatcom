package com.vavisa.monasabatcom.models.companyDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vavisa.monasabatcom.models.ServiceExtras;

import java.util.ArrayList;

public class WorkingDaysAndHours {

    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("WorkingHours")
    @Expose
    private ArrayList<WorkingHours> workingHours;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<WorkingHours> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(ArrayList<WorkingHours> workingHours) {
        this.workingHours = workingHours;
    }
}
