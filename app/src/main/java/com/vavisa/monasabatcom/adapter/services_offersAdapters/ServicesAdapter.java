package com.vavisa.monasabatcom.adapter.services_offersAdapters;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.MainActivity;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.fragments.ServiceDetailsFragment;
import com.vavisa.monasabatcom.models.companyDetails.Services;

import java.util.ArrayList;

public class ServicesAdapter extends ArrayAdapter<Services> {

    private ArrayList<Services> servicesList;
    private Activity activity;
    private String tab_tag;
    private String companyId, company_name_ar,
            company_name_en,searchDate,searchHour;

    public ServicesAdapter(
            @NonNull Activity activity, int resource,
            @NonNull ArrayList<Services> objects,
            String company_id, String company_name_ar,
            String company_name_en, String searchDate,
            String searchHour, String tab_tag) {

        super(activity, resource, objects);
        this.activity = activity;
        this.servicesList = objects;
        this.tab_tag = tab_tag;
        this.companyId = company_id;
        this.company_name_ar = company_name_ar;
        this.company_name_en = company_name_en;
        this.searchDate = searchDate;
        this.searchHour = searchHour;
    }

    @Override
    public int getCount() {
        return servicesList.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.service_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.serviceName = convertView.findViewById(R.id.service_name);
            viewHolder.servicePrice = convertView.findViewById(R.id.service_price);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // send company id used when send order
                    Bundle bundle = new Bundle();
                    bundle.putString("serviceId", String.valueOf(servicesList.get(position).getId()));
                    bundle.putString("companyId", companyId);
                    bundle.putString("company_name_ar", company_name_ar);
                    bundle.putString("company_name_en", company_name_en);
                    bundle.putString("searchDate", searchDate);
                    bundle.putString("searchHour", searchHour);
                    bundle.putString("tag", tab_tag);
                    Fragment serviceFragment = new ServiceDetailsFragment();
                    serviceFragment.setArguments(bundle);

                    ((MainActivity) activity).pushFragments(tab_tag, serviceFragment, true);
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (Common.isArabic) {
            viewHolder.serviceName.setText(servicesList.get(position).getNameAR());
        } else {
            viewHolder.serviceName.setText(servicesList.get(position).getNameEN());
        }
        viewHolder.servicePrice.setText(
                servicesList.get(position).getPrice()
                        + " "
                        + activity.getResources().getString(R.string.kd));

        return convertView;
    }

    private static class ViewHolder {
        TextView serviceName;
        TextView servicePrice;
    }
}
