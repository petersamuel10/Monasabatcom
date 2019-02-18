package com.vavisa.monasabatcom.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.fragments.CompaniesByOffer;
import com.vavisa.monasabatcom.models.Offer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OfferViewHolder extends RecyclerView.ViewHolder {

    private Offer mOffer;

    @BindView(R.id.offer_card)
    CardView offer_cart;
    @BindView(R.id.offer_logo)
    ImageView offer_logo;
    @BindView(R.id.offer_title)
    TextView offer_title;
    @OnClick(R.id.offer_card)
    public void offerClick()
    {
        Common.offerId = mOffer.getId();
        if (Common.isArabic)
            Common.offerName = mOffer.getNameAR();
        else
            Common.offerName =mOffer.getNameEN();

        Fragment fragment = new CompaniesByOffer();
        FragmentManager fragmentManager = Common.mActivity.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).addToBackStack(null).commit();
    }


    public OfferViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(Offer offer)
    {
        mOffer = offer;
        Picasso.with(Common.mActivity).load(offer.getPhoto()).into(offer_logo);
        if(Common.isArabic)
            offer_title.setText(offer.getNameAR());
        else
            offer_title.setText(offer.getNameEN());
    }
}
