package com.vavisa.monasabatcom.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.models.Offer;

import java.util.ArrayList;

public class OfferAdapter extends RecyclerView.Adapter<OfferViewHolder> {

    ArrayList<Offer> offers;

    public OfferAdapter() {
        offers = new ArrayList<>();

    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer,null);

        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder offerViewHolder, int position) {

        offerViewHolder.bind(offers.get(position));
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public void addOffer(ArrayList<Offer> newOffer)
    {
        offers.addAll(newOffer);

    }
}
