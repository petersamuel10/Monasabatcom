package com.vavisa.monasabatcom.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.Interface.RecyclerViewItemClickListener;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.fragments.AppointmentDetails;
import com.vavisa.monasabatcom.models.Address;
import com.vavisa.monasabatcom.models.companyDetails.CompanyDetailsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    ArrayList<Address> addressList;
    private RecyclerViewItemClickListener listener = null;

    public AddressAdapter() {
        addressList = new ArrayList<>();
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address,null);
        ButterKnife.bind(this,view);

        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder addressViewHolder, int position) {
        addressViewHolder.bind(addressList.get(position));

    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public void addAddresses(ArrayList<Address> newAddress)
    {
        addressList.addAll(newAddress);
    }

    public void removeAddresses(int position)
    {
        addressList.remove(position);
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Address mAddress;
       // private CompanyDetailsModel comDetails;
      //  private CompositeDisposable compositeDisposable = new CompositeDisposable();

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

            tv_edit.setOnClickListener(this);
            tv_delete.setOnClickListener(this);
        }

        public void bind(Address address)
        {
            sw.close(true);
            mAddress = address;
            address_name.setText(address.getName());
            address_phone.setText(address.getPhone());

            tv_edit.setOnClickListener(this);
            tv_delete.setOnClickListener(this);

            if(Common.isArabic) {
                fullAddress.setText(address.getFullAddressAR());
                sw.setDragEdge(1);
            }
            else {
                fullAddress.setText(address.getFullAddressEN());
                sw.setDragEdge(2);
            }
        }

        @Override
        public void onClick(View v) {

            if (v == tv_edit) {
                if (listener != null) {
                    listener.onItemClick(getAdapterPosition(), 1, v);
                    sw.close(true);
                }
            } else if (v == tv_delete){
              //  Toast.makeText(Common.mActivity, "//////1111111111111///////////////", Toast.LENGTH_SHORT).show();
                if (listener != null) {
                    listener.onItemClick(getAdapterPosition(), 0, v);
                 sw.close(true);
                }

            }
        }
    }

    public void setListener(RecyclerViewItemClickListener listener) {
        this.listener = listener;
    }
}
