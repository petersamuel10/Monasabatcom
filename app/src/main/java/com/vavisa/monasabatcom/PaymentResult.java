package com.vavisa.monasabatcom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.models.companyDetails.PaymentMethod;
import com.vavisa.monasabatcom.models.orderModels.CartModel;
import com.vavisa.monasabatcom.models.orderModels.OfferOrder;
import com.vavisa.monasabatcom.models.orderModels.ServicesOrder;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentResult extends AppCompatActivity {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.message)
    TextView message_txt;
    @BindView(R.id.pay_info)
    LinearLayout payment_info;
    @BindView(R.id.result)
    TextView result_txt;
    @BindView(R.id.payment)
    TextView payment_id_txt;
    @BindView(R.id.transaction)
    TextView transaction_txt;
    @BindView(R.id.reference)
    TextView reference_txt;
    @BindView(R.id.date)
    TextView date_txt;
    @BindView(R.id.amount)
    TextView amount_txt;

    @OnClick(R.id.continueBtn)
    public void continue_() {
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_result);
        ButterKnife.bind(this);

        Common.cart = new CartModel(-1, -1, -1, -1,
                0.0f, new ArrayList<ServicesOrder>(), new ArrayList<OfferOrder>(), new ArrayList<PaymentMethod>());

        if(getIntent().getExtras().getString("payment_method").equals("knet")){
            if(getIntent().getExtras().getString("paymentId").equals("error")){

                message_txt.setText(getString(R.string.payment_progress_faild));
                image.setImageDrawable(getDrawable(R.drawable.failed_payment));
                payment_info.setVisibility(View.GONE);

            }else {
                message_txt.setText(getString(R.string.message_payment));
                payment_info.setVisibility(View.VISIBLE);

                result_txt.setText(getIntent().getExtras().getString("result"));
                payment_id_txt.setText(getIntent().getExtras().getString("paymentId"));
                transaction_txt.setText(getIntent().getExtras().getString("transactionId"));
                reference_txt.setText(getIntent().getExtras().getString("referenceNo"));
                date_txt.setText(getIntent().getExtras().getString("post_data"));
                amount_txt.setText(getIntent().getExtras().getString("total"));
            }

        }else{
            message_txt.setText(getString(R.string.your_appointment_has_been_booked));
            payment_info.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {

    }
}
