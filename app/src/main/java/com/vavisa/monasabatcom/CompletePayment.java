package com.vavisa.monasabatcom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vavisa.monasabatcom.Common.Common;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CompletePayment extends AppCompatActivity {

   // @BindView(R.id.wb_payment)
   // WebView wb_payment;
    @OnClick(R.id.continueBtn)
    public void goHome(){
        startActivity(new Intent(CompletePayment.this,MainActivity.class));
        finish();
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    String url;
    String postData;

    ProgressDialog progressDialog;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Common.isArabic)
        {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/Changa-Regular.ttf")
                    .setFontAttrId(R.attr.fontPath).build());
        }else
        {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/Avenir.otf")
                    .setFontAttrId(R.attr.fontPath).build());
        }
        setContentView(R.layout.activity_complete_payment);
        ButterKnife.bind(this);

     //   requestData();

    }

/*
    private void requestData() {

        progressDialog.show();

        compositeDisposable.add(Common.getPayment().getPayment(Common.orderId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<PaymentUrl>() {
            @Override
            public void accept(PaymentUrl paymentUrl) throws Exception {
                try{

                    url = paymentUrl.getUrl();
                    postData = "Data="+ URLEncoder.encode(paymentUrl.getData().getData(),"UTF-8");

                    WebSettings webSettings = wb_payment.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    progressDialog.dismiss();
                    wb_payment.postUrl(url,postData.getBytes());

                    wb_payment.evaluateJavascript("document.getElementById('hf_PaymentResult').value", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {

                        }
                    });

                }catch (Exception e){
                    Toast.makeText(CompletePayment.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }));


    }
*/

}
