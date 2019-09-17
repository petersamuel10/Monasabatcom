package com.vavisa.monasabatcom.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.Login;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.adapter.Cart.OfferCartAdapter;
import com.vavisa.monasabatcom.adapter.Cart.ServiceCartAdapter;
import com.vavisa.monasabatcom.models.companyDetails.PaymentMethod;
import com.vavisa.monasabatcom.models.orderModels.CartModel;
import com.vavisa.monasabatcom.models.orderModels.ExtrasOrder;
import com.vavisa.monasabatcom.models.orderModels.OfferOrder;
import com.vavisa.monasabatcom.models.orderModels.ServicesOrder;
import com.vavisa.monasabatcom.touchHelper.SwipeToDeleteCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

public class CartActivity extends AppCompatActivity {

    @BindView(R.id.scroll)
    NestedScrollView scrollView;
    @BindView(R.id.empty_cart)
    TextView empty_cart;
    @BindView(R.id.company_name)
    TextView company_name;
    @BindView(R.id.total_amount)
    TextView total_amount;
    @BindView(R.id.offer_rec)
    RecyclerView offer_rec;
    @BindView(R.id.service_rec)
    RecyclerView service_rec;
    @BindView(R.id.continue_button)
    Button continue_btn;

    @OnClick(R.id.cancel)
    public void cancel() {
        finish();
    }

    CartModel cartModel;
    ServiceCartAdapter serviceAdapter;
    OfferCartAdapter offerAdapter;

    Float total = 0.0f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        cartModel = Common.cart;
        total = 0.0f;

        checkCartSize();
        if (cartModel.getServices().size() > 0) {

            serviceAdapter = new ServiceCartAdapter((ArrayList<ServicesOrder>) cartModel.getServices().clone());
            service_rec.setAdapter(serviceAdapter);

            for (ServicesOrder servicesOrder : cartModel.getServices()) {

                total += servicesOrder.getPrice();

                for (ExtrasOrder extras : servicesOrder.getExtrasOrder()) {
                    total += extras.getPrice() * extras.getQuantity();
                }
            }
        }

        if (cartModel.getOffers().size() > 0) {

            offerAdapter = new OfferCartAdapter((ArrayList<OfferOrder>) cartModel.getOffers().clone());
            offer_rec.setAdapter(offerAdapter);

            for (OfferOrder offerOrder : cartModel.getOffers()) {
                total += offerOrder.getPrice();
            }
        }

        total_amount.setText(String.format("%.3f", total) + " " + getString(R.string.kd));
        Common.cart.setTotal(total);

        enableSwipeToDeleteOffer();
        enableSwipeToDeleteService();

    }

    private void enableSwipeToDeleteOffer() {

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                offerAdapter.removeItem(position);
                total -= cartModel.getOffers().get(position).getPrice();

                // by reference so delete from this object and Common.cart
                cartModel.getOffers().remove(position);
                total_amount.setText(String.format("%.3f", total) + " " + getString(R.string.kd));
                Common.cart.setTotal(total);
                checkCartSize();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(offer_rec);
    }

    private void enableSwipeToDeleteService() {

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                serviceAdapter.removeItem(position);
                total -= cartModel.getServices().get(position).getPrice();
                for (ExtrasOrder extra : cartModel.getServices().get(position).getExtrasOrder()) {
                    total -= extra.getQuantity() * extra.getPrice();
                }

                // by reference so delete from this object and Common.cart
                cartModel.getServices().remove(position);
                total_amount.setText(String.format("%.3f", total) + " " + getString(R.string.kd));
                Common.cart.setTotal(total);
                checkCartSize();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(service_rec);
    }

    private void checkCartSize() {

        if (cartModel.getServices().size() == 0 && cartModel.getOffers().size() == 0) {
            empty_cart.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            continue_btn.setVisibility(View.GONE);

            // init new cart to new order
            Common.cart = new CartModel(-1, -1, -1, -1,
                    0.0f, new ArrayList<ServicesOrder>(), new ArrayList<OfferOrder>(), new ArrayList<PaymentMethod>());

        } else {

            empty_cart.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            continue_btn.setVisibility(View.VISIBLE);

            if (Common.isArabic)
                company_name.setText(cartModel.getCompany_name_ar());
            else
                company_name.setText(cartModel.getCompany_name_en());
        }

    }

    @OnClick(R.id.continue_button)
    public void continue_() {

        if (Paper.book("Monasabatcom").contains("currentUser"))
            startActivity(new Intent(this, AddressesActivity.class));
        else
            startActivity(new Intent(this, Login.class));
    }

}
