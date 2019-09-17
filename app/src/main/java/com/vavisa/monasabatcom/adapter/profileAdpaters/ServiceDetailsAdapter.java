package com.vavisa.monasabatcom.adapter.profileAdpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.models.profile.ServiceDetails;
import com.vavisa.monasabatcom.models.profile.ServiceExtrasDetails;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceDetailsAdapter extends RecyclerView.Adapter<ServiceDetailsAdapter.ViewHolder> {

    ArrayList<ServiceDetails> servicesList;
    Context context;

    public ServiceDetailsAdapter(ArrayList<ServiceDetails> services) {
        this.servicesList = services;
    }

    @NonNull
    @Override
    public ServiceDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_details, parent, false);
        context = parent.getContext();
        return new ServiceDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceDetailsAdapter.ViewHolder holder, int position) {

        holder.bind(servicesList.get(position));
    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.service_name)
        TextView service_name;
        @BindView(R.id.price)
        TextView service_price;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.notes)
        TextView notes;
        @BindView(R.id.extra)
        TextView service_extra;

        String extra_str = "";

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ServiceDetails service) {

            if (Common.isArabic) {
                service_name.setText(service.getNameAR());
                if (service.getErviceExtras().size() > 0)
                    extra_str = "الإضافات \n";
                for (ServiceExtrasDetails extra : service.getErviceExtras()) {
                    extra_str += "1x  " +
                            extra.getNameAR() +
                            "  (" + extra.getPrice() + " " + context.getString(R.string.kd) + ")\n";
                }
            } else {
                service_name.setText(service.getNameEN());
                if (service.getErviceExtras().size() > 0)
                    extra_str = "Extra notes\n";

                for (ServiceExtrasDetails extra : service.getErviceExtras()) {
                    extra_str += "1x  " +
                            extra.getNameEN() +
                            "  (" + extra.getPrice() + " " + context.getString(R.string.kd) + ")\n";
                }

            }

            if (service.getNote().equals(""))
                notes.setVisibility(View.GONE);
            else
                notes.setText(context.getString(R.string.extra_note) + service.getNote());

            service_extra.setText(extra_str);
            date.setText(context.getString(R.string.date) + ": " + service.getDate());
            time.setText(context.getString(R.string.time) + ": " + service.getTime());
            service_price.setText(context.getString(R.string.price) + service.getPrice() + " " + context.getString(R.string.kd));
        }
    }
}
