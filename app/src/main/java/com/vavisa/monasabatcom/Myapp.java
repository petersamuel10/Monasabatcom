package com.vavisa.monasabatcom;

import android.app.Application;
import android.content.res.Configuration;
import android.text.TextUtils;

import com.vavisa.monasabatcom.Common.Common;

import java.util.Locale;

import io.paperdb.Paper;

/**
 * Created by jesus on 11/26/2018.
 */

public class Myapp extends Application {

    Locale  myLocale;

    @Override
    public void onCreate() {
        super.onCreate();

        Paper.init(this);
        String lan = Paper.book("Monasabatcom").read("lan");
        if (!TextUtils.isEmpty(lan)) {
            lan = "en";
            Common.isArabic =false;
        }


      //  changeLang(lan);
    }

    public void changeLang(String lang)
    {
        // if (lang.equalsIgnoreCase(""))
        //    return;
        myLocale = new Locale("ar-rKW");
        // saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
       // Common.mActivity.getResources().updateConfiguration(config, Common.mActivity.getResources().getDisplayMetrics());
        getApplicationContext().getResources().updateConfiguration(config,getApplicationContext().getResources().getDisplayMetrics());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (myLocale != null){
            newConfig.locale = myLocale;
            Locale.setDefault(myLocale);
            Common.mActivity.getResources().updateConfiguration(newConfig, Common.mActivity.getResources().getDisplayMetrics());
        }
    }
}
