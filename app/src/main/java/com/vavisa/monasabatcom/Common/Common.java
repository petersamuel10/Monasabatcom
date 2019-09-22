package com.vavisa.monasabatcom.Common;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.models.orderModels.CartModel;
import com.vavisa.monasabatcom.models.profile.Address;
import com.vavisa.monasabatcom.models.profile.User;
import com.vavisa.monasabatcom.webService.APIInterface;
import com.vavisa.monasabatcom.webService.Controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Common {

    public static User currentUser;
    public static boolean isArabic = false;
    public static FragmentActivity mActivity;
    public static Boolean isEditAddress = false;
    public static Address address;
    public static CartModel cart;
    public static int filter_position = 0;


    public static APIInterface getAPI() {
        return new Controller().getAPI();
    }

    public static Boolean isConnectToTheInternet(Context context) {

        boolean connected = false;
        final ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            connected = true;
        } else if (netInfo != null && netInfo.isConnected()
                && cm.getActiveNetworkInfo().isAvailable()) {
            connected = true;
        } else if (netInfo != null && netInfo.isConnected()) {
            try {
                URL url = new URL("http://www.google.com");
                HttpURLConnection urlc = (HttpURLConnection) url
                        .openConnection();
                urlc.setConnectTimeout(3000);
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    connected = true;
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (cm != null) {
           /* final NetworkInfo[] netInfoAll = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfoAll) {
                System.out.println("get network type :::" + ni.getTypeName());
                if ((ni.getTypeName().equalsIgnoreCase("WIFI") || ni
                        .getTypeName().equalsIgnoreCase("MOBILE"))
                        && ni.isConnected() && ni.isAvailable()) {
                    connected = true;
                    if (connected) {
                        break;
                    }
                }
            }*/
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected() && activeNetwork.isAvailable()) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    connected = true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
                    connected = true;
                }
            } else {
                // not connected to the internet
                connected = false;
            }
        }
        return connected;

    }

    public static void errorAlert(Context context, String message_str) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_error_alert);
        dialog.setCancelable(false);

        TextView message = dialog.findViewById(R.id.alert_message);
        TextView ok = dialog.findViewById(R.id.ok);

        message.setText(message_str);

        ok.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}



