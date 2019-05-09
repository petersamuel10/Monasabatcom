package com.vavisa.monasabatcom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.models.orderModels.ExtrasOrder;
import com.vavisa.monasabatcom.models.orderModels.ServicesOrder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    ArrayList<ServicesOrder> servicesList;
    Context context;

    public CartAdapter(ArrayList<ServicesOrder> services) {
        this.servicesList = services;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        context = parent.getContext();
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {

        holder.bind(servicesList.get(position));
    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.service_name)
        TextView service_name;
        @BindView(R.id.service_price)
        TextView service_price;
        @BindView(R.id.pay_status)
        TextView pay_status;
        @BindView(R.id.date_time)
        TextView date_time;
        @BindView(R.id.service_extra)
        TextView service_extra;

        String extra_str = "";

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ServicesOrder service) {

            if (Common.isArabic) {
                service_name.setText(service.getService_name_ar());
                for (ExtrasOrder extra : service.getExtrasOrder()) {
                    if (extra.getQuantity().equals(0))
                        continue;
                    extra_str += extra.getQuantity() + "x  " +
                            extra.getService_name_ar() +
                            "  (" + extra.getPrice() + " "+context.getString(R.string.kd) + ")\n";
                }
            } else {
                service_name.setText(service.getService_name_en());

                for (ExtrasOrder extra : service.getExtrasOrder()) {
                    if (extra.getQuantity().equals(0))
                        continue;
                    extra_str += extra.getQuantity() + "x  " +
                            extra.getService_name_en() +
                            "  (" + extra.getPrice() +" "+ context.getString(R.string.kd) + ")\n";
                }

            }

            service_extra.setText(extra_str);

            date_time.setText(service.getDate() + "  " + service.getTime());

            switch (service.getPayType()) {
                case 1:
                case 2:
                    pay_status.setText(context.getString(R.string.pay_a_deposit));
                    break;
                case 3:
                    pay_status.setText(context.getString(R.string.pay_full_amount_in_advance));
                    break;
            }
            service_price.setText(service.getPrice() + " "+context.getString(R.string.kd));
        }
    }
}
