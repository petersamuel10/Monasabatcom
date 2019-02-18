package com.vavisa.monasabatcom.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.models.Appointment;

import java.util.ArrayList;

/**
 * Created by jesus on 11/21/2018.
 */

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentViewHolder> {

    ArrayList<Appointment> appointmentList;

    public AppointmentAdapter() {
        appointmentList = new ArrayList<>();
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment,null);

        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder appointmentViewHolder, int position) {

        appointmentViewHolder.bind(appointmentList.get(position));
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public void addAppointment(ArrayList<Appointment> newAppointment)
    {
        appointmentList.addAll(newAppointment);
    }
}
