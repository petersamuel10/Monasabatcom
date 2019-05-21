package com.vavisa.monasabatcom.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.PaymentPage;
import com.vavisa.monasabatcom.PaymentResult;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.adapter.Cart.OfferCartAdapter;
import com.vavisa.monasabatcom.adapter.Cart.ServiceCartAdapter;
import com.vavisa.monasabatcom.models.PaymentUrl;
import com.vavisa.monasabatcom.models.companyDetails.PaymentMethod;
import com.vavisa.monasabatcom.models.orderModels.CartModel;
import com.vavisa.monasabatcom.models.orderModels.ExtrasOrder;
import com.vavisa.monasabatcom.models.orderModels.OfferOrder;
import com.vavisa.monasabatcom.models.orderModels.OrderModel;
import com.vavisa.monasabatcom.models.orderModels.ServicesOrder;
import com.vavisa.monasabatcom.models.orderResult.AddOrderResult;
import com.vavisa.monasabatcom.models.orderResult.OfferOrderResult;
import com.vavisa.monasabatcom.models.orderResult.ServiceResultModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AppointmentDetails extends AppCompatActivity {

    @BindView(R.id.cancel)
    ImageView back;
    @BindView(R.id.com_name)
    TextView com_name_txt;
    @BindView(R.id.service_rec)
    RecyclerView services_rec;
    @BindView(R.id.offer_rec)
    RecyclerView offers_rec;
    @BindView(R.id.address_name)
    TextView address_name_txt;
    @BindView(R.id.address_phone)
    TextView address_phone_txt;
    @BindView(R.id.fullAddress)
    TextView address_details_txt;
    @BindView(R.id.payment_rg)
    RadioGroup payment_rg;
    @BindView(R.id.textView10)
    TextView payment_amount_tag;
    @BindView(R.id.payment_amount_rg)
    RadioGroup payment_amount_rg;
    @BindView(R.id.full_amount_rb)
    RadioButton full_amount_rb;
    @BindView(R.id.deposit_amount_rb)
    RadioButton deposite_amount_rb;
    @BindView(R.id.total_tag)
    TextView total_tag;
    @BindView(R.id.sub_total)
    TextView sub_total_txt;
    @BindView(R.id.delivery)
    TextView delivery_txt;
    @BindView(R.id.total_amount)
    TextView total_amount_txt;
    @BindView(R.id.terms)
    TextView terms_txt;

    @OnClick(R.id.cancel)
    public void back() {
        onBackPressed();
    }
    @OnClick(R.id.terms)
    public void terms() {
        startActivity(new Intent(this,TermsAndConditions.class));
    }

    private CartModel cartModel;
    private Float total_amount = 0.0f;
    private ServiceCartAdapter serviceAdapter;
    private OfferCartAdapter offerAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    ProgressDialog progressDialog;
    private int payment_method_id = -1, payment_amount_id = -1;
    float amount_rb = 0.0f, total_rb = 0.0f; // for deposit paid amount option

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        if(Common.isArabic)
            back.setRotation(180);

        cartModel = Common.cart;
        bindData();

        payment_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                payment_method_id = checkedId;
            }
        });

        payment_amount_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                payment_amount_id = checkedId;

                if (checkedId == R.id.deposit_amount_rb) {

                    amount_rb = 0.0f;

                    for (ServicesOrder service : cartModel.getServices()) {
                        if (service.getDepositPercentage() != 0) {
                            Float price = 0.0f;
                            price = service.getPrice();
                            for (ExtrasOrder extra : service.getExtrasOrder()) {
                                price += extra.getPrice() * extra.getQuantity();
                            }
                            amount_rb += (price * service.getDepositPercentage()) / 100;
                        }
                    }
                    for (OfferOrder offer : cartModel.getOffers()) {
                        if (offer.getDepositPercentage() != 0) {
                            amount_rb += (offer.getPrice() * offer.getDepositPercentage()) / 100;
                        }
                    }

                    total_rb = amount_rb + cartModel.getDeliveryCost();

                    total_tag.setText(getString(R.string.deposit) + ": ");
                    sub_total_txt.setText(String.format("%.3f", amount_rb) + getString(R.string.kd));
                    total_amount_txt.setText(String.format("%.3f", total_rb) + getString(R.string.kd));

                } else {
                    sub_total_txt.setText(String.format("%.3f", cartModel.getTotal()) + getString(R.string.kd));
                    total_tag.setText((R.string.sub_total));
                    total_amount_txt.setText(String.format("%.3f", total_amount) + getString(R.string.kd));
                }
            }
        });
    }

    private void bindData() {

        if (Common.isArabic) {
            com_name_txt.setText(cartModel.getCompany_name_ar());
            terms_txt.setText(getString(R.string.by_complete_this_order_i)+cartModel.getCompany_name_ar() );
        }else {
            com_name_txt.setText(cartModel.getCompany_name_en());
            terms_txt.setText(getString(R.string.by_complete_this_order_i)+cartModel.getCompany_name_en() );
        }

        if (cartModel.getServices().size() > 0) {
            serviceAdapter = new ServiceCartAdapter((ArrayList<ServicesOrder>) cartModel.getServices().clone());
            services_rec.setAdapter(serviceAdapter);
        }

        if (cartModel.getOffers().size() > 0) {
            offerAdapter = new OfferCartAdapter((ArrayList<OfferOrder>) cartModel.getOffers().clone());
            offers_rec.setAdapter(offerAdapter);
        }


        address_name_txt.setText(cartModel.getAddress().getName());
        address_phone_txt.setText(cartModel.getAddress().getPhone());
        if (Common.isArabic)
            address_details_txt.setText(cartModel.getAddress().getFullAddressAR());
        else
            address_details_txt.setText(cartModel.getAddress().getFullAddressEN());

        total_amount = cartModel.getTotal() + cartModel.getDeliveryCost();
        sub_total_txt.setText(String.format("%.3f", cartModel.getTotal()) + getString(R.string.kd));
        delivery_txt.setText(String.format("%.3f", cartModel.getDeliveryCost()) + getString(R.string.kd));
        total_amount_txt.setText(String.format("%.3f", total_amount) + getString(R.string.kd));

        paymentMethod();
    }

    private void paymentMethod() {
        for (PaymentMethod paymentMethod : Common.cart.getPaymentMethod()) {

            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(paymentMethod.getId());
            radioButton.setTextSize(16);
            if (Common.isArabic)
                radioButton.setText(paymentMethod.getNameAR());
            else
                radioButton.setText(paymentMethod.getNameEN());

            payment_rg.addView(radioButton);
        }

        checkPaymentMethod();
    }

    private void checkPaymentMethod() {

        // check if there is any changes with payment method after updated in adapter in Common cart

        RadioButton cash_rb = (RadioButton) payment_rg.getChildAt(0);

        Common.cart.setDeposit(false);
        Common.cart.setAdvance(false);
        for (ServicesOrder service : cartModel.getServices()) {
            if (service.getPayType() == 2)
                Common.cart.setDeposit(true);

            if (service.getPayType() == 3)
                Common.cart.setAdvance(true);
        }
        for (OfferOrder offer : cartModel.getOffers()) {
            if (offer.getPayType() == 2)
                Common.cart.setDeposit(true);

            if (offer.getPayType() == 3)
                Common.cart.setAdvance(true);
        }

        if (Common.cart.getDeposit()) {
            cash_rb.setVisibility(View.GONE);
            payment_amount_tag.setVisibility(View.VISIBLE);
            payment_amount_rg.setVisibility(View.VISIBLE);
        }

        if (Common.cart.getAdvance()) {
            cash_rb.setVisibility(View.GONE);
            payment_amount_tag.setVisibility(View.GONE);
            payment_amount_rg.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.make_appointment_btn)
    public void make_appointment() {
        if (payment_method_id == -1) {
            Common.errorAlert(this, getString(R.string.select_payment_method));
        } else {

            if (payment_amount_id == -1 && payment_amount_rg.getVisibility() == View.VISIBLE)
                Common.errorAlert(this, getString(R.string.select_payment_amount));
            else {

                OrderModel orderModel = new OrderModel(cartModel.getCompany_id(), Common.currentUser.getId(),
                        cartModel.getAddressId(), payment_method_id, cartModel.getDeliveryCost(),
                        cartModel.getServices(), cartModel.getOffers());

                Gson gson = new Gson();
                String result = gson.toJson(orderModel);
                Log.d("orderId",result);
                progressDialog.show();
                compositeDisposable.add(Common.getAPI().addOrder(orderModel)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<AddOrderResult>() {
                            @Override
                            public void accept(AddOrderResult result) throws Exception {

                                if (result.getOrderId() > 0) {
                                    if (payment_method_id == 1) {
                                        Intent intent = new Intent(AppointmentDetails.this, PaymentResult.class);
                                        intent.putExtra("payment_method", "cash");
                                        startActivity(intent);
                                        finish();
                                    }else
                                        getPaymentPageUrl(result.getOrderId());
                                } else if (result.getErrorId() == -1) {
                                    progressDialog.dismiss();
                                    Common.errorAlert(AppointmentDetails.this, getString(R.string.error_occure));
                                } else if (result.getErrorId() == -2) {
                                    progressDialog.dismiss();
                                    Common.errorAlert(AppointmentDetails.this, getString(R.string.missing_required_data));
                                } else if (result.getErrorId() == -3) {
                                    progressDialog.dismiss();
                                    Common.errorAlert(AppointmentDetails.this, getString(R.string.the_company_cant_deliver_to));
                                } else if (result.getErrorId() == -4) {
                                    progressDialog.dismiss();
                                    String message = getString(R.string.the_following_services_are_not_available);
                                    for (ServiceResultModel service : result.getNotAvailableServices()) {
                                        if (Common.isArabic)
                                            message += service.getServiceNameAR() + ",";
                                        else
                                            message += service.getServiceNameEN() + ",";
                                    }
                                    Common.errorAlert(AppointmentDetails.this, message);
                                } else if (result.getErrorId() == -5) {
                                    progressDialog.dismiss();
                                    String message = getString(R.string.the_following_offers_are_not_available);
                                    for (OfferOrderResult offer : result.getNotAvailableOffers()) {
                                        if (Common.isArabic)
                                            message += offer.getOfferNameAR() + ",";
                                        else
                                            message += offer.getServiceNameEN() + ",";
                                    }
                                    Common.errorAlert(AppointmentDetails.this, message);
                                }
                            }
                        }));
            }
        }
    }

    private void getPaymentPageUrl(final Integer integer) {

        Float paidAmount = 0.0f;

        if (payment_amount_id == R.id.deposit_amount_rb)
            paidAmount = total_rb;
        else
            paidAmount = total_amount;

        compositeDisposable.add(Common.getAPI().getPayment(integer, paidAmount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PaymentUrl>() {
                    @Override
                    public void accept(PaymentUrl paymentUrl) throws Exception {
                        progressDialog.dismiss();

                        Intent intent = new Intent(AppointmentDetails.this, PaymentPage.class);

                        intent.putExtra("url", paymentUrl.getUrl());
                        intent.putExtra("data", paymentUrl.getData().getData());

                        startActivity(intent);
                    }
                }));
    }

}
