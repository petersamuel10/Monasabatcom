package com.vavisa.monasabatcom.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class AddressesActivity extends AppCompatActivity implements RecyclerViewItemClickListener {

    @BindView(R.id.addresses_rec)
    RecyclerView address_rec;
    @BindView(R.id.addresses_progress)
    ProgressBar address_progress;

    @OnClick(R.id.add_address_icon)
    public void addAddress() {
        startActivity(new Intent(this, AddNewAddress.class));
    }

    @OnClick(R.id.back)
    public void setBack() { onBackPressed(); }

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    AddressAdapter adapter;
    ProgressDialog progressDialog;
    List<Address> list = new ArrayList<>();
    RecyclerViewItemClickListener recyclerViewItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses);
        ButterKnife.bind(this);

        ImageView arrow = findViewById(R.id.arrow);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        recyclerViewItemClickListener = this;

        if (Common.isArabic) {
            arrow.setRotation(180);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        list.clear();

        if (Common.isConnectToTheInternet(this)) {
            requestData();
        } else {
            AlertDialog.Builder error = new AlertDialog.Builder(this);
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
                        adapter = new AddressAdapter(addresses,true);
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
                            Toast.makeText(AddressesActivity.this, getResources().getString(R.string.deleteAddress_success), Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(AddressesActivity.this, getResources().getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(this, AddNewAddress.class));

        }else if(flag == 2)
            checkAndGetDeliveryCost(Common.cart.getCompany_id(),list.get(position).getId(),list.get(position));

    }

    public void checkAndGetDeliveryCost(int companyId, final int addressId, final Address address) {

        progressDialog.show();

        final Float[] result = new Float[1];

        compositeDisposable.add(Common.getAPI().checkAndGetDeliveryCost(companyId, addressId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Float>() {
                    @Override
                    public void accept(Float integer) throws Exception {
                        progressDialog.dismiss();

                        result[0] = integer;

                        if (result[0] >= 0){
                            Common.cart.setAddressId(addressId);
                            Common.cart.setAddress(address);
                            Common.cart.setDeliveryCost(integer);
                            startActivity(new Intent(AddressesActivity.this, AppointmentDetails.class));
                        }
                        else if(result[0] == -1)
                            showErrorAlert(getString(R.string.missing_required_data));
                        else if(result[0] == -2)
                            showErrorAlert(getString(R.string.the_company_cant_deliver_to));
                        else if(result[0] == -3)
                            showErrorAlert(getBaseContext().getString(R.string.error_occure));
                    }
                }));


    }

    private void showErrorAlert(String message_str){

        final Dialog dialog = new Dialog(AddressesActivity.this);
        dialog.setContentView(R.layout.custom_error_alert);

        TextView message = dialog.findViewById(R.id.alert_message);
        TextView ok = dialog.findViewById(R.id.ok);

        message.setText(message_str);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
