package com.vavisa.monasabatcom;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.models.companyDetails.PaymentMethod;
import com.vavisa.monasabatcom.models.orderModels.CartModel;
import com.vavisa.monasabatcom.models.orderModels.OfferOrder;
import com.vavisa.monasabatcom.models.orderModels.ServicesOrder;

import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Welcome extends AppCompatActivity {

    Handler handler = new Handler();
    int duration = 1000;
    String path;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final ImageView splashImage = findViewById(R.id.splash);

        // init activity and cart when app start
        Common.cart = new CartModel(-1, -1, -1, -1,
                0.0f, new ArrayList<ServicesOrder>(), new ArrayList<OfferOrder>(), new ArrayList<PaymentMethod>());

        if(Common.isConnectToTheInternet(getBaseContext())) {
            compositeDisposable.add(Common.getAPI().getSplashScreen()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<JsonElement>() {
                        @Override
                        public void accept(JsonElement jsonElement) throws Exception {
                            String result = jsonElement.toString();

                            if(result.equals("Photo")){
                                JSONObject object =new JSONObject(result);

                                path = object.getString("Photo");
                                duration = object.getInt("DisplayDurationInSeconds")*1000+2000;

                                Picasso.with(Welcome.this).load(path).into(splashImage);

                                bgThread();
                            }else
                                bgThread();
                        }
                    }));
        }else
            Common.errorAlert(this,getString(R.string.error_connection));

    }

    /**
     * thread of some time interval delay for next screen launch
     */
    private void bgThread() {
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                getDataFromIntent();
            }
        }, duration);
    }

    /**
     * get data from intent
     */
    private void getDataFromIntent() {
        if (getIntent() != null) {
            Intent mainIntent = new Intent(Welcome.this, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplication().startActivity(mainIntent);
            finish();
        }
    }
}
