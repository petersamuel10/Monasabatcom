package com.vavisa.monasabatcom.adapter.services_offersAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.EditText;
import com.rey.material.widget.LinearLayout;
import com.rey.material.widget.TextView;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.models.companyDetails.Services;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServicesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.serviceCB)
    CheckBox serviceNameCB;
    @BindView(R.id.servicePrice)
    TextView servicePrice;
    @BindView(R.id.serviceDetails)
    LinearLayout serviceDetails;
    @BindView(R.id.number_button)
    ElegantNumberButton count;
    @BindView(R.id.noteET)
    EditText note;

    public ServicesViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(Services service){
        if(Common.isArabic)
        {
            serviceNameCB.setText(service.getNameAR());

        }else{
            serviceNameCB.setText(service.getNameEN());
        }

    }
}
