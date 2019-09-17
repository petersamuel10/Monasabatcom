package com.vavisa.monasabatcom.adapter.services_offersAdapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.MainActivity;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.fragments.OfferDetailsFragment;
import com.vavisa.monasabatcom.models.companyDetails.Offers;

import java.util.ArrayList;

public class OffersAdapter extends ArrayAdapter<Offers> {

    private ArrayList<Offers> offersList;
    private Activity activity;
    private String tab_tag;
    private String companyId, company_name_ar,
            company_name_en, searchDate, searchHour;

    public OffersAdapter(
            @NonNull Activity activity, int resource, @NonNull ArrayList<Offers> objects,
            String company_id, String company_name_ar,
            String company_name_en, String searchDate,
            String searchHour, String tab_tag) {

        super(activity, resource, objects);
        this.activity = activity;
        this.offersList = objects;
        this.tab_tag = tab_tag;
        this.companyId = company_id;
        this.company_name_ar = company_name_ar;
        this.company_name_en = company_name_en;
        this.searchDate = searchDate;
        this.searchHour = searchHour;

    }

    @Override
    public int getCount() {
        return offersList.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(activity)
                    .inflate(R.layout.service_list_item, parent, false);

            viewHolder = new OffersAdapter.ViewHolder();
            viewHolder.offerName = convertView.findViewById(R.id.service_name);
            viewHolder.offerPrice = convertView.findViewById(R.id.service_price);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putString("offerId", String.valueOf(offersList.get(position).getId()));
                    bundle.putString("companyId", companyId);
                    bundle.putString("company_name_ar", company_name_ar);
                    bundle.putString("company_name_en", company_name_en);
                    bundle.putString("searchDate", searchDate);
                    bundle.putString("searchHour", searchHour);
                    bundle.putString("tag", tab_tag);
                    Fragment offerFragment = new OfferDetailsFragment();
                    offerFragment.setArguments(bundle);

                    ((MainActivity) activity).pushFragments(tab_tag, offerFragment, true);
                }
            });

            convertView.setTag(viewHolder);


        } else {
            viewHolder = (OffersAdapter.ViewHolder) convertView.getTag();
        }

        if (Common.isArabic) {
            viewHolder.offerName.setText(offersList.get(position).getNameAR());
        } else {
            viewHolder.offerName.setText(offersList.get(position).getNameEN());
        }

        viewHolder.offerPrice.setText(
                String.valueOf(offersList.get(position).getPrice())
                        + " "
                        + activity.getResources().getString(R.string.kd));


        return convertView;

    }

    private static class ViewHolder {
        TextView offerName;
        TextView offerPrice;
    }
}
