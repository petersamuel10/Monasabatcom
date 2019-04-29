package com.vavisa.monasabatcom.adapter.services_offersAdapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.fragments.ServiceDetailsFragment;
import com.vavisa.monasabatcom.models.companyDetails.Services;

import java.util.ArrayList;

public class ServicesAdapter extends ArrayAdapter<Services> {

    private ArrayList<Services> servicesList;
    private Context context;

    public ServicesAdapter(
            @NonNull Context context, int resource, @NonNull ArrayList<Services> objects) {
        super(context, resource, objects);
        this.context = context;
        this.servicesList = objects;
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

                    Bundle bundle = new Bundle();
                    bundle.putString("serviceId", String.valueOf(servicesList.get(position).getId()));
                    ServiceDetailsFragment serviceFragment = new ServiceDetailsFragment();
                    serviceFragment.setArguments(bundle);
                    FragmentManager fragmentManager = Common.mActivity.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.main_fragment, serviceFragment).addToBackStack(null).commit();

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
                        + context.getResources().getString(R.string.kd));

        return convertView;
    }

    private static class ViewHolder {
        TextView serviceName;
        TextView servicePrice;
    }
}
