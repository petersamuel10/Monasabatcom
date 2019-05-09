package com.vavisa.monasabatcom.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.MainActivity;
import com.vavisa.monasabatcom.ProfileFragments.Addresses;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.adapter.CartAdapter;
import com.vavisa.monasabatcom.models.orderModels.CartModel;
import com.vavisa.monasabatcom.models.orderModels.ExtrasOrder;
import com.vavisa.monasabatcom.models.orderModels.ServicesOrder;
import com.vavisa.monasabatcom.utility.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartActivity extends AppCompatActivity {

    @BindView(R.id.company_name)
    TextView company_name;
    @BindView(R.id.total_amount)
    TextView total_amount;
    @BindView(R.id.cart_rec)
    RecyclerView cart_rec;
    @BindView(R.id.continue_button)
    Button continue_btn;
    @OnClick(R.id.cancel)
    public void cancel() {
        finish();
    }
    @OnClick(R.id.continue_button)
    public void continue_() {
        startActivity(new Intent(this,AddressesActivity.class));
    }

    CartModel cartModel;
    CartAdapter adapter;
    Float total = 0.0f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        cartModel = Common.cart;

        if (Common.isArabic)
            company_name.setText(cartModel.getCompany_name_ar());
        else
            company_name.setText(cartModel.getCompany_name_en());

        adapter = new CartAdapter(cartModel.getServices());
        cart_rec.setAdapter(adapter);

        for (ServicesOrder servicesOrder : cartModel.getServices()) {

            total += servicesOrder.getPrice();

            for (ExtrasOrder extras : servicesOrder.getExtrasOrder()) {
                total += extras.getPrice() * extras.getQuantity();
            }
        }

        total_amount.setText(String.format("%.3f", total) + " " + getString(R.string.kd));
    }
}
