package com.vavisa.monasabatcom.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermsAndConditions extends AppCompatActivity {

    @BindView(R.id.terms)
    TextView terms;

    @OnClick(R.id.cancel)
    public void cancel() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        ButterKnife.bind(this);
        if(Common.isArabic)
            terms.setText(Common.cart.getTerms_ar());
        else
            terms.setText(Common.cart.getTerms_en());
    }
}
