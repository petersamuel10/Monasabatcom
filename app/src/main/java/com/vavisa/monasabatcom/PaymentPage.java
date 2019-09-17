package com.vavisa.monasabatcom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vavisa.monasabatcom.Common.Common;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class PaymentPage extends AppCompatActivity {

    @BindView(R.id.wb_payment)
    WebView wb_payment;
    @BindView(R.id.back)
    ImageView back_arrow;

    @OnClick(R.id.back)
    public void back() {
        onBackPressed();
    }

    String url;
    String postData;

    String hf_ReferenceNo, hf_Result, hf_TransactionId, hf_PaymentId, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Common.isArabic) {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/Changa-Regular.ttf")
                    .setFontAttrId(R.attr.fontPath).build());
        } else {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/Avenir.otf")
                    .setFontAttrId(R.attr.fontPath).build());
        }
        setContentView(R.layout.activity_payment_page);
        ButterKnife.bind(this);
        if (Common.isArabic)
            back_arrow.setRotation(180);


        url = getIntent().getExtras().getString("url");
        postData = "Data=" + getIntent().getExtras().getString("data");


        loadPage();

    }

    private void loadPage() {

        wb_payment.getSettings().setJavaScriptEnabled(true);
        wb_payment.postUrl(url, postData.getBytes());
        ;

        wb_payment.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                wb_payment.evaluateJavascript("document.getElementById('hf_PaymentResult').value", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {

                        Log.i("xx6", value);
                        if (value.equals("\"1\""))
                            getHiddenField();
                        else if (value.equals("\"0\"")) {
                            Intent intent = new Intent(PaymentPage.this, PaymentResult.class);
                            intent.putExtra("payment_method", "knet");
                            intent.putExtra("paymentId", "error");
                            startActivity(intent);
                            finish();
                        }

                    }
                });

            }
        });

    }

    private void getHiddenField() {

        wb_payment.evaluateJavascript("document.getElementById('hf_ReferenceNo').value", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {

                Log.i("xx1", value);
                hf_ReferenceNo = value;
            }
        });
        wb_payment.evaluateJavascript("document.getElementById('hf_Result').value", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {

                Log.i("xx2", value);
                hf_Result = value.substring(1, value.length() - 1);

            }
        });
        wb_payment.evaluateJavascript("document.getElementById('hf_TransactionId').value", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {

                Log.i("xx3", value);
                hf_TransactionId = value.substring(1, value.length() - 1);
            }
        });
        wb_payment.evaluateJavascript("document.getElementById('hf_PaymentId').value", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {

                Log.i("xx4", value);
                //  paymentId = value.substring(1, value.length() - 1);
                hf_PaymentId = value.substring(1, value.length() - 1);
            }
        });

        wb_payment.evaluateJavascript("document.getElementById('hf_PostDate').value", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {

                Intent intent = new Intent(PaymentPage.this, PaymentResult.class);
                intent.putExtra("payment_method", "knet");
                intent.putExtra("result", hf_Result);
                intent.putExtra("paymentId", hf_PaymentId);
                intent.putExtra("transactionId", hf_TransactionId);
                intent.putExtra("referenceNo", hf_ReferenceNo);
                intent.putExtra("post_data", value.substring(1, value.length() - 1));
                // intent.putExtra("total", total);
                startActivity(intent);
                finish();

            }
        });
    }

}
