package com.vavisa.monasabatcom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jesus on 2/2/2019.
 */

public class PaymentUrl {

    @SerializedName("Url")
    @Expose
    private String Url;
    @SerializedName("PostData")
    @Expose
    private PostData data;

    public PaymentUrl() {
    }

    public PaymentUrl(String url, PostData data) {
        Url = url;
        this.data = data;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public PostData getData() {
        return data;
    }

    public void setData(PostData data) {
        this.data = data;
    }
}

