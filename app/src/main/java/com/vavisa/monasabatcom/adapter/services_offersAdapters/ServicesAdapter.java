package com.vavisa.monasabatcom.adapter.services_offersAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.models.companyDetails.Services;

import java.util.ArrayList;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesViewHolder> {

    ArrayList<Services> servicesList;
    Context mContext;

    public ServicesAdapter(ArrayList<Services> services) {
        this.servicesList = services;
    }

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service,null);
        mContext = parent.getContext();

        return new ServicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesViewHolder holder, int position) {
        holder.bind(servicesList.get(position));
    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    public void addAddresses(ArrayList<Services> newService)
    {
        servicesList.addAll(newService);
    }
}
