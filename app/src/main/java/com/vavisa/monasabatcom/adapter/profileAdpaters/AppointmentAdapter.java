package com.vavisa.monasabatcom.adapter.profileAdpaters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.MainActivity;
import com.vavisa.monasabatcom.ProfileFragments.OrderDetails;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.models.profile.AppointmentModel;
import com.vavisa.monasabatcom.utility.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    Activity activity;
    Context context;
    ArrayList<AppointmentModel> appointmentList;
    public AppointmentAdapter(Activity activity) {

        appointmentList = new ArrayList<>();
        this.activity = activity;
    }


    @NonNull
    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, null);
        context = parent.getContext();
        return new AppointmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.ViewHolder holder, final int position) {

        holder.bind(appointmentList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("orderId", String.valueOf(appointmentList.get(position).getId()));
                Fragment orderDetails = new OrderDetails();
                orderDetails.setArguments(bundle);

                ((MainActivity) activity).pushFragments(Constants.TAB_PROFILE, orderDetails, true);
            }
        });

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public void addAppointment(ArrayList<AppointmentModel> newAppointment) {
        appointmentList.addAll(newAppointment);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.appointment_name)
        TextView name;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.totalAmount)
        TextView total;

       /* @OnClick(R.id.appointment_cardView)
        public void appointmentDetails() {

            Common.orderId = appointmentId;


            Fragment fragment = new OrderDetails();
            FragmentManager fragmentManager = Common.mActivity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).addToBackStack(null).commit();
        }*/


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(AppointmentModel appointment) {

            if (Common.isArabic) {
                name.setText(appointment.getCompanyNameAR());
                status.setText(appointment.getStatusNameAR());
            } else {
                name.setText(appointment.getCompanyNameEN());
                status.setText(appointment.getStatusNameEN());
            }

            total.setText(context.getResources().getString(R.string.due_amount)+
                    appointment.getDueAmount()+" "+context.getResources().getString(R.string.kd));

        }
    }
}
