package com.vavisa.monasabatcom.adapter.Cart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.activities.OfferEditCart;
import com.vavisa.monasabatcom.models.orderModels.OfferOrder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfferCartAdapter extends RecyclerView.Adapter<OfferCartAdapter.ViewHolder> {

    ArrayList<OfferOrder> offersList;
    Context context;

    public OfferCartAdapter(ArrayList<OfferOrder> offersList) {
        this.offersList = offersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        context = parent.getContext();
        return new OfferCartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.bind(offersList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OfferEditCart.class);
                intent.putExtra("index", position);
                intent.putExtra("offerId", offersList.get(position).getOfferIdId().toString());
                intent.putExtra("searchDate", offersList.get(position).getDate());
                intent.putExtra("searchHour", offersList.get(position).getTime());
                intent.putExtra("notes", offersList.get(position).getNote());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }

    public void removeItem(int position) {
        offersList.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.service_name)
        TextView offer_name;
        @BindView(R.id.service_price)
        TextView offer_price;
        @BindView(R.id.pay_status)
        TextView pay_status;
        @BindView(R.id.date_time)
        TextView date_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(OfferOrder offer) {

            if (Common.isArabic)
                offer_name.setText(offer.getOffer_name_ar());
            else
                offer_name.setText(offer.getOffer_name_en());

            date_time.setText(offer.getDate() + "  " + offer.getTime());

            switch (offer.getPayType()) {
                case 1:
                    pay_status.setVisibility(View.GONE);
                    break;
                case 2:
                    pay_status.setText(context.getString(R.string.pay_a_deposit) + " " + offer.getDepositPercentage() + "%");
                    break;
                case 3:
                    pay_status.setText(context.getString(R.string.pay_full_amount_in_advance));
                    break;
            }

            offer_price.setText(offer.getPrice() + " " + context.getString(R.string.kd));
        }
    }
}
