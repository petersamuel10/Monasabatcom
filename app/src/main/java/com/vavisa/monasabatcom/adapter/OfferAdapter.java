package com.vavisa.monasabatcom.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.MainActivity;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.fragments.CompaniesByOffer;
import com.vavisa.monasabatcom.models.Offer;
import com.vavisa.monasabatcom.utility.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {

    private ArrayList<Offer> offers;
    private Activity activity;

    public OfferAdapter(Activity activity) {
        offers = new ArrayList<>();
        this.activity = activity;
    }

    @NonNull
    @Override
    public OfferAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer, null);

        return new OfferAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferAdapter.ViewHolder offerViewHolder, final int position) {

        offerViewHolder.bind(offers.get(position));
        offerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("offerId", String.valueOf(offers.get(position).getId()));
                bundle.putString("offerName", (Common.isArabic) ? offers.get(position).getNameAR() : offers.get(position).getNameEN());

                Fragment companiesByCatFrag = new CompaniesByOffer();
                companiesByCatFrag.setArguments(bundle);

                ((MainActivity) activity).pushFragments(Constants.TAB_OFFERS, companiesByCatFrag, true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public void addOffer(ArrayList<Offer> newOffer) {
        offers.addAll(newOffer);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.offer_card)
        CardView offer_cart;
        @BindView(R.id.offer_logo)
        ImageView offer_logo;
        @BindView(R.id.offer_title)
        TextView offer_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Offer offer) {
            Picasso.with(Common.mActivity).load(offer.getPhoto()).into(offer_logo);
            if (Common.isArabic)
                offer_title.setText(offer.getNameAR());
            else
                offer_title.setText(offer.getNameEN());
        }
    }
}
