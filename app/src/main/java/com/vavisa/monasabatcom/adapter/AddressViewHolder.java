package com.vavisa.monasabatcom.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.Interface.RecyclerViewItemClickListener;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.fragments.AppointmentDetails;
import com.vavisa.monasabatcom.models.Address;
import com.vavisa.monasabatcom.models.companyDetails.CompanyDetailsModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    Address mAddress;
    private CompanyDetailsModel comDetails;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.sw)
    SwipeRevealLayout sw;
    @BindView(R.id.address_cardView)
    CardView addressCart;
    @BindView(R.id.tv_edit)
    TextView tv_edit;
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.address_name)
    TextView address_name;
    @BindView(R.id.address_phone)
    TextView address_phone;
    @BindView(R.id.fullAddress)
    TextView fullAddress;
    @OnClick(R.id.address_cardView)
    public void addressId(){
        if(Common.makeResr) {

            // Common.addressId = mAddress.getId();
             Common.appointment.setAddressId(mAddress.getId());

             //to view in appointment
             Common.address = mAddress;

            //to prevent click on address
            Common.makeResr = false;

            //to show reserve btn in appointment details
            Common.showReserBtn = true;
            Common.newOrder = true;

            Fragment fragment = new AppointmentDetails();
            FragmentManager fragmentManager = Common.mActivity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).addToBackStack(null).commit();
        }

    }


    public AddressViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

    }

    public void bind(Address address)
    {
        if (Common.isArabic)
            sw.setDragEdge(1);
        else
            sw.setDragEdge(2);

        mAddress = address;
        address_name.setText(address.getName());
        address_phone.setText(address.getPhone());
        if(Common.isArabic)
            fullAddress.setText(address.getFullAddressAR());
        else
            fullAddress.setText(address.getFullAddressEN());
    }

    @Override
    public void onClick(View v) {

    }
}
