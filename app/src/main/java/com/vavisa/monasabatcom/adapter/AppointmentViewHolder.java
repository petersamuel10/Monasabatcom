package com.vavisa.monasabatcom.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.fragments.AppointmentDetails;
import com.vavisa.monasabatcom.models.Appointment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jesus on 11/21/2018.
 */

public class AppointmentViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.appointment_name)
    TextView name;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.app_time)
    TextView time;
    @BindView(R.id.app_date)
    TextView date;
    @BindView(R.id.totalAmount)
    TextView total;
    @OnClick(R.id.appointment_cardView)
    public void appointmentDetails(){

        Common.orderId =appointmentId;


        Fragment fragment = new AppointmentDetails();
        FragmentManager fragmentManager = Common.mActivity.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).addToBackStack(null).commit();
    }

    int appointmentId;



    public AppointmentViewHolder(@NonNull View itemView) {
        super(itemView);

        ButterKnife.bind(this,itemView);
    }

    public void bind(Appointment appointment)
    {
        appointmentId = appointment.getId();

        if(Common.isArabic) {
            name.setText(appointment.getCompanyNameAR());
            status.setText(appointment.getStatusNameAR());
        }
        else {
            name.setText(appointment.getCompanyNameEN());
            status.setText(appointment.getStatusNameEN());
        }

        date.setText(appointment.getAppointmentDate());
        time.setText(" "+Common.mActivity.getResources().getString(R.string.from)+" "+appointment.getStartTime()+" "+Common.mActivity.getResources().getString(R.string.to)+" "+ appointment.getEndTime());
        total.setText(appointment.getTotalAmount()+" KD");
    }
}
