package com.vavisa.monasabatcom.adapter.services_offersAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.models.ServiceExtras;
import com.vavisa.monasabatcom.models.orderModels.ExtrasOrder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceExtraAdapter extends RecyclerView.Adapter<ServiceExtraAdapter.ViewHolder> {

    private ArrayList<ServiceExtras> extrasList;
    private Context context;
    // to add extra when quantity is decrease or increase and come back to class to complete order
    private ArrayList<ExtrasOrder> extrasOrdersList;

    public ServiceExtraAdapter(ArrayList<ServiceExtras> extrasList) {
        this.extrasList = extrasList;
        extrasOrdersList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ServiceExtraAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_extra, parent, false);
        context = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceExtraAdapter.ViewHolder holder, final int position) {

        final ServiceExtras extras = extrasList.get(position);
        extrasOrdersList.add(new ExtrasOrder(extras.getNameAR(), extras.getNameEN(), extras.getId(), extras.getQuantity(), extras.getPrice()));

        holder.bind(extras);
        final int[] count = {extrasOrdersList.get(position).getQuantity()};//Integer.parseInt(holder.count.getText().toString());
        holder.ic_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count[0]++;
                holder.count.setText(String.valueOf(count[0]));
                extrasOrdersList.get(position).setQuantity(count[0]);
            }
        });

        holder.ic_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count[0] > 0) {
                    count[0]--;
                    holder.count.setText(String.valueOf(count[0]));
                    extrasOrdersList.get(position).setQuantity(count[0]);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return extrasList.size();
    }

    public ArrayList<ExtrasOrder> getExtrasOrdersList() {
        return extrasOrdersList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.extra_name)
        TextView extra_name;
        @BindView(R.id.extra_price)
        TextView extra_price;

        @BindView(R.id.ic_minus)
        ImageView ic_minus;
        @BindView(R.id.count)
        TextView count;
        @BindView(R.id.ic_add)
        ImageView ic_add;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ServiceExtras serviceExtra) {
            if (Common.isArabic)
                extra_name.setText(serviceExtra.getNameAR());
            else
                extra_name.setText(serviceExtra.getNameEN());

            count.setText(String.valueOf(serviceExtra.getQuantity()));

            extra_price.setText(serviceExtra.getPrice() + " " + context.getString(R.string.kd));

        }
    }
}
