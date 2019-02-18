package com.vavisa.monasabatcom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostData{

	@SerializedName("Data")
	@Expose
	private String data;

	public void setData(String data){
		this.data = data;
	}

	public String getData(){
		return data;
	}

}
