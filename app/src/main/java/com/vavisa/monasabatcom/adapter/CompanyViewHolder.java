package com.vavisa.monasabatcom.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.fragments.CompanyDetails;
import com.vavisa.monasabatcom.fragments.CompanyDetailsFragment;
import com.vavisa.monasabatcom.models.Company;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompanyViewHolder extends RecyclerView.ViewHolder {

  Company mCompany;

  @BindView(R.id.company_card)
  CardView company_card;

  @BindView(R.id.com_photo)
  ImageView company_photo;

  @BindView(R.id.com_name)
  TextView company_name;

  @BindView(R.id.com_about)
  TextView company_about;

  @BindView(R.id.com_rating)
  RatingBar ratingBar;

  @BindView(R.id.com_ratingCount)
  TextView ratingCount;

  @OnClick(R.id.company_card)
  public void Company_details() {
    Common.companyId = mCompany.getId();
    // Fragment fragment = new CompanyDetails();
    Fragment fragment = new CompanyDetailsFragment();
    FragmentManager fragmentManager = Common.mActivity.getSupportFragmentManager();
    fragmentManager
        .beginTransaction()
        .replace(R.id.main_fragment, fragment)
        .addToBackStack(null)
        .commit();
  }

  public CompanyViewHolder(@NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(Company company) {
    mCompany = company;
    if (Common.isArabic) {
      company_name.setText(company.getNameAR());
      company_about.setText(company.getAboutAR());
    } else {
      company_name.setText(company.getNameEN());
      company_about.setText(company.getAboutEN());
    }
    Picasso.with(Common.mActivity).load(company.getLogo()).into(company_photo);
    ratingBar.setRating(company.getRating());
    ratingCount.setText("(" + String.valueOf(company.getRatingCount()) + ")");
  }
}
