package com.vavisa.monasabatcom.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.MainActivity;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.fragments.CompanyDetailsFragment;
import com.vavisa.monasabatcom.models.Company;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder> {

    private ArrayList<Company> companies;
    private Activity activity;
    private int viewType;
    private String tab_tag,searchDate,searchHour;

    public CompanyAdapter(Activity activity, ArrayList<Company> companies, String tab_tag, int viewType,String searchDate, String searchHour) {
        this.activity = activity;
        this.companies = companies;
        this.viewType = viewType;
        this.tab_tag = tab_tag;
        this.searchDate = searchDate;
        this.searchHour = searchHour;
    }

    public CompanyAdapter() { }

    @NonNull
    @Override
    public CompanyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = null;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, null);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company2, null);
                break;
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company3, null);
                break;
        }

        return new CompanyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyAdapter.ViewHolder holder, final int position) {

        holder.bind(companies.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("companyId", String.valueOf(companies.get(position).getId()));
                bundle.putString("tag",tab_tag);
                bundle.putString("searchDate",searchDate);
                bundle.putString("searchHour",searchHour);
                Fragment companyDetailsFragment = new CompanyDetailsFragment();
                companyDetailsFragment.setArguments(bundle);

                ((MainActivity) activity).pushFragments(tab_tag, companyDetailsFragment, true);
            }
        });

    }

    @Override
    public int getItemCount() {
        return companies.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Company company) {
            mCompany = company;

            if (Common.isArabic) {
                company_name.setText(company.getNameAR());
                company_about.setText(company.getAboutAR());
            } else {
                company_name.setText(Html.fromHtml(company.getNameEN()));
                company_about.setText(Html.fromHtml(company.getAboutEN()));
            }
            Picasso.with(Common.mActivity).load(company.getLogo()).into(company_photo);
            ratingBar.setRating(company.getRating());
        }
    }

}
