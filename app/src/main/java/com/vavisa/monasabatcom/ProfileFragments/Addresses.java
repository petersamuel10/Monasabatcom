package com.vavisa.monasabatcom.ProfileFragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.vavisa.monasabatcom.AddNewAddress;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.Interface.RecyclerViewItemClickListener;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.adapter.profileAdpaters.AddressAdapter;
import com.vavisa.monasabatcom.models.profile.Address;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class Addresses extends Fragment implements RecyclerViewItemClickListener {

    @BindView(R.id.addresses_rec)
    RecyclerView address_rec;
    @BindView(R.id.addresses_progress)
    ProgressBar address_progress;

    @OnClick(R.id.add_address_icon)
    void addAddress() {
        startActivity(new Intent(getActivity(), AddNewAddress.class));
    }

    @OnClick(R.id.back)
    public void setBack() {
        getActivity().onBackPressed();
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AddressAdapter adapter;
    private ProgressDialog progressDialog;
    private List<Address> list = new ArrayList<>();

    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_addresses, container, false);
        ImageView arrow = view.findViewById(R.id.arrow);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        recyclerViewItemClickListener = this;

        if (Common.isArabic) {
            arrow.setRotation(180);
        }

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        list.clear();

        /* setupRecyclerView();*/
        if (Common.isConnectToTheInternet(getContext())) {
            requestData();
        } else {
            AlertDialog.Builder error = new AlertDialog.Builder(getContext());
            error.setMessage(R.string.error_connection);
            AlertDialog dialog = error.create();
            dialog.show();
        }

    }

    private void requestData() {
        progressDialog.show();
        compositeDisposable.add(Common.getAPI().getUserAddresses(Common.currentUser.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<Address>>() {
                    @Override
                    public void accept(ArrayList<Address> addresses) throws Exception {
                        list.addAll(addresses);
                        adapter = new AddressAdapter(addresses, false);
                        address_rec.setAdapter(adapter);
                        adapter.setListener(recyclerViewItemClickListener);
                        progressDialog.dismiss();
                    }
                }));

    }

    private void callDeleteAddressAPI(int id) {
        compositeDisposable.add(Common.getAPI().deleteAddress(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        if (integer == 1)
                            Toast.makeText(getActivity(), getResources().getString(R.string.deleteAddress_success), Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getActivity(), getResources().getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    @Override
    public void onItemClick(int position, int flag, View view) {

        if (flag == 0) {
            //pos = position;
            callDeleteAddressAPI(list.get(position).getId());
            adapter.removeAddresses(position);
            adapter.notifyItemRemoved(position);
            ///   adapter.notifyItemChanged(position);
        } else if (flag == 1) {
            Common.address = list.get(position);
            Common.isEditAddress = true;
            startActivity(new Intent(getActivity(), AddNewAddress.class));

        }

    }


}
