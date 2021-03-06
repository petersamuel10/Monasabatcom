package com.vavisa.monasabatcom.adapter.profileAdpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.Interface.RecyclerViewItemClickListener;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.models.profile.Address;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    ArrayList<Address> addressList;
    Context context;
    boolean isPickAddress;
    private RecyclerViewItemClickListener listener = null;

    public AddressAdapter(ArrayList<Address> addressList, boolean isPickAddress) {

        this.addressList = addressList;
        this.isPickAddress = isPickAddress;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, null);
        context = parent.getContext();
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, final int position) {
        holder.bind(addressList.get(position));


      /*  holder.addressCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPickAddress) {
                    AddressesActivity addresses = new AddressesActivity();
                    addresses.checkAndGetDeliveryCost(Common.cart.getCompany_id(), addressList.get(position).getAddressId(), addressList.get(position));
                }
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }


    public void removeAddresses(int position) {
        addressList.remove(position);
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Address mAddress;

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


        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            tv_edit.setOnClickListener(this);
            tv_delete.setOnClickListener(this);
            addressCart.setOnClickListener(this);
        }

        public void bind(Address address) {
            sw.close(true);
            mAddress = address;
            address_name.setText(address.getName());
            address_phone.setText(address.getPhone());

            if (Common.isArabic) {
                fullAddress.setText(address.getFullAddressAR());
                sw.setDragEdge(1);
            } else {
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
            } else if (v == tv_delete) {
                if (listener != null) {
                    listener.onItemClick(getAdapterPosition(), 0, v);
                    sw.close(true);
                }

            } else if (v == addressCart) {
                if (listener != null) {
                    listener.onItemClick(getAdapterPosition(), 2, v);
                    sw.close(true);
                }
            }
        }
    }

    public void setListener(RecyclerViewItemClickListener listener) {
        this.listener = listener;
    }
}
