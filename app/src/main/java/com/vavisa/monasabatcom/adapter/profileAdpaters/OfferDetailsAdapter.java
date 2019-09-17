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
import com.vavisa.monasabatcom.models.profile.OfferDetails;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfferDetailsAdapter extends RecyclerView.Adapter<OfferDetailsAdapter.ViewHolder> {

    ArrayList<OfferDetails> offersList;
    Context context;

    public OfferDetailsAdapter(ArrayList<OfferDetails> offersList) {
        this.offersList = offersList;
    }

    @NonNull
    @Override
    public OfferDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_details, parent, false);
        context = parent.getContext();
        return new OfferDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferDetailsAdapter.ViewHolder holder, int position) {

        holder.bind(offersList.get(position));
    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.service_name)
        TextView offer_name;
        @BindView(R.id.price)
        TextView offer_price;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.notes)
        TextView notes;
        @BindView(R.id.extra)
        TextView service_extra;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(OfferDetails offer) {

            if (Common.isArabic)
                offer_name.setText(offer.getNameAR());
            else
                offer_name.setText(offer.getNameEN());

            date.setText(context.getString(R.string.date) + ": " + offer.getDate());
            time.setText(context.getString(R.string.time) + ": " + offer.getTime());
            offer_price.setText(context.getString(R.string.price) + offer.getPrice() + " " + context.getString(R.string.kd));

            if (offer.getNote().equals(""))
                notes.setVisibility(View.GONE);
            else
                notes.setText(context.getString(R.string.extra_note) + offer.getNote());

            service_extra.setVisibility(View.GONE);
        }
    }
}
